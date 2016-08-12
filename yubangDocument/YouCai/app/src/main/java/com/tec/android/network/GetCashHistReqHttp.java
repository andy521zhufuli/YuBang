package com.tec.android.network;

import android.content.Context;

import com.sales.vo.GetCashAccountListReq;
import com.sales.vo.GetCashHistReq;
import com.tec.android.configs.Configs;
import com.tec.android.utils.L;

/**
 * Created by andy on 15/8/28.
 * 拿到我的提现历史
 */
public class GetCashHistReqHttp {


    private Context mContext;

    public GetCashHistReqHttp(Context mContext) {
        this.mContext = mContext;
    }
    //设计一个回调函数, 因为这个类的主要操作是在子线程里面执行, 调用的时候, 我要知道什么时候执行成功, 和失败

    /**
     * 获取已有账户信息  html这个就是用户提现时， 可以选择之前用过的提现账户
     * @param successCallback
     * @param failedCallback
     */
    public void sendAndGetCashHistReqHtml(final DoingSuccessCallback successCallback, final DoingFailedCallback failedCallback, String status)
    {
        //配置参数
        GetCashHistReq getCashHistReq = new GetCashHistReq();

        String reqJson  = ReqSetProperties.setGetCashHistReqProperties(getCashHistReq, "html", status);
        L.i("GetCashHistReqHttp sendAndGetCashHistReqHtml, json = " + reqJson);
        L.i("GetCashHistReqHttp sendAndGetCashHistReqHtml ip address = " + Configs.SERVER_IP_ADDRESS + getCashHistReq.getMsgtype());

        new NetConnectionReq(Configs.SERVER_IP_ADDRESS + getCashHistReq.getMsgtype(),
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
