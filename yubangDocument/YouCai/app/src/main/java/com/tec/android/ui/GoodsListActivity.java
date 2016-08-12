package com.tec.android.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.sales.vo.LoadGoodsDetailReq;
import com.sales.vo.LoadGoodsDetailResp;
import com.sales.vo.LoadGoodsListResp;
import com.sales.vo.base.BriefGoods;
import com.sales.vo.base.MSG_TYPES;
import com.sales.vo.base.SalesMsgUtils;
import com.tec.android.R;
import com.tec.android.js.GoodsListjs;
import com.tec.android.network.LoadGoodsListHttp;

import org.apache.http.protocol.HTTP;
import org.json.JSONArray;

import com.tec.android.network.NetWorkHelper;
import com.tec.android.utils.L;
import com.tec.android.utils.toastMgr;
import com.tec.android.view.MyWebview;

import java.util.List;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;

/**
 * GoodsListActivity: 商品列表界面 程序进入主界面首先现实的界面
 *
 * @author andyzhu
 * @version 1.0
 * @created 2015-07-30
 */
// TODO 如果从商品详情界面返回, 应该怎么处理  这个要考虑一下
public class GoodsListActivity extends BaseActivity implements View.OnClickListener{

    private static final String TAG = "GoodsListActivity";
    private Context mContext;
    private MyWebview goods_list_webview;
    //默认是没加载完成
    public static boolean isLoadMoreFinished  = true;//下拉到webview底部  加载更多, 判断加载更多是否完成,完成了才允许用户下一次加载  因为这个加载事件可能同时出发多次  因为判断到达底部做的没那么好


    private RelativeLayout  app_network_model_layout;//网络
    private ImageView       app_network_model_arrow;//点击设置网络连接

    private Button goods_list_reload_button;//重新加载
    private SmoothProgressBar goods_list_smooth_progressbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_list);

        mContext = this;
        findViews();

        //检查当前网路是否可用, 如果可用, 直接去服务器拿数据, 如果不可用  提示用户
        checkNetwork();
    }

    /**
     * 检查当前网路是否可用, 如果可用, 直接去服务器拿数据, 如果不可用  提示用户
     */
    private void checkNetwork() {
        //先去检查网络, 如果网络连接可用, 就去服务器拿数据, 如果网络不可用, 那就提示用户网络没打开  网络不可用,
        boolean isNetworkAvailable = NetWorkHelper.isNetStateConnected(mContext);
        goods_list_smooth_progressbar.progressiveStart();
        //当前网络可用
        if (isNetworkAvailable)
        {
            //浏览器设置可见
            goods_list_webview.setVisibility(View.VISIBLE);
            goods_list_reload_button.setVisibility(View.GONE);
            goods_list_smooth_progressbar.setVisibility(View.VISIBLE);
            // 网络连接可用, OK, 那就去网络连接操作
            //1. 先去网络请求, 先拿到goodslist的html String
            LoadGoodsListHttp loadGoodsListHttp = new LoadGoodsListHttp(mContext);
            //2. 把String 放进webview .loadData()
            //3. 加载html(注意加载的时候 css JS这些文件怎么办)
            //4. webview.loadurl 的时候 要放在handler里面  因为LoadGoodsListReq是异步的   所以要等待
            loadGoodsListHttp.sendAndRecv(null, new LoadGoodsListHttp.DoingSuccessCallback() {
                        @Override
                        public void onSuccess(String result) {
                            Message msg = new Message();
                            // TODO 暂时先这么定义 这是测试  测试完了再在Configs底下定义
                            msg.what = 1;
                            msg.obj = result;
                            getHTTPHandler.sendMessage(msg);
                        }
                    },
                    new LoadGoodsListHttp.DoingFailedCallback() {
                        @Override
                        public void onFail(String resultMsg) {
                            Message msg1 = new Message();
                            msg1.what = 100;
                            getHTTPHandler.sendMessage(msg1);

                        }
                    }
            );
        }
        else
        {
            //当前网络不可用
            //网络提示可见
            //webview不可见
//            goods_list_webview.setVisibility(View.GONE);
//            goods_list_reload_button.setVisibility(View.VISIBLE);
//            goods_list_smooth_progressbar.setVisibility(View.GONE);
//            smoothProgressBarStop();
//            goods_list_smooth_progressbar.setVisibility(View.GONE);
//
//            goods_list_webview.setVisibility(View.GONE);
//            goods_list_reload_button.setVisibility(View.VISIBLE);
//            goods_list_smooth_progressbar.setVisibility(View.GONE);
//            goods_list_smooth_progressbar.progressiveStop();
            Message msg1 = new Message();
            msg1.what = 100;
            getHTTPHandler.sendMessage(msg1);
            return;
        }
    }

    /**
     * 给webview加载页面
     * @param html
     */
    private void webviewSetting(String html) {

        goods_list_webview.loadData(html, "text/html;charset=UTF-8",null);
        //goods_list_webview.loadDataWithBaseURL("", html, "text/html", "UTF-8", "");

        goods_list_webview.setWebChromeClient(new MyWebChromeClient());

        goods_list_webview.setWebViewClient(new MyWebViewClient());

        //设置支持js
        WebSettings webViewSettings = goods_list_webview.getSettings();
        webViewSettings.setJavaScriptEnabled(true);
        webViewSettings.setAllowFileAccess(true);

        goods_list_webview.addJavascriptInterface(new GoodsListjs(mContext), "callClient");

        /**
         * 判断webview是否加载到最底端
         */
        goods_list_webview.setOnCustomScroolChangeListener(new MyWebview.ScrollInterface() {
            @Override
            public void onSChanged(int l, int t, int oldl, int oldt) {
                L.e("Value ContentHeight() = " + goods_list_webview.getContentHeight());
                L.e("Value getScale() = " + goods_list_webview.getScale());
                L.e("Value getHeight() = " + goods_list_webview.getHeight());
                L.e("Value getScrollY() = " + goods_list_webview.getScrollY());
                L.e("Value", (goods_list_webview.getContentHeight() * goods_list_webview.getScale() - (goods_list_webview.getHeight() + goods_list_webview.getScrollY())) + "");
                if (goods_list_webview.getContentHeight() * goods_list_webview.getScale() - (goods_list_webview.getHeight() + goods_list_webview.getScrollY()) <= 10) {
                    final String loadMoreJson = "{\"goodslist\":[{\"secondarytitle\":\"sales2\",\"originalprice\":1313,\"discountprice\":2131,\"goodsid\":\"1\",\"imgurl\":\"jsp/images/img1.png\",\"title\":\"sales2\"},{\"secondarytitle\":\"sales1\",\"originalprice\":123,\"discountprice\":123,\"goodsid\":\"129\",\"imgurl\":\"jsp/images/img1.png\",\"title\":\"sales1\"},{\"secondarytitle\":\"sales1\",\"originalprice\":123,\"discountprice\":123,\"goodsid\":\"130\",\"imgurl\":\"jsp/images/img1.png\",\"title\":\"sales1\"}],\"returncode\":0,\"errmsg\":\"成功\",\"msgtype\":\"LoadGoodsList\",\"seq\":\"201508151752380001\"}";
                    //已经处于底端
                    toastMgr.builder.display("webview 加载到低端", 0);
                    if (isLoadMoreFinished == true)
                    {
                        isLoadMoreFinished = false;
                        toastMgr.builder.display("isLoadMoreFinished call", 0);
                        LoadGoodsListHttp loadGoodsListHttp = new LoadGoodsListHttp(mContext);
                        loadGoodsListHttp.sendAndRecv(null, new LoadGoodsListHttp.DoingSuccessCallback() {
                                    @Override
                                    public void onSuccess(String result) {
                                        result = loadMoreJson;
                                        Message msg = new Message();
                                        msg.what = 10;
                                        msg.obj = result;
                                        getHTTPHandler.sendMessage(msg);
                                    }
                                },
                                new LoadGoodsListHttp.DoingFailedCallback() {
                                    @Override
                                    public void onFail(String resultMsg) {
                                        isLoadMoreFinished = true;
                                    }
                                }
                        );
                    }


                }
            }
        });

        /**
         * 判断webview是否加载到顶端
         */
//        goods_list_webview.setOnScrollListener(new MyWebview.OnScrollListener() {
//            @Override
//            public void onScroll(WebView wv, int scrollX, int scrollY) {
//                if (scrollY == 0)
//                {
//                    toastMgr.builder.display("webview 加载到顶端", 0);
//                }
//            }
//        });


    }

    //初始化控件
    private void findViews() {
        goods_list_webview = (MyWebview) findViewById(R.id.goods_list_webview);

        goods_list_smooth_progressbar = (SmoothProgressBar) findViewById(R.id.goods_list_smooth_progressbar);

        goods_list_reload_button = (Button) findViewById(R.id.goods_list_reload_button);

        app_network_model_layout = (RelativeLayout) findViewById(R.id.app_network_model_layout);

        goods_list_reload_button.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {

            case R.id.goods_list_reload_button:
                //检查网络 并且加载
                checkNetwork();
                goods_list_webview.setVisibility(View.VISIBLE);
                goods_list_reload_button.setVisibility(View.GONE);
                goods_list_smooth_progressbar.setVisibility(View.VISIBLE);
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
            goods_list_webview.loadUrl("javascript:addNextPage('1aqsdgasg')");
        }

        @Override
        public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
            if (event.getKeyCode() == KeyEvent.KEYCODE_BACK)
                toastMgr.builder.display("back", 0);
            return true;
        }
    }





    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 商品列表  主线程子线程交互handler
     */
    android.os.Handler getHTTPHandler = new android.os.Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1)
            {
                goods_list_smooth_progressbar.progressiveStop();
                goods_list_smooth_progressbar.setVisibility(View.GONE);
                String result = (String) msg.obj;
                webviewSetting(result);
            }
            else if (msg.what == 100)
            {
                goods_list_webview.setVisibility(View.GONE);
                goods_list_reload_button.setVisibility(View.VISIBLE);
                goods_list_smooth_progressbar.setVisibility(View.GONE);
                goods_list_smooth_progressbar.progressiveStop();
//                goods_list_smooth_progressbar.progressiveStop();

            }
            else if (msg.what == 10)
            {
                goods_list_smooth_progressbar.progressiveStop();
                toastMgr.builder.display("isLoadMoreFinished call2", 0);
                String result = (String) msg.obj;
                LoadGoodsListResp loadGoodsListResp = (LoadGoodsListResp) SalesMsgUtils.fromJson(result, MSG_TYPES.MSG_LOAD_GOODS_LIST, false);
                List<BriefGoods> briefGoodsList =   loadGoodsListResp.getGoodslist();
                if (briefGoodsList != null && briefGoodsList.size() > 0)
                {
                    toastMgr.builder.display("isLoadMoreFinished call3", 0);
                    String briefGoodsJson = new Gson().toJson(briefGoodsList);
                    L.i("briefGoodsJson" + briefGoodsJson);
                    //goods_list_webview.loadUrl("javascript:loadMoreGoods('" + briefGoodsJson + "')");
                    goods_list_webview.loadUrl("javascript:loadMoreGoods('" + briefGoodsJson +  "')");
                    toastMgr.builder.display("isLoadMoreFinished call4", 0);

                }


            }

        }
    };



    private void smoothProgressBarStart()
    {
        goods_list_smooth_progressbar.setVisibility(View.VISIBLE);
        goods_list_smooth_progressbar.progressiveStart();
    }
    private void smoothProgressBarStop()
    {
        goods_list_smooth_progressbar.setVisibility(View.GONE);
        goods_list_smooth_progressbar.progressiveStop();
    }

//    @Override
//    public boolean dispatchKeyEvent(KeyEvent event) {
//        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK)
//        {
//            toastMgr.builder.display("dispatchKeyEvent back" , 0);
//        }
//        return true;
//    }
}
