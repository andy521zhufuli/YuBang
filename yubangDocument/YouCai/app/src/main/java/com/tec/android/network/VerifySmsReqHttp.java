package com.tec.android.network;

import android.content.Context;

import com.sales.vo.VerifySmsReq;
import com.tec.android.configs.Configs;
import com.tec.android.utils.L;


/**
 * Created by andy on 15/8/28.
 * 验证手机验证码的请求
 */
public class VerifySmsReqHttp {

    private Context mContext;

    public VerifySmsReqHttp(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * 像服务器请求验证  用户输入的验证码
     * @param successCallback 成功回调
     * @param failedCallback 失败回调
     * @param code   用户输入的手机验证码
     */
    public void sendAndVerifySmsReqHttpJson(final DoingSuccessCallback successCallback, final DoingFailedCallback failedCallback, String code)
    {
        VerifySmsReq verifySmsReq = new VerifySmsReq();
        //验证手机验证码
        String reqJson = ReqSetProperties.setVerifySmsReqProperties(verifySmsReq, "json", code);
        L.i("VerifySmsReqHttp sendAndVerifySmsReqHttpJson reqJson = " + reqJson);
        L.i("VerifySmsReqHttp sendAndVerifySmsReqHttpJson ip address = " + Configs.SERVER_IP_ADDRESS + verifySmsReq.getMsgtype());
        new NetConnectionReq(Configs.SERVER_IP_ADDRESS + verifySmsReq.getMsgtype(),
                new NetConnectionReq.SuccessCallback() {
                    @Override
                    public void onSuccess(String result) {
                        if (successCallback != null)
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
                            //每次从这里回调的 error都是空的
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
