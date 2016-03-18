package com.android.andy.yubang.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.andy.yubang.utils.toastMgr;
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

    private RelativeLayout btn_pay;//提交订单  去支付


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_shopping_mall_conform_order);

        mContext = this;

        findViews();
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
        //注册监听器

        img_back.setOnClickListener(this);//返回
        payment_way.setOnClickListener(this);//选择支付方式

        conform_order_choose_online.setOnClickListener(this);//在线支付

        conform_order_choose_offline.setOnClickListener(this);//线下支付
        my_layout_mine_order.setOnClickListener(this);//优惠券

        btn_pay.setOnClickListener(this);//提交订单
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
                    online_pay_imageview.setImageDrawable(getDrawable(R.mipmap.button_l_01));
                }
                else
                {
                    online_pay_imageview.setImageDrawable(getDrawable(R.mipmap.button_l_02));
                }
                isOnlineOrOffline = true;//线上支付
                break;
            case R.id.conform_order_choose_offline:
                if (isOnlineOrOffline == true)
                {
                    offline_pay_imageview.setImageDrawable(getDrawable(R.mipmap.button_l_01));
                }
                else
                {
                    offline_pay_imageview.setImageDrawable(getDrawable(R.mipmap.button_l_02));
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
        }
    }
}
