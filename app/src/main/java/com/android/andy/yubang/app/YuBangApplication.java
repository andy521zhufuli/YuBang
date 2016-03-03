package com.android.andy.yubang.app;

import android.app.Application;

import com.android.andy.yubang.utils.toastMgr;

/**
 * Created by andy on 16/2/22.
 */
public class YuBangApplication extends Application
{
    @Override
    public void onCreate() {
        super.onCreate();
        toastMgr.builder.init(getApplicationContext());
    }
}
