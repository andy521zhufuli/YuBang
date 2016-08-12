package com.tec.android.network;

import android.content.Context;

import com.sales.vo.GetContactInfoReq;
import com.tec.android.configs.Configs;
import com.tec.android.utils.L;

/**
 *  联系客服时候的网络操作
 *
 * @author andyzhu
 * @data   2015-8-7
 */
public class GetContactInfoHttp
{
    private Context mContext;

    public GetContactInfoHttp()
    {

    }

    public GetContactInfoHttp(Context context)
    {
        this.mContext = context;
    }

    public void sendAndGetContactInfoHtml(final DoingSuccessCallback successCallback, final DoingFailedCallback failedCallback)
    {
        GetContactInfoReq getContactInfoReq = new GetContactInfoReq();
        String reqJson = ReqSetProperties.setGetContactInfoReqProperties(getContactInfoReq, "html");
        L.i("GetContactInfoHttp sendAndGetContactInfoHtml reqJson = " + reqJson);
        L.i("GetContactInfoHttp sendAndGetContactInfoHtml ip address = " + Configs.SERVER_IP_ADDRESS + getContactInfoReq.getMsgtype());
        new NetConnectionReq(Configs.SERVER_IP_ADDRESS + getContactInfoReq.getMsgtype(),
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
