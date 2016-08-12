package com.tec.android.ui;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.tec.android.configs.Configs;
import com.tec.android.js.AccountInfoJs;
import com.tec.android.js.GoodsListjs;
import com.tec.android.network.ConformOrderHttp;
import com.tec.android.utils.L;
import com.tec.android.utils.SPUtils;
import com.tec.android.utils.bean.GoodsInShoppingCarBean;
import com.tec.android.utils.bean.ShoppingcarSPBean;
import com.tec.android.utils.toastMgr;

import com.tec.android.R;
import com.tec.android.view.CustomProgressDialog;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

public class ConformOrderActivity extends BaseActivity implements View.OnClickListener {

    private Context mContext;
    private static final String TAG = "ConformOrderActivity";
    //wenview
    private WebView conform_order_webview;


    private ImageView title_back;//返回

    private RelativeLayout conform_order_btn_layout;//确认订单
    private TextView conform_order_order_id_textview;//订单号
    private ImageView order_delete_imageview;//删除订单
    private TextView titleText;//标题
    private TextView cart_count_price_tv3;//商品总价
    private TextView cart_count_price_tv1;//商品多少件




    private String mOrderId;


    private CustomProgressDialog mProgressDialog;//当前进度对话框



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_conform_order);
        mContext =this;
        findViews();

        //拿到传来的订单id
        Bundle bundle = getIntent().getExtras();


        mOrderId = bundle.getString("orderid", null);
        L.i("ConformOrderActivity ORDERID = " +  mOrderId);
        //拿到订单的状态  如果是待支付  就在顶部显示待支付
        String status = bundle.getString("status", null);
        if (status == null)
        {
            titleText.setText("确认订单");
            int priceTotal = 0;
            int num = 0;
            String goodsJson = SPUtils.getShoppingcarGoodsFromSP(mContext, Configs.SHOPPINGC_CAR_IN_SP, null);
            ShoppingcarSPBean shoppingcarGoods = new Gson().fromJson(goodsJson, ShoppingcarSPBean.class);
            List<GoodsInShoppingCarBean> goodsList = shoppingcarGoods.getGoodsInShoppingCarBeans();
            int length = goodsList.size();
            for (int i = 0; i < length; i++) {
                if (goodsList.get(i).isSelected() == true)
                {

                    int itemGoodNum = goodsList.get(i).getNum();
                    num += itemGoodNum;
                    //商品原价
                    int itemGoodPrice = goodsList.get(i).getLoadGoodsDetailResp().getDetailGoods().getOriginalprice();
                    priceTotal += itemGoodNum * itemGoodPrice;
                }
            }

            cart_count_price_tv3.setText("¥" + priceTotal);
            cart_count_price_tv1.setText(""+num);
            order_delete_imageview.setVisibility(View.GONE);
        }
        else
        {
            if (status.equals("待支付"))
            {
                titleText.setText("待支付");
                order_delete_imageview.setVisibility(View.VISIBLE);
            }
            else
            {
                titleText.setText("确认订单");
                order_delete_imageview.setVisibility(View.GONE);
            }
        }




        conform_order_order_id_textview.setText("订单号: " + mOrderId);
        //请求网络, 拿到订单详情, 调用webview去加载
        requestNetGetHtmlToLoad(mOrderId);

        //去检查有没有收货人
        //应该新建一类  去检查有没有收货人

        //当点击修改地址的时候, 我把选中的地址发送给后台, 然后再返回这个页面, 请求后台重新加载html
        //TODO 我认为  后台是返回选中的那个地址显示的
        //所以这里要有一个广播  这个广播的作用就是  改了地址以后, 请求后台 重新加载界面
        IntentFilter intentFilter =  new IntentFilter("change.address.reload.html");
        registerReceiver(changeAddressAndReloadBroadcastReceiver, intentFilter);

    }

    /**
     * 要注册的广播, 用来表示修改地址之后的重新加载界面的事件
     */
    private BroadcastReceiver changeAddressAndReloadBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //orderid 是一个全局变量 只要不回收,  应该一直保持原来的值 所以这里用 应该没问题
            requestNetGetHtmlToLoad(mOrderId);
        }
    };

    /**
     * 根据订单id 拿到要显示的订单页面的html
     * @param mOrderId
     */
    private void requestNetGetHtmlToLoad(String mOrderId) {
        mProgressDialog = new CustomProgressDialog(mContext);
        mProgressDialog = mProgressDialog.show(mContext, "加载中...", false, null);
        //1. 先去网络请求, 先拿到goodslist的html String
        ConformOrderHttp conformOrderHttp = new ConformOrderHttp(mContext);
        //2. 把String 放进webview .loadData()
        //3. 加载html(注意加载的时候 css JS这些文件怎么办)
        //4. webview.loadurl 的时候 要放在handler里面  因为LoadGoodsListReq是异步的   所以要等待
        conformOrderHttp.sendAndGetOrderHtml(mOrderId, new ConformOrderHttp.DoingSuccessCallback() {
                    @Override
                    public void onSuccess(String result) {
                        Message msg = new Message();
                        msg.what = 1;
                        msg.obj = result;
                        toGetOrderDetailHandler.sendMessage(msg);
                        L.i(TAG, "订单详情成功" + result);
                    }
                }, new ConformOrderHttp.DoingFailedCallback() {
                    @Override
                    public void onFail(String resultMsg) {
                        Message msg = new Message();
                        msg.what = 100;
                        msg.obj = resultMsg;
                        toGetOrderDetailHandler.sendMessage(msg);
                        L.i(TAG, "订单详情失败" + resultMsg);
                    }
                }
        );
    }

    /**
     * 初始化布局中对象
     */
    private void findViews() {

        conform_order_webview = (WebView) findViewById(R.id.conform_order_webview);

        title_back = (ImageView) findViewById(R.id.title_back);

        order_delete_imageview = (ImageView) findViewById(R.id.order_delete_imageview);

        titleText = (TextView) findViewById(R.id.titleText);

        cart_count_price_tv3 = (TextView) findViewById(R.id.cart_count_price_tv3);//商品总价
        cart_count_price_tv1 = (TextView) findViewById(R.id.cart_count_price_tv1);//商品总多少件


//        textview_receiver_name_content = (TextView) findViewById(R.id.textview_receiver_name_content);
//        textview_receiver_mobile_content = (TextView) findViewById(R.id.textview_receiver_mobile_content);
//        textview_receiver_address_content = (TextView) findViewById(R.id.textview_receiver_address_content);
//        layout_receiver_empty = (RelativeLayout) findViewById(R.id.layout_receiver_empty);
//        textview_receiver_empty_user = (TextView) findViewById(R.id.textview_receiver_empty_user);
//        layout_receiver_info = (RelativeLayout) findViewById(R.id.layout_receiver_info);

        conform_order_btn_layout = (RelativeLayout) findViewById(R.id.conform_order_btn_layout);
        conform_order_order_id_textview = (TextView) findViewById(R.id.conform_order_order_id_textview);
        //监听器
        title_back.setOnClickListener(this);
//        layout_receiver_empty.setOnClickListener(this);
        conform_order_btn_layout.setOnClickListener(this);
        conform_order_order_id_textview.setOnClickListener(this);
    }


    /**
     * webview的设置
     * @param html
     */
    private void webviewSetting(String html) {

        //conform_order_webview.loadData(html, "text/html;charset=UTF-8",null);
        conform_order_webview.loadDataWithBaseURL("", html, "text/html", "UTF-8","");

        conform_order_webview.setWebChromeClient(new MyWebChromeClient());

        conform_order_webview.setWebViewClient(new MyWebViewClient());

        //设置支持js
        WebSettings webViewSettings = conform_order_webview.getSettings();
        webViewSettings.setJavaScriptEnabled(true);

        conform_order_webview.addJavascriptInterface(new AccountInfoJs(mContext), "callClient");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消注册的广播
        unregisterReceiver(changeAddressAndReloadBroadcastReceiver);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.title_back:
                toastMgr.builder.display("返回", 0);
                finish();
                break;
            //TODO 改成js交互
//            case R.id.layout_receiver_empty:
//                toastMgr.builder.display("新建收货人", 0);
//                Intent intent = new Intent();
//                intent.setClass(mContext,ReceivingAddressListActivity.class);
//                startActivity(intent);
//                break;
            case R.id.conform_order_btn_layout:
                toastMgr.builder.display("确认订单 前去支付", 0);
                Intent intent = new Intent();
                intent.setClass(mContext, ChoosePaymentMethodActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }

    }


    private Handler toGetOrderDetailHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1)
            {
                //成功   还是没进度条提示
                mProgressDialog.dismiss();
                String result = (String) msg.obj;
                webviewSetting(result);

            }
            else  if (msg.what == 100)
            {
                //失败
                mProgressDialog.dismiss();
            }
        }
    };




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
            conform_order_webview.loadUrl("javascript:wave()");
        }
    }
}
