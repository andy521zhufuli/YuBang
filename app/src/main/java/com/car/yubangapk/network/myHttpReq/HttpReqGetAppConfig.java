package com.car.yubangapk.network.myHttpReq;

import android.content.Context;

import com.car.yubangapk.configs.Configs;
import com.car.yubangapk.network.okhttp.OkHttpUtils;
import com.car.yubangapk.network.okhttp.callback.StringCallback;
import com.car.yubangapk.utils.L;
import com.car.yubangapk.utils.SPUtils;

import okhttp3.Call;

/**
 * Created by andy on 16/5/10.
 */
public class HttpReqGetAppConfig {


    private Context mContext;
    private HttpReqCallback mCallback;

    public HttpReqGetAppConfig(Context context)
    {
        this.mContext = context;

    }


    public void setCallback(HttpReqCallback callback)
    {
        this.mCallback = callback;
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
            L.d("", "get MyAppConfigCallback error ====" + e.toString());
        }

        @Override
        public void onResponse(String response)
        {
            //持久性保存
            SPUtils.put(mContext, Configs.APP_SYS_CONFIG, response);
            L.d("qwer", "get MyAppConfigCallback onResponse ====" + response);

            if (mCallback != null)
            {
                mCallback.onSuccess(null);
            }
        }
    }
}
