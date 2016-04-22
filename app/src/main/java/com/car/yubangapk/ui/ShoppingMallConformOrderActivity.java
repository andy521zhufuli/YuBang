package com.car.yubangapk.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.car.yubangapk.banner.ImageLoaderTools;
import com.car.yubangapk.configs.Configs;
import com.car.yubangapk.configs.ErrorCodes;
import com.car.yubangapk.json.bean.AddressBean;
import com.car.yubangapk.json.bean.CouponsBean;
import com.car.yubangapk.json.bean.Json2AddressBean;
import com.car.yubangapk.json.bean.Json2CouponBean;
import com.car.yubangapk.json.bean.Json2InstallShopModelsBean;
import com.car.yubangapk.json.bean.Json2LoginBean;


import com.car.yubangapk.json.bean.Json2OrderPriceBean;
import com.car.yubangapk.json.bean.Json2ProductPackageBean;
import com.car.yubangapk.network.myHttpReq.HttpReqAddress;
import com.car.yubangapk.network.myHttpReq.HttpReqCallback;
import com.car.yubangapk.network.myHttpReq.HttpReqConformOrderCoupon;
import com.car.yubangapk.network.myHttpReq.HttpReqConformOrderCouponInterface;
import com.car.yubangapk.network.myHttpReq.HttpReqGetOrderPrice;
import com.car.yubangapk.network.myHttpReq.httpReqAddressInterface;
import com.car.yubangapk.utils.Warn.NotLogin;
import com.car.yubangapk.utils.Warn.UpdateApp;
import com.car.yubangapk.utils.toastMgr;
import com.andy.android.yubang.R;
import com.car.yubangapk.view.AlertDialog;
import com.car.yubangapk.view.CustomProgressDialog;
import com.car.yubangapk.view.WheelDatePicker.MyDatePicker;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * ShoppingMallConformOrderActivity: 确认订单界面
 *
 * @author andyzhu
 * @version 1.0
 * @created 2016-02-29
 * 只有一个入口
 */
public class ShoppingMallConformOrderActivity extends BaseActivity implements View.OnClickListener{

    private Context mContext;

    private ImageView       img_back;//返回
    private RelativeLayout  payment_way;//选择支付方式
    private ImageView       arrow1;//选择支付方式的箭头
    private LinearLayout    conform_order_choose_online;//在线支付
    private ImageView       online_pay_imageview;//在线支付选择
    private LinearLayout    conform_order_choose_offline;//线下支付
    private ImageView       offline_pay_imageview;//在线支付选择
    private RelativeLayout  my_layout_mine_order;//优惠券
    private LinearLayout    conform_order_choose_online_offline_payment;//隐藏的线上与到店支付的布局
    private boolean         isOnlineOrOffline = false; //false 代表线下支付
    private RelativeLayout  my_layout_order_coupon;//我的优惠券
    private RelativeLayout  conform_order_choose_time;//选择安装时间
    private TextView        conform_order_install_time;//安装时间显示


    private ListView        youhui_list;//优惠券列表
    private TextView        coupon_description;//是否使用优惠券描述



    private RelativeLayout  conform_order_choose_shop;//选择店铺
    private TextView        textview_receiver_address_content;//配到到哪里

    private ImageView       conform_product_first_image;//订单里面商品的第一个的图片
    private TextView        product_text_count;//订单里面商品数量
    private TextView        product_text_product;//多少件商品  件商品
    private LinearLayout    product_num_display;//显示订单里面有多少商品

    private TextView       textview_receiver_name_content;//收货名字
    private TextView       textview_receiver_mobile_content;//收货电话
    private RelativeLayout name_phone;//

    private TextView        tv_price;//价格


    private TextView        product_total_price;//总价
    private TextView        product_install_price;//安装费
    private TextView        product_dilivery_price;//运费
    private TextView        product_coupon_price;//优惠券的减免金额


    private RelativeLayout btn_pay;//提交订单  去支付


    private Json2AddressBean mAddressBean;

    private List<Json2ProductPackageBean> mProductPackageListToOrderProductDetailPage;


    private ArrayList<String>  mRepairServices;//上一个界面传来的  表示商城界面的6个repairService  代表6张图片
    private ArrayList<String> mShopServices;//店铺的服务
    private String mFrom;
    private Json2InstallShopModelsBean mInstallShopBean = null;//获取到的安装店铺



    CustomProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_shopping_mall_conform_order);

        mContext = this;
        findViews();
        Bundle bundle = getIntent().getExtras();
        mFrom = bundle.getString("from");


        mProgress = new CustomProgressDialog(mContext);

        List<Json2ProductPackageBean> productPackageList = (List<Json2ProductPackageBean>) bundle.getSerializable("productPackageList");
        mProductPackageListToOrderProductDetailPage = productPackageList;

        countTotalPrice(mProductPackageListToOrderProductDetailPage);

        if (mFrom.equals(Configs.FROM_SHOPPINGMALL))
        {
            mRepairServices = (ArrayList<String>) bundle.getSerializable("repairServices");
            if (mRepairServices == null)
            {

            }
        }
        else
        {
            mShopServices = (ArrayList<String>) bundle.getSerializable("shopServiceBean");
            if (mShopServices == null)
            {

            }
        }
        //设置默认线下支付
        getOfflinePayment();
        //中间显示订单里面商品数量  以及第一个商品的图片
        loadFisrtProductImage(mProductPackageListToOrderProductDetailPage);
        //获取默认的地址
        getDefaultAddress();
    }


    /**
     * 默认是线下支付
     */
    private void getOfflinePayment()
    {
        offline_pay_imageview.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.button_l_01));
        online_pay_imageview.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.button_l_02));
    }

    /**
     * 获取默认地址
     */
    private void getDefaultAddress() {
        Json2LoginBean bean = Configs.getLoginedInfo(mContext);
        String userid = bean.getUserid();
        HttpReqAddress reqGetAddressConformOrder = new HttpReqAddress(userid,"3", null, null, null);
        reqGetAddressConformOrder.setCallback(new httpReqAddressInterface() {
            @Override
            public void onGetAddressSucces(Json2AddressBean addressBean) {
                Json2AddressBean json2AddressBean = addressBean;
                mAddressBean = json2AddressBean;
                setTopAddressDetail(json2AddressBean.getDefaultAddress().getName(), json2AddressBean.getDefaultAddress().getPhone());
            }

            @Override
            public void onGetAddressFail(int errorCode) {
                if (errorCode == ErrorCodes.ERROR_CODE_LOW_VERSION)
                {
                    UpdateApp.gotoUpdateApp(mContext);
                }
                else if (errorCode == ErrorCodes.ERROR_CODE_NETWORK)
                {
                    toastMgr.builder.display("网络错误" ,1);
                }
                else if (errorCode == ErrorCodes.ERROR_CODE_NO_ADDRESS)
                {
                    toastMgr.builder.display("没有收货信息信息" ,1);
                }
                else if (errorCode == ErrorCodes.ERROR_CODE_NOT_LOGIN)
                {
                    toastMgr.builder.display("没有登录" ,1);
                    NotLogin.gotoLogin(ShoppingMallConformOrderActivity.this);

                }
                else if (errorCode == ErrorCodes.ERROR_CODE_SERVER)
                {
                    toastMgr.builder.display("服务器错误" ,1);
                }
            }
        });
        reqGetAddressConformOrder.getAddressPeopleInfo();
    }

    /**
     * 根据产品包页面传来的商品  去显示第一个商品的图片
     * @param productPackageList
     */
    private void loadFisrtProductImage(List<Json2ProductPackageBean> productPackageList)
    {


        if (productPackageList.size() >= 1)
        {
            product_text_count.setText(productPackageList.size()+ "");
            product_text_count.setVisibility(View.VISIBLE);
            product_text_product.setVisibility(View.VISIBLE);
        }
        else
        {
            product_text_count.setVisibility(View.GONE);
            product_text_product.setVisibility(View.GONE);
        }


        String pathcode = productPackageList.get(0).getPathCode();
        String photoName = productPackageList.get(0).getPhotoName();
        String url = Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GETFILE + "?fileReq.pathCode=" + pathcode + "&fileReq.fileName=" + photoName;
        ImageLoaderTools.getInstance(mContext).displayImage(url, conform_product_first_image);

    }


    /**
     * 设置顶部电话的名字
     * @param name
     * @param phone
     */
    private void setTopAddressDetail(String name , String phone)
    {
        //去设置地址
        textview_receiver_name_content.setText(name);
        textview_receiver_mobile_content.setText(phone);
    }

    private void findViews() {

        img_back = (ImageView) findViewById(R.id.img_back);//返回
        payment_way = (RelativeLayout) findViewById(R.id.payment_way);//选择支付方式
        arrow1 = (ImageView) findViewById(R.id.arrow1);//选择支付方式的箭头
        conform_order_choose_online = (LinearLayout) findViewById(R.id.conform_order_choose_online);//在线支付
        online_pay_imageview = (ImageView) findViewById(R.id.online_pay_imageview);//在线支付选择
        conform_order_choose_offline = (LinearLayout) findViewById(R.id.conform_order_choose_offline);//线下支付
        offline_pay_imageview = (ImageView) findViewById(R.id.offline_pay_imageview);//在线支付选择
        coupon_description = (TextView) findViewById(R.id.coupon_description);//是否使用优惠券描述

        conform_order_choose_online_offline_payment = (LinearLayout) findViewById(R.id.conform_order_choose_online_offline_payment);
        btn_pay = (RelativeLayout) findViewById(R.id.btn_pay);

        conform_product_first_image = (ImageView) findViewById(R.id.conform_product_first_image);
        product_text_count = (TextView) findViewById(R.id.product_text_count);//订单里面商品数量
        product_text_product = (TextView) findViewById(R.id.product_text_product);//多少件商品  件商品
        product_num_display = (LinearLayout) findViewById(R.id.product_num_display);//显示多少商品的布局


        conform_order_choose_time = (RelativeLayout) findViewById(R.id.conform_order_choose_time);//选择安装时间
        conform_order_install_time = (TextView) findViewById(R.id.conform_order_install_time);//安装时间显示


        conform_order_choose_shop = (RelativeLayout) findViewById(R.id.conform_order_choose_shop);//选择店铺


        textview_receiver_name_content = (TextView) findViewById(R.id.textview_receiver_name_content);;//收货名字
        textview_receiver_mobile_content = (TextView) findViewById(R.id.textview_receiver_mobile_content);;//收货电话

        name_phone = (RelativeLayout) findViewById(R.id.name_phone);;//电话 姓名layout


        my_layout_order_coupon = (RelativeLayout) findViewById(R.id.my_layout_order_coupon);;//我的优惠券
        textview_receiver_address_content = (TextView) findViewById(R.id.textview_receiver_address_content);//配送店铺

        youhui_list = (ListView) findViewById(R.id.youhui_list);

        tv_price                = (TextView) findViewById(R.id.tv_price);

        product_total_price     = (TextView) findViewById(R.id.product_total_price);//总价
        product_install_price   = (TextView) findViewById(R.id.product_install_price);//安装费
        product_dilivery_price  = (TextView) findViewById(R.id.product_dilivery_price);//运费
        product_coupon_price    = (TextView) findViewById(R.id.product_coupon_price);//优惠券的减免金额




        //注册监听器


        img_back.setOnClickListener(this);//返回
        payment_way.setOnClickListener(this);//选择支付方式

        conform_order_choose_online.setOnClickListener(this);//在线支付

        conform_order_choose_offline.setOnClickListener(this);//线下支付


        btn_pay.setOnClickListener(this);//提交订单
        name_phone.setOnClickListener(this);
        product_num_display.setOnClickListener(this);

        conform_order_choose_shop.setOnClickListener(this);//选择店铺

        my_layout_order_coupon.setOnClickListener(this);//我的优惠券

        conform_order_choose_time.setOnClickListener(this);//选择安装时间
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            //返回
            case R.id.img_back:
                finish();
                break;
            //选择支付方式
            case R.id.payment_way:
                //显示与隐藏在线与到店支付
                showPayment();
                break;
            //x已经显示出来  选择线上支付
            case R.id.conform_order_choose_online:
                //目前只能线下  支付
                toastMgr.builder.display("目前只支持到店支付,暂不支持线上支付",1);
                break;
            case R.id.conform_order_choose_offline:
                chooseOfflinePayment();
                break;
            //我的优惠券
            case R.id.my_layout_order_coupon:
                //请求网络, 看看是有有可用优惠券
                checkCoupon(mProductPackageListToOrderProductDetailPage);
                break;
            //提交订单 去支付
            case R.id.btn_pay:
                choosePayment();
                break;
            case R.id.name_phone://地址
                chooseAddress();
                break;
            case R.id.product_num_display://
                productNumDetailShow();
                break;
            case R.id.conform_order_choose_shop://选择店铺
                chooseInstallShop();
                break;
            case R.id.conform_order_choose_time://选择安装时间
                datePickerShow(conform_order_install_time);
                break;


        }
    }

    /**
     * 提交订单
     */
    private void commitOrder() {



    }

    /**
     * 检查是否有可用优惠券
     * @param productDetail
     */
    private void checkCoupon(List<Json2ProductPackageBean> productDetail) {

        if (mInstallShopBean == null)
        {
            AlertDialog alertDialog = new AlertDialog(mContext);
            alertDialog.builder().setTitle("提示")
                    .setMsg("您还没有选择安装门店")
                    .setCancelable(true)
                    .setPositiveButton("去选择", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            chooseInstallShop();
                        }
                    })
                    .show();
            return;
        }
        HttpReqConformOrderCoupon reqConformOrderCoupon = new HttpReqConformOrderCoupon();
        reqConformOrderCoupon.setCallback(new Coupon());

        String userid = Configs.getLoginedInfo(mContext).getUserid();
        String cartype = Configs.getLoginedInfo(mContext).getCarType();


        reqConformOrderCoupon.getUseableCoupon(userid, cartype, productDetail, mInstallShopBean);
    }

    class Coupon implements HttpReqConformOrderCouponInterface
    {


        @Override
        public void onSuccess(Json2CouponBean json2CouponBean) {

            Json2CouponBean json2CouponBean1 = json2CouponBean;
            List<CouponsBean> couponsBeen = json2CouponBean1.getCoupons();
            if (couponsBeen.size() >= 1 )
            {
                /*CouponListAdapter adapter = new CouponListAdapter(couponsBeen);
                youhui_list.setAdapter(adapter);
                youhui_list.setVisibility(View.VISIBLE);*/
                toastMgr.builder.display("您有优惠券可用", 1);

                Intent intent = new Intent();
                intent.setClass(mContext, ConformOrderUseableCouponActivity.class);

                Bundle bundle = new Bundle();
                bundle.putSerializable("coupons", (Serializable) couponsBeen);
                intent.putExtras(bundle);
                startActivityForResult(intent,REQUEST_CODE_CHOOSE_COUPON );
            }
            else
            {
                toastMgr.builder.display("没有优惠券可用", 1);
            }

        }

        @Override
        public void onFail(int errorCode, String message) {
            toastMgr.builder.display(message, 1);

        }
    }
    /**
     * 选择支付方式
     */
    private void choosePayment() {
        Intent intent = new Intent();
        intent.setClass(ShoppingMallConformOrderActivity.this, ShoppingMallChoosePaymentActivity.class);
        startActivity(intent);
    }

    /**
     * 选择到店支付
     */
    private void chooseOfflinePayment() {
        offline_pay_imageview.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.button_l_01));
        online_pay_imageview.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.button_l_02));
    }

    /**
     * 选择在线支付
     */
    private void chooseOnlinePayment() {
        online_pay_imageview.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.button_l_01));
        offline_pay_imageview.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.button_l_02));
    }
    /**
     * 显示与隐藏支付方式
     */
    private void showPayment() {
        if (conform_order_choose_online_offline_payment.getVisibility() == View.VISIBLE)
        {
            conform_order_choose_online_offline_payment.setVisibility(View.GONE);
            arrow1.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.personel_arrow_down));
        }
        else
        {
            conform_order_choose_online_offline_payment.setVisibility(View.VISIBLE);
            arrow1.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.personel_arrow_up));
        }
    }

    /**
     * 选择安装店铺
     */
    private void chooseInstallShop() {

        Bundle bundle = new Bundle();
        if (mFrom.equals(Configs.FROM_SHOPPINGMALL))
        {
            //这里面包含了所有的repairService
            bundle.putString("from", mFrom);
            bundle.putStringArrayList("repairServices",  mRepairServices);
        }
        else
        {
            //根据这个区获取门店的repairService
            bundle.putString("from", mFrom);
            bundle.putStringArrayList("shopServiceBean",  mShopServices);
        }

        Intent intent = new Intent();
        intent.setClass(mContext, ShoppingmallConformOrderChooseInstallShopActivity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, REQUEST_CODE_CHOOSE_INSTALL_SHOP);
    }

    /**
     * 点击进入商品列表详情
     */
    private void productNumDetailShow() {
        Intent intent = new Intent();
        intent.setClass(mContext, ConformOrderProductDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("productPackageList", (Serializable) mProductPackageListToOrderProductDetailPage);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 去选择收货人信息
     *
     *
     */
    private void chooseAddress() {
        Intent intent = new Intent();
        intent.setClass(mContext, ShoppingmallChooseReceiveAddressActivity.class);

        startActivityForResult(intent, REQUEST_CODE_CHOOSE_ADDRESS);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
        {
            if (requestCode == REQUEST_CODE_CHOOSE_ADDRESS)
            {
                Bundle bundle = data.getExtras();
                AddressBean bean = (AddressBean) bundle.getSerializable("address");

                setTopAddressDetail(bean.getName(), bean.getPhone());
            }
            else if (requestCode  == REQUEST_CODE_CHOOSE_INSTALL_SHOP)
            {
                //是从选择安装门店进来的
                Bundle bundle = data.getExtras();
                Json2InstallShopModelsBean shopModel = (Json2InstallShopModelsBean) bundle.getSerializable("shopModel");
                mInstallShopBean = shopModel;
                //设置选择的店铺
                setInstallShop(shopModel);
            }
            else if (requestCode == REQUEST_CODE_CHOOSE_COUPON)
            {
                //选择优惠券
                Bundle bundle = data.getExtras();
                CouponsBean coupon = (CouponsBean) bundle.getSerializable("coupon");

                mSelectedCoupon = coupon;

                if (coupon.getCouponName().equals("不使用"))
                {
                    coupon_description.setText(coupon.getCouponName()+"优惠券");
                }
                else
                {
                    coupon_description.setText("满" + coupon.getRegulationReach()+ "减" + coupon.getRegulationSubtract() + "优惠券");
                }


                //选择了优惠券 就去拿订单价格

                String userid = Configs.getLoginedInfo(mContext).getUserid();
                String cartype = Configs.getLoginedInfo(mContext).getCarType();
                getOrderPrice(userid, cartype, mProductPackageListToOrderProductDetailPage, mInstallShopBean, mSelectedCoupon);

            }
        }
        else
        {
            toastMgr.builder.display("您没有选择",1);
        }
    }

    /**
     * 去拿订单价格
     * @param userid
     * @param cartype
     * @param ProductDetailPage
     * @param nstallShopBean
     * @param coupon
     */
    private void getOrderPrice(String userid, String cartype, List<Json2ProductPackageBean> ProductDetailPage, Json2InstallShopModelsBean nstallShopBean, CouponsBean coupon) {
        mProgress = mProgress.show(mContext, "获取订单价格...", false, null);
        HttpReqGetOrderPrice reqGetOrderPrice = new HttpReqGetOrderPrice();
        reqGetOrderPrice.setCallback(new HttpReqCallback() {
            @Override
            public void onFail(int errorCode, String message) {
                mProgress.dismiss();
                if (errorCode == ErrorCodes.ERROR_CODE_LOW_VERSION)
                {
                    UpdateApp.gotoUpdateApp(mContext);
                }
                else if (errorCode == ErrorCodes.ERROR_CODE_SERVER_ERROR)
                {
                    toastMgr.builder.display(message, 1);
                }
                else
                {
                    toastMgr.builder.display(message, 1);
                }
            }

            @Override
            public void onSuccess(Object object) {
                mProgress.dismiss();
                Json2OrderPriceBean orderPrice = (Json2OrderPriceBean) object;
                setOrderPrice(orderPrice);

            }
        });
        reqGetOrderPrice.getOrderPrice(userid, cartype, ProductDetailPage, nstallShopBean, coupon);

    }

    /**
     * 设置订单价格
     * @param orderPrice
     */
    private void setOrderPrice(Json2OrderPriceBean orderPrice)
    {
        tv_price.setText(orderPrice.getTotalPrice()+"");
        product_total_price.setText(orderPrice.getTotalPrice()+"");
        product_install_price.setText(orderPrice.getInstallationCoast() + "");
        product_dilivery_price.setText("免运费");
        product_coupon_price.setText("-" + orderPrice.getCouponPrice());

    }

    /**
     * 优惠券列表适配
     */
    private class CouponListAdapter extends BaseAdapter
    {
        List<CouponsBean> coupons;

        public CouponListAdapter(List<CouponsBean> coupons)
        {
            this.coupons = coupons;
            CouponsBean bean = new CouponsBean();
            bean.setCouponName("不使用");
            this.coupons.add(bean);
        }

        @Override
        public int getCount() {
            return coupons.size();
        }

        @Override
        public Object getItem(int i) {
            return coupons.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {

            final ViewHolder holder;
            if (view == null)
            {
                holder = new ViewHolder();
                view = View.inflate(mContext, R.layout.item_conform_order_coupon_list_item, null);
                holder.conform_order_coupon_layout = (LinearLayout) view.findViewById(R.id.conform_order_coupon_layout);
                holder.coupon_name = (TextView) view.findViewById(R.id.coupon_name);
                holder.coupon_check_imageview = (ImageView) view.findViewById(R.id.coupon_check_imageview);
                view.setTag(holder);
            }
            else
            {
                holder = (ViewHolder) view.getTag();
            }

            if (coupons.get(position).getCouponName().equals("不使用"))
            {
                holder.coupon_name.setText("不使用");
            }
            else
            {
                holder.coupon_name.setText(coupons.get(position).getRegulationReach()+"元");
            }


            holder.coupon_check_imageview.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.button_l_02));

            holder.conform_order_coupon_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.coupon_check_imageview.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.button_l_01));
                    youhui_list.setVisibility(View.GONE);
                }
            });



            return view;
        }

        class ViewHolder
        {
            LinearLayout    conform_order_coupon_layout;
            TextView        coupon_name;
            ImageView       coupon_check_imageview;

        }
    }




    /**
     * 计算商品总价
     * @param beans
     */
    private void countTotalPrice(List<Json2ProductPackageBean> beans)
    {
        int size = beans.size();
        double l_total_size = 0;
        for (Json2ProductPackageBean bean : beans)
        {
            int num = bean.getProductAmount();
            double price = bean.getRetailPrice();
            l_total_size  = l_total_size + ((double)num) * price;
        }
        setTotalPrice(l_total_size);
    }


    private void setTotalPrice(double price)
    {
        tv_price.setText("￥" + price + "");
    }

    /**
     * 设置选择的店铺
     * @param shopModel
     */
    private void setInstallShop(Json2InstallShopModelsBean shopModel) {

        textview_receiver_address_content.setText("在" + shopModel.getShopAddress() + shopModel.getShopName() + "安装");


    }

    int dateYear;
    int dateMonth;
    int dateDay;
    protected void datePickerShow(final TextView textView)
    {
//      DatePickerDialog picker = new DatePickerDialog(this,
//          new OnDateSetListener() {
//              @Override
//              public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                  if (monthOfYear < 9 && dayOfMonth < 10) {
//                      textView.setText(year + "-0" + (monthOfYear + 1) + "-0" + dayOfMonth);
//                  } else if (monthOfYear >= 9 && dayOfMonth < 10) {
//                      textView.setText(year + "-" + (monthOfYear + 1) + "-0" + dayOfMonth);
//                  } else if (monthOfYear < 9 && dayOfMonth >= 10) {
//                      textView.setText(year + "-0" + (monthOfYear + 1) + "-" + dayOfMonth);
//                  } else {
//                      textView.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
//                  }
//              }
//          }, cd.get(Calendar.YEAR), cd.get(Calendar.MONTH), cd.get(Calendar.DAY_OF_MONTH));
//      picker.show();

        final Dialog dialog = new Dialog(this, R.style.ActionSheetDialogStyle);
        dialog.setContentView(R.layout.wheel_date_picker_dialog);

        MyDatePicker dpicker = (MyDatePicker) dialog.findViewById(R.id.datepicker_layout);
        final TextView txDateAndWeekDay = (TextView) dialog.findViewById(R.id.datepicker_date_and_weekday);
        Button btBeDown = (Button) dialog.findViewById(R.id.datepicker_btsure);
        Button btCancel = (Button) dialog.findViewById(R.id.datepicker_btcancel);
        dpicker.setOnChangeListener(new MyDatePicker.OnChangeListener() {
            @Override
            public void onChange(int year, int month, int day, int day_of_week) {
//              txDateAndWeekDay.setText(year + "年" + month + "月" + day + "日  星期" + MyDatePicker.getDayOfWeekCN(day_of_week));
                dateYear  = year;
                dateMonth = month;
                dateDay   = day;

                if (dateMonth < 10 && dateDay < 10) {
                    txDateAndWeekDay.setText(dateYear + "-0" + dateMonth + "-0" + dateDay+ " 星期" + MyDatePicker.getDayOfWeekCN(day_of_week));
                } else if (dateMonth >= 10 && dateDay < 10) {
                    txDateAndWeekDay.setText(dateYear + "-" + dateMonth + "-0" + dateDay+ " 星期" + MyDatePicker.getDayOfWeekCN(day_of_week));
                } else if (dateMonth < 10 && dateDay >= 10) {
                    txDateAndWeekDay.setText(dateYear + "-0" + dateMonth + "-" + dateDay+ " 星期" + MyDatePicker.getDayOfWeekCN(day_of_week));
                } else {
                    txDateAndWeekDay.setText(dateYear + "-" + dateMonth + "-" + dateDay+ " 星期" + MyDatePicker.getDayOfWeekCN(day_of_week));
                }
            }
        });


        btBeDown.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (dateMonth < 10 && dateDay < 10) {
                    textView.setText(dateYear + "-0" + dateMonth  + "-0" + dateDay);
                } else if (dateMonth >= 10 && dateDay < 10) {
                    textView.setText(dateYear + "-" + dateMonth  + "-0" + dateDay);
                } else if (dateMonth < 10 && dateDay >= 10) {
                    textView.setText(dateYear + "-0" + dateMonth + "-" + dateDay);
                } else {
                    textView.setText(dateYear + "-" + dateMonth  + "-" + dateDay);
                }
//              textView.setText(dateYear + "-" + dateMonth + "-" + dateDay);
                dialog.dismiss();
            }
        });

        btCancel.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.setCancelable(false);
        dialog.setOnKeyListener(new android.content.DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                switch (keyCode) {
                    case KeyEvent.KEYCODE_BACK:
                        return true;
                }
                return false;
            }
        });
        dialog.show();

    }
    private static final int REQUEST_CODE_CHOOSE_ADDRESS = 0X01;//选择地址
    private static final int REQUEST_CODE_CHOOSE_INSTALL_SHOP = 0X10;//选择安装门店
    private static final int REQUEST_CODE_CHOOSE_COUPON = 0X11;//选择安装门店

    private CouponsBean mSelectedCoupon = null;//选择优惠券之后   赋值全局变量

}
