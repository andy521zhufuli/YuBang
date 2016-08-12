package com.tec.android.network;

import android.content.Context;

import com.sales.vo.GetHistOrdersReq;
import com.sales.vo.GetMyCashReq;
import com.tec.android.configs.Configs;
import com.tec.android.utils.L;

/**
 * 我的提现
 */
public class GetMyCashHttp
{
    private Context mContext;

    public GetMyCashHttp()
    {

    }

    public GetMyCashHttp(Context context)
    {
        this.mContext = context;
    }



    public void sendAndGetGetMyCashHtml(final DoingSuccessCallback successCallback, final DoingFailedCallback failedCallback)
    {
        GetMyCashReq getMyCashReq = new GetMyCashReq();
        String reqJson = ReqSetProperties.setGetMyCashReqProperties(getMyCashReq, "html");


        L.i("GetMyCashHttp sendAndGetGetMyCashHtml reqJson = " + reqJson);
        L.i("GetMyCashHttp sendAndGetGetMyCashHtml ip address = " + Configs.SERVER_IP_ADDRESS + getMyCashReq.getMsgtype());
        new NetConnectionReq(Configs.SERVER_IP_ADDRESS + getMyCashReq.getMsgtype(),
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
