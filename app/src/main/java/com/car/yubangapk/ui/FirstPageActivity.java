package com.car.yubangapk.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ZoomControls;

import com.car.yubangapk.app.AppManager;
import com.car.yubangapk.configs.Configs;
import com.car.yubangapk.json.bean.Json2FirstPageShopBean;
import com.car.yubangapk.json.bean.Json2FirstPageTabsBean;
import com.car.yubangapk.json.bean.Json2LoginBean;
import com.car.yubangapk.json.formatJson.Json2FirstPageShop;
import com.car.yubangapk.json.formatJson.Json2FirstPageTabs;
import com.car.yubangapk.network.okhttp.OkHttpUtils;
import com.car.yubangapk.network.okhttp.callback.StringCallback;
import com.car.yubangapk.utils.BDMapData;
import com.car.yubangapk.utils.L;
import com.car.yubangapk.utils.SPUtils;
import com.car.yubangapk.utils.ViewGroupToBitmap;
import com.car.yubangapk.utils.toastMgr;
import com.andy.android.yubang.R;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
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
import com.car.yubangapk.view.AlertDialog;
import com.car.yubangapk.view.CustomProgressDialog;

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
public class FirstPageActivity extends BaseActivity implements View.OnClickListener{


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
    private RelativeLayout  layout_order1;
    private TextView        TextView01;

    private RelativeLayout  layout_order2;
    private TextView        TextView02;

    private RelativeLayout  layout_order3;
    private TextView        TextView03;

    private RelativeLayout  layout_order4;
    private TextView        TextView04;

    private RelativeLayout  layout_order5;
    private TextView        TextView05;


    private Button          first_page_location_btn;//定位按钮


    private int isLocationedSuccess;
    private static final int GPS_LOC_SUCCESS = 61;//GPS定位结果，GPS定位成功。
    private static final int NET_LOC_SUCCESS = 61;//网络定位结果，网络定位定位成功

    boolean isFirstLoc = true; // 是否首次定位
    private MyLocationConfiguration.LocationMode mCurrentMode;
    private Marker mMarkerA;
    private Marker mMarkerB;
    private Marker mMarkerC;
    private Marker mMarkerD;



    // 初始化全局 bitmap 信息，不用时及时 recycle
    BitmapDescriptor bdA;

    BitmapDescriptor bdB;
    BitmapDescriptor bdC;
    BitmapDescriptor bdD;
    BitmapDescriptor bd;
    BitmapDescriptor bdGround;

    // 点击地图上标注物之后  弹出下面的东西
    private LinearLayout bottom;
    private ImageView   image1;
    private ImageView   image2;
    private ImageView   image3;

    //进度条
    private ProgressBar first_page_progressbar;


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
        setContentView(R.layout.activity_first_page);

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
     * 判断用户是否已经登录
     */
    private void isLogined() {
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
            first_page_progressbar.setVisibility(View.GONE);
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
                    initMAp();
                    mPageTabsBeanList = pageTabsBeanList;
                    setTabsName(pageTabsBeanList);
                }

            }



        }
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

        mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);    //注册监听函数
        //初始化定位参数
        initLocation();
        /**
         * 覆盖物点击
         */
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {


                Intent intent = new Intent();
                intent.setClass(FirstPageActivity.this, FirstPageMarkerClickedActivity.class);
                intent.putExtra("shopBean", mShopBeanResponse);
                //这里需要把要显示的店铺信息放到FirstPageMarkerClickedActivity里面去  让它解析  然后显示
                startActivity(intent);
                return true;
            }
        });

        mLocationClient.start();
    }


    /**
     * s设置顶部tab的名字
     * @param pageTabsBeanList
     */
    private void setTabsName(List<Json2FirstPageTabsBean> pageTabsBeanList) {

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
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
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
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(12.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                longitude = location.getLongitude();
                latitude  = location.getLatitude();


                //首次定位成功才会去请求附近的店铺, 成功之后添加覆盖物
                mProgressDialog = mProgressDialog.show(mContext,"正在定位", false, null);

                httpSearchShop(mProvince, mCity, mDistrict, mPageTabsBeanList.get(0).getId(), longitude, latitude, FITST_GET_SHOP);
                //initOverlay(1, mJson2FirstPageShopBeanList);
            }
            longitude = location.getLongitude();
            latitude  = location.getLatitude();

            L.d(TAG + "当前经纬度",longitude +  "=="+ latitude + "");

            mLocationClient.stop();
        }

        public void onReceivePoi(BDLocation poiLocation) {
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
                    .addParams("dataReqModel.args.shopProvince", getUTF8XMLString(Province))
                    .addParams("dataReqModel.args.shopCity",getUTF8XMLString(City))
                    .addParams("dataReqModel.args.shopDistrict",getUTF8XMLString(District))
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
                    .addParams("dataReqModel.args.shopProvince",getUTF8XMLString(Province))
                    .addParams("dataReqModel.args.shopCity",getUTF8XMLString(City))
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
                    .addParams("dataReqModel.args.shopProvince",getUTF8XMLString(Province))
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
                        httpSearchShop(mProvince, mCity, mDistrict, json2FirstPageShopBeanList.get(0).getId(), longitude, latitude, CURRENT_GET_SHOP_TIME+1);
                    }
                    else if (CURRENT_GET_SHOP_TIME == 2)
                    {
                        //去请求省
                        httpSearchShop(mProvince, mCity, mDistrict, json2FirstPageShopBeanList.get(0).getId(), longitude, latitude, CURRENT_GET_SHOP_TIME+1);
                        first_page_progressbar.setVisibility(View.GONE);
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
                    initOverlay(1, mJson2FirstPageShopBeanList);
                    mShopBeanResponse = response;

                }
            }
        }
    }


    private void showLoc(BDLocation location) {
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
// 构造定位数据
        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(location.getRadius())
                        // 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(100).latitude(location.getLatitude())
                .longitude(location.getLongitude()).build();
        mBaiduMap.setMyLocationData(locData);    //设置定位数据
// 设置定位数据
        mBaiduMap.setMyLocationData(locData);


        // 设置定位图层的配置（定位模式，是否允许方向信息，用户自定义定位图标）
        BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory
                .fromResource(R.mipmap.location_icon);
        MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL, true, mCurrentMarker);
        mBaiduMap.setMyLocationConfigeration(config);
        // 当不需要定位图层时关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);

    }

    //初始化每个覆盖物对应的信息
    private void initMapDataList() {
        bdMapClientList = new ArrayList<>();
        //让所有覆盖物的经纬度与你自己的经纬度相近，以便打开地图就能看到那些覆盖物
        bdMapClientList.add(new BDMapData("当前位置", latitude, longitude));
        bdMapClientList.add(new BDMapData("领卓科技", latitude + 0.024, longitude - 0.0105));
        bdMapClientList.add(new BDMapData("蓝翔驾校", latitude - 0.00052, longitude - 0.01086));
        bdMapClientList.add(new BDMapData("优衣库折扣店", latitude + 0.0124, longitude + 0.00184));
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
            switch (nowPage)
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
            }
            bdA = BitmapDescriptorFactory.fromBitmap(vb.getViewBitmap(view));
            //第一个覆盖物
            MarkerOptions ooA = new MarkerOptions().position(ll).icon(bdA)
                    .zIndex(9).draggable(true);
            // 掉下动画
            ooA.animateType(MarkerOptions.MarkerAnimateType.drop);
            mMarkerA = (Marker) (mBaiduMap.addOverlay(ooA));

            LatLng loc = new LatLng(latitude, longitude);
            MapStatusUpdate u = MapStatusUpdateFactory
                    .newLatLng(loc);
            mBaiduMap.setMapStatus(u);

            first_page_progressbar.setVisibility(View.GONE);

        }

//        LatLng llA = new LatLng(latitude + 0.024, longitude + 0.024);
//        LatLng llB = new LatLng(latitude - 0.0024, longitude + 0.024);
//        LatLng llC = new LatLng(latitude + 0.0024, longitude - 0.024);
//        LatLng llD = new LatLng(latitude - 0.024, longitude - 0.024);
//
//        ViewGroupToBitmap vb = new ViewGroupToBitmap();
//
//        LayoutInflater inflater = LayoutInflater.from(this);
//        //首页地图覆盖物 view转变
//        View view = inflater.inflate(R.layout.view_to_marker2, null);
//        //修车店标号
//        TextView shop_num = (TextView) view.findViewById(R.id.marker_shop_num);
//        shop_num.setText("1");
//        //修车店名称
//        TextView textView = (TextView) view.findViewById(R.id.marker_info);
//        textView.setText("高德汇001");
//
//        LinearLayout imageView = (LinearLayout) view.findViewById(R.id.marker_ll_image);
//        switch (nowPage)
//        {
//            case ALL_CAR_PAGE:
//                imageView.setBackground(getResources().getDrawable(R.mipmap.marker_one_1));
//                break;
//            case BAO_YANG_PAGE:
//                imageView.setBackground(getResources().getDrawable(R.mipmap.marker_one_2));
//                break;
//            case DIAN_LU_PAGE:
//                imageView.setBackground(getResources().getDrawable(R.mipmap.marker_one_3));
//                break;
//            case PEI_JIAN_PAGE:
//                imageView.setBackground(getResources().getDrawable(R.mipmap.marker_one_4));
//                break;
//            case YOU_LU_PAGE:
//                imageView.setBackground(getResources().getDrawable(R.mipmap.marker_one_5));
//                break;
//        }
//
//
//        bdA = BitmapDescriptorFactory.fromBitmap(vb.getViewBitmap(view));
//
//        //第一个覆盖物
//        MarkerOptions ooA = new MarkerOptions().position(llA).icon(bdA)
//                .zIndex(9).draggable(true);
//        // 掉下动画
//        ooA.animateType(MarkerOptions.MarkerAnimateType.drop);
//        mMarkerA = (Marker) (mBaiduMap.addOverlay(ooA));
//
//        //第二个覆盖物
//        MarkerOptions ooB = new MarkerOptions().position(llB).icon(bdA)
//                .zIndex(5);
//        // 掉下动画
//        ooB.animateType(MarkerOptions.MarkerAnimateType.drop);
//        mMarkerB = (Marker) (mBaiduMap.addOverlay(ooB));
//
//        //第三个覆盖物
//        MarkerOptions ooC = new MarkerOptions().position(llC).icon(bdA)
//                .perspective(false).zIndex(7);
//        // 生长动画
//        ooC.animateType(MarkerOptions.MarkerAnimateType.drop);
//        mMarkerC = (Marker) (mBaiduMap.addOverlay(ooC));
//
//        //第四个覆盖物
//        MarkerOptions ooD = new MarkerOptions().position(llD).icon(bdA)
//                .zIndex(0);
//        // 生长动画
//        ooD.animateType(MarkerOptions.MarkerAnimateType.drop);
//        mMarkerD = (Marker) (mBaiduMap.addOverlay(ooD));
//
//        LatLng loc = new LatLng(latitude, longitude);
//        MapStatusUpdate u = MapStatusUpdateFactory
//                .newLatLng(loc);
//        mBaiduMap.setMapStatus(u);
//
//        first_page_progressbar.setVisibility(View.GONE);


    }


    /**
     * 添加覆盖物的方法
     */
    private void addOverlay() {
        Marker marker = null;
        LatLng point = null;
        MarkerOptions option = null;
        BitmapDescriptor bitmap =BitmapDescriptorFactory.fromResource(R.mipmap.location_icon);;
        for (BDMapData data : bdMapClientList) {
            point = new LatLng(data.getLatitude(), data.getLongitude());
            option = new MarkerOptions().position(point).icon(bitmap);
            marker = (Marker) mBaiduMap.addOverlay(option);
            //Bundle用于通信
            Bundle bundle = new Bundle();
            bundle.putSerializable("info", data.getName()+ "纬度："+data.getLatitude()+   "经度："+data.getLongitude());
            marker.setExtraInfo(bundle);//将bundle值传入marker中，给baiduMap设置监听时可以得到它
        }
        //将地图移动到最后一个标志点
        MapStatusUpdate status = MapStatusUpdateFactory.newLatLng(point);
        mBaiduMap.setMapStatus(status);

    }
    /**
     * 清除所有Overlay
     *
     * @param view
     */
    public void clearOverlay(View view) {
        mBaiduMap.clear();
        mMarkerA = null;
        mMarkerB = null;
        mMarkerC = null;
        mMarkerD = null;
    }



    /**
     * 关联
     */
    private void findViews() {
        //地图
        mMapView = (MapView) findViewById(R.id.first_page_mapview);

        layout_order1    = (RelativeLayout) findViewById(R.id.layout_order1);
        TextView01      = (TextView) findViewById(R.id.TextView01);

        layout_order2   = (RelativeLayout) findViewById(R.id.layout_order2);
        TextView02      = (TextView) findViewById(R.id.TextView02);

        layout_order3   = (RelativeLayout) findViewById(R.id.layout_order3);
        TextView03      = (TextView) findViewById(R.id.TextView03);

        layout_order4    = (RelativeLayout) findViewById(R.id.layout_order4);
        TextView04       = (TextView) findViewById(R.id.TextView04);

        layout_order5 = (RelativeLayout) findViewById(R.id.layout_order5);
        TextView05 = (TextView) findViewById(R.id.TextView05);
        //这里需要改成listview
        bottom = (LinearLayout) findViewById(R.id.bottom);

        //搜索
        first_page_search = (ImageView) findViewById(R.id.search_image_new);

        //定位
        first_page_location_btn = (Button) findViewById(R.id.first_page_location_btn);
        //进度条
        first_page_progressbar = (ProgressBar) findViewById(R.id.first_page_progressbar);

        /**
         * 设置监听器
         */
        layout_order1.setOnClickListener(this);
        layout_order2.setOnClickListener(this);
        layout_order3.setOnClickListener(this);
        layout_order4.setOnClickListener(this);
        layout_order5.setOnClickListener(this);

        first_page_search.setOnClickListener(this);
        first_page_location_btn.setOnClickListener(this);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.layout_order1:
                //显示进度条
                CURRENT_GET_SHOP_TIME = 1;//每次点击都默认是第一次去拿店铺
                TextView01.setBackground(getResources().getDrawable(R.mipmap.all_car_selected));
                TextView02.setBackground(getResources().getDrawable(R.mipmap.baoyang));
                TextView03.setBackground(getResources().getDrawable(R.mipmap.dianlu));
                TextView04.setBackground(getResources().getDrawable(R.mipmap.peijian));
                TextView05.setBackground(getResources().getDrawable(R.mipmap.youlu));

                if (currentPage != ALL_CAR_PAGE)
                {
                    first_page_progressbar.setVisibility(View.VISIBLE);
                    locationClick();
                    initOverlay(ALL_CAR_PAGE,mJson2FirstPageShopBeanList);
                }
                currentPage = ALL_CAR_PAGE;

                break;
            case R.id.layout_order2:
                //显示进度条
                CURRENT_GET_SHOP_TIME = 1;//每次点击都默认是第一次去拿店铺
                TextView01.setBackground(getResources().getDrawable(R.mipmap.all_car));
                TextView02.setBackground(getResources().getDrawable(R.mipmap.baoyang_selected));//需要美工再给我一张图
                TextView03.setBackground(getResources().getDrawable(R.mipmap.dianlu));
                TextView04.setBackground(getResources().getDrawable(R.mipmap.peijian));
                TextView05.setBackground(getResources().getDrawable(R.mipmap.youlu));

                if (currentPage != BAO_YANG_PAGE)
                {
                    first_page_progressbar.setVisibility(View.VISIBLE);
                    locationClick();
                    initOverlay(BAO_YANG_PAGE,mJson2FirstPageShopBeanList);
                }
                currentPage = BAO_YANG_PAGE;

                break;
            case R.id.layout_order3:
                CURRENT_GET_SHOP_TIME = 1;//每次点击都默认是第一次去拿店铺
                TextView01.setBackground(getResources().getDrawable(R.mipmap.all_car));
                TextView02.setBackground(getResources().getDrawable(R.mipmap.baoyang));
                TextView03.setBackground(getResources().getDrawable(R.mipmap.dianlu_selected));
                TextView04.setBackground(getResources().getDrawable(R.mipmap.peijian));
                TextView05.setBackground(getResources().getDrawable(R.mipmap.youlu));

                if (currentPage != DIAN_LU_PAGE)
                {
                    first_page_progressbar.setVisibility(View.VISIBLE);
                    locationClick();
                    initOverlay(DIAN_LU_PAGE,mJson2FirstPageShopBeanList);
                }
                currentPage = DIAN_LU_PAGE;
                break;
            case R.id.layout_order4:
                CURRENT_GET_SHOP_TIME = 1;//每次点击都默认是第一次去拿店铺
                TextView01.setBackground(getResources().getDrawable(R.mipmap.all_car));
                TextView02.setBackground(getResources().getDrawable(R.mipmap.baoyang));
                TextView03.setBackground(getResources().getDrawable(R.mipmap.dianlu));
                TextView04.setBackground(getResources().getDrawable(R.mipmap.peijain_selected));
                TextView05.setBackground(getResources().getDrawable(R.mipmap.youlu));

                if (currentPage != PEI_JIAN_PAGE)
                {
                    first_page_progressbar.setVisibility(View.VISIBLE);
                    locationClick();
                    initOverlay(PEI_JIAN_PAGE,mJson2FirstPageShopBeanList);
                }
                currentPage = PEI_JIAN_PAGE;
                break;
            case R.id.layout_order5:
                CURRENT_GET_SHOP_TIME = 1;//每次点击都默认是第一次去拿店铺
                TextView01.setBackground(getResources().getDrawable(R.mipmap.all_car));
                TextView02.setBackground(getResources().getDrawable(R.mipmap.baoyang));
                TextView03.setBackground(getResources().getDrawable(R.mipmap.dianlu));
                TextView04.setBackground(getResources().getDrawable(R.mipmap.peijian));
                TextView05.setBackground(getResources().getDrawable(R.mipmap.youlu_selected));

                if (currentPage != YOU_LU_PAGE)
                {
                    first_page_progressbar.setVisibility(View.VISIBLE);
                    locationClick();
                    initOverlay(YOU_LU_PAGE,mJson2FirstPageShopBeanList);
                }
                currentPage = YOU_LU_PAGE;
                break;
            case R.id.image1:
                //点击店铺 跳转
                Intent intent = new Intent();
                intent.setClass(FirstPageActivity.this, FirstPageShopShowActivity.class);
                startActivity(intent);
                toastMgr.builder.display("Big shop Photo",  0);
                break;
            case R.id.image2:
                //点击店铺  跳转
                Intent intent1 = new Intent();
                intent1.setClass(FirstPageActivity.this, FirstPageShopShowActivity.class);
                startActivity(intent1);
                toastMgr.builder.display("Big shop Photo",  0);
                break;
            case R.id.image3:
                //点击店铺 跳转
                Intent intent2 = new Intent();
                intent2.setClass(FirstPageActivity.this, FirstPageShopShowActivity.class);
                startActivity(intent2);
                toastMgr.builder.display("Big shop Photo",  0);
                break;
            //搜素
            case R.id.search_image_new:
                Intent intent3 = new Intent();
                intent3.setClass(FirstPageActivity.this, SearchActivity.class);
                startActivity(intent3);

                break;
            //定位
            case R.id.first_page_location_btn://这里按钮还要适配  还缺一种状态
                locationClick();
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
        clearOverlay(null);
        mLocationClient.start();

        initOverlay(currentPage,mJson2FirstPageShopBeanList);

    }


    @Override
    protected void onDestroy() {
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
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }




    /**
     * MyLocationListener: 百度地图定位的监听器类
     * 1.首页要展示地图
     *
     * @author andyzhu
     * @version 1.0
     * @created 2016-03-01
     */
    public class MyLocationListener1 implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            //Receive Location
            if (location == null || mBaiduMap == null)
            {
                return;
            }
            latitude = location.getLatitude();//得到纬度
            longitude = location.getLongitude();//得到经度
            StringBuffer sb = new StringBuffer(256);
            sb.append("time : ");
            sb.append(location.getTime());
            sb.append("\nerror code : ");
            sb.append(location.getLocType());
            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());
            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());
            sb.append("\nradius : ");
            sb.append(location.getRadius());
            if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());// 单位：公里每小时
                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());
                sb.append("\nheight : ");
                sb.append(location.getAltitude());// 单位：米
                sb.append("\ndirection : ");
                sb.append(location.getDirection());// 单位度
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                sb.append("\ndescribe : ");
                sb.append("gps定位成功");
                //定位成功 就关闭定位
                mLocationClient.stop();

                showLoc(location);
            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                //运营商信息
                sb.append("\noperationers : ");
                sb.append(location.getOperators());
                sb.append("\ndescribe : ");
                sb.append("网络定位成功");
                //定位成功  就关闭定位
                mLocationClient.stop();
//                showLoc(location);
                initMapDataList();
                addOverlay();
            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                sb.append("\ndescribe : ");
                sb.append("离线定位成功，离线定位结果也是有效的");
            } else if (location.getLocType() == BDLocation.TypeServerError) {
                sb.append("\ndescribe : ");
                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                sb.append("\ndescribe : ");
                sb.append("网络不同导致定位失败，请检查网络是否通畅");
                //提示用户检查网络
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                sb.append("\ndescribe : ");
                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
            }
            sb.append("\nlocationdescribe : ");
            sb.append(location.getLocationDescribe());// 位置语义化信息
            List<Poi> list = location.getPoiList();// POI数据
            if (list != null) {
                sb.append("\npoilist size = : ");
                sb.append(list.size());
                for (Poi p : list) {
                    sb.append("\npoi= : ");
                    sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
                }
            }
            L.i("BaiduLocationApiDem", sb.toString());
        }
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
