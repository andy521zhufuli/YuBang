package com.android.andy.yubang.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.andy.yubang.utils.toastMgr;
import com.andy.android.yubang.R;
/**
 * MyWwalletActivity: 我的钱包界面
 *
 * @author andyzhu
 * @version 1.0
 * @created 2016-02-26
 */
public class MyWwalletActivity extends BaseActivity {

    private Context mContext;

    private RelativeLayout my_personal_item_real_name_certification_layout;//我的优惠券

    private TextView my_wallet_amount_of_detail_money_item_button;//金额明细
    private TextView my_wallet_member_consumer_commission_button;//会员消费提成明细
    private TextView my_wallet_member_ad_button;//广告转发明细
    private TextView my_wallet_member_invite_yongjin_button;//佣金明细

    private ImageView img_back;//返回

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_my_wwallet);

        mContext = this;

        findViews();

    }

    private void findViews() {
        my_personal_item_real_name_certification_layout = (RelativeLayout) findViewById(R.id.my_personal_item_real_name_certification_layout);
        my_wallet_amount_of_detail_money_item_button = (TextView) findViewById(R.id.my_wallet_amount_of_detail_money_item_button);//金额明细
        my_wallet_member_consumer_commission_button = (TextView) findViewById(R.id.my_wallet_member_consumer_commission_button);//会员消费提成明细
        my_wallet_member_ad_button = (TextView) findViewById(R.id.my_wallet_member_ad_button);//广告转发明细
        my_wallet_member_invite_yongjin_button = (TextView) findViewById(R.id.my_wallet_member_invite_yongjin_button);//佣金明细
        img_back                = (ImageView) findViewById(R.id.img_back);//返回

        /**
         * 监听器
         */

        //金额明细
        my_wallet_amount_of_detail_money_item_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toastMgr.builder.display("waiting...", 0);
            }
        });
        //会员消费提成明细
        my_wallet_member_consumer_commission_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toastMgr.builder.display("waiting...", 0);
            }
        });
        //广告转发明细
        my_wallet_member_ad_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toastMgr.builder.display("waiting...", 0);
            }
        });
        //佣金明细
        my_wallet_member_invite_yongjin_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MyWwalletActivity.this, MyCommissionDetailActivity.class);
                startActivity(intent);
            }
        });
        //返回
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //我的优惠券
        my_personal_item_real_name_certification_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MyWwalletActivity.this, MyCouponActivity.class);
                startActivity(intent);
            }
        });
    }

}
