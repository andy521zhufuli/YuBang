package com.car.yubangapk.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.car.yubangapk.utils.toastMgr;
import com.andy.android.yubang.R;

/**
 * ShoppingMallChoosePaymentActivity: 收银台
 *
 * @author andyzhu
 * @version 1.0
 * @created 2016-02-29
 */
public class ShoppingMallChoosePaymentActivity extends BaseActivity implements View.OnClickListener {


    private RelativeLayout shoppingmall_choose_payment_weixin_wallet_layout;//微信支付

    private RelativeLayout shoppingmall_choose_payment_alipay_wallet_layout;//支付宝支付

    private RelativeLayout shoppingmall_choose_payment_bank_wallet_layout;//银行卡支付

    private ImageView      title_back;//返回

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_shopping_mall_choose_payment);

        findViews();
    }

    private void findViews() {

        shoppingmall_choose_payment_weixin_wallet_layout = (RelativeLayout) findViewById(R.id.shoppingmall_choose_payment_weixin_wallet_layout);//微信支付
        shoppingmall_choose_payment_alipay_wallet_layout = (RelativeLayout) findViewById(R.id.shoppingmall_choose_payment_alipay_wallet_layout);//支付宝支付
        shoppingmall_choose_payment_bank_wallet_layout = (RelativeLayout) findViewById(R.id.shoppingmall_choose_payment_bank_wallet_layout);//银行卡支付
        title_back = (ImageView) findViewById(R.id.title_back);//返回

        /**
         * 监听器
         */
        shoppingmall_choose_payment_weixin_wallet_layout.setOnClickListener(this);
        shoppingmall_choose_payment_alipay_wallet_layout.setOnClickListener(this);
        shoppingmall_choose_payment_bank_wallet_layout.setOnClickListener(this);
        title_back.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            //微信支付
            case R.id.shoppingmall_choose_payment_weixin_wallet_layout:
                //
                toastMgr.builder.display("选择微信支付", 0);
                break;
            //支付宝支付
            case R.id.shoppingmall_choose_payment_alipay_wallet_layout:
                toastMgr.builder.display("选择支付宝支付", 0);
                break;
            //银行卡支付
            case R.id.shoppingmall_choose_payment_bank_wallet_layout:
                toastMgr.builder.display("选择银行卡支付, 请您先绑定银行卡", 0);
                Intent intent = new Intent();
                intent.setClass(ShoppingMallChoosePaymentActivity.this, ShoppingMallPaymentAddBankCardActivity.class);
                startActivity(intent);
                break;
            case R.id.title_back:
                finish();
                break;
        }
    }
}
