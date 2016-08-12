package com.tec.android.ui;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import com.tec.android.R;
import com.tec.android.utils.toastMgr;

/**
 * 选择支付方式
 */
public class ChoosePaymentMethodActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_choose_payment_method);

        findViews();

        toastMgr.builder.display("选择支付方式", 0);
    }

    /**
     * 初始化布局文件的对象
     */
    private void findViews() {

    }




    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case 1:
                break;
            default:
                break;
        }
    }


    /**
     * 先定义 回头名字再改
     */
    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
}
