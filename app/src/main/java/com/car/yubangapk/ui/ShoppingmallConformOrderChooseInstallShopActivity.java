package com.car.yubangapk.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.andy.android.yubang.R;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.car.yubangapk.banner.ImageLoaderTools;
import com.car.yubangapk.configs.Configs;
import com.car.yubangapk.json.bean.Json2InstallShopBean;
import com.car.yubangapk.json.bean.Json2InstallShopModelsBean;
import com.car.yubangapk.json.bean.Json2ShopServiceBean;
import com.car.yubangapk.json.bean.Json2ShoppingmallBottomPicsBean;
import com.car.yubangapk.network.myHttpReq.HttpReqInstallShopList;
import com.car.yubangapk.network.myHttpReq.HttpReqInstallShopListInterface;
import com.car.yubangapk.utils.L;
import com.car.yubangapk.utils.toastMgr;
import com.car.yubangapk.view.CustomProgressDialog;

import java.util.List;



/**
 * ShoppingmallChooseInstallShopActivity: 确认订单 选择安装门店
 *
 * @author andyzhu
 * @version 1.0
 * @created 2016-04-20
 */
public class ShoppingmallConformOrderChooseInstallShopActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private final static String TAG = ShoppingMallChoosePaymentActivity.class.getSimpleName();
    private Context mContext;

    private ImageView img_back;

    private LinearLayout        choose_install_shop_nearest_shop;//最近的一个店的布局
    //最近的一个门店
    private ImageView           im_shop_img;//店铺的照片
    private TextView            shopbusinesstype;//店铺类型
    private TextView            tv_shop_name;//店铺名字
    private TextView            tv_shop_juli;//店铺距离当前位置距离
    private TextView            tv_evaluate_text;//店铺评价分数
    private TextView            tv_shop_points;//店铺接单数
    private TextView            tv_shop_address;//店铺地址信息
    private ImageView           zfb;//店铺支持的支付类型         支付宝
    private ImageView           wx;//店铺支持的支付类型          微信
    private ImageView           yhk;//店铺支持的支付类型         银行卡
    private ImageView           xj;//店铺支持的支付类型          现金

    private ListView            conform_order_choose_install_shop_listview;//店铺列表

    boolean isFirstLoc = true; // 是否首次定位
    public LocationClient mLocationClient = null;
    private String mCountry;    //国家
    private String mProvince;   //省
    private String mCity;       //市
    private String mCityCode;   //市代码
    private String mDistrict;   //区
    private String mStreet;     //街道
    //当前定位结果的经纬度
    private double mLatitude;
    private double mLongitude;


    private CustomProgressDialog mProgress;

    private List<Json2InstallShopModelsBean> mInstallShopModelsList;


    //适配器
    private InstallShopListAdapter mShopListAdapter;


    private List<Json2ShoppingmallBottomPicsBean> mRepairServices;//上一个界面传来的  表示商城界面的6个repairService  代表6张图片
    private List<Json2ShopServiceBean> mShopServices;//店铺的服务
    String from;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_shoppingmall_conform_order_choose_install_shop);

        mContext = this;

        findViews();

        Bundle bundle = getIntent().getExtras();

        getExtra(bundle);




        mProgress = new CustomProgressDialog(mContext);

        //首先获取经纬度

        locationLocatioin();



    }

    /**
     * 获取上一个界面的信息
     * @param bundle
     */
    private void getExtra(Bundle bundle) {

        from = bundle.getString("from");
        if (from.equals(Configs.FROM_SHOPPINGMALL))
        {
//            mRepairServices = (List<Json2ShoppingmallBottomPicsBean>) bundle.getSerializable("repairServices");
        }
        else
        {
            //mShopServices = (List<Json2ShopServiceBean>) bundle.getSerializable("shopServiceBean");
        }

    }

    /**
     * 定位
     */
    private void locationLocatioin() {
        mProgress = mProgress.show(mContext,"正在获取定位信息...", false, null);
        mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
        MyLocationListenner myListener = new MyLocationListenner();
        mLocationClient.registerLocationListener(myListener);    //注册监听函数
        //初始化定位参数
        initLocation();
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

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.choose_install_shop_nearest_shop:
                toastMgr.builder.display("click", 1);
                Json2InstallShopModelsBean shop = mInstallShopModelsList.get(0);
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("shopModel",shop);
                intent.putExtras(bundle);
                setResult(Activity.RESULT_OK,intent );
                finish();
                break;
            case R.id.img_back:
                setResult(Activity.RESULT_CANCELED);
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

        Json2InstallShopModelsBean shop = mInstallShopModelsList.get(position);
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("shopModel",shop);
        intent.putExtras(bundle);
        setResult(Activity.RESULT_OK,intent );
        finish();

    }


    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null) {
                toastMgr.builder.display("定位失败, 请确保开启网络,然后重试!!", 1);
                mProgress = mProgress.show(mContext,"定位失败", false, null);
                mProgress.dismiss();
                finish();
                return;
            }
            //获取定位信息
            mCountry  = location.getCountry();
            mProvince = location.getProvince();
            mCity     = location.getCity();
            mCityCode = location.getCityCode();
            mDistrict = location.getDistrict();
            mStreet   = location.getStreet();
            mLatitude = location.getLatitude();
            mLongitude = location.getLongitude();

            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            if (isFirstLoc) {
                isFirstLoc = false;
//                LatLng ll = new LatLng(location.getLatitude(),
//                        location.getLongitude());
//                MapStatus.Builder builder = new MapStatus.Builder();
//                builder.target(ll).zoom(12.0f);

                //获取定位成功, 拿到需要的信息,去请求店铺列表
                String userid = Configs.getLoginedInfo(mContext).getUserid();
                String carType = Configs.getLoginedInfo(mContext).getCarType();
                getInstallShop(mLongitude, mLatitude, userid, carType, mProvince ,mCity, mDistrict, "1");
            }

            L.d(TAG + "当前经纬度", mLongitude +  "=="+ mLatitude + "");

            mLocationClient.stop();
        }
    }

    /**
     * 去后台获取可安装店铺列表
     */
    private void getInstallShop(double lon, double lat, String userid, String carType, String province, String city, String district, String shopStatus) {

        mProgress.setMessage("正在获取附近门店...");

        HttpReqInstallShopList req = new HttpReqInstallShopList(new ShopListListener());
        req.getInstallShop(lon, lat, userid, carType, province, city, district, shopStatus);

    }

    class ShopListListener implements HttpReqInstallShopListInterface {


        @Override
        public void onGetInstallShopSucces(List<Json2InstallShopModelsBean> shopModels) {
            mProgress.dismiss();

            //这里 去显示列表
            mShopListAdapter = new InstallShopListAdapter(shopModels);
            mInstallShopModelsList = shopModels;
            conform_order_choose_install_shop_listview.setAdapter(mShopListAdapter);


            //另外 第一个也要显示

            showTheNearestInstallShop(shopModels.get(0));
            //如果有更新 那就调用
            // mShopListAdapter.refresh(list);
        }



        @Override
        public void onGetInstallShopFail(int errorCode, String message) {
            mProgress.dismiss();
        }
    }

    /**
     * 显示最近的一个店铺
     * @param json2InstallShopModelsBean
     */
    private void showTheNearestInstallShop(Json2InstallShopModelsBean json2InstallShopModelsBean) {
        String pathcode = json2InstallShopModelsBean.getPathCode();
        String photoname = json2InstallShopModelsBean.getPhotoName();
        String url = Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GETFILE + "?fileReq.pathCode=" + pathcode + "&fileReq.fileName=" + photoname;
        L.d(TAG,"商城中间图片加载url = " + url);
        ImageLoaderTools.getInstance(mContext).displayImage(url, im_shop_img);

        tv_shop_name.setText(json2InstallShopModelsBean.getShopName());
        tv_shop_juli.setText(json2InstallShopModelsBean.getDistance() + "米");
        tv_shop_address.setText(json2InstallShopModelsBean.getShopAddress());
    }

    /**
     * 绑定控件
     */
    private void findViews() {

        img_back = (ImageView) findViewById(R.id.img_back);//

        im_shop_img = (ImageView) findViewById(R.id.im_shop_img);//店铺的照片

        shopbusinesstype = (TextView) findViewById(R.id.shopbusinesstype);//店铺类型

        tv_shop_name = (TextView) findViewById(R.id.tv_shop_name);//店铺名字

        tv_shop_juli = (TextView) findViewById(R.id.tv_shop_juli);//店铺距离当前位置距离

        tv_evaluate_text = (TextView) findViewById(R.id.tv_evaluate_text);//店铺评价分数

        tv_shop_points = (TextView) findViewById(R.id.tv_shop_points);//店铺接单数

        tv_shop_address = (TextView) findViewById(R.id.tv_shop_address);//店铺地址信息

        zfb = (ImageView) findViewById(R.id.im_shop_img);//店铺支持的支付类型         支付宝

        wx = (ImageView) findViewById(R.id.im_shop_img);//店铺支持的支付类型          微信

        yhk = (ImageView) findViewById(R.id.im_shop_img);//店铺支持的支付类型         银行卡

        xj = (ImageView) findViewById(R.id.im_shop_img);//店铺支持的支付类型          现金


        conform_order_choose_install_shop_listview = (ListView) findViewById(R.id.conform_order_choose_install_shop_listview);
        choose_install_shop_nearest_shop = (LinearLayout) findViewById(R.id.choose_install_shop_nearest_shop);
        choose_install_shop_nearest_shop.setOnClickListener(this);
        conform_order_choose_install_shop_listview.setOnItemClickListener(this);
    }


    class InstallShopListAdapter extends BaseAdapter
    {

        private List<Json2InstallShopModelsBean> mShopModels;
        private List<Json2InstallShopBean> mInstallShops;

        public InstallShopListAdapter(List<Json2InstallShopModelsBean> shopModels)
        {
            this.mShopModels = shopModels;
        }

        public void refresh(List<Json2InstallShopModelsBean> shopModels)
        {
            this.mShopModels = shopModels;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mShopModels.size();
        }

        @Override
        public Object getItem(int position) {
            return mShopModels.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {

            ViewHoler holder = null;
            if (view == null)
            {
                holder = new ViewHoler();
                view = View.inflate(mContext, R.layout.item_choose_install_shop, null);

                holder.im_shop_img                  = (ImageView) view.findViewById(R.id.im_shop_img);
                holder.shopbusinesstype             = (TextView) view.findViewById(R.id.shopbusinesstype);
                holder.tv_shop_name                 = (TextView) view.findViewById(R.id.tv_shop_name);
                holder.tv_shop_juli                 = (TextView) view.findViewById(R.id.tv_shop_juli);
                holder.tv_evaluate_text             = (TextView) view.findViewById(R.id.tv_evaluate_text);
                holder.tv_shop_points               = (TextView) view.findViewById(R.id.tv_shop_points);
                holder.tv_shop_address              = (TextView) view.findViewById(R.id.tv_shop_address);
                holder.zfb                          = (ImageView) view.findViewById(R.id.zfb);
                holder.wx                           = (ImageView) view.findViewById(R.id.wx);
                holder.yhk                          = (ImageView) view.findViewById(R.id.yhk);
                holder.xj                           = (ImageView) view.findViewById(R.id.xj);

                view.setTag(holder);
            }
            else
            {
                holder = (ViewHoler) view.getTag();
            }

            String pathcode = mShopModels.get(position).getPathCode();
            String photoname = mShopModels.get(position).getPhotoName();
            String url = Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GETFILE + "?fileReq.pathCode=" + pathcode + "&fileReq.fileName=" + photoname;
            L.d(TAG,"商城中间图片加载url = " + url);
            ImageLoaderTools.getInstance(mContext).displayImage(url, holder.im_shop_img);

            holder.tv_shop_name.setText(mShopModels.get(position).getShopName());
            holder.tv_shop_juli.setText(mShopModels.get(position).getDistance() + "米");
            holder.tv_shop_address.setText(mShopModels.get(position).getShopAddress());
            return view;
        }



        class ViewHoler
        {

           ImageView           im_shop_img;//店铺的照片

           TextView            shopbusinesstype;//店铺类型

           TextView            tv_shop_name;//店铺名字

           TextView            tv_shop_juli;//店铺距离当前位置距离

           TextView            tv_evaluate_text;//店铺评价分数

           TextView            tv_shop_points;//店铺接单数

           TextView            tv_shop_address;//店铺地址信息

           ImageView           zfb;//店铺支持的支付类型         支付宝

           ImageView           wx;//店铺支持的支付类型          微信

           ImageView           yhk;//店铺支持的支付类型         银行卡

           ImageView           xj;//店铺支持的支付类型          现金
        }
    }


}