package com.tec.android.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sales.vo.LoadGoodsDetailResp;
import com.sales.vo.base.MSG_TYPES;
import com.sales.vo.base.SalesMsgUtils;
import com.tec.android.configs.Configs;
import com.tec.android.utils.bean.GoodsInShoppingCarBean;
import com.tec.android.utils.bean.ShoppingcarSPBean;
import com.tec.android.utils.toastMgr;
import com.tec.android.R;
import com.tec.android.js.GoodsListjs;
import com.tec.android.network.LoadGoodsDetailHttp;
import com.tec.android.utils.L;
import com.tec.android.utils.SPUtils;
import com.tec.android.view.CustomProgressDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * GoodsDetailActivity: 商品详情页面 从商品列表页面来, 商品列表点击某一个item, 调用到这个页面   单独的一个activity
 *
 * @author andy
 * @version 1.0
 * @created 2015-08-3
 */

public class GoodsDetailActivity extends BaseActivity implements View.OnClickListener{

    private static final String TAG = "GoodsDetailActivity";
    private Context mContext;
    private ImageView title_back;//顶部返回
    private WebView goods_detail_webview;//浏览器
    private LinearLayout goods_detail_share_layout;//分享

    private RelativeLayout goods_detail_goto_shoppingcar_layout;//点击进入购物车

    private TextView goods_detail_add2shoppingcar;//点击添加到购物车

    private TextView goods_detail_shoppingcar_number;//购物车里面的数量


    private int mCurrentGoodsNum = 0;//当前购物车商品数量
    private int mCurrentGoodsMoneySum = 0;//当前购物车商品总价

    //
    private String mGoodsDetailJson;
    private String mGoodsID;

    private CustomProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_goods_detail);
        mContext = this;

        findViews();
        String goodId = (String) getIntent().getExtras().get("goodId");
        mGoodsID = goodId;

        toGetAndLoadGoodsDetailHtmlAndJsonByGoodID(mGoodsID);

        countGoodsNumInShoppingcar();


    }

    /**
     * 通过商品ID 拿到商品详情的页面和商品详情的json
     * @param mGoodsID 商品ID 从上一个页面传过来的
     */
    private void toGetAndLoadGoodsDetailHtmlAndJsonByGoodID(String mGoodsID) {

        //这里要请求网络, 就要有进度显示
        mProgressDialog = new CustomProgressDialog(mContext);
        mProgressDialog = mProgressDialog.show(mContext, "加载中...", false, null);


        //1. 先去网络请求, 先拿到goodslist的html String
        LoadGoodsDetailHttp loadGoodsDetailHttp = new LoadGoodsDetailHttp(mContext);
        //2. 把String 放进webview .loadData()
        //3. 加载html(注意加载的时候 css JS这些文件怎么办)
        //4. webview.loadurl 的时候 要放在handler里面  因为LoadGoodsListReq是异步的   所以要等待
        loadGoodsDetailHttp.sendAndRecv(mGoodsID, new LoadGoodsDetailHttp.DoingSuccessCallback() {
                    @Override
                    public void onSuccess(String result) {
                        Message msg = new Message();
                        // TODO 暂时先这么定义 这是测试  测试完了再在Configs底下定义
                        msg.what = 1;
                        msg.obj = result;
                        goodDetailGetDataHandler.sendMessage(msg);
                    }
                },
                new LoadGoodsDetailHttp.DoingFailedCallback() {
                    @Override
                    public void onFail(String resultMsg) {
                    }
                }
        );
        //拿到商品详情的json
        loadGoodsDetailHttp.sendAndGetGoodsDetailJson(mGoodsID, new LoadGoodsDetailHttp.DoingSuccessCallback() {
                    @Override
                    public void onSuccess(String result) {
                        Message msg = new Message();
                        msg.what = 2;
                        msg.obj = result;
                        goodDetailGetDataHandler.sendMessage(msg);
                    }
                },
                new LoadGoodsDetailHttp.DoingFailedCallback() {
                    @Override
                    public void onFail(String resultMsg) {

                    }
                }
        );
    }

    /**
     * 初始化控件
     */
    private void findViews() {
        goods_detail_webview = (WebView) findViewById(R.id.goods_detail_webview);
        goods_detail_share_layout = (LinearLayout) findViewById(R.id.goods_detail_share_layout);
        goods_detail_goto_shoppingcar_layout = (RelativeLayout) findViewById(R.id.goods_detail_goto_shoppingcar_layout);
        goods_detail_add2shoppingcar = (TextView) findViewById(R.id.goods_detail_add2shoppingcar);
        title_back = (ImageView) findViewById(R.id.title_back);

        // TODO  要写进sharedPreference里面
        goods_detail_shoppingcar_number = (TextView) findViewById(R.id.goods_detail_shoppingcar_number);

        //listener
        goods_detail_share_layout.setOnClickListener(this);
        goods_detail_goto_shoppingcar_layout.setOnClickListener(this);
        goods_detail_add2shoppingcar.setOnClickListener(this);
        title_back.setOnClickListener(this);

    }

    /**
     * 浏览器设置
     * @param html
     */
    private void webviewSetting(String html) {

        goods_detail_webview.loadData(html, "text/html;charset=UTF-8",null);
        //goods_list_webview.loadDataWithBaseURL("http://172.25.217.2:8080/WebRoot1/", html, "text/html", "HTF-8",null);

        goods_detail_webview.setWebChromeClient(new MyWebChromeClient());

        goods_detail_webview.setWebViewClient(new MyWebViewClient());

        //设置支持js
        WebSettings webViewSettings = goods_detail_webview.getSettings();
        webViewSettings.setJavaScriptEnabled(true);

        goods_detail_webview.addJavascriptInterface(new GoodsListjs(mContext), "callClient");

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    /**
     * 点击事件
     * @param v
     */
    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            //分享
            case R.id.goods_detail_share_layout:
                shareGoods();
                toastMgr.builder.display("商品分享", 0);
                break;
            //去购物车页面
            case R.id.goods_detail_goto_shoppingcar_layout:
                //根据我的业务逻辑 去购物车界面要先判断本地购物车里面是否有商品

                gotoShoppingcar();
                toastMgr.builder.display("去购物车查看", 0);
                break;
            // 加入到购物车
            case R.id.goods_detail_add2shoppingcar:
                toastMgr.builder.display("加入购物车", 0);
                add2shoppingcar();
                break;
            case R.id.title_back:
                finish();
            default:
                break;
        }

    }

    /**
     * 分享这个商品  必须得到商品的地址
     */
    private void shareGoods() {

        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("goodid", mGoodsID);
        intent.putExtras(bundle);
        intent.setClass(mContext, SocialShareActivity.class);
        startActivity(intent);

    }

    /**
     * 点击按钮  去到购物车界面
     */
    private void gotoShoppingcar() {
        Intent intent = new Intent();
        intent.setClass(mContext, ShoppingCarActivity.class);
        intent.putExtra("from","gooddetail");
        startActivity(intent);
    }

    /**
     * 判断购物车里面一共多少个商品了
     */
    private void countGoodsNumInShoppingcar()
    {
        String shoppingcarInfo = getShoppingCarInfo();
        if (shoppingcarInfo != null) {
            //购物车里面有商品

            int numCount = 0;
            //设置显示
            goods_detail_shoppingcar_number.setVisibility(View.VISIBLE);

            String goodsJson = SPUtils.getShoppingcarGoodsFromSP(mContext, Configs.SHOPPINGC_CAR_IN_SP, null);
            ShoppingcarSPBean shoppingcarGoods = new Gson().fromJson(goodsJson, ShoppingcarSPBean.class);
            List<GoodsInShoppingCarBean> goodsList = shoppingcarGoods.getGoodsInShoppingCarBeans();
            int length = goodsList.size();
            for (int i = 0; i < length; i++)
            {
                //取得所有商品的总数
                numCount += goodsList.get(i).getNum();
            }
            goods_detail_shoppingcar_number.setText(numCount + "");
        }
        else
        {
            //购物车里面没有商品
            goods_detail_shoppingcar_number.setVisibility(View.GONE);
        }
    }

    /**
     * 拿到购物车信息  用户本地购物车是否有商品
     *
     * @return
     */
    private String getShoppingCarInfo() {
        String shoppingcarInfo = (String) SPUtils.getShoppingcarGoodsFromSP(mContext, Configs.SHOPPINGC_CAR_IN_SP, null);
        if (shoppingcarInfo == null || shoppingcarInfo.equals("{\"goodsInShoppingCarBeans\":[]}"))
            return null;
        else
            return shoppingcarInfo;
    }




    /**
     * 点击加入购物车   把商品添加到购物车
     */
    private void add2shoppingcar() {

        //TODO 仍然要考虑  如何放  因为这样放 只是放一个  下次放就覆盖了
        /**
         * 1.已经拿到mGoodsDetailJson 生成LoadGoodsDetailResp
         * 2.List OrderGoods <---
         */
        String  goodsId;
        LoadGoodsDetailResp loadGoodsDetailResp;
        if (mGoodsDetailJson == null || mGoodsDetailJson.equals("")) {
            //购物车没有商品
            return;
        }
        else
        {
            loadGoodsDetailResp = (LoadGoodsDetailResp) SalesMsgUtils.fromJson(mGoodsDetailJson, MSG_TYPES.MSG_LOAD_GOODS_DETAIL, false);
            goodsId = loadGoodsDetailResp.getDetailGoods().getGoodsid();
        }

        //先从SP里面拿出来  判断有没有这个id  有这个id  直接修改
        String shoppingcarSPString = SPUtils.getShoppingcarGoodsFromSP(mContext, Configs.SHOPPINGC_CAR_IN_SP, null);
        if (shoppingcarSPString == null || shoppingcarSPString.equals("[]"))
            shoppingcarSPString = null;
        if (shoppingcarSPString == null)
        {
            //购物车里面一个商品都没有
            //把这个商品生成一个对象  把这个对象变成json   把json放在SP里面
            ShoppingcarSPBean shoppingcarSPBean = new ShoppingcarSPBean();
            List<GoodsInShoppingCarBean> list = new ArrayList<GoodsInShoppingCarBean>();
            GoodsInShoppingCarBean goodsInShoppingCarBean = new GoodsInShoppingCarBean();
            //LoadGoodsDetailResp loadGoodsDetailResp = (LoadGoodsDetailResp) SalesMsgUtils.fromJson(mGoodsDetailJson, MSG_TYPES.MSG_LOAD_GOODS_DETAIL,false);
            goodsInShoppingCarBean.setLoadGoodsDetailResp(loadGoodsDetailResp);
            goodsInShoppingCarBean.setNum(1);
            list.add(goodsInShoppingCarBean);
            shoppingcarSPBean.setGoodsInShoppingCarBeans(list);

            String writeSpJson = new Gson().toJson(shoppingcarSPBean);
            SPUtils.putShoppingcarToSp(mContext,Configs.SHOPPINGC_CAR_IN_SP, writeSpJson);

        }
        else
        {
            //购物车里面当前有物品在里面
            //拿出来, 转化成对象, 取出里面的所有商品, 判断id是否在里面
            //如果本商品的id在里面, 那就更新这个id所对应的商品的数量
            //更新了数量之后, 重新写到SP里面保存
            ShoppingcarSPBean shoppingcarSPBean = new Gson().fromJson(shoppingcarSPString, ShoppingcarSPBean.class);
            List<GoodsInShoppingCarBean> list = shoppingcarSPBean.getGoodsInShoppingCarBeans();
            boolean isThisIDExits = false;
            for (int i = 0; i < list.size(); i++)
            {
                if (list.get(i).getLoadGoodsDetailResp().getDetailGoods().getGoodsid().equals(goodsId))
                {
                    isThisIDExits = true;
                    int num = list.get(i).getNum();
                    num = num + 1;
                    list.get(i).setNum(num);
                    shoppingcarSPBean.setGoodsInShoppingCarBeans(list);
                    break;
                }
            }

            if (isThisIDExits == false)
            {

                GoodsInShoppingCarBean goodsInShoppingCarBean = new GoodsInShoppingCarBean();
                //LoadGoodsDetailResp loadGoodsDetailResp = (LoadGoodsDetailResp) SalesMsgUtils.fromJson(mGoodsDetailJson, MSG_TYPES.MSG_LOAD_GOODS_DETAIL,false);
                goodsInShoppingCarBean.setLoadGoodsDetailResp(loadGoodsDetailResp);
                goodsInShoppingCarBean.setNum(1);
                list.add(goodsInShoppingCarBean);
                shoppingcarSPBean.setGoodsInShoppingCarBeans(list);
            }

            String writeSpJson = new Gson().toJson(shoppingcarSPBean);
            SPUtils.putShoppingcarToSp(mContext, Configs.SHOPPINGC_CAR_IN_SP, writeSpJson);


        }
        countGoodsNumInShoppingcar();
        //给主界面发送广播, 改变购物车数量显示
        Intent sender = new Intent("main.count.goods.num");
        mContext.sendBroadcast(sender);

        L.i("商品详情,点击加入购物车,商品详情json = " + mGoodsDetailJson);

    }


    /**
     * 辅助Webview的类
     * 当webview里有JsAlert JsConfirm  加载信息的时候  回调一些信息
     * 与Native进行一些交互
     * 这个可以考虑一下   封装成一个类 // TODO
     *
     */
    class MyWebChromeClient extends WebChromeClient
    {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
        }

        @Override
        public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
            //用Android组件替换
            new AlertDialog.Builder(mContext)
                    .setTitle("JS提示")
                    .setMessage(message)
                    .setPositiveButton(android.R.string.ok, new AlertDialog.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            result.confirm();
                        }
                    })
                    .setCancelable(false)
                    .create().show();
            return true;
        }

        @Override
        public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {

            //用Android组件替换
            new AlertDialog.Builder(mContext)
                    .setTitle("JS提示")
                    .setMessage(message)
                    .setPositiveButton(android.R.string.ok, new AlertDialog.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            result.confirm();
                        }
                    })
                    .setCancelable(false)
                    .create().show();

            return true;
        }
    }

    /**
     * 也是webview的帮助类   辅助webview来完成一些事件  动作
     * 比如加载完成   加载出错等等
     */
    class MyWebViewClient extends WebViewClient
    {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            L.i(TAG, "shouldOverrideUrlLoading, url = " + url);
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            // 调用webview (服务器界面上的JS)
        }
    }


    /**
     * 去更新界面
     */
    private Handler goodDetailGetDataHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1)
            {
                String result = (String) msg.obj;
                webviewSetting(result);
                mProgressDialog.dismiss();
            }
            else if (msg.what == 2)
            {
                //网络出错
                mGoodsDetailJson = (String) msg.obj;
                mProgressDialog.dismiss();

            }
        }
    };

}
