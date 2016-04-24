package com.car.yubangapk.location;

import android.content.Context;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

/**
 * Created by andy on 16/4/23.
 *
 * 定位的类  抽象出来
 *
 */
public class GetLocationData
{
    //定位
    LocationClient mLocationClient = null;
    Context mContext;
    private boolean isFirstLoc = true;

    LocationCallback mCallback;

    public GetLocationData(Context context)
    {
        this.mContext = context;
    }


    public void setCallback(LocationCallback callback)
    {
        this.mCallback = callback;
    }


    public void startLocation()
    {
        mLocationClient = new LocationClient(mContext);     //声明LocationClient类
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


    double mLongitude;
    double mLatitude;
    String mProvince;
    String mCity;
    String mDistrict;
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
            if (location == null)
            {
                mCallback.onLocationFail("定位失败, 请确保开启网络,然后重试!!");
                return;
            }
            //获取定位信息
            mProvince = location.getProvince();
            mCity     = location.getCity();
            mDistrict  = location.getDistrict();
            if (isFirstLoc) {
                isFirstLoc = false;
                mLongitude = location.getLongitude();
                mLatitude  = location.getLatitude();
                mCallback.onLocationSuccess(mLongitude, mLatitude, mProvince, mCity, mDistrict);
            }
            mLocationClient.stop();
        }
    }


    public interface LocationCallback
    {
        void onLocationFail(String message);
        void onLocationSuccess(double lon, double lat, String province, String city, String district);
    }
}
