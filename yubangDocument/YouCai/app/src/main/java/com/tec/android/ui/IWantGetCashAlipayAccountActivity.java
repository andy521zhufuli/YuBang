package com.tec.android.ui;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import com.tec.android.R;


/**
 * 我要提现到支付宝账户
 */

public class IWantGetCashAlipayAccountActivity extends BaseActivity {

    private Context mContext;
    private static final String TAG = "IWantGetCashAlipayAccountActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_iwant_get_cash_alipay_account);

        mContext = this;

        findViews();


    }

    /**
     * 初始化控件
     */
    private void findViews() {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
