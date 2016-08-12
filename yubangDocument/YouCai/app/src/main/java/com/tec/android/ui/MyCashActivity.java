package com.tec.android.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.tec.android.network.GetMyCashHttp;
import com.tec.android.network.LoadGoodsListHttp;
import com.tec.android.utils.L;
import com.tec.android.utils.toastMgr;
import com.tec.android.view.CustomProgressDialog;

/**
 * 我的提现界面
 *
 */
public class MyCashActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "MyCashActivity";
    private Context mContext;
    private WebView my_cash_webview;//我的提现展示的载体  webview

    private ImageView title_back;//返回按钮
    private CustomProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_my_cash);
        mContext = this;

        mProgressDialog = new CustomProgressDialog(mContext);
        //初始化控件
        findViews();
        //去服务器拿我的提现  我的资金的展示内容 也就是html
        toGetAndLoadHistoryOrderHtml();
    }

    /**
     * 去服务器拿我的现金的展示内容 也就是html
     * 成功失败都去调用handler去跟主线程交互
     */
    private void toGetAndLoadHistoryOrderHtml() {

        mProgressDialog = mProgressDialog.show(mContext, "加载中...", false, null);

        //1. 先去网络请求, 先拿到goodslist的html String
        GetMyCashHttp getMyCashHttp = new GetMyCashHttp(mContext);
        //2. 把String 放进webview .loadData()
        //3. 加载html(注意加载的时候 css JS这些文件怎么办)
        //4. webview.loadurl 的时候 要放在handler里面  因为LoadGoodsListReq是异步的   所以要等待
        getMyCashHttp.sendAndGetGetMyCashHtml(
                new GetMyCashHttp.DoingSuccessCallback() {
                    @Override
                    public void onSuccess(String result) {
                        Message msg = new Message();
                        // TODO 暂时先这么定义 这是测试  测试完了再在Configs底下定义
                        msg.what = 1;
                        msg.obj = result;
                        getMyCashHtmlHandler.sendMessage(msg);
                    }
                },
                new GetMyCashHttp.DoingFailedCallback() {
                    @Override
                    public void onFail(String resultMsg) {
                        toastMgr.builder.display("网络错误 请重新加载", 0);
                    }
                }
        );
    }

    /**
     * 初始化控件
     */
    private void findViews() {
        //webview
        my_cash_webview = (WebView) findViewById(R.id.my_cash_webview);
        //返回
        title_back = (ImageView) findViewById(R.id.title_back);

        //设置监听器
        title_back.setOnClickListener(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    /**
     * 从服务器成功拿到历史订单html 用此函数设置到webview上显示
     *
     * @param html webview要显示的内容
     */
    private void webviewSetting(String html) {

        my_cash_webview.loadData(html, "text/html;charset=UTF-8", null);
        //goods_list_webview.loadDataWithBaseURL("http://172.25.217.2:8080/WebRoot1/", html, "text/html", "HTF-8",null);

        my_cash_webview.setWebChromeClient(new MyWebChromeClient());

        my_cash_webview.setWebViewClient(new MyWebViewClient());

        //设置支持js
        WebSettings webViewSettings = my_cash_webview.getSettings();
        webViewSettings.setJavaScriptEnabled(true);

        my_cash_webview.addJavascriptInterface(new AccountInfoJs(mContext), "callClient");

    }

    /**
     * 用户点击事件响应
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            //返回按钮
            case R.id.title_back:
                finish();
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
//            my_cash_webview.loadUrl("javascript:addNextPage('1aqsdgasg')");
        }

        @Override
        public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
            if (event.getKeyCode() == KeyEvent.KEYCODE_BACK)
                toastMgr.builder.display("back", 0);
            return true;
        }
    }


    /**
     * 我的提现
     */
    private Handler getMyCashHtmlHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1)
            {
                String html = (String) msg.obj;
                webviewSetting(html);
                mProgressDialog.dismiss();
            }
        }
    };
}
