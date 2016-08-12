package com.tec.android.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.sales.vo.ModifyOrderResp;
import com.sales.vo.base.MSG_TYPES;
import com.sales.vo.base.OrderGoods;
import com.sales.vo.base.SalesMsgUtils;
import com.tec.android.R;
import com.tec.android.adapter.ShoppingcarGoodsDetailAdapter;
import com.tec.android.configs.Configs;
import com.tec.android.network.ConformOrderHttp;
import com.tec.android.utils.L;
import com.tec.android.utils.SPUtils;
import com.tec.android.utils.bean.GoodsInShoppingCarBean;
import com.tec.android.utils.bean.ShoppingcarSPBean;
import com.tec.android.utils.toastMgr;
import com.tec.android.view.AlertDialog;
import com.tec.android.view.CustomProgressDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * ShoppingCarActivity: 购物车
 *
 * @author andyzhu
 * @version 1.0
 * @created 2015-07-31
 */

public class ShoppingCarActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "ShoppingCarActivity";
    private Context mContext;
    private PullToRefreshListView shoppingcarPullToRefreshListView;
    private List<GoodsInShoppingCarBean> list;//从sp里面拿出来的购物车详情
    private List<OrderGoods> mOrderGoodsList;//请求订单id时候需要用到


    private ImageView title_back;
    private CheckBox shoppingcar_select_all_button_edit;//底部 全选的按钮
    private RelativeLayout cart_settle_accounts_layout;//底部去结算
    private Object shoppingCarInfo;
    private LinearLayout console_line_bottom;//底部导航栏

    private TextView shoppingcar_no_goods_textview;//购物车没有商品 文本框
    private TextView shoppingcar_goto_fisrt_page;//购物车没有商品  请去首页

    private TextView shoppingcar_goods_price_sum_textview;//购物车底部 商品总价

    private static int mCurrentGoodsNum;//当前购物车商品总量 多少件
    private static int mCurrentGoodsPriceSum;//当前购物车商品总价
    private int mSumPrice = 0;//多少钱
    private int mSumNum = 0;//多少件


    private static ShoppingcarGoodsDetailAdapter adapter;//购物车的适配器

    //等待进度条
    private CustomProgressDialog progressBarDialog;

    /**
     * cache folder path which be used when saving images
     **/
    public static final String DEFAULT_CACHE_FOLDER = new StringBuilder()
            .append(Environment.getExternalStorageDirectory()
                    .getAbsolutePath()).append(File.separator)
            .append("LifeShop").append(File.separator)
            .append("shoppingcar").append(File.separator)
            .append("ImageSDCardCache").toString();
    public static final String TAG_CACHE = "image_sdcard_cache";
    private String fromWhich = null;//记录从哪个页面跳过来的
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_shopping_car);
        mContext = this;
        mOrderGoodsList = new ArrayList<OrderGoods>();
        findViews();

        fromWhich = getIntent().getStringExtra("from");

        if (fromWhich != null)
        {
            if (fromWhich.equals("main"))
            {
                //从main跳过来的
                title_back.setVisibility(View.GONE);
            }
            else
            {
                //从商品详情跳过来的
                title_back.setVisibility(View.VISIBLE);
            }
        }

        progressBarDialog = new CustomProgressDialog(mContext);


        //注册一个broadcastReceiver  用来接收adapter里面发送的更改商品数量 价格的广播
        //ui再去修改数量
        IntentFilter filter = new IntentFilter("shoppingcar.modify_goods");
        registerReceiver(setGoodsSumPriceBroadcastReceiver, filter);


        // TODO 还是不行 还是要从数据库里面哪得
        //这里应该是ShoppingCarInfoBean
        loadShoppingcarItems();

        //判断是不是购物车里面的所有商品都选中了
        isAllGoodsInShoppingcarChoosed();
    }


    /**
     * 判断购物车里面的商品是不是全选了,
     * 如果全选了  就打钩, 并且显示总价
     * 如果没有全选, 不打钩, 显示当前选中的总价
     */
    private void isAllGoodsInShoppingcarChoosed() {
        String shoppingcarInfo = getShoppingCarInfo();
        if (shoppingcarInfo != null) {
            //购物车里面有商品
            //其实这些都可以抽象成一个单独的方法, 因为有好几个地方用到  抽出来作为方法, 更好
            int priceTotal = 0;
            boolean isAllChoosed = true;
            String goodsJson = SPUtils.getShoppingcarGoodsFromSP(mContext, Configs.SHOPPINGC_CAR_IN_SP, null);
            ShoppingcarSPBean shoppingcarGoods = new Gson().fromJson(goodsJson, ShoppingcarSPBean.class);
            List<GoodsInShoppingCarBean> goodsList = shoppingcarGoods.getGoodsInShoppingCarBeans();
            int length = goodsList.size();
            for (int i = 0; i < length; i++) {


                //如果有一个没有选中, 就代表没有全部选中
                if (goodsList.get(i).isSelected() == false) {
                    isAllChoosed = false;
                } else {
                    int itemGoodNum = goodsList.get(i).getNum();
                    //商品原价
                    int itemGoodPrice = goodsList.get(i).getLoadGoodsDetailResp().getDetailGoods().getOriginalprice();
                    priceTotal += itemGoodNum * itemGoodPrice;
                }
            }
            shoppingcar_goods_price_sum_textview.setText("¥" + priceTotal);
            //判断是不是全选了
            if (isAllChoosed == true)
                shoppingcar_select_all_button_edit.setChecked(true);
            else
                shoppingcar_select_all_button_edit.setChecked(false);
        } else {
            //购物里面没有商品 不做处理
        }
    }


    /**
     * 购物车加载用户添加在购物车里面的物品
     * 添加一个方法   让全选的时候也能去加载
     */
    private void loadShoppingcarItems() {
        String shoppingcarInfo = getShoppingCarInfo();
        if (shoppingcarInfo == null) {
            toastMgr.builder.display("您的购物车还没有商品, 去逛逛吧", 0);
//            Intent intent = new Intent();
//            intent.setClass(mContext, ShoppingNoGoodsActivity.class);
//            startActivity(intent);
            shoppingcar_no_goods_textview.setVisibility(View.VISIBLE);
            shoppingcar_goto_fisrt_page.setVisibility(View.VISIBLE);
            shoppingcarPullToRefreshListView.setVisibility(View.GONE);
            return;
        } else {
            shoppingcar_no_goods_textview.setVisibility(View.GONE);
            shoppingcar_goto_fisrt_page.setVisibility(View.GONE);
            shoppingcarPullToRefreshListView.setVisibility(View.VISIBLE);
            console_line_bottom.setVisibility(View.VISIBLE);
        }

        ShoppingcarSPBean shoppingcarSPBean = new Gson().fromJson(shoppingcarInfo, ShoppingcarSPBean.class);
        list = shoppingcarSPBean.getGoodsInShoppingCarBeans();


        shoppingcarPullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                toastMgr.builder.display("refresh", 0);
                shoppingcarPullToRefreshListView.onRefreshComplete();
            }
        });


        adapter = new ShoppingcarGoodsDetailAdapter(mContext, list, mOrderGoodsList);
        shoppingcarPullToRefreshListView.setAdapter(adapter);
    }


    /**
     * 修改了购物车选中商品的  广播
     */
    private BroadcastReceiver setGoodsSumPriceBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            progressBarDialog = progressBarDialog.show(mContext, "", false, null);
            //从intent里面拿出choosed, 来判断当前是选中还是非  从而设置全选按钮的状态
            boolean isItemChoosed = intent.getBooleanExtra("choosed", false);
//            if (isItemChoosed == true) {
//                shoppingcar_select_all_button_edit.setChecked(true);
//            } else {
//                shoppingcar_select_all_button_edit.setChecked(false);
//            }

            int priceTotal = 0;
            int selectCount = 0;
            //广播里面没有传递任何参数, 所以我就要从sp里面去遍历购物车商品
            String goodsJson = SPUtils.getShoppingcarGoodsFromSP(mContext, Configs.SHOPPINGC_CAR_IN_SP, null);
            ShoppingcarSPBean shoppingcarGoods = new Gson().fromJson(goodsJson, ShoppingcarSPBean.class);
            List<GoodsInShoppingCarBean> goodsList = shoppingcarGoods.getGoodsInShoppingCarBeans();
            int length = goodsList.size();
            for (int i = 0; i < length; i++) {
                if (goodsList.get(i).isSelected() == true) {
                    selectCount++;
                    int itemGoodNum = goodsList.get(i).getNum();
                    //商品原价
                    int itemGoodPrice = goodsList.get(i).getLoadGoodsDetailResp().getDetailGoods().getOriginalprice();
                    priceTotal += itemGoodNum * itemGoodPrice;
                }
            }
            if (selectCount == goodsList.size())
            {
                shoppingcar_select_all_button_edit.setChecked(true);
            }
            else
            {
                shoppingcar_select_all_button_edit.setChecked(false);
            }
            shoppingcar_goods_price_sum_textview.setText("¥" + priceTotal);
            progressBarDialog.dismiss();
        }
    };


    /**
     * 初始化布局对象
     */
    private void findViews() {

        //下拉刷新
        shoppingcarPullToRefreshListView = (PullToRefreshListView) findViewById(R.id.shoppingcar_pull_refresh_listView);

        title_back = (ImageView) findViewById(R.id.title_back);
        shoppingcar_select_all_button_edit = (CheckBox) findViewById(R.id.shoppingcar_select_all_button_edit);
        cart_settle_accounts_layout = (RelativeLayout) findViewById(R.id.cart_settle_accounts_layout);
        shoppingcarPullToRefreshListView = (PullToRefreshListView) findViewById(R.id.shoppingcar_pull_refresh_listView);
        shoppingcar_no_goods_textview = (TextView) findViewById(R.id.shoppingcar_no_goods_textview);
        shoppingcar_goto_fisrt_page = (TextView) findViewById(R.id.shoppingcar_goto_fisrt_page);

        console_line_bottom = (LinearLayout) findViewById(R.id.console_line_bottom);

        //购物车底部商品总价显示
        shoppingcar_goods_price_sum_textview = (TextView) findViewById(R.id.shoppingcar_goods_price_sum_textview);

        //set listener
        title_back.setOnClickListener(this);
        shoppingcar_select_all_button_edit.setOnClickListener(this);
        //去结算
        cart_settle_accounts_layout.setOnClickListener(this);
        shoppingcar_goto_fisrt_page.setOnClickListener(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(setGoodsSumPriceBroadcastReceiver);
    }

    /**
     * 拿到购物车信息  用户本地购物车是否有商品
     *
     * @return
     */
    public String getShoppingCarInfo() {
        String shoppingcarInfo = (String) SPUtils.getShoppingcarGoodsFromSP(mContext, Configs.SHOPPINGC_CAR_IN_SP, null);
        if (shoppingcarInfo == null || shoppingcarInfo.equals("{\"goodsInShoppingCarBeans\":[]}"))
            return null;
        else
            return shoppingcarInfo;
    }

    /**
     * 用户界面交互 点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //顶部返回
            case R.id.title_back:
                toastMgr.builder.display("返回", 0);
                Intent intent1 = new Intent();
                intent1.setClass(mContext, MainActivity.class);
                Bundle bundle1 = new Bundle();
                bundle1.putString("otherActivity", "SHOPPINGCAR");
                intent1.putExtras(bundle1);
                finish();
                break;
            //全选按钮
            case R.id.shoppingcar_select_all_button_edit:
                toastMgr.builder.display("全选", 0);
                chooseAllShppingcarGoods();
                break;
            //去结算按钮
            case R.id.cart_settle_accounts_layout:
                //TODO 逻辑应该是这样的  点击去结算的时候  先拿到id
                //先判断有没有登陆
                //如果如果已经登陆就执行去结算  缺人订单页面

                String isUserLogined = SPUtils.getUserInfo(mContext, Configs.IS_USER_LOGINED, null);

                //用户了登陆
                if (isUserLogined != null && isUserLogined.equals(Configs.LOGINED)) {
                    L.i("购物车---isUserLogined = " + isUserLogined);

                    boolean isAtLeastOneChoosed = isHavaGoodChoosed();
                    if (isAtLeastOneChoosed == false)
                    {
                        //没有一个商品是选中的 那你去支付个毛啊  你是测试吧
                        AlertDialog noGoodsChoosedAlertDialog = new AlertDialog(mContext);
                        noGoodsChoosedAlertDialog.builder()
                                .setTitle("提示")
                                .setMsg("您还没有意见商品选中哦, 请在购物车里面点击选中商品吧.")
                                .setPositiveButton("确定", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                    }
                                })
                                .setCancelable(false)//不能取消
                                .show();
                        return;
                    }
                    progressBarDialog = progressBarDialog.show(mContext, "加载中...", false, null);

                    //网络操作去拿到此订单的id  应该在一个进度条  去提示用户正在操作  稍等
                    ConformOrderHttp conformOrderHttp = new ConformOrderHttp(mContext);
                    conformOrderHttp.sendAndGetOrderID(mOrderGoodsList, new ConformOrderHttp.DoingSuccessCallback() {
                                @Override
                                public void onSuccess(String result) {
                                    L.i("conform order result = " + result);
                                    Message msg = new Message();
                                    msg.what = 1;
                                    msg.obj = result;

                                    toGetOrderIDHandler.sendMessage(msg);
                                }
                            }, new ConformOrderHttp.DoingFailedCallback() {
                                @Override
                                public void onFail(String resultMsg) {
                                    Message msg = new Message();
                                    msg.what = 100;
                                    msg.obj = resultMsg;
                                    toGetOrderIDHandler.sendMessage(msg);
                                }
                            }
                    );
                } else {
                    //如果没登陆  那就先去登陆界面
                    //登陆完了之后 还是要回来这个界面的
                    L.i("购物车---isUserLogined = " + isUserLogined);
                    Intent intent = new Intent();
                    intent.setClass(mContext, LoginActivity.class);
                    startActivity(intent);
                }

                break;
            //购物车没有商品, 点击去到商品列表首页
            case R.id.shoppingcar_goto_fisrt_page:
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("otherActivity", "SHOPPINGCAR_NO_GOODS");
                intent.setClass(mContext, MainActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            default:
                break;
        }
    }

    /**
     * 全选购物车所有的商品
     */
    private void chooseAllShppingcarGoods() {
        progressBarDialog = progressBarDialog.show(mContext, "", false, null);
        if (shoppingcar_select_all_button_edit.isChecked() == true) {
            int priceTotal = 0;
            String goodsJson = SPUtils.getShoppingcarGoodsFromSP(mContext, Configs.SHOPPINGC_CAR_IN_SP, null);
            ShoppingcarSPBean shoppingcarGoods = new Gson().fromJson(goodsJson, ShoppingcarSPBean.class);
            List<GoodsInShoppingCarBean> goodsList = shoppingcarGoods.getGoodsInShoppingCarBeans();
            int length = goodsList.size();
            for (int i = 0; i < length; i++) {
                goodsList.get(i).setSelected(true);
                int itemGoodNum = goodsList.get(i).getNum();
                //商品原价
                int itemGoodPrice = goodsList.get(i).getLoadGoodsDetailResp().getDetailGoods().getOriginalprice();
                priceTotal += itemGoodNum * itemGoodPrice;
            }
            //修改后的数量   换成json
            ShoppingcarSPBean shoppingcarSPBean = new ShoppingcarSPBean();
            shoppingcarSPBean.setGoodsInShoppingCarBeans(goodsList);
            String writeToSpJson = new Gson().toJson(shoppingcarSPBean);
            //讲修改后的值  也就是生成的json 保存在sp里面
            SPUtils.putShoppingcarToSp(mContext, Configs.SHOPPINGC_CAR_IN_SP, writeToSpJson);
            shoppingcar_goods_price_sum_textview.setText("¥" + priceTotal);
            mSumPrice = priceTotal;

//            adapter.notifyDataSetChanged();
            loadShoppingcarItems();
        }
        else
        {
            int priceTotal = 0;
            String goodsJson = SPUtils.getShoppingcarGoodsFromSP(mContext, Configs.SHOPPINGC_CAR_IN_SP, null);
            ShoppingcarSPBean shoppingcarGoods = new Gson().fromJson(goodsJson, ShoppingcarSPBean.class);
            List<GoodsInShoppingCarBean> goodsList = shoppingcarGoods.getGoodsInShoppingCarBeans();
            int length = goodsList.size();
            for (int i = 0; i < length; i++) {
                goodsList.get(i).setSelected(false);
                int itemGoodNum = goodsList.get(i).getNum();
                //商品原价
                int itemGoodPrice = goodsList.get(i).getLoadGoodsDetailResp().getDetailGoods().getOriginalprice();
                priceTotal += itemGoodNum * itemGoodPrice;
            }
            //修改后的数量   换成json
            ShoppingcarSPBean shoppingcarSPBean = new ShoppingcarSPBean();
            shoppingcarSPBean.setGoodsInShoppingCarBeans(goodsList);
            String writeToSpJson = new Gson().toJson(shoppingcarSPBean);
            //讲修改后的值  也就是生成的json 保存在sp里面
            SPUtils.putShoppingcarToSp(mContext, Configs.SHOPPINGC_CAR_IN_SP, writeToSpJson);
            shoppingcar_goods_price_sum_textview.setText("¥" + 0.0);
//            adapter.notifyDataSetChanged();
            loadShoppingcarItems();
        }


        progressBarDialog.dismiss();

    }

    /**
     * 去结算
     *
     * @param orderid
     */
    private void carSettleAccount(String orderid) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("orderid", orderid);
        intent.setClass(mContext, ConformOrderActivity.class);
        bundle.putInt("price", mSumPrice);
        intent.putExtras(bundle);
        startActivity(intent);
    }


    /**
     * 一个handler 从网络获取orderid  然后执行这个
     */
    private Handler toGetOrderIDHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //成功 拿到订单id
            if (msg.what == 1) {
                String resultJson = (String) msg.obj;
                ModifyOrderResp modifyOrderResp = (ModifyOrderResp) SalesMsgUtils.fromJson(resultJson, MSG_TYPES.MSG_MODIFY_ORDER, false);
                String orderid = modifyOrderResp.getOrderId();
                progressBarDialog.dismiss();
                //去结算函数
                carSettleAccount(orderid);
            }
            //失败
            else if (msg.what == 100) {
                progressBarDialog.dismiss();
                //失败  这里去相应的操作
                carSettleAccount("100");
            }
        }
    };


    /**
     *
     */
    public static void notifyActivtyListChanged() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * 判断用户购物车里面是否选了商品
     *
     * @return true 有商品选中, false 没有商品选中
     */
    private boolean isHavaGoodChoosed()
    {
        String shoppingcarInfo = getShoppingCarInfo();
        if (shoppingcarInfo != null) {
            //购物车里面有商品
            //其实这些都可以抽象成一个单独的方法, 因为有好几个地方用到  抽出来作为方法, 更好
            int priceTotal = 0;
            boolean isHasAtLeastOneChoosed = false;//是不是至少有一个选中了
            String goodsJson = SPUtils.getShoppingcarGoodsFromSP(mContext, Configs.SHOPPINGC_CAR_IN_SP, null);
            ShoppingcarSPBean shoppingcarGoods = new Gson().fromJson(goodsJson, ShoppingcarSPBean.class);
            List<GoodsInShoppingCarBean> goodsList = shoppingcarGoods.getGoodsInShoppingCarBeans();
            int length = goodsList.size();
            for (int i = 0; i < length; i++) {
                //只要有一个商品选中就可以去结算了
                if (goodsList.get(i).isSelected() == true) {
                    isHasAtLeastOneChoosed = true;
                    return true;
                }
            }

        }
        return false;
    }
}



