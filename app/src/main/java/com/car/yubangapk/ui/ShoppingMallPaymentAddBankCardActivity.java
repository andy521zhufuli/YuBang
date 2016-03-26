package com.car.yubangapk.ui;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.car.yubangapk.utils.toastMgr;
import com.andy.android.yubang.R;

/**
 * ShoppingMallChoosePaymentActivity: 收银台
 *
 * @author andyzhu
 * @version 1.0
 * @created 2016-02-29
 */
public class ShoppingMallPaymentAddBankCardActivity extends BaseActivity implements View.OnClickListener{

    private ImageView img_back;//
    private Button    btn_verify_bank_account;//验证按钮

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_shopping_mall_payment_add_bank_card);
        
        findViews();
    }

    private void findViews() {

        img_back = (ImageView) findViewById(R.id.img_back);

        btn_verify_bank_account = (Button) findViewById(R.id.btn_verify_bank_account);

        /**
         * 注册监听器
         */
        img_back.setOnClickListener(this);
        btn_verify_bank_account.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            //返回
            case R.id.img_back:
                finish();
                break;
            //验证
            case R.id.btn_verify_bank_account:
                toastMgr.builder.display("恭喜您, 您的银行卡已经验证 可以是用了", 0);
                break;
        }

    }
}
