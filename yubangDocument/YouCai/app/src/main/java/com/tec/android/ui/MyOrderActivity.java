package com.tec.android.ui;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.tec.android.R;
import com.tec.android.js.AccountInfoJs;
import com.tec.android.js.GoodsListjs;
import com.tec.android.network.GetFriendListHttp;
import com.tec.android.network.GetHistOrdersHttp;
import com.tec.android.network.ModifyOrderReqHttp;
import com.tec.android.utils.L;
import com.tec.android.utils.toastMgr;
import com.tec.android.view.CustomProgressDialog;

/**
 * 我的订单 历史订单 所有订单
 */
public class MyOrderActivity extends BaseActivity implements View.OnClickListener{

    private Context mContext;
    private static final String TAG = "MyFriendsActivity";
    private WebView my_orders_webview;
    private ImageView title_back;//返回

    private CustomProgressDialog mCustomProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_my_order);

        mContext = this;

        findViews();

        mCustomProgressDialog = new CustomProgressDialog(mContext);


        //还是需要一个广播, 在本页面上点击头部5个按钮的时候, 就发送广播  本页面接受广播, 然后重新加载
        IntentFilter intentFilter = new IntentFilter("myorder.reload");
        registerReceiver(myorderBroadcastReceiver, intentFilter);

        toGetHistoryOrderHtml("all");
    }

    /**
     * 广播接收器
     */
    private BroadcastReceiver myorderBroadcastReceiver =  new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String status = intent.getStringExtra("status");
            if(status.equals("ToCharge"))
            {
                //待支付的订单 就去请求网络  然后我的订单页面就重新加载一次
                toGetHistoryOrderHtml(status);

            }
            else if (status.equals("ToDeliver;ToReceive"))
            {
                //代签收的订单  就去请求网络  然后我的订单页面就重新加载一次
                toGetHistoryOrderHtml(status);
            }
            else if (status.equals("ToCancel;Cancelling"))
            {
                //退货的订单   就去请求网络  然后我的订单页面就重新加载一次
                toGetHistoryOrderHtml(status);
            }
            else if (status.equals("Finished"))
            {
                //完成的订单  就去请求网络  然后我的订单页面就重新加载一次
                toGetHistoryOrderHtml(status);
            }
            else if(status.equals("all"))
            {
                toGetHistoryOrderHtml(status);
            }
            else
            {
                //确认收货
                String orderid = intent.getStringExtra("orderid");
                toReceiveGoodsOrderHtml(orderid);
            }
        }
    };

    /**
     * 初始化控件
     */
    private void findViews()
    {
        my_orders_webview = (WebView) findViewById(R.id.my_orders_webview);
        title_back = (ImageView) findViewById(R.id.title_back);

        //设置监听器
        title_back.setOnClickListener(this);
    }

    /**
     * 拿到此用户的历史订单
     */
    private void toGetHistoryOrderHtml(String status) {

        mCustomProgressDialog = mCustomProgressDialog.show(mContext, "加载中...", false, null);

        //1. 先去网络请求, 先拿到goodslist的html String
        GetHistOrdersHttp getHistOrdersHttp = new GetHistOrdersHttp(mContext);
        //2. 把String 放进webview .loadData()
        //3. 加载html(注意加载的时候 css JS这些文件怎么办)
        //4. webview.loadurl 的时候 要放在handler里面  因为LoadGoodsListReq是异步的   所以要等待
        getHistOrdersHttp.sendAndGetHistOrdersHtml(
                new GetHistOrdersHttp.DoingSuccessCallback() {
                    @Override
                    public void onSuccess(String result) {
                        Message msg = new Message();
                        // TODO 暂时先这么定义 这是测试  测试完了再在Configs底下定义
                        msg.what = 1;
                        msg.obj = result;
                        handler.sendMessage(msg);
                    }
                },
                new GetHistOrdersHttp.DoingFailedCallback() {
                    @Override
                    public void onFail(String resultMsg) {
                        Message msg1 = new Message();
                        msg1.what = 100;
                        msg1.obj = resultMsg;
                        handler.sendMessage(msg1);
                        toastMgr.builder.display("网络错误 请检查您的网络", 0);
                    }
                }, status
        );
    }

    /**
     * 确认收货
     * @param orderid
     */
    private void toReceiveGoodsOrderHtml(String orderid) {
        mCustomProgressDialog = mCustomProgressDialog.show(mContext, "加载中...", false, null);
        ModifyOrderReqHttp modifyOrderReqHttp = new ModifyOrderReqHttp(mContext);
        modifyOrderReqHttp.sendAndModifyOrderReceive(new ModifyOrderReqHttp.DoingSuccessCallback() {
            @Override
            public void onSuccess(String result) {
                Message msg = new Message();
                // TODO 暂时先这么定义 这是测试  测试完了再在Configs底下定义
                //修改订单成功  确认收货成功
                msg.what = 2;
                msg.obj = result;
                handler.sendMessage(msg);
            }
        }, new ModifyOrderReqHttp.DoingFailedCallback() {
            @Override
            public void onFail(String resultMsg) {
                //修改订单失败  确认收货失败
                Message msg = new Message();
                // TODO 暂时先这么定义 这是测试  测试完了再在Configs底下定义
                msg.what = 102;
                msg.obj = resultMsg;
                handler.sendMessage(msg);
            }
        }, "receive", orderid);
    }




    /**
     * 此界面销毁
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myorderBroadcastReceiver);
    }

    /**
     * 点击事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            //用户点击返回
            case R.id.title_back:
                finish();
                break;
            default:
                break;
        }
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
            L.i("onPageFinished loadUrl call js");
            toastMgr.builder.display("onPageFinished loadUrl call js", 0);

        }

        @Override
        public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
            if (event.getKeyCode() == KeyEvent.KEYCODE_BACK)
                toastMgr.builder.display("back", 0);
            return true;
        }
    }


    /**
     * 历史订单的hanler 负责跟界面交互
     */
    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case 1:
                    String html = (String) msg.obj;
                    webviewSetting(html);
                    mCustomProgressDialog.dismiss();
                    break;
                case 100:
                    //webview上显示错误界面
                    mCustomProgressDialog.dismiss();
                    break;
                //修改订单失败  确认收货失败
                case 2:
                    mCustomProgressDialog.dismiss();
                    toGetHistoryOrderHtml("Finished");
                    toastMgr.builder.display("确认收货完成", 0);
                    break;
                case 102:
                    mCustomProgressDialog.dismiss();
                    toastMgr.builder.display("网络请求失败, 请检查您的网络", 0);
                    break;

                default:
                    break;
            }
        }
    };

    /**
     * 从服务器拿到数据成功, 通过handler调用webview界面设置
     * webview设置页面显示的html
     * @param html
     */
    private void webviewSetting(String html) {

        //my_orders_webview.loadData(html, "text/html;charset=UTF-8", null);
        my_orders_webview.loadDataWithBaseURL("", html, "text/html", "UTF-8", "");
        my_orders_webview.setWebChromeClient(new MyWebChromeClient());

        my_orders_webview.setWebViewClient(new MyWebViewClient());
        my_orders_webview.requestFocus();

        //设置支持js
        WebSettings webViewSettings = my_orders_webview.getSettings();
        webViewSettings.setJavaScriptEnabled(true);

        my_orders_webview.addJavascriptInterface(new AccountInfoJs(mContext), "callClient");
    }
}
