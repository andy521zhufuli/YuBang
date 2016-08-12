package com.tec.android.network;

import android.content.Context;

import com.sales.vo.GetCancelOrderPromptReq;
import com.sales.vo.GetFriendListReq;
import com.sales.vo.LoadGoodsListReq;
import com.tec.android.configs.Configs;
import com.tec.android.utils.L;

/**
 * 获取退货须知  详情页
 */
public class GetCancelOrderPromptHttp
{
    private Context mContext;

    public GetCancelOrderPromptHttp()
    {

    }

    public GetCancelOrderPromptHttp(Context context)
    {
        this.mContext = context;
    }


    /**
     * 退货须知的请求  html
     * @param successCallback
     * @param failedCallback
     */
    public void sendAndGetCancelOrderPromptHtml(final DoingSuccessCallback successCallback, final DoingFailedCallback failedCallback)
    {
        //配置参数
        final GetCancelOrderPromptReq getCancelOrderPromptReq = new GetCancelOrderPromptReq();
        String reqJson  = ReqSetProperties.setGetCancelOrderPromptReqProperties(getCancelOrderPromptReq, "html");
        L.i("GetCancelOrderPromptHttp sendAndGetCancelOrderPromptHtml, json = " + reqJson);
        L.i("GetCancelOrderPromptHttp sendAndGetCancelOrderPromptHtml ip address = " + Configs.SERVER_IP_ADDRESS + getCancelOrderPromptReq.getMsgtype());

        new NetConnectionReq(Configs.SERVER_IP_ADDRESS + getCancelOrderPromptReq.getMsgtype(),
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


    //设计一个回调函数, 因为这个类的主要操作是在子线程里面执行, 调用的时候, 我要知道什么时候执行成功, 和失败

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
