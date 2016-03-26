package com.car.yubangapk.ui;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.andy.android.yubang.R;


/**
 * MyCommissionDetailActivity: 我的邀请佣金明细
 *
 * @author andyzhu
 * @version 1.0
 * @created 2016-03-02
 */
public class MyCommissionDetailActivity extends BaseActivity implements View.OnClickListener {

    private ImageView img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_my_commission_detail);

        findViews();

    }

    private void findViews() {

        img_back = (ImageView) findViewById(R.id.img_back);

        img_back.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.img_back:
                finish();
                break;
        }
    }
}
