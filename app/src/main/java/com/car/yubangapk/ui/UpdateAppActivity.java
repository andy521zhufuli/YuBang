package com.car.yubangapk.ui;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import com.andy.android.yubang.R;
import com.car.yubangapk.okhttp.callback.StringCallback;

public class UpdateAppActivity extends AppCompatActivity {


    private Context mContext;
    private static final String TAG = UpdateAppActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_update_app);

        findViews();




    }

    /**
     * 绑定控件
     */
    private void findViews() {


    }
}
