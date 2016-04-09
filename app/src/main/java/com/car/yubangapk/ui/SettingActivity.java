package com.car.yubangapk.ui;

import android.os.Bundle;
import android.view.Window;

import com.andy.android.yubang.R;
/**
 * SettingActivity: 设置界面
 *
 * @author andyzhu
 * @version 1.0
 * @created 2016-02-28
 */
public class SettingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_setting);
    }

}