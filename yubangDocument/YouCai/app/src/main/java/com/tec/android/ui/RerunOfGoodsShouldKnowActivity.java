package com.tec.android.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import com.tec.android.R;
import com.tec.android.network.GetCashAccountListReqHttp;
import com.tec.android.view.CustomProgressDialog;

/**
 * 退货须知
 */

public class RerunOfGoodsShouldKnowActivity extends BaseActivity {

    private ImageView closeImageView;
    private WebView return_of_goods_should_know_webview;

    private CustomProgressDialog mProgressDialog;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rerun_of_goods_should_know);

        findViews();

        mContext = this;

        mProgressDialog = new CustomProgressDialog(mContext);

        requestNetGetReturnOfGoodsKnowHtmlToLoad();

    }

    /**
     * 初始化控件
     */
    private void findViews()
    {
        closeImageView = (ImageView) findViewById(R.id.return_of_goods_should_know_close);
        return_of_goods_should_know_webview = (WebView) findViewById(R.id.return_of_goods_should_know_webview);

        //监听器
        closeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
    }





    /**
     * 拿到退货须知  并且调用Handler 去加载页面
     */
    private void requestNetGetReturnOfGoodsKnowHtmlToLoad() {
        mProgressDialog = mProgressDialog.show(mContext, "加载中...", false, null);

        //TODO 这里这个req还没有完成
        GetCashAccountListReqHttp getCashAccountListReqHttp = new GetCashAccountListReqHttp(mContext);
        getCashAccountListReqHttp.sendAndGetCashAccountListReqHtml(new GetCashAccountListReqHttp.DoingSuccessCallback() {
            @Override
            public void onSuccess(String result) {
                Message msg = new Message();
                msg.what = 1;
                msg.obj = result;
                returnShouldKnowHandler.sendMessage(msg);
            }
        }, new GetCashAccountListReqHttp.DoingFailedCallback() {
            @Override
            public void onFail(String resultMsg) {
                Message msg = new Message();
                msg.what = 100;
                msg.obj = resultMsg;
                returnShouldKnowHandler.sendMessage(msg);
            }
        });

    }


    /**
     * 界面UI显示的handler
     */
    private Handler returnShouldKnowHandler = new Handler()
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
        return_of_goods_should_know_webview.loadDataWithBaseURL("", html, "text/html", "UTF-8","");
        return_of_goods_should_know_webview.setWebChromeClient(new MyWebChromeClient());
        return_of_goods_should_know_webview.setWebViewClient(new MyWebViewClient());
        //设置支持js
        WebSettings webViewSettings = return_of_goods_should_know_webview.getSettings();
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

    /**
     *
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mProgressDialog = null;
    }
}
