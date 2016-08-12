package com.tec.android.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

/**
 * 我的好友个人中心
 */
public class MyFriendPersonalCenterActivity extends BaseActivity {

    private static final String TAG = "MyFriendPersonalCenterActivity";
    private Context mContext;
    private ImageView title_back;//返回
    private WebView my_friend_personal_center_webview;//我的好友个人中心 展示
    private String mFUserid;//朋友id
    private CustomProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_my_friend_personal_center);

        mContext = this;
        findViews();

        //拿到传过来的朋友id
        mFUserid = getIntent().getStringExtra("fuserid");
        //请求网络
        requestServerAndGetMyFriendCenterHtml(mFUserid);
    }

    /**
     * 初始化控件
     */
    private void findViews() {
        title_back = (ImageView) findViewById(R.id.title_back);
        my_friend_personal_center_webview  = (WebView) findViewById(R.id.my_friend_personal_center_webview);

        title_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }




    /**
     * 向后台请求界面
     * @param fuserid
     */
    private void requestServerAndGetMyFriendCenterHtml(String fuserid) {
        //进度对话框
        mProgressDialog = new CustomProgressDialog(mContext);
        mProgressDialog = mProgressDialog.show(mContext, "加载中...", false, null);

        //1. 先去网络请求, 先拿到goodslist的html String
        GetAccountInfoHttp getAccountInfoHttp = new GetAccountInfoHttp(mContext);
        //登陆的信息
        String postLoginRespJson = SPUtils.getUserInfo(mContext, Configs.LOGIN_INFO, null);
        PostLoginResp postLoginResp = (PostLoginResp) SalesMsgUtils.fromJson(postLoginRespJson, MSG_TYPES.MSG_POST_LOGIN, false);

        //2. 把String 放进webview .loadData()
        //3. 加载html(注意加载的时候 css JS这些文件怎么办)
        //4. webview.loadurl 的时候 要放在handler里面  因为LoadGoodsListReq是异步的   所以要等待
        getAccountInfoHttp.sendAndGetFriendPersonalInfoHtml(new GetAccountInfoHttp.DoingSuccessCallback() {
                                                                @Override
                                                                public void onSuccess(String result) {
                                                                    L.i("获取个人信息 result" + result);
                                                                    Message msg = new Message();
                                                                    msg.what = 1;
                                                                    msg.obj = result;
                                                                    handler.sendMessage(msg);
                                                                }
                                                            }, new GetAccountInfoHttp.DoingFailedCallback() {
                                                                @Override
                                                                public void onFail(String resultMsg) {
                                                                    Message msg = new Message();
                                                                    msg.what = 100;
                                                                    msg.obj = resultMsg;
                                                                    handler.sendMessage(msg);
                                                                }
                                                            },
                postLoginResp, fuserid

        );
    }




    /**
     * 整个界面主线程交互的handler
     */
    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case 1:
                    mProgressDialog.dismiss();
                    String html = (String) msg.obj;
                    webviewSetting(html);
                    break;
                case 100:
                    mProgressDialog.dismiss();
                    //这里应该显示错误信息
                    break;
                default:
                    break;
            }
        }
    };



    /**
     * 从服务器拿到数据成功, handler就调用这个函数去显示界面
     * @param html
     */
    private void webviewSetting(String html) {

        my_friend_personal_center_webview.loadData(html, "text/html;charset=UTF-8", null);
        //goods_list_webview.loadDataWithBaseURL("http://172.25.217.2:8080/WebRoot1/", html, "text/html", "HTF-8",null);

        my_friend_personal_center_webview.setWebChromeClient(new MyWebChromeClient());

        my_friend_personal_center_webview.setWebViewClient(new MyWebViewClient());

        //设置支持js
        WebSettings webViewSettings = my_friend_personal_center_webview.getSettings();
        webViewSettings.setJavaScriptEnabled(true);

        my_friend_personal_center_webview.addJavascriptInterface(new AccountInfoJs(mContext), "callClient");


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

}
