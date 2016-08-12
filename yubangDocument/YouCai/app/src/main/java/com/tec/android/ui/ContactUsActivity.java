package com.tec.android.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import com.tec.android.R;
import com.tec.android.network.GetCashAccountListReqHttp;
import com.tec.android.utils.L;
import com.tec.android.view.CustomProgressDialog;

/**
 * 联系客服  联系我们
 * SettingsActivity-->connect_service_layout-->联系客服ContactUsActivity
 */
public class ContactUsActivity extends BaseActivity {

    private Context mContext;
    private static final String TAG = "ContactUsActivity";
    private ImageView title_back;//返回
    private WebView contact_us_webview;//界面展示

    //进度提示框
    private CustomProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_contact_us);

        mContext = this;

        //初始化控件
        findViews();

        //拿到联系客服的界面  并且调用Handler 去加载页面
        requestNetGetContactUsHtmlToLoad();

    }

    /**
     * 初始化控件
     */
    private void findViews() {
        title_back = (ImageView) findViewById(R.id.title_back);
        contact_us_webview = (WebView) findViewById(R.id.contact_us_webview);

        //设置监听器
        title_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }



    /**
     * 拿到联系客服的界面  并且调用Handler 去加载页面
     */
    private void requestNetGetContactUsHtmlToLoad() {
        mProgressDialog = new CustomProgressDialog(mContext);
        mProgressDialog = mProgressDialog.show(mContext, "加载中...", false, null);

        //TODO 这里这个req还没有完成
        GetCashAccountListReqHttp getCashAccountListReqHttp = new GetCashAccountListReqHttp(mContext);
        getCashAccountListReqHttp.sendAndGetCashAccountListReqHtml(new GetCashAccountListReqHttp.DoingSuccessCallback() {
            @Override
            public void onSuccess(String result) {
                Message msg = new Message();
                msg.what = 1;
                msg.obj = result;
                contactUsHandler.sendMessage(msg);
                L.i(TAG, "已有账户列表成功" + result);
            }
        }, new GetCashAccountListReqHttp.DoingFailedCallback() {
            @Override
            public void onFail(String resultMsg) {
                Message msg = new Message();
                msg.what = 100;
                msg.obj = resultMsg;
                contactUsHandler.sendMessage(msg);
                L.i(TAG, "已有账户列表失败" + resultMsg);
            }
        });

    }


    /**
     * 界面UI显示的handler
     */
    private Handler contactUsHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what)
            {
                //成功
                case 1:
                    mProgressDialog.dismiss();
                    String html = (String) msg.obj;
                    //展示界面
                    webviewSetting(html);
                    break;
                //失败  网络失败 问题
                case 100:
                    break;
                default:
                    break;
            }
        }
    };





    /**
     * 设置webview界面  显示
     * @param html
     */
    private void webviewSetting(String html) {

        //conform_order_webview.loadData(html, "text/html;charset=UTF-8",null);
        contact_us_webview.loadDataWithBaseURL("", html, "text/html", "UTF-8","");
        contact_us_webview.setWebChromeClient(new MyWebChromeClient());
        contact_us_webview.setWebViewClient(new MyWebViewClient());
        //设置支持js
        WebSettings webViewSettings = contact_us_webview.getSettings();
//        webViewSettings.setJavaScriptEnabled(true);
//        contact_us_webview.addJavascriptInterface(new AccountInfoJs(mContext), "callClient");

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
//            conform_order_webview.loadUrl("javascript:wave()");
        }
    }





    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
