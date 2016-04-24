package com.car.yubangapk.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ZoomControls;

import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MyLocationData;
import com.car.yubangapk.configs.Configs;
import com.car.yubangapk.configs.ErrorCodes;
import com.car.yubangapk.json.bean.Json2FirstPageShopBean;
import com.car.yubangapk.json.bean.Json2InstallShopBean;
import com.car.yubangapk.json.bean.Json2InstallShopModelsBean;
import com.car.yubangapk.json.bean.Json2RecommendShopClickBean;
import com.car.yubangapk.network.myHttpReq.HttpReqCallback;
import com.car.yubangapk.network.myHttpReq.HttpReqGetRecommendPartnerShop;
import com.car.yubangapk.network.myHttpReq.HttpReqRecommendShopClick;
import com.car.yubangapk.utils.BDMapData;
import com.car.yubangapk.utils.L;
import com.car.yubangapk.utils.ViewGroupToBitmap;
import com.car.yubangapk.utils.Warn.NotLogin;
import com.car.yubangapk.utils.Warn.UpdateApp;
import com.car.yubangapk.utils.toastMgr;
import com.andy.android.yubang.R;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.car.yubangapk.view.AlertDialog;
import com.car.yubangapk.view.CustomProgressDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * DiscoverRecommendPartnerActivity: 发现界面的推荐合伙人
 *
 * @author andyzhu
 * @version 1.0
 * @created 2016-03-02
 */
public class DiscoverRecommendPartnerActivity extends BaseActivity implements View.OnClickListener {

    private String TAG = DiscoverRecommendPartnerActivity.class.getSimpleName();
    private ImageView img_back;
    private TextView  discover_add_partner;//新增
    private Button      recommend_partner_location_btn;//定位

    private Context mContext;

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
    boolean isFirstLoc = true; // 是否首次定位
    /**
     * MapView 是地图主控件
     */
    private MapView mMapView;
    private SDKInitializer initializer;
    private BaiduMap mBaiduMap;

    //定位
    public LocationClient mLocationClient = null;

    CustomProgressDialog mProgressDialog;
    private boolean isLocationBtnClicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_discover_recommend_partner);
        mContext = this;
        findViews();
        mProgressDialog = new CustomProgressDialog(mContext);
        /**
         * 1.获取定位
         * 2.定位成功
         * 3.后台拿数据
         * 4.拿数据成功
         * 5.展示,添加覆盖物
         * 6.设置覆盖物点击事件
         */
        startLocation();


    }

    /**
     * 开始定位
     */
    private void startLocation() {
        mProgressDialog = mProgressDialog.show(mContext,"正在定位", false, null);
        initMAp();
    }


    /**
     * 初始化地图
     */
    private void initMAp() {
        mBaiduMap = mMapView.getMap();
        //初始化定位
        mBaiduMap.setMyLocationEnabled(true);//使能百度地图的定位功能
        mMapView.showZoomControls(false);
        View child = mMapView.getChildAt(1);
        if (child != null && (child instanceof ImageView || child instanceof ZoomControls)) {
            child.setVisibility(View.INVISIBLE);
        }

        mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
        BDLocationListener myListener = new MyLocationListener();
        //初始化定位参数
        initLocation();
        mLocationClient.registerLocationListener(myListener);    //注册监听函数
        //开始定位
        mLocationClient.start();

    }
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
     * MyLocationListener: 百度地图定位的监听器类
     * 1.首页要展示地图
     *
     * @author andyzhu
     * @version 1.0
     * @created 2016-03-01
     */
    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                mProgressDialog.dismiss();
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

            if (isFirstLoc || isLocationBtnClicked == true) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(12.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                longitude = location.getLongitude();
                latitude  = location.getLatitude();


                //首次定位成功才会去请求附近的店铺, 成功之后添加覆盖物
                String userid = Configs.getLoginedInfo(mContext).getUserid();
                String carType = Configs.getLoginedInfo(mContext).getCarType();
                httpGetRecommendShop(userid, longitude, latitude, carType, mProvince, mCity, mDistrict);
            }

            L.d(TAG + "当前经纬度",longitude +  "=="+ latitude + "");

            mLocationClient.stop();
        }
    }

    /**
     * 去后台拿推荐合伙人店铺
     * @param userid
     * @param longitude
     * @param latitude
     * @param carType
     * @param province
     * @param city
     * @param district
     */
    private void httpGetRecommendShop(String userid, double longitude, double latitude, String carType, String province, String city, String district) {

        mProgressDialog.setMessage("正在获取附近店铺...");
        HttpReqGetRecommendPartnerShop req = new HttpReqGetRecommendPartnerShop();
        req.setCallback(new GetRecommendShop());
        req.getRecommendShop(userid, longitude, latitude, carType, province, city, district);

    }
    class GetRecommendShop implements HttpReqCallback
    {

        @Override
        public void onFail(int errorCode, String message) {
            mProgressDialog.dismiss();
            if (errorCode == ErrorCodes.ERROR_CODE_NOT_LOGIN)
            {
                NotLogin.gotoLogin(DiscoverRecommendPartnerActivity.this);
                return;
            }
            else if (errorCode == ErrorCodes.ERROR_CODE_LOW_VERSION)
            {
                UpdateApp.gotoUpdateApp(mContext);
                return;
            }
            else if (errorCode == ErrorCodes.ERROR_CODE_NO_DATA)
            {
                toastMgr.builder.display("附近没有店铺", 1);
            }
            else
            {
                toastMgr.builder.display(message, 1);
            }

        }

        @Override
        public void onSuccess(Object object) {
            mProgressDialog.dismiss();
            Json2InstallShopBean shopBean = (Json2InstallShopBean) object;
            List<Json2InstallShopModelsBean> shops = shopBean.getShopModels();
            mShops = shops;

            double mostFar = 0;
            mostFar = getMostFarDistance(shops);
            initOverlay(shops, mostFar);
        }
    }

    List<Json2InstallShopModelsBean> mShops;
    /**
     * 拿到距离当前位置最远的距离
     * 方便地图缩放显示
     * @param shops
     * @return
     */
    private double getMostFarDistance(List<Json2InstallShopModelsBean> shops)
    {
        //去遍历店铺   找到最远的店铺
        double distance = 0;
        double lastDistance = 0;
        double mostFarDistance = 0;
        for (Json2InstallShopModelsBean shop:shops)
        {
            distance = shop.getDistance();

            if (distance >= lastDistance)
            {
                lastDistance = distance;
            }
        }
        if (distance >= lastDistance)
        {
            mostFarDistance = distance;
        }
        else
        {
            mostFarDistance = lastDistance;
        }
        return mostFarDistance;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void initOverlay(List<Json2InstallShopModelsBean> shops , double distance) {
        //这里是根据网上拿到的数据,去看要添加什么覆盖物
        //这里可以抽象出一个类  然后后面就会方便
        // add marker overlay
        List<LatLng> lls = new ArrayList<>();
        int size = shops.size();

        for (int i = 0; i < size; i++) {
            double _lat = shops.get(i).getLat();

            double _lon = shops.get(i).getLon();
            L.d(TAG, "覆盖物经纬度,la = " + _lat + "lo = " + _lon);
            LatLng ll = new LatLng(_lat, _lon);

            ViewGroupToBitmap vb = new ViewGroupToBitmap();


            LayoutInflater inflater = LayoutInflater.from(this);
            //首页地图覆盖物 view转变
            View view = inflater.inflate(R.layout.view_to_marker2, null);
            //修车店标号
            TextView shop_num = (TextView) view.findViewById(R.id.marker_shop_num);
//            shop_num.setBackgroundColor(new Color());
            shop_num.setText(shops.get(i).getOrder() + "");
            //修车店名称
            TextView textView = (TextView) view.findViewById(R.id.marker_info);
            textView.setText(shops.get(i).getShopName());

            LinearLayout imageView = (LinearLayout) view.findViewById(R.id.marker_ll_image);

            if (shops.get(i).getOwn() == 0)
            {
                imageView.setBackground(getResources().getDrawable(R.mipmap.marker_one_1));
            }
            else
            {
                imageView.setBackground(getResources().getDrawable(R.mipmap.marker_one_2));
            }
            BitmapDescriptor bdA = null;
            bdA = BitmapDescriptorFactory.fromBitmap(vb.getViewBitmap(view));
            //第一个覆盖物
            MarkerOptions ooA = new MarkerOptions().position(ll).icon(bdA)
                    .zIndex(9).draggable(true);
            // 掉下动画
            ooA.animateType(MarkerOptions.MarkerAnimateType.drop);
            Marker mMarkerA = null;
            mMarkerA = (Marker) (mBaiduMap.addOverlay(ooA));

            //Bundle用于通信
            Bundle bundle = new Bundle();
            bundle.putString("shopid", shops.get(i).getShopId());
            mMarkerA.setExtraInfo(bundle);//将bundle值传入marker中，给baiduMap设置监听时可以得到它
            mMakers.add(mMarkerA);//用来清除覆盖物

        }
        LatLng loc = new LatLng(latitude, longitude);
        MapStatus.Builder builder = new MapStatus.Builder();
        if (distance / 1000 < 5)
        {
            builder.target(loc).zoom(10.0f);
        }
        else if (distance / 1000 == 5)
        {
            builder.target(loc).zoom(10.0f);
        }
        else if (distance / 1000 >= 10)
        {
            builder.target(loc).zoom(12.0f);
        }
        else
        {
            builder.target(loc).zoom(11.0f);
        }

        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));


        //设置覆盖物监听
        setMarkerClickListener();
    }

    private List<Marker> mMakers = new ArrayList<>();



    /**
     * 设置覆盖物点击的监听
     */
    private void setMarkerClickListener()
    {
        mBaiduMap.setOnMarkerClickListener(new MarkerClicked());
    }
    //覆盖物点击监听类
    class MarkerClicked implements BaiduMap.OnMarkerClickListener
    {
        @Override
        public boolean onMarkerClick(Marker marker) {

            Bundle bundle = marker.getExtraInfo();
            String shopid = bundle.getString("shopid");
            String userid = Configs.getLoginedInfo(mContext).getUserid();

            HttpReqRecommendShopClick reqRecommendShopClick = new HttpReqRecommendShopClick();
            reqRecommendShopClick.setListener(new RecommendShopClickCallbck());
            reqRecommendShopClick.getInstallShop(userid, shopid);


//            InfoWindow infoWindow;
//            //动态生成一个Button对象，用户在地图中显示InfoWindow
//            final Button textInfo = new Button(getApplicationContext());
//            textInfo.setBackgroundColor(Color.parseColor("#ffffffff"));
//            textInfo.setPadding(10, 10, 10, 10);
//            textInfo.setTextColor(Color.BLACK);
//            textInfo.setTextSize(20);
//            int size = mShops.size();
//            textInfo.setText("附近有"+ size+ "个推荐店");
//
//            //得到点击的覆盖物的经纬度
//            LatLng ll = marker.getPosition();
//
//            //将marker所在的经纬度的信息转化成屏幕上的坐标
//            Point p = mBaiduMap.getProjection().toScreenLocation(ll);
//            p.y -= 90;
//            LatLng llInfo = mBaiduMap.getProjection().fromScreenLocation(p);
//            //初始化infoWindow，最后那个参数表示显示的位置相对于覆盖物的竖直偏移量，这里也可以传入一个监听器
//            infoWindow = new InfoWindow(textInfo, llInfo, 0);
//            mBaiduMap.showInfoWindow(infoWindow);//显示此infoWindow
//            //让地图以备点击的覆盖物为中心
//            MapStatusUpdate status = MapStatusUpdateFactory.newLatLng(ll);
//            mBaiduMap.setMapStatus(status);
            return true;
        }
    }
    class RecommendShopClickCallbck implements HttpReqCallback
    {

        @Override
        public void onFail(int errorCode, String message) {
            if (errorCode == ErrorCodes.ERROR_CODE_LOW_VERSION)
            {
                UpdateApp.gotoUpdateApp(mContext);
            }
            else if (errorCode == ErrorCodes.ERROR_CODE_NOT_LOGIN)
            {
                NotLogin.gotoLogin(DiscoverRecommendPartnerActivity.this);
            }
            else
            {
                toastMgr.builder.display(message, 1);
            }
        }

        @Override
        public void onSuccess(Object object) {
            Json2RecommendShopClickBean shopBean = (Json2RecommendShopClickBean) object;
            AlertDialog alertDialog = new AlertDialog(mContext);
            alertDialog.builder().setMsg("恭喜,推荐成功")
                    .setPositiveButton("确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    })
                    .show();
        }
    }



    private void findViews() {

        img_back = (ImageView) findViewById(R.id.img_back);
        discover_add_partner = (TextView) findViewById(R.id.discover_add_partner);//新增
        mMapView = (MapView) findViewById(R.id.recommend_partner_baidumap);
        recommend_partner_location_btn = (Button) findViewById(R.id.recommend_partner_location_btn);
        /**
         * 监听器
         */
        img_back.setOnClickListener(this);
        discover_add_partner.setOnClickListener(this);
        recommend_partner_location_btn.setOnClickListener(this);

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
     * 添加覆盖物的方法
     */
    private void addOverlay() {
        Marker marker = null;
        LatLng point = null;
        MarkerOptions option = null;
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.mipmap.location);;
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

    private void clearOverlay()
    {
        for (Marker marker :mMakers)
        {
            marker = null;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.img_back:
                finish();
                break;
            case R.id.discover_add_partner:
                Intent intent = new Intent();
                intent.setClass(DiscoverRecommendPartnerActivity.this, DiscoverAddPartnerDetailInfoActivity.class);
                startActivity(intent);
                break;
            case R.id.recommend_partner_location_btn:
                //定位
                startLocationBtnClick();
                break;
        }
    }

    private void startLocationBtnClick() {
        mProgressDialog = mProgressDialog.show(mContext,"正在定位", false, null);
        isLocationBtnClicked = true;
        clearOverlay();
        mLocationClient.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
        clearOverlay();

    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }










}
