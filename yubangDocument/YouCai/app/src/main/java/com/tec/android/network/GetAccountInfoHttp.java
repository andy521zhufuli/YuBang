package com.tec.android.network;

import android.content.Context;

import com.sales.vo.GetAccountInfoReq;
import com.sales.vo.PostLoginResp;
import com.sales.vo.base.SalesBaseMsg;
import com.sales.vo.base.SalesMsgUtils;
import com.tec.android.configs.Configs;
import com.tec.android.utils.L;

/**
 * 获得用户信息  个人信息
 * 同时也可以获得好友信息
 */
public class GetAccountInfoHttp
{
    private Context mContext;


    public GetAccountInfoHttp()
    {

    }

    public GetAccountInfoHttp(Context context)
    {
        this.mContext = context;
    }

    /**
     * 拿到用户个人信息的html形式
     * @param successCallback 成功回调
     * @param failedCallback 失败回调
     */
    public void sendAndGetAccountInfoHtml(final DoingSuccessCallback successCallback, final DoingFailedCallback failedCallback, PostLoginResp postLoginResp)
    {
        //返回html形式的报文消息
        GetAccountInfoReq getAccountInfoReq = new GetAccountInfoReq();
        String reqJson = ReqSetProperties.setGetAccountInfoReqProperties(getAccountInfoReq, "html", postLoginResp);
        L.i("GetAccountInfoHttp reqJson = " + reqJson);
        L.i("GetAccountInfoHttp ip address = " + Configs.SERVER_IP_ADDRESS + getAccountInfoReq.getMsgtype());
        new NetConnectionReq(Configs.SERVER_IP_ADDRESS + getAccountInfoReq.getMsgtype(),
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
                },
                reqJson
        );

    }

    /**
     * 根据朋友的id  去获取朋友的个人中心页面
     * @param successCallback 成功回调
     * @param failedCallback 失败回调
     * @param postLoginResp 登陆的个人信息
     * @param fuserid 朋友的id
     */
    public void sendAndGetFriendPersonalInfoHtml(final DoingSuccessCallback successCallback, final DoingFailedCallback failedCallback, PostLoginResp postLoginResp, String fuserid)
    {
        GetAccountInfoReq getAccountInfoReq = new GetAccountInfoReq();
        String reqJson = ReqSetProperties.setGetFriendAccountInfoReqProperties(getAccountInfoReq, "html", postLoginResp, fuserid);
        L.i("GetAccountInfoHttp reqJson = " + reqJson);
        L.i("GetAccountInfoHttp ip address = " + Configs.SERVER_IP_ADDRESS + getAccountInfoReq.getMsgtype());
        new NetConnectionReq(Configs.SERVER_IP_ADDRESS + getAccountInfoReq.getMsgtype(),
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
                },
                reqJson
        );
    }


    public void sendAndGetAccountInfoJson(final DoingSuccessCallback successCallback, final DoingFailedCallback failedCallback,
                                          PostLoginResp postLoginResp, String fuserid
                                          )
    {
        GetAccountInfoReq getAccountInfoReq = new GetAccountInfoReq();
        String reqJson = ReqSetProperties.setGetFriendAccountInfoReqProperties(getAccountInfoReq, "json", postLoginResp, fuserid);
        L.i("GetAccountInfoHttp reqJson = " + reqJson);
        L.i("GetAccountInfoHttp ip address = " + Configs.SERVER_IP_ADDRESS + getAccountInfoReq.getMsgtype());
        new NetConnectionReq(Configs.SERVER_IP_ADDRESS + getAccountInfoReq.getMsgtype(),
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
