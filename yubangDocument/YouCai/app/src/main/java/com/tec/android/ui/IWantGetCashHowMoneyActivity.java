package com.tec.android.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.tec.android.R;
import com.tec.android.utils.toastMgr;


/**
 * 我要提现多少钱
 */
public class IWantGetCashHowMoneyActivity extends BaseActivity implements View.OnClickListener{

    private Context mContext;
    private static final String TAG = "IWantGetCashHowMoneyActivity";


    private EditText iwant_get_cash_money_editview;//输入提现金额
    private Button iwant_get_cash_nextstep_button;//下一步



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_iwant_get_cash_how_money);

        mContext = this;

        findViews();

    }

    private void findViews() {
        iwant_get_cash_money_editview = (EditText) findViewById(R.id.iwant_get_cash_money_editview);//金额
        iwant_get_cash_nextstep_button = (Button) findViewById(R.id.iwant_get_cash_nextstep_button);//下一步

        //设置监听器
        iwant_get_cash_nextstep_button.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            //下一步
            case R.id.iwant_get_cash_nextstep_button:
                gotoNextStep();
                break;
        }
    }

    /**
     * 提现下一步
     */
    private void gotoNextStep() {
        //首先拿到用户提现金额
        String iwant_cash_out_money_count = iwant_get_cash_money_editview.getText().toString();


        toastMgr.builder.display("IWantGetCashActivity下一步", 0);
        Intent intent = new Intent();
        intent.putExtra("money", iwant_cash_out_money_count);
        intent.setClass(mContext, IWantGetCashAccountActivity.class);
        startActivity(intent);
    }
}
