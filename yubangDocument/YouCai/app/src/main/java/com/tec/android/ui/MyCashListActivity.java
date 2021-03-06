package com.tec.android.ui;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

import com.sales.vo.GetCashHistReq;
import com.tec.android.R;
import com.tec.android.js.AccountInfoJs;
import com.tec.android.js.GoodsListjs;
import com.tec.android.network.GetCashHistReqHttp;
import com.tec.android.network.GetMyCashHttp;
import com.tec.android.utils.L;
import com.tec.android.utils.toastMgr;
import com.tec.android.view.CustomProgressDialog;

/**
 * 我的提现历史记录
 */
public class MyCashListActivity extends BaseActivity {

    private static final String TAG = "MyCashListActivity";

    private Context mContext;
    private ImageView title_back;
    private WebView my_cash_list_webview;

    private CustomProgressDialog mCustomProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_my_cash_list);

        mContext = this;
        findViews();

        IntentFilter intentFilter = new IntentFilter("MyCashList");
        registerReceiver(myCashListBroadcastReceiver, intentFilter);
        //首先是加载全部
        toGetAndLoadMyCashHistoryListHtml("all");

    }

    /**
     * 初始化控件
     */
    private void findViews() {
        my_cash_list_webview = (WebView) findViewById(R.id.my_cash_list_webview);
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
     * 广播接收器   AccountInfoJs里面发送广播
     * 接收到之后, 就去后台请求网络  刷新界面
     */
    private BroadcastReceiver myCashListBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String status = intent.getStringExtra("status");
            toGetAndLoadMyCashHistoryListHtml(status);

        }
    };


    /**
     * 去服务器拿历史订单的展示内容 也就是html
     * 成功失败都去调用handler去跟主线程交互
     */
    private void toGetAndLoadMyCashHistoryListHtml(String status) {

        mCustomProgressDialog = new CustomProgressDialog(mContext);
        mCustomProgressDialog = mCustomProgressDialog.show(mContext, "加载中...", false, null);

        //1. 先去网络请求, 先拿到goodslist的html String
        GetCashHistReqHttp getCashHistReqHttp = new GetCashHistReqHttp(mContext);
        //2. 把String 放进webview .loadData()
        //3. 加载html(注意加载的时候 css JS这些文件怎么办)
        //4. webview.loadurl 的时候 要放在handler里面  因为LoadGoodsListReq是异步的   所以要等待
        getCashHistReqHttp.sendAndGetCashHistReqHtml(
                new GetCashHistReqHttp.DoingSuccessCallback() {
                    @Override
                    public void onSuccess(String result) {
                        Message msg = new Message();
                        // TODO 暂时先这么定义 这是测试  测试完了再在Configs底下定义
                        msg.what = 1;
                        msg.obj = result;
                        myCashListHandler.sendMessage(msg);
                    }
                },
                new GetCashHistReqHttp.DoingFailedCallback() {
                    @Override
                    public void onFail(String resultMsg) {
                        toastMgr.builder.display("网络错误  返回是空 timeout 服务器 或者本地网络问题 具体错误是什么 这个需要完善", 0);
                    }
                },status
        );
    }



    /**
     * 从服务器成功拿到历史订单html 用此函数设置到webview上显示
     *
     * @param html webview要显示的内容
     */
    private void webviewSetting(String html) {

        my_cash_list_webview.loadData(html, "text/html;charset=UTF-8", null);
        //goods_list_webview.loadDataWithBaseURL("http://172.25.217.2:8080/WebRoot1/", html, "text/html", "HTF-8",null);

        my_cash_list_webview.setWebChromeClient(new MyWebChromeClient());

        my_cash_list_webview.setWebViewClient(new MyWebViewClient());

        //设置支持js
        WebSettings webViewSettings = my_cash_list_webview.getSettings();
        webViewSettings.setJavaScriptEnabled(true);

        my_cash_list_webview.addJavascriptInterface(new AccountInfoJs(mContext), "callClient");


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



    private Handler myCashListHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case 1:
                    mCustomProgressDialog.dismiss();
                    String html = (String) msg.obj;
                    webviewSetting(html);
                    break;
                case 2:
                    mCustomProgressDialog.dismiss();
                    break;
                case 100:
                    mCustomProgressDialog.dismiss();
                    break;
                case 101:
                    mCustomProgressDialog.dismiss();
                    break;
                default:
                    break;
            }


        }
    };


    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_cash_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
