package com.tec.android.network;

import android.content.Context;

import com.sales.vo.GetVerifyCodeReq;
import com.tec.android.configs.Configs;
import com.tec.android.utils.L;

/**
 * Created by andy on 15/8/29.
 * 去后台获取用户的手机号码的验证码
 */
public class GetVerifyCodeReqHttp {

    private Context mContext;

    public GetVerifyCodeReqHttp()
    {

    }

    public GetVerifyCodeReqHttp(Context context)
    {
        this.mContext = context;
    }



    public void sendAndGetVerifyCodeReqJson(final DoingSuccessCallback successCallback, final DoingFailedCallback failedCallback, String phoneNum)
    {

        GetVerifyCodeReq getVerifyCodeReq = new GetVerifyCodeReq();
        String reqJson = ReqSetProperties.setGetVerifyCodeReqProperties(getVerifyCodeReq, "json", phoneNum);


        L.i("GetVerifyCodeReqHttp sendAndGetVerifyCodeReqJson reqJson = " + reqJson);
        L.i("GetVerifyCodeReqHttp sendAndGetVerifyCodeReqJson ip address = " + Configs.SERVER_IP_ADDRESS + getVerifyCodeReq.getMsgtype());
        new NetConnectionReq(Configs.SERVER_IP_ADDRESS + getVerifyCodeReq.getMsgtype(),
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
                            failedCallback.onFail(error);
                        }
                    }
                }, reqJson);
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
