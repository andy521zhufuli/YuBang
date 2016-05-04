package com.car.yubangapk.ui.firstpagefragment;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ZoomControls;

import com.andy.android.yubang.R;

import com.car.yubangapk.configs.Configs;
import com.car.yubangapk.json.bean.Json2FirstPageShopBean;
import com.car.yubangapk.json.bean.Json2FirstPageTabsBean;
import com.car.yubangapk.json.bean.Json2LoginBean;
import com.car.yubangapk.json.formatJson.Json2FirstPageShop;
import com.car.yubangapk.json.formatJson.Json2FirstPageTabs;
import com.car.yubangapk.network.okhttp.OkHttpUtils;
import com.car.yubangapk.network.okhttp.callback.StringCallback;
import com.car.yubangapk.ui.FirstPageMarkerClickedActivity;
import com.car.yubangapk.ui.FirstPageShopShowActivity;
import com.car.yubangapk.ui.LoginActivity;
import com.car.yubangapk.ui.RegisterDetailsActivity;
import com.car.yubangapk.ui.SearchActivity;
import com.car.yubangapk.ui.UploadedInfosCheckActivity;
import com.car.yubangapk.ui.myrecommendpartner.MyRecommendedPartnerFirstFragmentActivityFragment;
import com.car.yubangapk.utils.BDMapData;
import com.car.yubangapk.utils.L;
import com.car.yubangapk.utils.SPUtils;
import com.car.yubangapk.utils.ViewGroupToBitmap;
import com.car.yubangapk.utils.toastMgr;
import com.car.yubangapk.view.AlertDialog;
import com.car.yubangapk.view.CustomProgressDialog;
import com.car.yubangapk.view.ScrollviewNavigationTabNoViewPager.FragmentAdapter;
import com.car.yubangapk.view.ScrollviewNavigationTabNoViewPager.ScrollTabView1;
import com.car.yubangapk.view.ScrollviewNavigationTabNoViewPager.ScrollTabsAdapter1;
import com.car.yubangapk.view.ScrollviewNavigationTabNoViewPager.TabAdapter1;
import com.umeng.analytics.MobclickAgent;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * FirstPageActivity: 首页界面
 * 1.首页要展示地图
 *
 * @author andyzhu
 * @version 1.0
 * @created 2016-02-22
 */
public class FirstPageNewActivity extends FragmentActivity implements View.OnClickListener{


    private Context mContext;
    private final static String TAG = "FirstPageActivity";
    private ImageView first_page_search;//搜索

    List<Json2FirstPageTabsBean> mPageTabsBeanList;

    private ScrollTabView1      first_page_nav_tabs;//顶部
    private TabAdapter1         tabsAdapter;//顶部导航的适配器
    private LinearLayout        nothing_layout;//网络错误显示
    private TextView            text_empty;//显示网络错误

    private CustomProgressDialog mProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page_new);
        mContext = this;
        findViews();
        mProgress = new CustomProgressDialog(mContext);
        //去拿首页上面导航的5getab
        httpGetFirstPageTopTab();
    }

    /**
     * 关联
     */
    private void findViews() {

        //搜索
        first_page_search = (ImageView) findViewById(R.id.search_image_new);
        first_page_nav_tabs = (ScrollTabView1) findViewById(R.id.first_page_nav_tabs);
        nothing_layout = (LinearLayout) findViewById(R.id.nothing_layout);//网络错误显示
        text_empty = (TextView) findViewById(R.id.text_empty);//显示网络错误

        /**
         * 设置监听器
         */
        first_page_search.setOnClickListener(this);

        text_empty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                httpGetFirstPageTopTab();
            }
        });

        first_page_nav_tabs.setOnItemClickListener(new ScrollTabView1.OnItemClickListener() {
            @Override
            public void onTabItemClick(TabAdapter1 tabAdapter1, int pos) {

            }
        });
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View view) {
        switch (view.getId())
        {

            //搜素
            case R.id.search_image_new:
                Intent intent3 = new Intent();
                intent3.setClass(FirstPageNewActivity.this, SearchActivity.class);
                startActivity(intent3);
                break;

        }

    }

    /**
     * 首页拿导航tabs
     */
    private void httpGetFirstPageTopTab()
    {
        mProgress = mProgress.show(mContext, "正在加载...", false, null);
        //请求的时候设置为不可见, 当网络出错的时候才可见
        nothing_layout.setVisibility(View.GONE);
        OkHttpUtils.post()
                .url(Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GET_FIRST_PAGE_TAB)
                .addParams("sqlName",  "clientSearchLogicalService")
                .build()
                .execute(new GetTabsCallBack());
        L.i(TAG, "获取首页tab url = " + Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GET_FIRST_PAGE_TAB + "?"
                        + "sqlName=" + "clientSearchLogicalService"
        );

    }

    class GetTabsCallBack extends StringCallback{

        @Override
        public void onError(Call call, Exception e) {

            toastMgr.builder.display("网络错误,请连接网络后重试!", 1);
            //地图也不给看  不会去加载
            mProgress.dismiss();
            nothing_layout.setVisibility(View.VISIBLE);
            text_empty.setText("网络错误, 请连接网络后点击加载!");
        }

        @Override
        public void onResponse(String response) {
            mProgress.dismiss();
            L.d(TAG, "获取首页tab json = " + response);
            Json2FirstPageTabs pageTabs = new Json2FirstPageTabs(response);
            List<Json2FirstPageTabsBean> pageTabsBeanList = pageTabs.getFirstPageTabs();

            if (pageTabsBeanList == null)
            {
                toastMgr.builder.display("版本太低,请更新app!",1);
            }
            else
            {
                if (pageTabsBeanList.get(0).isHasData() ==false)
                {
                    //没有数据
                    toastMgr.builder.display("没有数据, 服务器错误,请联系客服!", 1);
                }
                else
                {
                    //里面有数据
                    mPageTabsBeanList = pageTabsBeanList;
                    initTabs(mPageTabsBeanList);
                }
            }
        }
    }




    void initTabs(List<Json2FirstPageTabsBean> pageTabsBeanList)
    {
        tabsAdapter = new ScrollTabsAdapter1(this);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentAdapter fragmentAdapter = new FragmentAdapter(fragmentManager);
        for (Json2FirstPageTabsBean bean : pageTabsBeanList)
        {
            tabsAdapter.add(bean);
        }
        first_page_nav_tabs.setFragments(fragmentAdapter);
        first_page_nav_tabs.setAdapter(tabsAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    @Override
    protected void onPause()
    {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    /**
     * Get XML String of utf-8
     *
     * @return XML-Formed string
     */
    public static String getUTF8XMLString(String xml) {
        // A StringBuffer Object
        StringBuffer sb = new StringBuffer();
        sb.append(xml);
        String xmString = "";
        String xmlUTF8="";
        try {
            xmString = new String(sb.toString().getBytes("UTF-8"));
//            xmlUTF8 = URLEncoder.encode(xmString, "UTF-8");
            System.out.println("utf-8 编码：" + xmString) ;
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // return to String Formed
        return xmlUTF8;
    }
}
