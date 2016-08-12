package com.tec.android.network;

import android.content.Context;

import com.sales.vo.GetFriendListReq;
import com.sales.vo.GetHistOrdersReq;
import com.tec.android.configs.Configs;
import com.tec.android.utils.L;

/**
 *  获取历史订单列表
 *
 * @author andyzhu
 * @data   2015-8-7
 */
public class GetHistOrdersHttp
{
    private Context mContext;

    public GetHistOrdersHttp()
    {

    }

    public GetHistOrdersHttp(Context context)
    {
        this.mContext = context;
    }

    public void sendAndGetHistOrdersHtml(final DoingSuccessCallback successCallback, final DoingFailedCallback failedCallback, String status)
    {
        GetHistOrdersReq getHistOrdersReq = new GetHistOrdersReq();
        String reqJson = ReqSetProperties.setGetHistOrdersReqProperties(getHistOrdersReq, "html", status);

        L.i("GetHistOrdersHttp sendAndGetHistOrdersHtml reqJson = " + reqJson);
        L.i("GetHistOrdersHttp sendAndGetHistOrdersHtml ip address = " + Configs.SERVER_IP_ADDRESS + getHistOrdersReq.getMsgtype());
        new NetConnectionReq(Configs.SERVER_IP_ADDRESS + getHistOrdersReq.getMsgtype(),
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
