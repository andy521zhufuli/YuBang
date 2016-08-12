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
import android.widget.Button;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshWebView;
import com.sales.vo.ShareResp;
import com.tec.android.R;
import com.tec.android.configs.Configs;
import com.tec.android.js.AccountInfoJs;
import com.tec.android.js.GoodsListjs;
import com.tec.android.network.GetFriendListHttp;
import com.tec.android.network.LoadGoodsListHttp;
import com.tec.android.network.ShareReqHttp;
import com.tec.android.utils.L;
import com.tec.android.utils.toastMgr;
import com.tec.android.view.CustomProgressDialog;
import com.tencent.connect.share.QQShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

/**
 * 我的朋友列表
 */
public class MyFriendsActivity extends BaseActivity implements View.OnClickListener{

    private Context mContext;
    private static final String TAG = "MyFriendsActivity";
    private WebView my_friends_webview;//我的朋友展示界面的载体webview 用来展示html
    private ImageView title_back;//返回
    private String fuserId;
    private CustomProgressDialog customProgressDialog;
    private ShareResp mShareResp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_my_friends);

        mContext = this;

        fuserId = getIntent().getStringExtra("fuserid");

        //初始化控件
        findViews();

        customProgressDialog = new CustomProgressDialog(mContext);

        toGetAndLoadFriendsListHtml();


        IntentFilter intentFilter = new IntentFilter("MyFriendsActivity.invite.friend");

        registerReceiver(inviteFriendBroadcastReceiver, intentFilter);
    }


    /**
     * 邀请好友
     */
    private BroadcastReceiver inviteFriendBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            shareFriend();
        }
    };


    /**
     * qq分享的函数
     */
    private void shareFriend()
    {
        customProgressDialog = customProgressDialog.show(mContext, "加载中...", false, null);
        //调用预分享  拿到分享的一个参数
        ShareReqHttp shareReqHttp = new ShareReqHttp(mContext);
        shareReqHttp.sendAndGetShareReqParamsJson(new ShareReqHttp.DoingSuccessCallback() {
                                                      @Override
                                                      public void onSuccess(String result) {
                                                          //拿到分享参数ok
                                                          try {
                                                              //如果可以成功转成规定类型 ok
                                                              ShareResp shareResp = new Gson().fromJson(result, ShareResp.class);
                                                              Message m1 = new Message();
                                                              m1.what = 1;
                                                              m1.obj = result;
                                                              shareHandler.sendMessage(m1);
                                                          }catch (Exception e)
                                                          {
                                                              L.i("SocialShareActivity onSuccess result json Exception = " + e.toString());
                                                              customProgressDialog.dismiss();
                                                              toastMgr.builder.display("json转换出错", 0);
                                                              finish();
                                                          }

                                                      }
                                                  }, new ShareReqHttp.DoingFailedCallback() {
                                                      @Override
                                                      public void onFail(String resultMsg) {
//                                                          String aaaa = "<html>";
//                                                          try {
//                                                              ShareResp shareResp = new Gson().fromJson(aaaa, ShareResp.class);
//                                                          }catch (Exception e)
//                                                          {
//                                                              e.printStackTrace();
//                                                          }
                                                          Message m2 = new Message();
                                                          m2.what = 100;
                                                          m2.obj = resultMsg;
                                                          shareHandler.sendMessage(m2);
                                                      }
                                                  },"129"

        );
    }

    /**
     * 分享的handler
     */
    private Handler shareHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case 1:
                    customProgressDialog.dismiss();
                    String result = (String) msg.obj;
                    ShareResp shareResp = new Gson().fromJson(result, ShareResp.class);
                    mShareResp = shareResp;
                    Tencent mTencent = Tencent.createInstance(Configs.QQ_APP_ID, MyFriendsActivity.this.getApplicationContext());
                    Bundle params = new Bundle();
                    //
                    params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
                    //选择是否分享的时候  标题分享的标题, 最长30个字符。
                    params.putString(QQShare.SHARE_TO_QQ_TITLE, mShareResp.getShareGoodModel().getSharetitle());
                    //选择是否分享的实惠   摘要分享的消息摘要，最长40个字。
                    params.putString(QQShare.SHARE_TO_QQ_SUMMARY, mShareResp.getShareGoodModel().getSharecontent());
                    //真正发送到qq好友上的url
                    params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, mShareResp.getShareGoodModel().getShareurl());
                    //选择是否发送dialog上显示的图片   分享图片的URL或者本地路径
                    params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,mShareResp.getShareGoodModel().getShareimgurl());
                    //应用的名字手Q客户端顶部，替换“返回”按钮文字，如果为空，用返回代替
                    params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "生活彩家");
                    mTencent.shareToQQ(MyFriendsActivity.this, params, qqShareListener);
                    break;
                case 100:
                    customProgressDialog.dismiss();
                    toastMgr.builder.display("请检查您的网络", 0);
                    //一旦这里出现问题 就说明没有拿到参数, 就不能继续分享  否则 会报错
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * qq分享的回调
     */
    IUiListener qqShareListener = new IUiListener() {
        @Override
        public void onCancel() {
            toastMgr.builder.display("您取消了分享: ", 0);
        }
        @Override
        public void onComplete(Object response) {
            // TODO Auto-generated method stub
            toastMgr.builder.display("分享成功", 0);
        }


        @Override
        public void onError(UiError e) {
            // TODO Auto-generated method stub
            toastMgr.builder.display("分享出错了, 请稍后再试", 0);
            toastMgr.builder.display("分享出错了, 请稍后再试" + e.errorMessage, 0);
        }
    };


    /**
     * 初始化控件
     */
    private void findViews() {
        my_friends_webview = (WebView) findViewById(R.id.my_friends_webview);
        title_back = (ImageView) findViewById(R.id.title_back);

        //设置监听器
        title_back.setOnClickListener(this);

    }
    /**
     * 去服务器拿朋友列表
     */
    private void toGetAndLoadFriendsListHtml() {
        //1. 先去网络请求, 先拿到goodslist的html String
        GetFriendListHttp getFriendListHttp = new GetFriendListHttp(mContext);
        //2. 把String 放进webview .loadData()
        //3. 加载html(注意加载的时候 css JS这些文件怎么办)
        //4. webview.loadurl 的时候 要放在handler里面  因为LoadGoodsListReq是异步的   所以要等待
        getFriendListHttp.sendAndGetFriendListHtml(
                new GetFriendListHttp.DoingSuccessCallback() {
                    @Override
                    public void onSuccess(String result) {
                        Message msg = new Message();
                        // TODO 暂时先这么定义 这是测试  测试完了再在Configs底下定义
                        msg.what = 1;
                        msg.obj = result;
                        handler.sendMessage(msg);
                    }
                },
                new GetFriendListHttp.DoingFailedCallback() {
                    @Override
                    public void onFail(String resultMsg) {
                        Message msg = new Message();
                        msg.what = 100;
                        msg.obj = resultMsg;
                        handler.sendMessage(msg);
                        toastMgr.builder.display("网络错误 请检查您的网络", 0);
                    }
                }
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
                    String html = (String) msg.obj;
                    webviewSetting(html);
                    break;
                case 100:
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

        my_friends_webview.loadData(html, "text/html;charset=UTF-8", null);
        //goods_list_webview.loadDataWithBaseURL("http://172.25.217.2:8080/WebRoot1/", html, "text/html", "HTF-8",null);

        my_friends_webview.setWebChromeClient(new MyWebChromeClient());

        my_friends_webview.setWebViewClient(new MyWebViewClient());

        //设置支持js
        WebSettings webViewSettings = my_friends_webview.getSettings();
        webViewSettings.setJavaScriptEnabled(true);

        my_friends_webview.addJavascriptInterface(new AccountInfoJs(mContext), "callClient");


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(inviteFriendBroadcastReceiver);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {

            //返回
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



}
