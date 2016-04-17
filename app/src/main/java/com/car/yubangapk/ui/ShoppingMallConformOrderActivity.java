package com.car.yubangapk.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.car.yubangapk.configs.Configs;
import com.car.yubangapk.configs.ErrorCodes;
import com.car.yubangapk.json.bean.Json2AddressBean;
import com.car.yubangapk.json.bean.Json2LoginBean;


import com.car.yubangapk.network.myHttp.HttpReqAddress;
import com.car.yubangapk.network.myHttp.httpReqInterface;
import com.car.yubangapk.utils.Warn.NotLogin;
import com.car.yubangapk.utils.Warn.UpdateApp;
import com.car.yubangapk.utils.toastMgr;
import com.andy.android.yubang.R;

/**
 * ShoppingMallConformOrderActivity: 确认订单界面
 *
 * @author andyzhu
 * @version 1.0
 * @created 2016-02-29
 */
public class ShoppingMallConformOrderActivity extends BaseActivity implements View.OnClickListener{

    private Context mContext;

    private ImageView      img_back;//返回
    private RelativeLayout payment_way;//选择支付方式
    private ImageView      arrow1;//选择支付方式的箭头
    private LinearLayout   conform_order_choose_online;//在线支付
    private ImageView      online_pay_imageview;//在线支付选择
    private LinearLayout   conform_order_choose_offline;//线下支付
    private ImageView      offline_pay_imageview;//在线支付选择
    private RelativeLayout my_layout_mine_order;//优惠券
    private LinearLayout   conform_order_choose_online_offline_payment;//隐藏的线上与到店支付的布局
    private boolean        isOnlineOrOffline = false; //false 代表线下支付

    private TextView       textview_receiver_name_content;//收货名字
    private TextView       textview_receiver_mobile_content;//收货电话
    private RelativeLayout name_phone;//




    private RelativeLayout btn_pay;//提交订单  去支付


    private Json2AddressBean mAddressBean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_shopping_mall_conform_order);

        mContext = this;

        findViews();

        Json2LoginBean bean = Configs.getLoginedInfo(mContext);
        String userid = bean.getUserid();
        HttpReqAddress reqGetAddressConformOrder = new HttpReqAddress(userid,"3", null, null, null);
        reqGetAddressConformOrder.setCallback(new httpReqInterface() {
            @Override
            public void onGetAddressSucces(Json2AddressBean addressBean) {
                Json2AddressBean json2AddressBean = addressBean;
                mAddressBean = json2AddressBean;
                //去设置地址
                textview_receiver_name_content.setText(json2AddressBean.getDefaultAddress().getName());
                textview_receiver_mobile_content.setText(json2AddressBean.getDefaultAddress().getPhone());
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

    private void findViews() {

        img_back = (ImageView) findViewById(R.id.img_back);//返回
        payment_way = (RelativeLayout) findViewById(R.id.payment_way);//选择支付方式
        arrow1 = (ImageView) findViewById(R.id.arrow1);//选择支付方式的箭头
        conform_order_choose_online = (LinearLayout) findViewById(R.id.conform_order_choose_online);//在线支付
        online_pay_imageview = (ImageView) findViewById(R.id.online_pay_imageview);//在线支付选择
        conform_order_choose_offline = (LinearLayout) findViewById(R.id.conform_order_choose_offline);//线下支付
        offline_pay_imageview = (ImageView) findViewById(R.id.offline_pay_imageview);//在线支付选择
        my_layout_mine_order = (RelativeLayout) findViewById(R.id.my_layout_mine_order);//优惠券
        conform_order_choose_online_offline_payment = (LinearLayout) findViewById(R.id.conform_order_choose_online_offline_payment);
        btn_pay = (RelativeLayout) findViewById(R.id.btn_pay);


        textview_receiver_name_content = (TextView) findViewById(R.id.textview_receiver_name_content);;//收货名字
        textview_receiver_mobile_content = (TextView) findViewById(R.id.textview_receiver_mobile_content);;//收货电话

        name_phone = (RelativeLayout) findViewById(R.id.name_phone);;//电话 姓名layout

        //注册监听器

        img_back.setOnClickListener(this);//返回
        payment_way.setOnClickListener(this);//选择支付方式

        conform_order_choose_online.setOnClickListener(this);//在线支付

        conform_order_choose_offline.setOnClickListener(this);//线下支付
        my_layout_mine_order.setOnClickListener(this);//优惠券

        btn_pay.setOnClickListener(this);//提交订单
        name_phone.setOnClickListener(this);
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

                break;
            //x已经显示出来  选择线上支付
            case R.id.conform_order_choose_online:
                if (isOnlineOrOffline == false)
                {
                    online_pay_imageview.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.button_l_01));
                }
                else
                {
                    online_pay_imageview.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.button_l_02));
                }
                isOnlineOrOffline = true;//线上支付
                break;
            case R.id.conform_order_choose_offline:
                if (isOnlineOrOffline == true)
                {
                    offline_pay_imageview.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.button_l_01));
                }
                else
                {
                    offline_pay_imageview.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.button_l_02));
                }
                isOnlineOrOffline = false;
                break;
            //我的优惠券
            case R.id.my_layout_mine_order:
                toastMgr.builder.display("没有可用优惠券", 0);
                break;
            //提交订单 去支付
            case R.id.btn_pay:
                Intent intent = new Intent();
                intent.setClass(ShoppingMallConformOrderActivity.this, ShoppingMallChoosePaymentActivity.class);
                startActivity(intent);
                break;
            case R.id.name_phone:
                chooseAddress();
                break;
        }
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

    private static final int REQUEST_CODE_CHOOSE_ADDRESS = 0X01;

}
