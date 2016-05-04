package com.car.yubangapk.ui.firstpagefragment;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ZoomControls;

import com.andy.android.yubang.R;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
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
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.car.yubangapk.banner.ImageLoaderTools;
import com.car.yubangapk.configs.Configs;
import com.car.yubangapk.configs.ErrorCodes;
import com.car.yubangapk.json.bean.Json2FirstPageShopBean;
import com.car.yubangapk.json.bean.Json2FirstPageTabsBean;
import com.car.yubangapk.json.bean.Json2MyOrderBean;
import com.car.yubangapk.json.bean.MyOrderBean;
import com.car.yubangapk.json.formatJson.Json2FirstPageShop;
import com.car.yubangapk.network.myHttpReq.HttpGetMyOrders;
import com.car.yubangapk.network.myHttpReq.HttpReqCallback;
import com.car.yubangapk.network.okhttp.OkHttpUtils;
import com.car.yubangapk.network.okhttp.callback.StringCallback;
import com.car.yubangapk.swipetoloadlayout.SwipeToLoadLayout;
import com.car.yubangapk.ui.FirstPageMarkerClickedActivity;
import com.car.yubangapk.ui.myordersfragment.MyOrdersActivity;
import com.car.yubangapk.utils.L;
import com.car.yubangapk.utils.String2UTF8;
import com.car.yubangapk.utils.ViewGroupToBitmap;
import com.car.yubangapk.utils.Warn.NotLogin;
import com.car.yubangapk.utils.toastMgr;
import com.car.yubangapk.view.AlertDialog;
import com.car.yubangapk.view.CustomProgressDialog;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * 首页每个tab对应的Fragment
 */
public class FirstPageFragment extends Fragment implements View.OnClickListener{

    private String                          mType;
    String                                  mUserId ;
    Context                                 mContext;
    Json2FirstPageTabsBean                  mCurrentTab;
    MapView                                 first_page_mapview;//地图
    Button                                  first_page_location_btn;//定位按钮

    private BaiduMap                        mBaiduMap;
    //定位
    public LocationClient                   mLocationClient = null;
    public BDLocationListener               myListener = new MyLocationListenner();

    //当前定位结果的经纬度
    private double                          latitude;
    private double                          longitude;

    private String                          mCountry;    //国家
    private String                          mProvince;   //省
    private String                          mCity;       //市
    private String                          mCityCode;   //市代码
    private String                          mDistrict;   //区
    private String                          mStreet;     //街道
    boolean                                 isFirstLoc              = true; // 是否首次定位
    private CustomProgressDialog            mProgressDialog;
    String                                  TAG = FirstPageFragment.class.getSimpleName();
    private static int                      FITST_GET_SHOP = 1;//第一次去拿店铺
    private static int                      SECOND_GET_SHOP = 2;//如果没有区里面没有店铺信息  就去拿市里的
    private static int                      THIRD_GET_SHOP = 3;//如果没有市里面没有店铺信息  就去拿省里的
    private int                             CURRENT_GET_SHOP_TIME = 1;

    private List<Json2FirstPageShopBean>    mJson2FirstPageShopBeanList;//保存拿来的店铺信息
    private String                          mShopBeanResponse;//覆盖物点击的时候  传递给下一个界面

    public FirstPageFragment(Json2FirstPageTabsBean currentTab, String tag)
    {
        this.mCurrentTab = currentTab;
        this.mType = tag;
    }


	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
        this.mContext = context;
        mUserId = Configs.getLoginedInfo(mContext).getUserid();
	}

	@Override
	public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        L.e("TAG " + mType, "onCreate");


    }

	@Override
	public View onCreateView(LayoutInflater inflater,
			 ViewGroup container,  Bundle savedInstanceState) {

        L.e("TAG " + mType, "onCreateView");
        View view = inflater.inflate(R.layout.fragment_first_page, container, false);
//        SDKInitializer.initialize(getActivity().getApplicationContext());
        first_page_mapview = (MapView) view.findViewById(R.id.first_page_mapview);
        first_page_location_btn = (Button) view.findViewById(R.id.first_page_location_btn);
        mProgressDialog = new CustomProgressDialog(getActivity());

        first_page_location_btn.setOnClickListener(this);



        if (isFirstVisibleToUser == false)
        {
            //这里是被销毁了 重新createView  再去拿数据  显示

        }
		return view;
	}


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        L.e("TAG " + mType, "onActivityCreated");
        //加载地图
        initMAp();
        clearOverlay();
    }

    /**
     * 初始化地图
     */
    private void initMAp()
    {
        mBaiduMap = first_page_mapview.getMap();
        //初始化定位
        mBaiduMap.setMyLocationEnabled(true);//使能百度地图的定位功能
        first_page_mapview.showZoomControls(false);
        View child = first_page_mapview.getChildAt(1);
        if (child != null && (child instanceof ImageView || child instanceof ZoomControls)) {
            child.setVisibility(View.INVISIBLE);
        }

        mLocationClient = new LocationClient(getActivity().getApplicationContext());     //声明LocationClient类
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
                intent.setClass(getActivity(), FirstPageMarkerClickedActivity.class);
                intent.putExtra("shopBean", mShopBeanResponse);
                //这里需要把要显示的店铺信息放到FirstPageMarkerClickedActivity里面去  让它解析  然后显示
                startActivity(intent);
                return true;
            }
        });
        //开始定位
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
            if (location == null || first_page_mapview == null) {
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

                mProgressDialog = mProgressDialog.show(mContext,"正在定位", false, null);
                //首次定位成功才会去请求附近的店铺, 请求店铺成功, 才会去加载覆盖物
                httpSearchShop(mProvince, mCity, mDistrict, mCurrentTab.getId(), longitude, latitude, FITST_GET_SHOP);
                //initOverlay(1, mJson2FirstPageShopBeanList);
            }
            longitude = location.getLongitude();
            latitude  = location.getLatitude();


            L.d(TAG + "当前经纬度",longitude +  "=="+ latitude + "");

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
                        httpSearchShop(mProvince, mCity, mDistrict, mCurrentTab.getId(), longitude, latitude, CURRENT_GET_SHOP_TIME+1);
                    }
                    else if (CURRENT_GET_SHOP_TIME == 2)
                    {
                        //去请求省
                        httpSearchShop(mProvince, mCity, mDistrict, mCurrentTab.getId(), longitude, latitude, CURRENT_GET_SHOP_TIME + 1);
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
    /**
     * 初始化覆盖物
     */
    // 初始化全局 bitmap 信息，不用时及时 recycle
    BitmapDescriptor bdA;
    private Marker mMarkerA;
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

            LayoutInflater inflater = LayoutInflater.from(getActivity());
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
                case 1:
                    imageView.setBackground(getResources().getDrawable(R.mipmap.marker_one_1));
                    break;
                case 2:
                    imageView.setBackground(getResources().getDrawable(R.mipmap.marker_one_2));
                    break;
                case 3:
                    imageView.setBackground(getResources().getDrawable(R.mipmap.marker_one_3));
                    break;
                case 4:
                    imageView.setBackground(getResources().getDrawable(R.mipmap.marker_one_4));
                    break;
                case 5:
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
        }
    }


    /**
     * 清除所有Overlay
     *
     * @param
     */
    public void clearOverlay() {
        mBaiduMap.clear();

    }

    @Override
    public void onStart() {
        super.onStart();
        L.e("TAG " + mType, "onStart 是不是此时才可见");
    }

    @Override
    public void onResume() {
        super.onResume();
        L.e("TAG-- " + mType, "onResume 是不是此时才可见");
        MobclickAgent.onPageStart("myOrderFragment" + mType);
        first_page_mapview.onResume();
    }


    @Override
    public void onPause() {
        super.onPause();
        L.e("TAG-- " + mType, "onPause ");
        MobclickAgent.onPageEnd("myOrderFragment" + mType);
        first_page_mapview.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        L.e("TAG-- " + mType, "onDestroyView ");
        isFirstVisibleToUser = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        L.e("TAG-- " + mType, "onDestroy ");
        first_page_mapview.onDestroy();
    }

    private boolean isFirstVisibleToUser = true;

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.first_page_location_btn:
                //定位
                toastMgr.builder.display("定位" ,1);
                break;
        }
    }
}
