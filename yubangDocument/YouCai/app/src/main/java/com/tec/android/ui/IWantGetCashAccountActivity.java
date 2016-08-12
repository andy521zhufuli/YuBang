package com.tec.android.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;

import com.tec.android.R;
import com.tec.android.utils.toastMgr;

/**
 * 我要提现 账户选择界面  支付宝还是银行卡
 */
public class IWantGetCashAccountActivity extends BaseActivity implements View.OnClickListener{

    private Context mContext;
    private RelativeLayout iwant_get_cash_account_weixin_wallet_layout;//提现到我的微信钱包
    private RelativeLayout iwant_get_cash_account_weixin_account_layout;//提现到已有账户
    private RelativeLayout iwant_get_cash_account_alipay_wallet_layout;//支付宝账户
    private RelativeLayout iwant_get_cash_account_alipay_account_layout;//支付宝已有账户

    private String mCashOutMoney;//提现金额

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_iwant_get_cash_account);

        //拿到用户要提现的金额
        mCashOutMoney = getIntent().getStringExtra("money");

        mContext = this;

        findViews();




    }

    private void findViews()
    {
        iwant_get_cash_account_weixin_wallet_layout = (RelativeLayout) findViewById(R.id.iwant_get_cash_account_weixin_wallet_layout);
        iwant_get_cash_account_weixin_account_layout = (RelativeLayout) findViewById(R.id.iwant_get_cash_account_weixin_account_layout);
        iwant_get_cash_account_alipay_wallet_layout = (RelativeLayout) findViewById(R.id.iwant_get_cash_account_alipay_wallet_layout);
        iwant_get_cash_account_alipay_account_layout = (RelativeLayout) findViewById(R.id.iwant_get_cash_account_alipay_account_layout);

        //设置监听器
        iwant_get_cash_account_weixin_wallet_layout.setOnClickListener(this);
        iwant_get_cash_account_weixin_account_layout.setOnClickListener(this);
        iwant_get_cash_account_alipay_wallet_layout.setOnClickListener(this);
        iwant_get_cash_account_alipay_account_layout.setOnClickListener(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            //提现到我的微信钱包
            case R.id.iwant_get_cash_account_weixin_wallet_layout:
                toastMgr.builder.display("提现到我的微信钱包", 0);
                //调用微信
                break;
            //提现到已有账户
            case R.id.iwant_get_cash_account_weixin_account_layout:
                toastMgr.builder.display("提现到已有账户", 0);
                //发送请求给后台 ,  拿到已有账户
                break;
            //支付宝账户
            case R.id.iwant_get_cash_account_alipay_wallet_layout:
                toastMgr.builder.display("支付宝账户", 0);
                //调用支付宝
                break;
            //银行账户账户
            case R.id.iwant_get_cash_account_alipay_account_layout:
                toastMgr.builder.display("银行账户", 0);
                Intent intent =  new Intent();
                intent.putExtra("money", mCashOutMoney);
                intent.setClass(mContext, IWantGetCashBankAccountActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
