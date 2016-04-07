package com.car.yubangapk.app;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.car.yubangapk.configs.Configs;
import com.car.yubangapk.okhttp.OkHttpUtils;
import com.car.yubangapk.okhttp.callback.StringCallback;
import com.car.yubangapk.utils.DensityUtil;
import com.car.yubangapk.utils.L;
import com.car.yubangapk.utils.SPUtils;
import com.car.yubangapk.utils.toastMgr;
import com.baidu.mapapi.SDKInitializer;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGNotifaction;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.android.tpush.XGPushNotifactionCallback;


import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;

/**
 * Created by andy on 16/2/22.
 */
public class YuBangApplication extends Application
{
    private static final String TAG = "YuBangApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        toastMgr.builder.init(getApplicationContext());
        SDKInitializer.initialize(getApplicationContext());

        Log.d("TPush", "application oncreate call time");
        //okhttp
        OkHttpUtils.getInstance().debug("OkHttpUtils").setConnectTimeout(100000, TimeUnit.MILLISECONDS);
        //使用https，但是默认信任全部证书
        OkHttpUtils.getInstance().setCertificates();

        DensityUtil densityUtil = new DensityUtil(getApplicationContext());
        densityUtil.px2dip((float)1.0);


        // 在主进程设置信鸽相关的内容
        if (isMainProcess())
        {
            XGPushConfig.enableDebug(getApplicationContext(), true);
            XGPushManager.registerPush(getApplicationContext(), new XGIOperateCallback() {
                @Override
                public void onSuccess(Object data, int flag) {
                    Log.d("TPush", "注册成功，设备token为：" + data);
                }

                @Override
                public void onFail(Object data, int errCode, String msg) {
                    Log.d("TPush", "注册失败，错误码：" + errCode + ",错误信息：" + msg);
                }
            });


            // 为保证弹出通知前一定调用本方法，需要在application的onCreate注册
            // 收到通知时，会调用本回调函数。
            // 相当于这个回调会拦截在信鸽的弹出通知之前被截取
            // 一般上针对需要获取通知内容、标题，设置通知点击的跳转逻辑等等
            XGPushManager
                    .setNotifactionCallback(new XGPushNotifactionCallback() {

                        @Override
                        public void handleNotify(XGNotifaction xGNotifaction) {
                            L.i("test", "处理信鸽通知：" + xGNotifaction);
                            // 获取标签、内容、自定义内容
                            String title = xGNotifaction.getTitle();
                            String content = xGNotifaction.getContent();
                            String customContent = xGNotifaction
                                    .getCustomContent();
                            // 其它的处理
                            // 如果还要弹出通知，可直接调用以下代码或自己创建Notifaction，否则，本通知将不会弹出在通知栏中。
                            xGNotifaction.doNotify();
                        }
                    });
        }
        //去拿配置信息
        getAppConfig();

    }


    public boolean isMainProcess() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = android.os.Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 拿到启动后配置信息
     */
    public void getAppConfig()
    {
        OkHttpUtils.post()
                .url(Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_SYS_CONFIG)
                .build().execute(new MyAppConfigCallback());
    }

    /**
     * 获取配置信息回调
     */
    public class MyAppConfigCallback extends StringCallback
    {

        @Override
        public void onError(Call call, Exception e)
        {
            L.d(TAG, "get MyAppConfigCallback error ====" + e.toString());
        }

        @Override
        public void onResponse(String response)
        {
            //持久性保存
            SPUtils.put(getApplicationContext(), Configs.APP_SYS_CONFIG, response);
            L.d(TAG, "get MyAppConfigCallback onResponse ====" + response);
        }
    }


}
