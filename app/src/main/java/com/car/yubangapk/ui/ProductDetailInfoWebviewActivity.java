package com.car.yubangapk.ui;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.andy.android.yubang.R;
import com.car.yubangapk.configs.Configs;
import com.car.yubangapk.network.myHttpReq.HttpReqCallback;
import com.car.yubangapk.network.myHttpReq.HttpReqGetProductDetailIinfo;
import com.car.yubangapk.utils.L;
import com.car.yubangapk.utils.toastMgr;
import com.car.yubangapk.view.AlertDialog;
import com.car.yubangapk.view.CustomProgressDialog;

/**
 * ProductDetailInfoWebviewActivity:产品详情的webview页面
 *
 * @author andy
 * @version 1.0
 * @created 2016-05-10
 */

public class ProductDetailInfoWebviewActivity extends BaseActivity implements View.OnClickListener, HttpReqCallback{


    private Context mContext;
    private static final String TAG = AdWebViewActivity.class.getName();
    private ImageView img_back;
    private WebView   ad_webview;

    private String mProductId;
    private CustomProgressDialog mProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_product_detail_info_webview);
        mContext = this;

        findVies();

        setProductId();

        mProgress = new CustomProgressDialog(mContext);

        getDetailByProductId(getProductId());
    }

    /**
     * 通过商品id去拿到商品详情
     * @param productId
     */
    private void getDetailByProductId(String productId) {

        mProgress = mProgress.show(mContext, "正在加载商品详情...", false, null);
        HttpReqGetProductDetailIinfo info = new HttpReqGetProductDetailIinfo();
        info.setCallback(this);
        info.getProductDetailInfoWebview(productId);

    }


    private void setProductId() {

        Bundle bundle = getIntent().getExtras();
        mProductId = bundle.getString("productId");
    }

    private String getProductId()
    {
        return mProductId;
    }


    /**
     * 绑定控件对象
     */
    private void findVies()
    {

        img_back = (ImageView) findViewById(R.id.img_back);
        ad_webview = (WebView) findViewById(R.id.ad_webview);

        //设置监听器
        img_back.setOnClickListener(this);

    }

    /**
     * 结束这个界面
     */
    private void finishActivy()
    {
        finish();
    }


    /**
     * 给webview加载页面
     * @param html
     */
    private void webviewSetting(String html) {

        //ad_webview.loadData(html, "text/html;charset=UTF-8", null);
        ad_webview.loadUrl(html);
        //goods_list_webview.loadDataWithBaseURL("", html, "text/html", "UTF-8", "");
        ad_webview.setWebChromeClient(new MyWebChromeClient());
        ad_webview.setWebViewClient(new MyWebViewClient());
        //设置支持js
        WebSettings webViewSettings = ad_webview.getSettings();
        webViewSettings.setJavaScriptEnabled(true);
        webViewSettings.setAllowFileAccess(true);
        //ad_webview.addJavascriptInterface(new AccountInfoJs(mContext), "callClient");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.img_back:
                finishActivy();
                break;


        }
    }

    @Override
    public void onFail(int errorCode, String message) {
        mProgress.dismiss();
        toastMgr.builder.display("服务器错误", 1);
    }

    @Override
    public void onSuccess(Object object) {
        mProgress.dismiss();

        String url = Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GET_PRODUCT_DETAIL_INFO_DETAIL_INFO + "?" + "productDetailReq.productId" + "=" + getProductId();
        webviewSetting(url);
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
            AlertDialog alertDialog = new AlertDialog(mContext);
            alertDialog.builder()
                    .setTitle("JS提示")
                    .setMsg(message)
                    .setPositiveButton("确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            result.confirm();
                        }
                    })
                    .setCancelable(false)
                    .show();
            return true;
        }

        @Override
        public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {

            //用Android组件替换
            AlertDialog alertDialog = new AlertDialog(mContext);
            alertDialog.builder()
                    .setTitle("JS提示")
                    .setMsg(message)
                    .setPositiveButton("确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            result.confirm();
                        }
                    })
                    .setCancelable(false)
                    .show();

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

        }

        @Override
        public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
            if (event.getKeyCode() == KeyEvent.KEYCODE_BACK)
                toastMgr.builder.display("back", 0);
            return true;
        }
    }
}
