package com.tec.android.network;

import android.content.Context;

import com.sales.vo.ShareReq;
import com.tec.android.configs.Configs;
import com.tec.android.utils.L;

/**
 * 分享的请求
 * 点击分享, 发送请求给后台, 去拿分享参数
 * Created by andy on 15/8/18.
 */
public class ShareReqHttp {

    private Context mContext;

    public ShareReqHttp()
    {

    }

    public ShareReqHttp(Context context)
    {
        this.mContext = context;
    }


    public void sendAndGetShareReqParamsJson(final DoingSuccessCallback successCallback, final DoingFailedCallback failedCallback, String goodsId)
    {
        ShareReq shareReq = new ShareReq();
        //退出参数设置
        String reqJson = ReqSetProperties.setShareReqProperties(shareReq, "json", goodsId);
        L.i("ShareReqHttp sendAndGetShareReqParamsJson reqJson = " + reqJson);
        L.i("ShareReqHttp sendAndGetShareReqParamsJson ip address = " + Configs.SERVER_IP_ADDRESS + shareReq.getMsgtype());
        new NetConnectionReq(Configs.SERVER_IP_ADDRESS + shareReq.getMsgtype(),
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
     * 邀请朋友
     * @param successCallback
     * @param failedCallback
     * @param goodsId
     */
    public void sendAndGetShareFriendReqParamsJson(final DoingSuccessCallback successCallback, final DoingFailedCallback failedCallback, String goodsId)
    {
        ShareReq shareReq = new ShareReq();
        //退出参数设置
        String reqJson = ReqSetProperties.setShareFriendReqProperties(shareReq, "json", "129");
        L.i("ShareReqHttp sendAndGetShareReqParamsJson reqJson = " + reqJson);
        L.i("ShareReqHttp sendAndGetShareReqParamsJson ip address = " + Configs.SERVER_IP_ADDRESS + shareReq.getMsgtype());
        new NetConnectionReq(Configs.SERVER_IP_ADDRESS + shareReq.getMsgtype(),
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
