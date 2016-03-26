package com.car.yubangapk.ui;

import android.os.Bundle;
import android.view.Window;

import com.andy.android.yubang.R;

/**
 * MyPrivilegeActivity: 我的特权
 *
 * @author andyzhu
 * @version 1.0
 * @created 2016-02-27
 */
public class MyPrivilegeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_my_privilege);
    }


}
