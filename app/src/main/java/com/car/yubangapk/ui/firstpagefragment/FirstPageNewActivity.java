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
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.car.yubangapk.app.AppManager;
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
    private BMapManager mBMapManager;

    private ImageView first_page_search;//搜索

    private List<BDMapData> bdMapClientList;
    //当前定位结果的经纬度
    private double latitude;
    private double longitude;

    private String mCountry;    //国家
    private String mProvince;   //省
    private String mCity;       //市
    private String mCityCode;   //市代码
    private String mDistrict;   //区
    private String mStreet;     //街道

    /**
     * MapView 是地图主控件
     */

    private Button          first_page_location_btn;//定位按钮



    boolean isFirstLoc = true; // 是否首次定位
    private MyLocationConfiguration.LocationMode mCurrentMode;




    // 初始化全局 bitmap 信息，不用时及时 recycle
    BitmapDescriptor bdA;

    //当前界面
    private int currentPage;
    private static final int ALL_CAR_PAGE   = 1;
    private static final int BAO_YANG_PAGE  = 2;
    private static final int DIAN_LU_PAGE   = 3;
    private static final int PEI_JIAN_PAGE  = 4;
    private static final int YOU_LU_PAGE    = 5;


    List<Json2FirstPageTabsBean> mPageTabsBeanList;
    private static int FITST_GET_SHOP = 1;//第一次去拿店铺
    private static int SECOND_GET_SHOP = 2;//如果没有区里面没有店铺信息  就去拿市里的
    private static int THIRD_GET_SHOP = 3;//如果没有市里面没有店铺信息  就去拿省里的
    private int CURRENT_GET_SHOP_TIME = 1;


    private String mShopBeanResponse;


    private CustomProgressDialog mProgressDialog;
    //保存拿来的店铺信息
    private List<Json2FirstPageShopBean> mJson2FirstPageShopBeanList;




    private ScrollTabView1 first_page_nav_tabs;//顶部
    private TabAdapter1 tabsAdapter;//
    private Fragment firstFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page_new);

        mContext = this;

        findViews();

        currentPage = ALL_CAR_PAGE;

        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
        //进度框
        mProgressDialog = new CustomProgressDialog(mContext);
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
        /**
         * 设置监听器
         */
        first_page_search.setOnClickListener(this);

        first_page_nav_tabs.setOnItemClickListener(new ScrollTabView1.OnItemClickListener() {
            @Override
            public void onTabItemClick(View view, int pos) {
                FragmentAdapter fragmentAdapter = first_page_nav_tabs.getFragments();

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
            //网络错误  就不要加载地图了
            //initMAp();
        }

        @Override
        public void onResponse(String response) {
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
            fragmentAdapter.addFragment(new  FirstPageFragment(bean.getTabDisplayName()));
        }
        first_page_nav_tabs.setFragments(fragmentAdapter);
        first_page_nav_tabs.setAdapter(tabsAdapter);
    }




    /**实现切换不同的Fragment
     * @param i 点击的第几个按钮
     */
//    private void select(int i)
//    {
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        hideFragment(transaction);
//
//        switch (i)
//        {
//            case 0:
//                if (firstFragment == null)
//                {
//                    firstFragment = new MyRecommendedPartnerFirstFragmentActivityFragment(ALL_PARTNERS);
//                    transaction.add(R.id.my_recommended_partner_framlayout, firstFragment);
//                } else
//                {
//                    transaction.show(firstFragment);
//                }
//                break;
//            case 1:
//                if (secondFragment == null)
//                {
//                    secondFragment = new MyRecommendedPartnerFirstFragmentActivityFragment(PENDING_APPROVAL);
//                    transaction.add(R.id.my_recommended_partner_framlayout, secondFragment);
//                } else
//                {
//                    transaction.show(secondFragment);
//                }
//                break;
//            case 2:
//                if (threeFragment == null)
//                {
//                    threeFragment = new MyRecommendedPartnerFirstFragmentActivityFragment(NOT_APPROVAL);
//                    transaction.add(R.id.my_recommended_partner_framlayout, threeFragment);
//                } else
//                {
//                    transaction.show(threeFragment);
//                }
//                break;
//        }
//        transaction.commit();
//    }
//
//    /**
//     * 用于每一显示不同的Fragment时候隐藏之前的所有可能显示的Fragment
//     * @param transaction
//     *          事物
//     */
//    private void hideFragment(FragmentTransaction transaction)
//    {
//        if (firstFragment != null)
//        {
//            transaction.hide(firstFragment);
//        }
//        if (secondFragment != null)
//        {
//            transaction.hide(secondFragment);
//        }
//        if (threeFragment != null)
//        {
//            transaction.hide(threeFragment);
//        }
//    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理



    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        toastMgr.builder.display("onresume", 1);


    }
    @Override
    protected void onPause()
    {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
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
