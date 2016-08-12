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
import com.tec.android.network.ConformOrderHttp;
import com.tec.android.network.GetBankListReqHttp;
import com.tec.android.network.LoadGoodsListHttp;
import com.tec.android.utils.L;
import com.tec.android.utils.toastMgr;
import com.tec.android.view.CustomProgressDialog;
import com.tec.android.view.MyWebview;

/**
 * 我要提现  选择支持银行列表
 */
public class IWantGetCashBankListActivity extends BaseActivity{

    private Context mContext;
    private static final String TAG = "IWantGetCashBankListActivity";
    private ImageView title_back;//返回
    private WebView iwant_get_cash_bank_list_webview;//要展示的页面的webview
    private CustomProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_iwant_get_cash_bank_list);
        mContext = this;
        //初始化控件
        findViews();
        //请求网络  拿到银行列表  并且在webview里面显示
        requestNetGetHtmlToLoad();

        //注册广播接收器
        IntentFilter intentFilter = new IntentFilter("banklist");
        registerReceiver(bankListItemSelectedBroadcastReceiver, intentFilter);

    }

    /**
     * 初始化控件
     */
    private void findViews() {
        title_back = (ImageView) findViewById(R.id.title_back);
        iwant_get_cash_bank_list_webview = (WebView) findViewById(R.id.iwant_get_cash_bank_list_webview);

        //设置监听器
        title_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 生命广播接收器
     * 银行列表 item被点击的时候  js调用native  native里面发送广播 , 我这边调用
     *
     */
    private BroadcastReceiver bankListItemSelectedBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String bankname = intent.getStringExtra("bankname");
            Intent intent1 = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString("bankname", bankname);
            intent1.putExtras(bundle);
            IWantGetCashBankListActivity.this.setResult(Activity.RESULT_OK, intent1);
            finish();

        }
    };



    /**
     * 拿到支持的银行列表
     */
    private void requestNetGetHtmlToLoad() {
        mProgressDialog = new CustomProgressDialog(mContext);
        mProgressDialog = mProgressDialog.show(mContext, "加载中...", false, null);

        GetBankListReqHttp getBankListReqHttp = new GetBankListReqHttp(mContext);
        getBankListReqHttp.sendAndGetBankListReqHttpHtml(new GetBankListReqHttp.DoingSuccessCallback() {
            @Override
            public void onSuccess(String result) {
                Message msg = new Message();
                msg.what = 1;
                msg.obj = result;
                bankListHandler.sendMessage(msg);
                L.i(TAG, "银行列表成功" + result);
            }
        }, new GetBankListReqHttp.DoingFailedCallback() {
            @Override
            public void onFail(String resultMsg) {
                Message msg = new Message();
                msg.what = 100;
                msg.obj = resultMsg;
                bankListHandler.sendMessage(msg);
                L.i(TAG, "银行列表失败  网络出错" + resultMsg);
            }
        });
    }



    /**
     * 多线程 mainthread交互
     */
    private Handler bankListHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what)
            {
                //成功拿到银行列表
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
     * 给webview加载页面
     * @param html
     */
    private void webviewSetting(String html) {

        iwant_get_cash_bank_list_webview.loadData(html, "text/html;charset=UTF-8",null);
        //goods_list_webview.loadDataWithBaseURL("", html, "text/html", "UTF-8", "");
        iwant_get_cash_bank_list_webview.setWebChromeClient(new MyWebChromeClient());
        iwant_get_cash_bank_list_webview.setWebViewClient(new MyWebViewClient());
        //设置支持js
        WebSettings webViewSettings = iwant_get_cash_bank_list_webview.getSettings();
        webViewSettings.setJavaScriptEnabled(true);
        webViewSettings.setAllowFileAccess(true);
        iwant_get_cash_bank_list_webview.addJavascriptInterface(new AccountInfoJs(mContext), "callClient");
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
            //iwant_get_cash_bank_list_webview.loadUrl("javascript:addNextPage('1aqsdgasg')");
        }

        @Override
        public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
            if (event.getKeyCode() == KeyEvent.KEYCODE_BACK)
                toastMgr.builder.display("back", 0);
            return true;
        }
    }/**
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
            //iwant_get_cash_bank_list_webview.loadUrl("javascript:addNextPage('1aqsdgasg')");
        }

        @Override
        public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
            if (event.getKeyCode() == KeyEvent.KEYCODE_BACK)
                toastMgr.builder.display("back", 0);
            return true;
        }
    }


    /**
     * 菜单
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_iwant_get_cash_bank_list, menu);
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
        unregisterReceiver(bankListItemSelectedBroadcastReceiver);
    }
}
