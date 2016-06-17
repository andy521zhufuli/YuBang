package com.car.yubangapk.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.car.yubangapk.configs.Configs;
import com.car.yubangapk.configs.ErrorCodes;
import com.car.yubangapk.json.bean.wallet.Json2MyWalletBean;
import com.car.yubangapk.network.myHttpReq.HttpReqCallback;
import com.car.yubangapk.network.myHttpReq.wallet.HttpReqGetUserWallet;
import com.car.yubangapk.utils.Warn.NotLogin;
import com.car.yubangapk.utils.toastMgr;
import com.andy.android.yubang.R;
import com.car.yubangapk.view.AlertDialog;
import com.car.yubangapk.view.CustomProgressDialog;

/**
 * MyWwalletActivity: 我的钱包界面
 *
 * @author andyzhu
 * @version 1.0
 * @created 2016-02-26
 */
public class MyWwalletActivity extends BaseActivity implements HttpReqCallback{

    private Context mContext;

    private ImageView img_back;//返回
    private TextView  my_wallet_amount_of_recharge_item_button;//充值按钮
    private TextView  my_wallet_amount_of_detail_money_item_button;//金额明细 的按钮
    private TextView  my_wallet_member_consumer_commission_button;//会员消费提成明细按钮
    private TextView  my_wallet_member_ad_button;//广告转发明细按钮
    private TextView  my_wallet_member_invite_yongjin_button;//佣金明细 按钮
    private RelativeLayout my_personal_item_real_name_certification_layout;//我的优惠券

    private TextView  my_wallet_total_money;//总金额
    private TextView  my_wallet_amount_of_recharge_item_tv_sum;//充值显示金额
    private TextView  my_wallet_amount_of_detail_money_item_tv_sum;//金额明细 的文本
    private TextView  my_wallet_member_consumer_commission_tv_sum;//会员消费提成明细总数
    private TextView  my_wallet_member_add_tv_sum;//广告转发总数
    private TextView  my_wallet_invite_yongjin_tv_sum;//佣金明细总数
    private TextView  my_wallet_wait_come_back_money_tv_sum;//带返还总金额
    private TextView  my_wallet_today_come_back_money_tv_sum;//今日返还




    private CustomProgressDialog mProgressDialog;
    private HttpReqGetUserWallet httpReqGetUserWallet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_my_wwallet);

        mContext = this;

        findViews();

        mProgressDialog = new CustomProgressDialog(mContext);
        httpReqGetUserWallet = new HttpReqGetUserWallet();
        httpReqGetUserWallet.setCallback(this);

        getMyWallet();

    }

    /**
     * 网络获取钱包信息
     */
    private void getMyWallet() {
        mProgressDialog = mProgressDialog.show(mContext, "正在加载...", false, null);
        String userid = Configs.getLoginedInfo(mContext).getUserid();
        httpReqGetUserWallet.getUserWallet(userid);
    }

    /**
     * 绑定控件
     */
    private void findViews() {
        img_back                                        = (ImageView) findViewById(R.id.img_back);//返回

        my_wallet_total_money                           = (TextView) findViewById(R.id.my_wallet_total_money);//总额

        my_wallet_amount_of_recharge_item_tv_sum        = (TextView) findViewById(R.id.my_wallet_amount_of_recharge_item_tv_sum);
        my_wallet_amount_of_recharge_item_button        = (TextView) findViewById(R.id.my_wallet_amount_of_recharge_item_button);

        my_wallet_amount_of_detail_money_item_tv_sum    = (TextView) findViewById(R.id.my_wallet_amount_of_detail_money_item_tv_sum);//总额明细 显示
        my_wallet_member_consumer_commission_tv_sum     = (TextView) findViewById(R.id.my_wallet_member_consumer_commission_tv_sum);//会员消费提成
        my_wallet_member_add_tv_sum                     = (TextView) findViewById(R.id.my_wallet_member_add_tv_sum);//广告转发


        my_wallet_invite_yongjin_tv_sum                 = (TextView) findViewById(R.id.my_wallet_invite_yongjin_tv_sum);
        my_wallet_wait_come_back_money_tv_sum           = (TextView) findViewById(R.id.my_wallet_wait_come_back_money_tv_sum);
        my_wallet_today_come_back_money_tv_sum          = (TextView) findViewById(R.id.my_wallet_today_come_back_money_tv_sum);



        my_personal_item_real_name_certification_layout = (RelativeLayout) findViewById(R.id.my_personal_item_real_name_certification_layout);
        my_wallet_amount_of_detail_money_item_button    = (TextView) findViewById(R.id.my_wallet_amount_of_detail_money_item_button);//金额明细
        my_wallet_member_consumer_commission_button     = (TextView) findViewById(R.id.my_wallet_member_consumer_commission_button);//会员消费提成明细
        my_wallet_member_ad_button                      = (TextView) findViewById(R.id.my_wallet_member_ad_button);//广告转发明细
        my_wallet_member_invite_yongjin_button          = (TextView) findViewById(R.id.my_wallet_member_invite_yongjin_button);//佣金明细


        /**
         * 监听器
         */

        my_wallet_amount_of_recharge_item_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recharge();
            }
        });

        //金额明细
        my_wallet_amount_of_detail_money_item_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toastMgr.builder.display("waiting...", 0);
                totalMoneyDetail();
            }
        });
        //会员消费提成明细
        my_wallet_member_consumer_commission_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toastMgr.builder.display("waiting...", 0);
                memberConsumerCommission();
            }
        });
        //广告转发明细
        my_wallet_member_ad_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toastMgr.builder.display("waiting...", 0);
                adTransmit();
            }
        });
        //佣金明细
        my_wallet_member_invite_yongjin_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inviteYongjin();
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

    /**
     * 邀请佣金明细
     */
    private void inviteYongjin() {
        Intent intent = new Intent();
        intent.setClass(MyWwalletActivity.this, MyCommissionDetailActivity.class);
        startActivity(intent);
    }

    /**
     * 广告转发明细
     */
    private void adTransmit() {

    }

    /**
     * 会员消费提成
     */
    private void memberConsumerCommission() {

    }

    /**
     * 金额明细
     */
    private void totalMoneyDetail() {

    }

    /**
     * 充值
     */
    private void recharge() {


    }

    @Override
    public void onFail(int errorCode, String message) {
        //进度匡取消
        mProgressDialog.dismiss();
        //获取钱包数据失败  提示用户为何失败
        if (errorCode == ErrorCodes.ERROR_CODE_NETWORK)
        {
            //网络错误, 提示用户
            networkErrorAndReload();
        }
        else if (errorCode == ErrorCodes.ERROR_CODE_NOT_LOGIN)
        {
            NotLogin.gotoLogin(MyWwalletActivity.this);
        }
        else
        {

        }
    }

    @Override
    public void onSuccess(Object object)
    {
        mProgressDialog.dismiss();
        Json2MyWalletBean myWalletBean = (Json2MyWalletBean) object;
        setMyWalletInfo(myWalletBean);

    }

    /**
     * 设置钱包数据
     * @param walletBean
     */
    private void setMyWalletInfo(Json2MyWalletBean walletBean) {

        my_wallet_total_money.setText(walletBean.getTotalMoney() + "");//总金额
        my_wallet_amount_of_recharge_item_tv_sum.setText(walletBean.getRechargeMoney() + "");//充值显示金额
        //my_wallet_amount_of_detail_money_item_tv_sum.setText(walletBean.get);//金额明细 的文本
        my_wallet_member_consumer_commission_tv_sum.setText(walletBean.getConsumeMoney() + "");//会员消费提成明细总数
        my_wallet_member_add_tv_sum.setText(walletBean.getAdMoney() + "");//广告转发总数
        my_wallet_invite_yongjin_tv_sum.setText(walletBean.getInvetMoney() + "");//佣金明细总数
        my_wallet_wait_come_back_money_tv_sum.setText(walletBean.getWaitTotalMoney() + "");//带返还总金额
        my_wallet_today_come_back_money_tv_sum.setText(walletBean.getTodayMoney() + "");//今日返还
    }

    /**
     * 提示用户网络错误  并且看用户是否选择重新加载
     */
    private void networkErrorAndReload() {
        AlertDialog alertDialog = new AlertDialog(mContext);
        alertDialog.builder().setCancelable(false)
                .setMsg("请确认网络连接正常后重新加载!")
                .setTitle("网络错误")
                .setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //do nothing
                    }
                })
                .setPositiveButton("重新加载", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //重新加载钱包数据
                        getMyWallet();
                    }
                })
                .show();
    }

}
