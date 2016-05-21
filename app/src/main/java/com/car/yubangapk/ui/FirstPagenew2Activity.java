package com.car.yubangapk.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import com.car.yubangapk.json.bean.sysconfigs.Json2AppConfigs;
import com.car.yubangapk.json.formatJson.Json2FirstPageShop;
import com.car.yubangapk.json.formatJson.Json2FirstPageTabs;
import com.car.yubangapk.json.formatJson.formatSysconfigs.Json2SYSConfigs;
import com.car.yubangapk.network.myHttpReq.HttpReqCallback;
import com.car.yubangapk.network.myHttpReq.HttpReqGetAppConfig;
import com.car.yubangapk.network.okhttp.OkHttpUtils;
import com.car.yubangapk.network.okhttp.callback.StringCallback;
import com.car.yubangapk.utils.APPUtils;
import com.car.yubangapk.utils.BDMapData;
import com.car.yubangapk.utils.L;
import com.car.yubangapk.utils.SPUtils;
import com.car.yubangapk.utils.String2UTF8;
import com.car.yubangapk.utils.ViewGroupToBitmap;
import com.car.yubangapk.utils.toastMgr;
import com.car.yubangapk.view.ActionSheetDialogList;
import com.car.yubangapk.view.AlertDialog;
import com.car.yubangapk.view.CustomProgressDialog;
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
public class FirstPagenew2Activity extends BaseActivity implements View.OnClickListener{


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
    private MapView mMapView;
    private SDKInitializer initializer;
    private BaiduMap mBaiduMap;

    //定位
    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListenner();



    //顶部5个tab
    ScrollTabView1          first_page_nav_tabs;
    private TabAdapter1     tabsAdapter;//顶部导航的适配器
    private LinearLayout    nothing_layout;//网络错误显示
    private TextView        text_empty;//显示网络错误
    private Button          first_page_location_btn;//定位按钮


    boolean isFirstLoc = true; // 是否首次定位
    private MyLocationConfiguration.LocationMode mCurrentMode;
    private Marker mMarkerA;



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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page_new2);

        mContext = this;

        findViews();

        currentPage = ALL_CAR_PAGE;

        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
        mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
        //进度框
        mProgressDialog = new CustomProgressDialog(mContext);
        //去拿首页上面导航的5getab
        httpGetFirstPageTopTab();

        checkNewVersion();

    }

    /**
     * 检查新版本
     */
    private void checkNewVersion() {

        HttpReqGetAppConfig getAppConfig = new HttpReqGetAppConfig(mContext);
        getAppConfig.setCallback(new HttpReqCallback() {
            @Override
            public void onFail(int errorCode, String message) {

            }

            @Override
            public void onSuccess(Object object) {
                Json2SYSConfigs configs = new Json2SYSConfigs(mContext);
                Json2AppConfigs appConfigs = configs.getAppConfigs();



                final String version = appConfigs.getSys().getCzVersion();
                if ("".equals(version))
                {

                }
                else
                {
                    if (!getVersion().equals(version))
                    {
                        AlertDialog alertDialog = new AlertDialog(mContext);
                        alertDialog.builder().setTitle("更新提醒")
                                .setMsg("您有新版本可以下载")
                                .setCancelable(true)
                                .setPositiveButton("立即更新", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        updateAPp();
                                    }
                                })
                                .setNegativeButton("下次再说", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                    }
                                })
                                .show();
                    }
                }
            }
        });

        getAppConfig.getAppConfig();



    }

    private void updateAPp() {
        Intent intent = new Intent();
        intent.setClass(mContext, UpgradeAppWebviewActivity.class);
        startActivity(intent);
    }

    public String getVersion()
    {
        return APPUtils.getVersion(mContext);
    }


    /**
     * 判断用户是否已经登录
     */
    private void isLogined()
    {
        //首先判断是不是登录了
        //--登录了,那就ok
        AlertDialog alertDialog = new AlertDialog(mContext);
        String loinged = (String)SPUtils.getUserInfo(mContext, Configs.LoginOrNot, Configs.NOTLOGINED);
        if (Configs.NOTLOGINED.equals(loinged))
        {
            //没有 登录
            alertDialog.builder().setTitle("提示")
                    .setMsg("您还没登录,请先登录!!")
                    .setCancelable(false)
                    .setPositiveButton("去登录", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent();
                            intent.setClass(mContext, LoginActivity.class);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("取消", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            AppManager.getAppManager().finishAllActivity();
                        }
                    })
                    .show();
            return;
        }
        else
        {
            //已登录  判断是不是添加了车型
            Json2LoginBean json2LoginBean = Configs.getLoginedInfo(mContext);
            String cartype = json2LoginBean.getCarType();
            final String userid = json2LoginBean.getUserid();
            int returnCode = json2LoginBean.getReturnCode();
            String status = json2LoginBean.getStatus();
            if (returnCode == 0)//表示成功的
            {
                if ("0".equals(status))
                {
                    //用户未审核处在正在审核
                    toastMgr.builder.display("您正在审核中,暂时不可登陆",1);
                    alertDialog.builder().setTitle("提示")
                            .setMsg("您正在审核中,暂时不可操作!")
                            .setCancelable(false)
                            .setPositiveButton("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent();
                                    intent.setClass(mContext, UploadedInfosCheckActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("status", "0");
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                }
                            })
                            .setNegativeButton("取消", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    AppManager.getAppManager().finishAllActivity();
                                }
                            })
                            .show();
                    return;
                }
                else if ("2".equals(status))
                {
                    //用户未审核处在正在审核
                    toastMgr.builder.display("您审核不通过,暂时不可登陆",1);
                    alertDialog.builder().setTitle("提示")
                            .setMsg("您审核不通过,暂时不可操作!")
                            .setCancelable(false)
                            .setPositiveButton("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent();
                                    intent.setClass(mContext, UploadedInfosCheckActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("status", "2");
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                }
                            })
                            .setNegativeButton("取消", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    AppManager.getAppManager().finishAllActivity();
                                }
                            })
                            .show();
                    return;
                }
            }
            if (cartype == null || cartype.equals(""))
            {
                //没有 登录
                alertDialog.builder().setTitle("提示")
                        .setMsg("您还未添加车型,请添加!")
                        .setCancelable(false)
                        .setPositiveButton("去添加", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent();
                                intent.setClass(mContext, RegisterDetailsActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("userid", userid);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        })
                        .show();
                return;
            }

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
            errorDisplayTips("网络错误,请连接网络点击此处重新加载!");
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
                    initTabs(pageTabsBeanList);
                }

            }
        }
    }

    /**
     * 当出现错误的时候  提示画面
     */
    private void errorDisplayTips(String tips)
    {
        mMapView.setVisibility(View.GONE);
        first_page_location_btn.setVisibility(View.GONE);
        nothing_layout.setVisibility(View.VISIBLE);
        text_empty.setText(tips);
    }

    /**
     * 出现错误后点击重新加载  正常的画面
     */
    private void reloadDisplayTips()
    {
        mMapView.setVisibility(View.VISIBLE);
        first_page_location_btn.setVisibility(View.VISIBLE);
        nothing_layout.setVisibility(View.GONE);
    }


    private void initMAp()
    {

        mBaiduMap = mMapView.getMap();
        //初始化定位
        mBaiduMap.setMyLocationEnabled(true);//使能百度地图的定位功能
        mMapView.showZoomControls(false);
        View child = mMapView.getChildAt(1);
        if (child != null && (child instanceof ImageView || child instanceof ZoomControls)) {
            child.setVisibility(View.INVISIBLE);
        }

        mLocationClient.registerLocationListener(myListener);    //注册监听函数
        //初始化定位参数
        initLocation();
        /**
         * 覆盖物点击
         */
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                Json2FirstPageShopBean bean1 = (Json2FirstPageShopBean) marker.getExtraInfo().getSerializable("marker");

                List<Json2FirstPageShopBean> list = new ArrayList<Json2FirstPageShopBean>();
                for (Json2FirstPageShopBean bean : mJson2FirstPageShopBeanList)
                {
                    if (bean.getOrder() != bean1.getOrder())
                    {list.add(bean);}
                }
                list.add(0, bean1);


                final ActionSheetDialogList dialogList = new ActionSheetDialogList(mContext);
                dialogList.builder().setTitle("请选择您要的操作")
                        .setCancelable(false)
                        .setCanceledOnTouchOutside(true)
                        .setOnItemClickListener(new ActionSheetDialogList.OnSheetItemClickListener() {
                            @Override
                            public void onItemClick(Json2FirstPageShopBean shop, int which, int position) {
                                if (which == SHOP_PHOTO_CLICK)
                                {
                                    Intent intent = new Intent();
                                    intent.setClass(mContext, FirstPageShopShowActivity.class);
                                    intent.putExtra("shopBean", shop);
                                    startActivity(intent);
                                    dialogList.dismiss();
                                }
                                else {
                                    dialogList.dismiss();
                                    String phone = shop.getPhoneNum();
                                    toastMgr.builder.display("call",1);
                                    makePhoneCall(phone);
                                }
                            }
                        })
                        .setSheetItemList(list)
                        .show();
                return true;
            }
        });
        mLocationClient.start();
    }


    private void makePhoneCall(final String phoneNUm)
    {
        new AlertDialog(mContext).builder()
                .setTitle("打电话给店主吗").setMsg("确定退出吗?").setCancelable(false)
                .setPositiveButton("现在打", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse("tel:" + phoneNUm));
                        mContext.startActivity(intent);
                    }
                })
                .setNegativeButton("考虑下", new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {

                    }
                })
                .show();
    }


    int SHOP_PHOTO_CLICK = 1;
    int SHOP_MAKE_CALL   = 2;

    /**
     * s设置顶部tab的名字
     * @param pageTabsBeanList
     */
    void initTabs(List<Json2FirstPageTabsBean> pageTabsBeanList)
    {
        tabsAdapter = new ScrollTabsAdapter1(this);
        for (Json2FirstPageTabsBean bean : pageTabsBeanList)
        {
            tabsAdapter.add(bean);
        }
        first_page_nav_tabs.setAdapter(tabsAdapter);
    }

    /**
     * 初始化定位配置参数
     */
    private void initLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span=5000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }

    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {

            mProgressDialog = mProgressDialog.show(mContext,"正在定位...", false, null);

            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                mProgressDialog.dismiss();
                errorDisplayTips("定位失败, 请确保开启网络,然后重试!!");
                toastMgr.builder.display("定位失败, 请确保开启网络,然后重试!!", 1);
                return;
            }
            //获取定位信息
            mCountry  = location.getCountry();
            mProvince = location.getProvince();
            mCity     = location.getCity();
            mCityCode = location.getCityCode();
            mDistrict  = location.getDistrict();
            mStreet   = location.getStreet();

            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                            // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            if (isFirstLoc || mNavTabClick) {
                isFirstLoc = false;
                mNavTabClick = false;

                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(12.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                longitude = location.getLongitude();
                latitude  = location.getLatitude();


                //首次定位成功才会去请求附近的店铺, 成功之后添加覆盖物


                httpSearchShop(mProvince, mCity, mDistrict, mTabapter.getTabsList().get(mPosition).getId(), longitude, latitude, FITST_GET_SHOP);
                //initOverlay(1, mJson2FirstPageShopBeanList);
            }
            longitude = location.getLongitude();
            latitude  = location.getLatitude();

            L.d(TAG + "当前经纬度",longitude +  "=="+ latitude + "");
            if (mLocationBtnClick)
            {
                mLocationBtnClick = false;
                mProgressDialog.dismiss();
            }
            mLocationClient.stop();
        }

    }

    /**
     * 去请求附近的店铺
     * @param Province
     * @param City
     * @param serviceId  这是上面tab点击的时候对应的id   这里还需要逻辑处理
     * @param District
     * @param longitude
     * @param latitude
     */
    private void httpSearchShop(String Province, String City, String District, String serviceId, double longitude, double latitude, int condition) {

        mProgressDialog.setMessage("正在加载店铺信息...");
        if (condition == FITST_GET_SHOP)
        {

            OkHttpUtils.post()
                    .url(Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GET_SHOP)
                    .addParams("sqlName", "clientSearchShop")
                    .addParams("dataReqModel.args.logicalService",serviceId)
                    .addParams("dataReqModel.args.needTotal", "needTotal")
                    .addParams("dataReqModel.args.shopProvince", String2UTF8.getUTF8String(Province))
                    .addParams("dataReqModel.args.shopCity", String2UTF8.getUTF8String(City))
                    .addParams("dataReqModel.args.shopDistrict", String2UTF8.getUTF8String(District))
                    .addParams("shopReq.lon", longitude + "")
                    .addParams("shopReq.lat",latitude + "")
                    .build()
                    .execute(new GetShopCallback());
            CURRENT_GET_SHOP_TIME = 1;
            L.i(TAG, "获取首页店铺url = " + Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GET_SHOP + "?"
                            + "sqlName=" + "clientSearchShop"
                            + "&dataReqModel.args.logicalService=" + serviceId
                            + "&dataReqModel.args.needTotal=needTotal"
                            + "&dataReqModel.args.shopProvince=" + Province
                            + "&dataReqModel.args.shopCity=" + City
                            + "&dataReqModel.args.shopDistrict=" + District
                            + "&shopReq.lon=" + longitude
                            + "&shopReq.lat=" + latitude
            );
        }
        else if (condition == SECOND_GET_SHOP)
        {
            //
            OkHttpUtils.post()
                    .url(Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GET_SHOP)
                    .addParams("sqlName", "clientSearchShop")
                    .addParams("dataReqModel.args.logicalService",serviceId)
                    .addParams("dataReqModel.args.needTotal","needTotal")
                    .addParams("dataReqModel.args.shopProvince", String2UTF8.getUTF8String(Province))
                    .addParams("dataReqModel.args.shopCity", String2UTF8.getUTF8String(City))
                    .addParams("shopReq.lon",longitude + "")
                    .addParams("shopReq.lat",latitude + "")
                    .build()
                    .execute(new GetShopCallback());
            CURRENT_GET_SHOP_TIME = 2;
            L.i(TAG, "获取首页店铺 市 url = " + Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GET_SHOP + "?"
                            + "sqlName=" + "clientSearchShop"
                            + "&dataReqModel.args.logicalService=" + serviceId
                            + "&dataReqModel.args.needTotal=needTotal"
                            + "&dataReqModel.args.shopProvince=" + Province
                            + "&dataReqModel.args.shopCity=" + City
                            + "&shopReq.lon=" + longitude
                            + "&shopReq.lat=" + latitude
            );
        }
        else if (condition == THIRD_GET_SHOP)
        {
            OkHttpUtils.post()
                    .url(Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GET_SHOP)
                    .addParams("sqlName", "clientSearchShop")
                    .addParams("dataReqModel.args.logicalService",serviceId)
                    .addParams("dataReqModel.args.needTotal","needTotal")
                    .addParams("dataReqModel.args.shopProvince", String2UTF8.getUTF8String(Province))
                    .addParams("shopReq.lon", longitude + "")
                    .addParams("shopReq.lat",latitude + "")
                    .build()
                    .execute(new GetShopCallback());
            CURRENT_GET_SHOP_TIME = 3;
            L.i(TAG, "获取首页店铺 省url = " + Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GET_SHOP + "?"
                            + "sqlName=" + "clientSearchShop"
                            + "&dataReqModel.args.logicalService=" + serviceId
                            + "&dataReqModel.args.needTotal=needTotal"
                            + "&dataReqModel.args.shopProvince=" + Province
                            + "&shopReq.lon=" + longitude
                            + "&shopReq.lat=" + latitude
            );
        }

    }

    class GetShopCallback extends StringCallback
    {

        @Override
        public void onError(Call call, Exception e) {
            toastMgr.builder.display("网络错误, 请重试!", 0);
            mProgressDialog.dismiss();
        }

        @Override
        public void onResponse(String response) {
            L.d(TAG, "获取店铺 json = " + response );
            Json2FirstPageShop json2FirstPageShop = new Json2FirstPageShop(response);
            List<Json2FirstPageShopBean> json2FirstPageShopBeanList = json2FirstPageShop.getFirstPageShop();
            mJson2FirstPageShopBeanList = json2FirstPageShopBeanList;
            if (json2FirstPageShopBeanList == null)
            {
                //解析json错误 就是服务器错误 提示更新app???
                toastMgr.builder.display("更新app", 0);
                mProgressDialog.dismiss();
            }
            else
            {
                if (json2FirstPageShopBeanList.get(0).getId().equals("0"))
                {
                    //证明附近没有店铺需要重新获取
                    if (CURRENT_GET_SHOP_TIME == 1)
                    {
                        //去请求市
                        httpSearchShop(mProvince, mCity, mDistrict, mPageTabsBeanList.get(0).getId(), longitude, latitude, CURRENT_GET_SHOP_TIME+1);
                    }
                    else if (CURRENT_GET_SHOP_TIME == 2)
                    {
                        //去请求省
                        httpSearchShop(mProvince, mCity, mDistrict, mPageTabsBeanList.get(0).getId(), longitude, latitude, CURRENT_GET_SHOP_TIME + 1);

                    }
                    else
                    {
                        mProgressDialog.dismiss();
                        AlertDialog alertDialog = new AlertDialog(mContext);
                        alertDialog.builder().setCancelable(false)
                                .setTitle("您所在区域暂无店铺,请持续关注...")
                                .setPositiveButton("确定", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                    }
                                })
                                .show();
                    }
                }
                else
                {
                    //里面有数据, 那就显示出来数据
                    mProgressDialog.dismiss();
                    initOverlay(mPosition, mJson2FirstPageShopBeanList);
                    mShopBeanResponse = response;

                }
            }
        }
    }

    /**
     * 初始化覆盖物
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void initOverlay(int nowPage, List<Json2FirstPageShopBean> jsonPageShopBeanList) {
        //这里是根据网上拿到的数据,去看要添加什么覆盖物
        //这里可以抽象出一个类  然后后面就会方便
        // add marker overlay
        List<LatLng> lls = new ArrayList<>();
        int size = jsonPageShopBeanList.size();

        for (int i = 0; i < size; i++)
        {
            double _lat =  jsonPageShopBeanList.get(i).getShopLatitude();

            double _lon = jsonPageShopBeanList.get(i).getShopLongitude();
            L.d(TAG, "覆盖物经纬度,la = " + _lat + "lo = " + _lon);
            LatLng ll = new LatLng(_lat, _lon);

            ViewGroupToBitmap vb = new ViewGroupToBitmap();

            LayoutInflater inflater = LayoutInflater.from(this);
            //首页地图覆盖物 view转变
            View view = inflater.inflate(R.layout.view_to_marker2, null);
            //修车店标号
            TextView shop_num = (TextView) view.findViewById(R.id.marker_shop_num);
            shop_num.setText(jsonPageShopBeanList.get(i).getOrder()+"");
            //修车店名称
            TextView textView = (TextView) view.findViewById(R.id.marker_info);
            textView.setText(jsonPageShopBeanList.get(i).getShopName());

            LinearLayout imageView = (LinearLayout) view.findViewById(R.id.marker_ll_image);
            switch (nowPage+1)
            {
                case ALL_CAR_PAGE:
                    imageView.setBackground(getResources().getDrawable(R.mipmap.marker_one_1));
                    break;
                case BAO_YANG_PAGE:
                    imageView.setBackground(getResources().getDrawable(R.mipmap.marker_one_2));
                    break;
                case DIAN_LU_PAGE:
                    imageView.setBackground(getResources().getDrawable(R.mipmap.marker_one_3));
                    break;
                case PEI_JIAN_PAGE:
                    imageView.setBackground(getResources().getDrawable(R.mipmap.marker_one_4));
                    break;
                case YOU_LU_PAGE:
                    imageView.setBackground(getResources().getDrawable(R.mipmap.marker_one_5));
                    break;
                default:
                    imageView.setBackground(getResources().getDrawable(R.mipmap.marker_one_1));
                    break;
            }
            bdA = BitmapDescriptorFactory.fromBitmap(vb.getViewBitmap(view));
            //第一个覆盖物
            MarkerOptions ooA = new MarkerOptions().position(ll).icon(bdA)
                    .zIndex(9).draggable(true);
            // 掉下动画
            ooA.animateType(MarkerOptions.MarkerAnimateType.drop);
            mMarkerA = (Marker) (mBaiduMap.addOverlay(ooA));
            Bundle bundle = new Bundle();
            bundle.putSerializable("marker", jsonPageShopBeanList.get(i));
            mMarkerA.setExtraInfo(bundle);

            LatLng loc = new LatLng(latitude, longitude);
            MapStatusUpdate u = MapStatusUpdateFactory
                    .newLatLng(loc);
            mBaiduMap.setMapStatus(u);
        }
    }

    /**
     * 清除所有Overlay
     *
     * @param view
     */
    public void clearOverlay(View view) {
        mBaiduMap.clear();
        mMarkerA = null;
    }


    private TabAdapter1 mTabapter;
    private int mPosition;//当前是哪一个tab被点击了

    /**
     * 关联
     */
    private void findViews() {
        //地图
        mMapView = (MapView) findViewById(R.id.first_page_mapview);

        first_page_nav_tabs = (ScrollTabView1) findViewById(R.id.first_page_nav_tabs);
        nothing_layout = (LinearLayout) findViewById(R.id.nothing_layout);//网络错误显示
        text_empty = (TextView) findViewById(R.id.text_empty);//显示网络错误

        //搜索
        first_page_search = (ImageView) findViewById(R.id.search_image_new);

        //定位
        first_page_location_btn = (Button) findViewById(R.id.first_page_location_btn);

        /**
         * 设置监听器
         */

        first_page_search.setOnClickListener(this);
        first_page_location_btn.setOnClickListener(this);
        first_page_nav_tabs.setOnItemClickListener(new ScrollTabView1.OnItemClickListener() {
            @Override
            public void onTabItemClick(TabAdapter1 tabAdapter, int pos) {
                //首先就是第一item点击
                mTabapter = tabAdapter;
                mPosition = pos;
                //在这里去定位   获取经纬度   加载店铺信息
                if (isFirstLoc)
                {
                    initMAp();
                }
                else{
                    mLocationClient.start();
                }

                clearOverlay(null);
                mNavTabClick = true;
            }
        });
        text_empty.setOnClickListener(this);
    }

    private boolean mNavTabClick = false;//是不是导航tab被点击, 标记, 点击的时候才可以定位, 去后台拿数据
    private boolean mLocationBtnClick = false;//是不是定位按钮被点击, 标记, 点击的时候才可以定位, 去后台拿数据


   @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View view) {
        switch (view.getId())
        {

//            case R.id.layout_order5:
//                CURRENT_GET_SHOP_TIME = 1;//每次点击都默认是第一次去拿店铺
//                TextView01.setBackground(getResources().getDrawable(R.mipmap.all_car));
//                TextView02.setBackground(getResources().getDrawable(R.mipmap.baoyang));
//                TextView03.setBackground(getResources().getDrawable(R.mipmap.dianlu));
//                TextView04.setBackground(getResources().getDrawable(R.mipmap.peijian));
//                TextView05.setBackground(getResources().getDrawable(R.mipmap.youlu_selected));
//
//                if (currentPage != YOU_LU_PAGE)
//                {

//                    locationClick();
//                    initOverlay(YOU_LU_PAGE,mJson2FirstPageShopBeanList);
//                }
//                currentPage = YOU_LU_PAGE;
//                break;
            //搜素
            case R.id.search_image_new:
                Intent intent3 = new Intent();
                intent3.setClass(FirstPagenew2Activity.this, SearchActivity.class);
                startActivity(intent3);

                break;
            //定位
            case R.id.first_page_location_btn://这里按钮还要适配  还缺一种状态
                locationClick();
                mLocationBtnClick = true;
                break;

            //重新加载
            case R.id.text_empty:
                //这里重新加载
                reloadDisplayTips();
                //去拿首页上面导航的5getab
                httpGetFirstPageTopTab();
                break;

        }

    }

    /**
     * 点击定位
     */
    private void locationClick() {

        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
        mBaiduMap
                .setMyLocationConfigeration(new MyLocationConfiguration(
                        mCurrentMode, true, null));
        //当前位置显示在地图中心左右
        MapStatusUpdate status = MapStatusUpdateFactory.newLatLng(new LatLng(latitude, longitude));
        mBaiduMap.setMapStatus(status);
        //clearOverlay(null);
        mLocationClient.start();
        //initOverlay(currentPage, mJson2FirstPageShopBeanList);

    }


    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
        //回收bitmap资源
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        toastMgr.builder.display("onresume", 1);

        mMapView.onResume();
        isLogined();
    }
    @Override
    protected void onPause()
    {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }
}
