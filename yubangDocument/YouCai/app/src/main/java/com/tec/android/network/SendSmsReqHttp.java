package com.tec.android.network;

import android.content.Context;

import com.sales.vo.PostLoginReq;
import com.sales.vo.SendSmsReq;
import com.tec.android.configs.Configs;
import com.tec.android.utils.L;
import com.tec.android.utils.bean.QQLoginBean;

/**
 * Created by andy on 15/8/12.
 */
public class SendSmsReqHttp {


    private Context mContext;

    public SendSmsReqHttp()
    {

    }

    public SendSmsReqHttp(Context context)
    {
        this.mContext = context;
    }

    /**
     * 发送登陆请求 此时已经拿到了code  qq登陆 用户已经授权
     * @param successCallback
     * @param failedCallback
     */
    public void sendAndSendSmsReqJson(final DoingSuccessCallback successCallback, final DoingFailedCallback failedCallback, String phone)
    {
        SendSmsReq sendSmsReq = new SendSmsReq();
        String reqJson = ReqSetProperties.setSendSmsReqProperties(sendSmsReq, "json", phone);

        L.i("SendSmsReqHttp sendAndPostLoginReqJson reqJson = " + reqJson);
        L.i("SendSmsReqHttp sendAndPostLoginReqJson ip address = " + Configs.SERVER_IP_ADDRESS + sendSmsReq.getMsgtype());
        new NetConnectionReq(Configs.SERVER_IP_ADDRESS + sendSmsReq.getMsgtype(),
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

        sendSmsReq = null;
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
