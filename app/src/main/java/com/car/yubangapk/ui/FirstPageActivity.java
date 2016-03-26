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

import com.car.yubangapk.utils.BDMapData;
import com.car.yubangapk.utils.L;
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

import java.util.ArrayList;
import java.util.List;

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




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);

        mContext = this;

        findViews();

        currentPage = ALL_CAR_PAGE;

        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;

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
                //这里需要把要显示的店铺信息放到FirstPageMarkerClickedActivity里面去  让它解析  然后显示
                startActivity(intent);
                intent = null;
//                if (bottom.getVisibility() == View.VISIBLE) {
//                    bottom.setVisibility(View.INVISIBLE);
//                } else
//                    bottom.setVisibility(View.VISIBLE);
//                final String info = (String) marker.getExtraInfo().get("info");
//                InfoWindow infoWindow;
//                //动态生成一个Button对象，用户在地图中显示InfoWindow
//                final Button textInfo = new Button(getApplicationContext());
//                textInfo.setBackgroundResource(R.mipmap.alipay_payment);
//                textInfo.setPadding(10, 10, 10, 10);
//                textInfo.setTextColor(Color.BLACK);
//                textInfo.setTextSize(12);
//                textInfo.setText(info);
//                //得到点击的覆盖物的经纬度
//                LatLng ll = marker.getPosition();
//                textInfo.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                    }
//                });
//                //将marker所在的经纬度的信息转化成屏幕上的坐标
//                Point p = mBaiduMap.getProjection().toScreenLocation(ll);
//                p.y -= 90;
//                LatLng llInfo = mBaiduMap.getProjection().fromScreenLocation(p);
//                //初始化infoWindow，最后那个参数表示显示的位置相对于覆盖物的竖直偏移量，这里也可以传入一个监听器
//                infoWindow = new InfoWindow(textInfo, llInfo, 0);
//                mBaiduMap.showInfoWindow(infoWindow);//显示此infoWindow
//                //让地图以备点击的覆盖物为中心
//                MapStatusUpdate status = MapStatusUpdateFactory.newLatLng(ll);
//                mBaiduMap.setMapStatus(status);
                return true;
            }
        });

        mLocationClient.start();

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
                toastMgr.builder.display("定位失败", 0);
                return;
            }
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
                builder.target(ll).zoom(14.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                longitude = location.getLongitude();
                latitude  = location.getLatitude();
                initOverlay(1);
            }
            longitude = location.getLongitude();
            latitude  = location.getLatitude();



            mLocationClient.stop();
        }

        public void onReceivePoi(BDLocation poiLocation) {
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
    public void initOverlay(int nowPage) {
        //这里是根据网上拿到的数据,去看要添加什么覆盖物
        //这里可以抽象出一个类  然后后面就会方便
        // add marker overlay
        LatLng llA = new LatLng(latitude + 0.024, longitude + 0.024);
        LatLng llB = new LatLng(latitude - 0.0024, longitude + 0.024);
        LatLng llC = new LatLng(latitude + 0.0024, longitude - 0.024);
        LatLng llD = new LatLng(latitude - 0.024, longitude - 0.024);

        ViewGroupToBitmap vb = new ViewGroupToBitmap();

        LayoutInflater inflater = LayoutInflater.from(this);
        //首页地图覆盖物 view转变
        View view = inflater.inflate(R.layout.view_to_marker2, null);
        //修车店标号
        TextView shop_num = (TextView) view.findViewById(R.id.marker_shop_num);
        shop_num.setText("1");
        //修车店名称
        TextView textView = (TextView) view.findViewById(R.id.marker_info);
        textView.setText("高德汇001");

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
        MarkerOptions ooA = new MarkerOptions().position(llA).icon(bdA)
                .zIndex(9).draggable(true);
        // 掉下动画
        ooA.animateType(MarkerOptions.MarkerAnimateType.drop);
        mMarkerA = (Marker) (mBaiduMap.addOverlay(ooA));

        //第二个覆盖物
        MarkerOptions ooB = new MarkerOptions().position(llB).icon(bdA)
                .zIndex(5);
        // 掉下动画
        ooB.animateType(MarkerOptions.MarkerAnimateType.drop);
        mMarkerB = (Marker) (mBaiduMap.addOverlay(ooB));

        //第三个覆盖物
        MarkerOptions ooC = new MarkerOptions().position(llC).icon(bdA)
                .perspective(false).zIndex(7);
        // 生长动画
        ooC.animateType(MarkerOptions.MarkerAnimateType.drop);
        mMarkerC = (Marker) (mBaiduMap.addOverlay(ooC));

        //第四个覆盖物
        MarkerOptions ooD = new MarkerOptions().position(llD).icon(bdA)
                .zIndex(0);
        // 生长动画
        ooD.animateType(MarkerOptions.MarkerAnimateType.drop);
        mMarkerD = (Marker) (mBaiduMap.addOverlay(ooD));

        LatLng loc = new LatLng(latitude, longitude);
        MapStatusUpdate u = MapStatusUpdateFactory
                .newLatLng(loc);
        mBaiduMap.setMapStatus(u);

        first_page_progressbar.setVisibility(View.GONE);


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

                TextView01.setBackground(getResources().getDrawable(R.mipmap.all_car_selected));
                TextView02.setBackground(getResources().getDrawable(R.mipmap.baoyang));
                TextView03.setBackground(getResources().getDrawable(R.mipmap.dianlu));
                TextView04.setBackground(getResources().getDrawable(R.mipmap.peijian));
                TextView05.setBackground(getResources().getDrawable(R.mipmap.youlu));

                if (currentPage != ALL_CAR_PAGE)
                {
                    first_page_progressbar.setVisibility(View.VISIBLE);
                    locationClick();
                    initOverlay(ALL_CAR_PAGE);
                }
                currentPage = ALL_CAR_PAGE;

                break;
            case R.id.layout_order2:
                //显示进度条
                TextView01.setBackground(getResources().getDrawable(R.mipmap.all_car));
                TextView02.setBackground(getResources().getDrawable(R.mipmap.baoyang_selected));//需要美工再给我一张图
                TextView03.setBackground(getResources().getDrawable(R.mipmap.dianlu));
                TextView04.setBackground(getResources().getDrawable(R.mipmap.peijian));
                TextView05.setBackground(getResources().getDrawable(R.mipmap.youlu));

                if (currentPage != BAO_YANG_PAGE)
                {
                    first_page_progressbar.setVisibility(View.VISIBLE);
                    locationClick();
                    initOverlay(BAO_YANG_PAGE);
                }
                currentPage = BAO_YANG_PAGE;

                break;
            case R.id.layout_order3:

                TextView01.setBackground(getResources().getDrawable(R.mipmap.all_car));
                TextView02.setBackground(getResources().getDrawable(R.mipmap.baoyang));
                TextView03.setBackground(getResources().getDrawable(R.mipmap.dianlu_selected));
                TextView04.setBackground(getResources().getDrawable(R.mipmap.peijian));
                TextView05.setBackground(getResources().getDrawable(R.mipmap.youlu));

                if (currentPage != DIAN_LU_PAGE)
                {
                    first_page_progressbar.setVisibility(View.VISIBLE);
                    locationClick();
                    initOverlay(DIAN_LU_PAGE);
                }
                currentPage = DIAN_LU_PAGE;
                break;
            case R.id.layout_order4:

                TextView01.setBackground(getResources().getDrawable(R.mipmap.all_car));
                TextView02.setBackground(getResources().getDrawable(R.mipmap.baoyang));
                TextView03.setBackground(getResources().getDrawable(R.mipmap.dianlu));
                TextView04.setBackground(getResources().getDrawable(R.mipmap.peijain_selected));
                TextView05.setBackground(getResources().getDrawable(R.mipmap.youlu));

                if (currentPage != PEI_JIAN_PAGE)
                {
                    first_page_progressbar.setVisibility(View.VISIBLE);
                    locationClick();
                    initOverlay(PEI_JIAN_PAGE);
                }
                currentPage = PEI_JIAN_PAGE;
                break;
            case R.id.layout_order5:

                TextView01.setBackground(getResources().getDrawable(R.mipmap.all_car));
                TextView02.setBackground(getResources().getDrawable(R.mipmap.baoyang));
                TextView03.setBackground(getResources().getDrawable(R.mipmap.dianlu));
                TextView04.setBackground(getResources().getDrawable(R.mipmap.peijian));
                TextView05.setBackground(getResources().getDrawable(R.mipmap.youlu_selected));

                if (currentPage != YOU_LU_PAGE)
                {
                    first_page_progressbar.setVisibility(View.VISIBLE);
                    locationClick();
                    initOverlay(YOU_LU_PAGE);
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

        initOverlay(currentPage);

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
        mMapView.onResume();
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
}
