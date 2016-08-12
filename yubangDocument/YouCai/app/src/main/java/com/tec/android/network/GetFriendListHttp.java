package com.tec.android.network;

import android.content.Context;

import com.sales.vo.GetContactInfoReq;
import com.sales.vo.GetFriendListReq;
import com.tec.android.configs.Configs;
import com.tec.android.utils.L;

/**
 *  联系客服时候的网络操作
 *
 * @author andyzhu
 * @data   2015-8-7
 */
public class GetFriendListHttp
{
    private Context mContext;

    public GetFriendListHttp()
    {

    }

    public GetFriendListHttp(Context context)
    {
        this.mContext = context;
    }

    public void sendAndGetFriendListHtml(final DoingSuccessCallback successCallback, final DoingFailedCallback failedCallback)
    {
        GetFriendListReq getFriendListReq = new GetFriendListReq();
        String reqJson = ReqSetProperties.setGetFriendListReqProperties(getFriendListReq, "html");
        L.i("GetFriendListHttp sendAndGetFriendListHtml reqJson = " + reqJson);
        L.i("GetFriendListHttp sendAndGetFriendListHtml ip address = " + Configs.SERVER_IP_ADDRESS + getFriendListReq.getMsgtype());
        new NetConnectionReq(Configs.SERVER_IP_ADDRESS + getFriendListReq.getMsgtype(),
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
