package com.car.yubangapk.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import com.andy.android.yubang.R;
import com.car.yubangapk.utils.APPUtils;
import com.car.yubangapk.utils.SPUtils;

import cn.trinea.android.common.util.AppUtils;

public class SplashActivity extends BaseActivity {

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);

        mContext = this;

        boolean mFirst = isFirstEnter(SplashActivity.this);
        if(mFirst)
            mHandler.sendEmptyMessageDelayed(SWITCH_GUIDACTIVITY,3000);
        else
            mHandler.sendEmptyMessageDelayed(SWITCH_MAINACTIVITY,3000);
    }

    public static final String KEY_GUIDE_ACTIVITY = "guide_activity";
    public static final String KEY_APP_VERSION = "app_version";
    private boolean isFirstEnter(Context context){

        /**
         * 版本更新之后, 并不会显示guide_activity界面
         * 所以这个方法是行不通的
         */
        boolean isFirst = (boolean) SPUtils.get(context, KEY_GUIDE_ACTIVITY, true);
        String version = (String) SPUtils.get(context, KEY_APP_VERSION, "");
        String currentVersion = APPUtils.getVersion(mContext);

        if (!version.equals(currentVersion))
        {
            //安装了新版本了  或者  首次安装
            if (isFirst == true)
            {
                //首次安装
            }
            else
            {
                //安装新版本
            }
            return true;
        }
        return false;
    }


    private final static int SWITCH_MAINACTIVITY = 1000;
    private final static int SWITCH_GUIDACTIVITY = 1001;
    public Handler mHandler = new Handler(){
        public void handleMessage(Message msg) {
            switch(msg.what){
                case SWITCH_MAINACTIVITY:
                    Intent mIntent = new Intent();
                    mIntent.setClass(SplashActivity.this, MainActivity.class);
                    SplashActivity.this.startActivity(mIntent);
                    SplashActivity.this.finish();
                    break;
                case SWITCH_GUIDACTIVITY:
                    mIntent = new Intent();
                    mIntent.setClass(SplashActivity.this, GuideActivity.class);
                    SplashActivity.this.startActivity(mIntent);
                    SplashActivity.this.finish();
                    break;
            }
            super.handleMessage(msg);
        }
    };

}
