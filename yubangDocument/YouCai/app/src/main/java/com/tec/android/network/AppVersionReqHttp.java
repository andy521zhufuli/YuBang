package com.tec.android.network;

import android.content.Context;

import com.sales.vo.AppVersionReq;
import com.tec.android.configs.Configs;
import com.tec.android.utils.L;

/**
 * Created by andy on 15/9/1.
 * 检查App版本更新
 */
public class AppVersionReqHttp {

    private Context mContext;

    public AppVersionReqHttp(Context mContext) {
        this.mContext = mContext;
    }


    /**
     * 检查APP版本 是否需要更新
     * @param successCallback
     * @param failedCallback
     * @param currentAppversion 当前app版本
     */
    public void sendAndCheckAppVersionReqJson(final DoingSuccessCallback successCallback, final DoingFailedCallback failedCallback, String currentAppversion)
    {
        AppVersionReq appVersionReq = new AppVersionReq();

        String reqJson = ReqSetProperties.setCheckAppVersionReqProperties(appVersionReq, "json", currentAppversion);
        L.i("AppVersionReqHttp sendAndCheckAppVersionReqJson reqJson = " + reqJson);
        L.i("AppVersionReqHttp sendAndCheckAppVersionReqJson ip address = " + Configs.SERVER_IP_ADDRESS + appVersionReq.getMsgtype());
        new NetConnectionReq(Configs.SERVER_IP_ADDRESS + appVersionReq.getMsgtype(),
                new NetConnectionReq.SuccessCallback() {
                    @Override
                    public void onSuccess(String result) {
                        if (successCallback!= null)
                        {
                            successCallback.onSuccess(result);
                        }
                    }
                },
                new NetConnectionReq.FailCallback() {
                    @Override
                    public void onFail(String error) {
                        if (failedCallback != null)
                        {
                            failedCallback.onFail(error);
                        }
                    }
                },
                reqJson
        );
    }


    /**
     *执行成功的时候回调
     */
    public interface DoingSuccessCallback
    {
        /**
         *
         * @param result 成功的时候, 返回的应该是html
         */
        void onSuccess(String result);
    }

    /**
     * 执行失败时候的回调
     */
    public interface DoingFailedCallback
    {
        /**
         *
         * @param resultMsg 失败的信息, 或者是失败码
         */
        void onFail(String resultMsg);
    }



}
