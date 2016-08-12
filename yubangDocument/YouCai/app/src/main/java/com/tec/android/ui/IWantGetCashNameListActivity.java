package com.tec.android.ui;

import android.app.Activity;
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

import com.sales.vo.GetCashAccountListReq;
import com.tec.android.R;
import com.tec.android.js.AccountInfoJs;
import com.tec.android.network.ConformOrderHttp;
import com.tec.android.network.GetAccountInfoHttp;
import com.tec.android.network.GetCashAccountListReqHttp;
import com.tec.android.utils.L;
import com.tec.android.view.CustomProgressDialog;

/**
 * 选择收款人   就是添加收款人信息的时候 点击人物  选择已有账户列表
 */
public class IWantGetCashNameListActivity extends BaseActivity {

    private static final String TAG = "IWantGetCashNameListActivity";
    private Context mContext;
    private ImageView title_back;
    private WebView iwant_get_cash_name_list_webview;
    private CustomProgressDialog mProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_iwant_get_cash_name_list);
        mContext = this;
        //初始化控件
        findViews();

        //注册广播接收器
        IntentFilter intentFilter = new IntentFilter("cashnamelist");
        registerReceiver(cashNameListBroadcastReceiver, intentFilter);

        requestNetGetHtmlToLoad();

    }



    /**
     * 初始化控件
     */
    private void findViews() {
        title_back = (ImageView) findViewById(R.id.title_back);
        iwant_get_cash_name_list_webview = (WebView) findViewById(R.id.iwant_get_cash_name_list_webview);

        title_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    /**
     * 定义广播接收器
     */
    private BroadcastReceiver cashNameListBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Bundle bundle = intent.getBundleExtra("cashnamelistitem");

            Intent intent1 = new Intent();
            intent1.putExtra("cashnamelistitem", bundle);
            IWantGetCashNameListActivity.this.setResult(Activity.RESULT_OK, intent1);
            IWantGetCashNameListActivity.this.finish();
        }
    };



    /**
     * * 拿到已有账户的列表 给用户选择
     */
    private void requestNetGetHtmlToLoad() {
        mProgressDialog = new CustomProgressDialog(mContext);
        mProgressDialog = mProgressDialog.show(mContext, "加载中...", false, null);

        GetCashAccountListReqHttp getCashAccountListReqHttp = new GetCashAccountListReqHttp(mContext);

        getCashAccountListReqHttp.sendAndGetCashAccountListReqHtml(new GetCashAccountListReqHttp.DoingSuccessCallback() {
            @Override
            public void onSuccess(String result) {
                Message msg = new Message();
                msg.what = 1;
                msg.obj = result;
                getCashNameListHandler.sendMessage(msg);
                L.i(TAG, "已有账户列表成功" + result);
            }
        }, new GetCashAccountListReqHttp.DoingFailedCallback() {
            @Override
            public void onFail(String resultMsg) {
                Message msg = new Message();
                msg.what = 100;
                msg.obj = resultMsg;
                getCashNameListHandler.sendMessage(msg);
                L.i(TAG, "已有账户列表失败" + resultMsg);
            }
        });

    }


    /**
     * mianthread交互
     */
    private Handler getCashNameListHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what)
            {
                //成功已有账户列表
                case 1:
                    mProgressDialog.dismiss();
                    String html = (String) msg.obj;
                    webviewSetting(html);
                    break;
                case 2:
                    mProgressDialog.dismiss();
                    break;
                case 100:
                    mProgressDialog.dismiss();
                    break;
                case 101:
                    mProgressDialog.dismiss();
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
        iwant_get_cash_name_list_webview.loadDataWithBaseURL("", html, "text/html", "UTF-8","");

        iwant_get_cash_name_list_webview.setWebChromeClient(new MyWebChromeClient());

        iwant_get_cash_name_list_webview.setWebViewClient(new MyWebViewClient());

        //设置支持js
        WebSettings webViewSettings = iwant_get_cash_name_list_webview.getSettings();
        webViewSettings.setJavaScriptEnabled(true);

        iwant_get_cash_name_list_webview.addJavascriptInterface(new AccountInfoJs(mContext), "callClient");

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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_iwant_get_cash_name_list, menu);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(cashNameListBroadcastReceiver);
    }
}
