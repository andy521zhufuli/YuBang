package com.tec.android.app;

import android.app.Application;
import com.tec.android.utils.toastMgr;
/**
 * Created by andy on 15/7/30.
 */
public class YouCaiApplication extends Application
{
    @Override
    public void onCreate() {
        super.onCreate();

        toastMgr.builder.init(getApplicationContext());
    }
}
