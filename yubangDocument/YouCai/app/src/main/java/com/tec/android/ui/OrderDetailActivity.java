package com.tec.android.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
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
import com.tec.android.network.ConformOrderHttp;
import com.tec.android.utils.L;
import com.tec.android.utils.toastMgr;
import com.tec.android.view.CustomProgressDialog;

public class OrderDetailActivity extends AppCompatActivity {

    private Context mContext;
    private static final String TAG = "OrderDetailActivity";
    private WebView order_detail_webview;
    private ImageView title_back;//返回
    private CustomProgressDialog mProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_order_detail);
        mContext = this;
        findViews();

        mProgressDialog = new CustomProgressDialog(mContext);
        String orderid = getIntent().getStringExtra("orderid");

        requestNetGetHtmlToLoad(orderid);


    }

    /**
     * 初始化控件
     */
    private void findViews()
    {
        order_detail_webview = (WebView) findViewById(R.id.order_detail_webview);
        title_back = (ImageView) findViewById(R.id.title_back);

        //设置监听器
        title_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    /**
     * 根据订单id 拿到要显示的订单页面的html
     * @param mOrderId
     */
    private void requestNetGetHtmlToLoad(String mOrderId) {

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
    private Handler toGetOrderDetailHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case 1:
                    String html = (String) msg.obj;
                    webviewSetting(html);
                    mProgressDialog.dismiss();
                    break;
                case 100:
                    //webview上显示错误界面
                    mProgressDialog.dismiss();
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
        order_detail_webview.loadDataWithBaseURL("", html, "text/html", "UTF-8", "");
        order_detail_webview.setWebChromeClient(new MyWebChromeClient());

        order_detail_webview.setWebViewClient(new MyWebViewClient());
        order_detail_webview.requestFocus();

        //设置支持js
        WebSettings webViewSettings = order_detail_webview.getSettings();
        webViewSettings.setJavaScriptEnabled(true);

        order_detail_webview.addJavascriptInterface(new AccountInfoJs(mContext), "callClient");
    }

}
