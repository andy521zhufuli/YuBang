package com.tec.android.ui;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.sales.vo.PostLoginResp;
import com.sales.vo.base.MSG_TYPES;
import com.sales.vo.base.SalesMsgUtils;
import com.tec.android.R;
import com.tec.android.configs.Configs;
import com.tec.android.js.AccountInfoJs;
import com.tec.android.network.GetAccountInfoHttp;
import com.tec.android.utils.L;
import com.tec.android.utils.SPUtils;
import com.tec.android.utils.toastMgr;
import com.tec.android.view.CustomProgressDialog;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;

/**
 * MyActivity: 个人中心界面
 *
 * @author andyzhu
 * @version 1.0
 * @created 2015-07-31
 */

public class MyActivity extends BaseActivity implements View.OnClickListener{

    private static final String TAG = "MyActivity";
    private Context mContext;



    private WebView my_webview;//浏览器
    private ImageView setting_btn;//右上角  设置按钮

    private RelativeLayout app_network_model_layout;//网络错误提示
    private Button my_center_reload_button;//网络错误时候重新加载按钮
    private SmoothProgressBar my_center_smooth_progressbar;//进度条
    private CustomProgressDialog mProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_my);
        mContext = this;
        findViews();

        mProgressDialog = new CustomProgressDialog(mContext);

        L.i("my oncreate");
        //加载页面
        loadHtmlToWebview();

        IntentFilter intentFilter = new IntentFilter("loginoutsuccess");
        registerReceiver(loginOutSuccessBroadcastReceiver, intentFilter);


    }

    /**
     *
     */
    private BroadcastReceiver loginOutSuccessBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            toastMgr.builder.display("loginout  success", 0);
            loadHtmlToWebview();

        }
    };


    /**
     * 请求网络, 加载webview
     */
    private void loadHtmlToWebview()
    {
        mProgressDialog = mProgressDialog.show(mContext, "加载中...", false, null);
        //进度条开始闪动
        my_center_smooth_progressbar.progressiveStart();
        my_center_smooth_progressbar.setVisibility(View.VISIBLE);

        //逻辑正确
        if (isUserLogind() ==  true)
        {
            //用户已经登录
            //需要传递userid token seq 等等
            String postLoginRespJson = SPUtils.getUserInfo(mContext, Configs.LOGIN_INFO, null);
            //既然到了这一步 拿到的json肯定不会空

            PostLoginResp postLoginResp = (PostLoginResp) SalesMsgUtils.fromJson(postLoginRespJson, MSG_TYPES.MSG_POST_LOGIN, false);
            requestServerAndGetMyCenterHtml(postLoginResp);
        }
        else
        {
            //用户没有登陆
            //传递空
            requestServerAndGetMyCenterHtml(null);
        }
    }

    /**
     * 初始化控件
     */
    private void findViews() {

        my_webview = (WebView) findViewById(R.id.my_webview);
        setting_btn = (ImageView) findViewById(R.id.setting_btn);
        app_network_model_layout = (RelativeLayout) findViewById(R.id.app_network_model_layout);
        my_center_reload_button = (Button) findViewById(R.id.my_center_reload_button);
        my_center_smooth_progressbar = (SmoothProgressBar) findViewById(R.id.my_center_smooth_progressbar);


        //监听器
        setting_btn.setOnClickListener(this);
        my_center_reload_button.setOnClickListener(this);
    }

    /**
     * 判断用户是否已经登陆
     * @return
     */
    public boolean isUserLogind() {
        String isLogined = SPUtils.getUserInfo(mContext, Configs.IS_USER_LOGINED, "NO");
        if (isLogined.equals(Configs.LOGINED))
        {
            return true;
        }
        else
        {
            return false;
        }

    }


    /**
     * 向后台请求界面
     * @param postLoginResp
     */
    private void requestServerAndGetMyCenterHtml(PostLoginResp postLoginResp) {
        //1. 先去网络请求, 先拿到goodslist的html String
        GetAccountInfoHttp getAccountInfoHttp = new GetAccountInfoHttp(mContext);
        //2. 把String 放进webview .loadData()
        //3. 加载html(注意加载的时候 css JS这些文件怎么办)
        //4. webview.loadurl 的时候 要放在handler里面  因为LoadGoodsListReq是异步的   所以要等待
        getAccountInfoHttp.sendAndGetAccountInfoHtml(new GetAccountInfoHttp.DoingSuccessCallback() {
                                                         @Override
                                                         public void onSuccess(String result) {
                                                             L.i("获取个人信息 result" + result);
                                                             Message msg = new Message();
                                                             msg.what = 1;
                                                             msg.obj = result;
                                                             toGetAccountInfolHandler.sendMessage(msg);
                                                         }
                                                     }, new GetAccountInfoHttp.DoingFailedCallback() {
                                                         @Override
                                                         public void onFail(String resultMsg) {
                                                             Message msg = new Message();
                                                             msg.what = 100;
                                                             msg.obj = resultMsg;
                                                             toGetAccountInfolHandler.sendMessage(msg);
                                                         }
                                                     },
                                                        postLoginResp

        );
    }


    /**
     * 点击事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            //设置
            case R.id.setting_btn:
                Intent intent = new Intent();
                intent.setClass(mContext, SettingsActivity.class);
                startActivity(intent);
                break;
            //网络出现错误 重新加载
            case R.id.my_center_reload_button:
                //重新加载个人中心页面
                loadHtmlToWebview();
                //失败的时候 会显示tips  还有重新加载按钮
                my_webview.setVisibility(View.VISIBLE);
                my_center_reload_button.setVisibility(View.GONE);
                my_center_smooth_progressbar.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }


    /**
     * 个人中心的webview的设置
     *
     * @param html html的代码
     */
    private void webviewSetting(String html) {

        //my_webview.loadData(html, "text/html;charset=UTF-8",null);
        my_webview.loadDataWithBaseURL("", html, "text/html", "UTF-8", "");

        my_webview.setWebChromeClient(new MyWebChromeClient());

        my_webview.setWebViewClient(new MyWebViewClient());
        my_webview.requestFocus();
        //设置支持js
        WebSettings webViewSettings = my_webview.getSettings();
        webViewSettings.setJavaScriptEnabled(true);

        my_webview.addJavascriptInterface(new AccountInfoJs(mContext), "callClient");

    }

    /**
     * 从后台拿到数据之后, 通过这个handler设置web页面
     */
    private Handler toGetAccountInfolHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1)
            {
                //进度条取消闪烁
                mProgressDialog.dismiss();
                my_center_smooth_progressbar.progressiveStop();
                my_center_smooth_progressbar.setVisibility(View.GONE);
                //成功   还是没进度条提示
                String result = (String) msg.obj;
                webviewSetting(result);


            }
            else  if (msg.what == 100)
            {
                //失败
                my_center_smooth_progressbar.progressiveStop();
                //失败的时候 会显示tips  还有重新加载按钮
                my_webview.setVisibility(View.GONE);
                my_center_reload_button.setVisibility(View.VISIBLE);
                my_center_smooth_progressbar.setVisibility(View.GONE);
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
            toastMgr.builder.display("shouldOverrideUrlLoading url = " + url, 0);
            return true;
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            view.getSettings().setJavaScriptEnabled(true);
            super.onPageStarted(view, url, favicon);

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            // 调用webview (服务器界面上的JS)
            my_webview.loadUrl("javascript:wave()");
        }
    }


    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(loginOutSuccessBroadcastReceiver);
        L.i("my oncreate");
    }

    @Override
    protected void onResume() {
        super.onResume();
        L.i("my onResume");
    }
}
