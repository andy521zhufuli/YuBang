package com.car.yubangapk.ui;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.andy.android.yubang.R;
import com.car.yubangapk.utils.SPUtils;

public class GuideActivity extends BaseActivity implements View.OnClickListener{


    Context mContext;
    Button main_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_guide);


        mContext = this;

        SPUtils.put(mContext, SplashActivity.KEY_GUIDE_ACTIVITY, false);


        findViews();

    }

    private void findViews() {
        main_button = (Button) findViewById(R.id.main_button);


    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.main_button:
                toMainPage();
                break;
        }
    }

    private void toMainPage() {
        Intent intent = new Intent();
        intent.setClass(mContext, MainActivity.class);
        startActivity(intent);
        this.finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
