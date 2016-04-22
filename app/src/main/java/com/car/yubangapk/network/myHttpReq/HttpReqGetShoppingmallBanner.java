package com.car.yubangapk.network.myHttpReq;

import android.support.v4.app.NotificationCompatSideChannelService;

import com.andy.android.yubang.R;
import com.car.yubangapk.banner.constants.EffectConstants;
import com.car.yubangapk.configs.Configs;
import com.car.yubangapk.configs.ErrorCodes;
import com.car.yubangapk.json.FormatJson;
import com.car.yubangapk.json.bean.BannerAd;
import com.car.yubangapk.network.okhttp.OkHttpUtils;
import com.car.yubangapk.network.okhttp.callback.StringCallback;
import com.car.yubangapk.utils.L;
import com.car.yubangapk.utils.toastMgr;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by andy on 16/4/22.
 *
 * 商城首页 拿到banner
 */
public class HttpReqGetShoppingmallBanner {


    private static final String TAG = HttpReqGetShoppingmallBanner.class.getSimpleName();
    private HttpReqGetShoppingmallBannerInterface mCallback;

    public HttpReqGetShoppingmallBanner()
    {

    }

    public void setCallback(HttpReqGetShoppingmallBannerInterface callback)
    {
        this.mCallback = callback;
    }



    /**
     * 网络连接去拿banner的图片
     */
    public void getBannerPics()
    {
        /**
         * 去拿轮播图片
         */
        OkHttpUtils.post()
                .url(Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GETDATA)
                .addParams("sqlName", "clientSearchAd")
                .addParams("dataReqModel.args.needTotal","needTotal")
                .addParams("dataReqModel.args.position","2")//2代表banner广告
                .build().execute(new MyBannerAdCallback());
        L.i(TAG, "banner url = " + Configs.IP_ADDRESS+Configs.IP_ADDRESS_ACTION_GETDATA + "?" + "sqlName=clientSearchAd&dataReqModel.args.needTotal=needTotal&dataReqModel.args.position=2" );

    }

    /**
     * 轮播图片
     */
    public class MyBannerAdCallback extends StringCallback
    {
        @Override
        public void onBefore(Request request)
        {
            super.onBefore(request);
            L.i(TAG + "http MyStringCallback loading");
        }

        @Override
        public void onAfter()
        {
            super.onAfter();
            L.i(TAG + "http MyStringCallback onAfter");

        }

        @Override
        public void onError(Call call, Exception e)
        {

            L.i(TAG + "http MyStringCallback error "  + e.getMessage());

            mCallback.onFail(ErrorCodes.ERROR_CODE_NETWORK, ErrorCodes.NETWORK_ERROR);
        }

        @Override
        public void onResponse(String response)
        {
            L.i(TAG + "http MyStringCallback onResponse " + response);
            FormatJson formatJson = new FormatJson(response);
            List<BannerAd> bannerad = formatJson.getBannerAdImageList();

            if (bannerad == null)
            {
                //解析json错误  肯定是服务器返回给我的有错误
                toastMgr.builder.display("服务器返回错误",0);
                mCallback.onFail(ErrorCodes.ERROR_CODE_LOW_VERSION, ErrorCodes.LOW_VERSION_TO_UPGRADE_APP);
                return;
            }
            else
            {
                mCallback.onSuccess(bannerad);
            }
        }

        @Override
        public void inProgress(float progress)
        {
            L.i(TAG + "http MyStringCallback inProgress " + progress);
        }
    }









}
