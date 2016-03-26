package com.car.yubangapk.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import com.car.yubangapk.utils.toastMgr;
import com.andy.android.yubang.R;

/**
 * DiscoverActivity: 发现界面
 *
 * @author andyzhu
 * @version 1.0
 * @created 2016-02-22
 */
public class DiscoverActivity extends BaseActivity implements View.OnClickListener{


    private LinearLayout discover_activity_recommend_partners_wallet_layout;//推荐合伙人
    private LinearLayout discover_activity_nearby_layout;//附近的
    private LinearLayout iwant_get_cash_account_alipay_wallet_layout;//物流

    private LinearLayout iwant_get_cash_account_alipay_account_layout;//保险
    private LinearLayout iwant_get_cash_account_alipay_account_layout1;//油卡

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_discover);
        
        findViews();
    }

    private void findViews() {

        discover_activity_recommend_partners_wallet_layout = (LinearLayout) findViewById(R.id.discover_activity_recommend_partners_wallet_layout);//推荐合伙人
        discover_activity_nearby_layout = (LinearLayout) findViewById(R.id.discover_activity_nearby_layout);//附近的
        iwant_get_cash_account_alipay_wallet_layout = (LinearLayout) findViewById(R.id.iwant_get_cash_account_alipay_wallet_layout);//物流
        iwant_get_cash_account_alipay_account_layout = (LinearLayout) findViewById(R.id.iwant_get_cash_account_alipay_account_layout);//保险
        iwant_get_cash_account_alipay_account_layout1 = (LinearLayout) findViewById(R.id.iwant_get_cash_account_alipay_account_layout1);//油卡
        /**
         * 注册监听器
         */
        discover_activity_recommend_partners_wallet_layout.setOnClickListener(this);//推荐合伙人
        discover_activity_nearby_layout.setOnClickListener(this);//附近的
        iwant_get_cash_account_alipay_wallet_layout.setOnClickListener(this);//物流
        iwant_get_cash_account_alipay_account_layout.setOnClickListener(this);//保险
        iwant_get_cash_account_alipay_account_layout1.setOnClickListener(this);//油卡

    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId())
        {
            //推荐合伙人
            case R.id.discover_activity_recommend_partners_wallet_layout:

                intent = new Intent();
                intent.setClass(DiscoverActivity.this, DiscoverRecommendPartnerActivity.class);
                startActivity(intent);
                intent = null;
                break;
            //附近的
            case R.id.discover_activity_nearby_layout:
                intent = new Intent();
                intent.setClass(DiscoverActivity.this, DiscoverNearbyActivity.class);
                startActivity(intent);
                intent = null;
                break;
            //物流
            case R.id.iwant_get_cash_account_alipay_wallet_layout:
                toastMgr.builder.display("第一期不用做", 0);
                break;
            //保险
            case R.id.iwant_get_cash_account_alipay_account_layout:
                toastMgr.builder.display("第一期不用做", 0);
                break;
            //油卡
            case R.id.iwant_get_cash_account_alipay_account_layout1:
                toastMgr.builder.display("第一期不用做", 0);
                break;
        }
    }
}
