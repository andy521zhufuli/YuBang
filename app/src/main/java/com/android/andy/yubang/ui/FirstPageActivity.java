package com.android.andy.yubang.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.andy.yubang.utils.BDMapData;
import com.android.andy.yubang.utils.L;
import com.android.andy.yubang.utils.toastMgr;
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
import com.baidu.mapapi.map.InfoWindow;
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

    private TextView first_page_search;//搜索

    private List<BDMapData> bdMapClientList;
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
    public BDLocationListener myListener = new MyLocationListener();


    // 点击地图上标注物之后  弹出下面的东西
    private LinearLayout bottom;
    private ImageView   image1;
    private ImageView   image2;
    private ImageView   image3;

    //顶部5个tab
    private RelativeLayout  layout_order1;
    private TextView        TextView01;
    private View            indicator1;

    private RelativeLayout  layout_order2;
    private TextView        TextView02;
    private View            indicator2;

    private RelativeLayout  layout_order3;
    private TextView        TextView03;
    private View            indicator3;

    private RelativeLayout  layout_order4;
    private TextView        TextView04;
    private View            indicator4;

    private RelativeLayout  layout_order5;
    private TextView        TextView05;
    private View            indicator5;


    private int isLocationedSuccess;
    private static final int GPS_LOC_SUCCESS = 61;//GPS定位结果，GPS定位成功。
    private static final int NET_LOC_SUCCESS = 61;//网络定位结果，网络定位定位成功


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_first_page);

        mContext = this;

        findViews();
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMyLocationEnabled(true);//使能百度地图的定位功能
        mMapView.showZoomControls(false);
        mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);    //注册监听函数

        initLocation();
        /**
         * 覆盖物点击
         */
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                final String info = (String) marker.getExtraInfo().get("info");
                InfoWindow infoWindow;
                //动态生成一个Button对象，用户在地图中显示InfoWindow
                final Button textInfo = new Button(getApplicationContext());
                textInfo.setBackgroundResource(R.mipmap.alipay_payment);
                textInfo.setPadding(10, 10, 10, 10);
                textInfo.setTextColor(Color.BLACK);
                textInfo.setTextSize(12);
                textInfo.setText(info);
                //得到点击的覆盖物的经纬度
                LatLng ll = marker.getPosition();
                textInfo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mContext, "你点击了" + info, Toast.LENGTH_SHORT).show();

                        if (bottom.getVisibility() == View.VISIBLE)
                        {
                            bottom.setVisibility(View.INVISIBLE);
                        }
                        else
                            bottom.setVisibility(View.VISIBLE);
                    }
                });
                //将marker所在的经纬度的信息转化成屏幕上的坐标
                Point p = mBaiduMap.getProjection().toScreenLocation(ll);
                p.y -= 90;
                LatLng llInfo = mBaiduMap.getProjection().fromScreenLocation(p);
                //初始化infoWindow，最后那个参数表示显示的位置相对于覆盖物的竖直偏移量，这里也可以传入一个监听器
                infoWindow = new InfoWindow(textInfo, llInfo, 0);
                mBaiduMap.showInfoWindow(infoWindow);//显示此infoWindow
                //让地图以备点击的覆盖物为中心
                MapStatusUpdate status = MapStatusUpdateFactory.newLatLng(ll);
                mBaiduMap.setMapStatus(status);
                return true;
            }
        });

        mLocationClient.start();

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
     *
     */
    private void findViews() {
        //地图
        mMapView = (MapView) findViewById(R.id.first_page_mapview);

        layout_order1    = (RelativeLayout) findViewById(R.id.layout_order1);
        TextView01      = (TextView) findViewById(R.id.TextView01);
        indicator1      = findViewById(R.id.indicator1);

        layout_order2   = (RelativeLayout) findViewById(R.id.layout_order2);
        TextView02      = (TextView) findViewById(R.id.TextView02);
        indicator2      = findViewById(R.id.indicator2);

        layout_order3   = (RelativeLayout) findViewById(R.id.layout_order3);
        TextView03      = (TextView) findViewById(R.id.TextView03);
        indicator3       = findViewById(R.id.indicator3);

        layout_order4    = (RelativeLayout) findViewById(R.id.layout_order4);
        TextView04       = (TextView) findViewById(R.id.TextView04);
        indicator4      = findViewById(R.id.indicator4);

        layout_order5 = (RelativeLayout) findViewById(R.id.layout_order5);
        TextView05 = (TextView) findViewById(R.id.TextView05);
        indicator5 = findViewById(R.id.indicator5);

        bottom = (LinearLayout) findViewById(R.id.bottom);
        image1 = (ImageView) findViewById(R.id.image1);
        image2 = (ImageView) findViewById(R.id.image2);
        image3 = (ImageView) findViewById(R.id.image3);

        //搜索
        first_page_search = (TextView) findViewById(R.id.first_page_search);


        /**
         * 设置监听器
         */
        layout_order1.setOnClickListener(this);
        layout_order2.setOnClickListener(this);
        layout_order3.setOnClickListener(this);
        layout_order4.setOnClickListener(this);
        layout_order5.setOnClickListener(this);

        image1.setOnClickListener(this);
        image2.setOnClickListener(this);
        image3.setOnClickListener(this);
        first_page_search.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.layout_order1:
                indicator1.setVisibility(View.VISIBLE);
                indicator2.setVisibility(View.INVISIBLE);
                indicator3.setVisibility(View.INVISIBLE);
                indicator4.setVisibility(View.INVISIBLE);
                indicator5.setVisibility(View.INVISIBLE);
                TextView01.setTextColor(Color.parseColor("#ff0000"));
                TextView02.setTextColor(Color.parseColor("#4f4f4f"));
                TextView03.setTextColor(Color.parseColor("#4f4f4f"));
                TextView04.setTextColor(Color.parseColor("#4f4f4f"));
                TextView05.setTextColor(Color.parseColor("#4f4f4f"));
                break;
            case R.id.layout_order2:
                indicator1.setVisibility(View.INVISIBLE);
                indicator2.setVisibility(View.VISIBLE);
                indicator3.setVisibility(View.INVISIBLE);
                indicator4.setVisibility(View.INVISIBLE);
                indicator5.setVisibility(View.INVISIBLE);
                TextView01.setTextColor(Color.parseColor("#4f4f4f"));
                TextView02.setTextColor(Color.parseColor("#ff0000"));
                TextView03.setTextColor(Color.parseColor("#4f4f4f"));
                TextView04.setTextColor(Color.parseColor("#4f4f4f"));
                TextView05.setTextColor(Color.parseColor("#4f4f4f"));
                break;
            case R.id.layout_order3:
                indicator1.setVisibility(View.INVISIBLE);
                indicator2.setVisibility(View.INVISIBLE);
                indicator3.setVisibility(View.VISIBLE);
                indicator4.setVisibility(View.INVISIBLE);
                indicator5.setVisibility(View.INVISIBLE);
                TextView01.setTextColor(Color.parseColor("#4f4f4f"));
                TextView02.setTextColor(Color.parseColor("#4f4f4f"));
                TextView03.setTextColor(Color.parseColor("#ff0000"));
                TextView04.setTextColor(Color.parseColor("#4f4f4f"));
                TextView05.setTextColor(Color.parseColor("#4f4f4f"));
                break;
            case R.id.layout_order4:
                indicator1.setVisibility(View.INVISIBLE);
                indicator2.setVisibility(View.INVISIBLE);
                indicator3.setVisibility(View.INVISIBLE);
                indicator4.setVisibility(View.VISIBLE);
                indicator5.setVisibility(View.INVISIBLE);
                TextView01.setTextColor(Color.parseColor("#4f4f4f"));
                TextView02.setTextColor(Color.parseColor("#4f4f4f"));
                TextView03.setTextColor(Color.parseColor("#4f4f4f"));
                TextView04.setTextColor(Color.parseColor("#ff0000"));
                TextView05.setTextColor(Color.parseColor("#4f4f4f"));
                break;
            case R.id.layout_order5:
                indicator1.setVisibility(View.INVISIBLE);
                indicator2.setVisibility(View.INVISIBLE);
                indicator3.setVisibility(View.INVISIBLE);
                indicator4.setVisibility(View.INVISIBLE);
                indicator5.setVisibility(View.VISIBLE);
                TextView01.setTextColor(Color.parseColor("#4f4f4f"));
                TextView02.setTextColor(Color.parseColor("#4f4f4f"));
                TextView03.setTextColor(Color.parseColor("#4f4f4f"));
                TextView04.setTextColor(Color.parseColor("#4f4f4f"));
                TextView05.setTextColor(Color.parseColor("#ff0000"));
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
            case R.id.first_page_search:
                Intent intent3 = new Intent();
                intent3.setClass(FirstPageActivity.this, SearchActivity.class);
                startActivity(intent3);

                break;
        }

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
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
