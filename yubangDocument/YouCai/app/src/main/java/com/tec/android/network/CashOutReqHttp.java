package com.tec.android.network;

import android.content.Context;

import com.sales.vo.CashOutReq;
import com.sales.vo.base.CashInfo;
import com.tec.android.configs.Configs;
import com.tec.android.utils.L;

/**
 * Created by andy on 15/8/24.
 * 提现  立即提现
 */
public class CashOutReqHttp {
    private Context mContext;

    public CashOutReqHttp(Context mContext) {
        this.mContext = mContext;
    }


    /**
     *
     * @param successCallback
     * @param failedCallback
     */
    public void sendAndGetCashOutReqHtml(final DoingSuccessCallback successCallback, final DoingFailedCallback failedCallback)
    {
        CashOutReq cashOutReq = new CashOutReq();
        CashInfo cashInfo = new CashInfo();
        String reqJson = ReqSetProperties.setGetCashOutReqProperties(cashOutReq, "html",cashInfo);
        L.i("CashOutReqHttp sendAndGetCashOutReqHtml reqJson = " + reqJson);
        L.i("CashOutReqHttp sendAndGetCashOutReqHtml ip address = " + Configs.SERVER_IP_ADDRESS + cashOutReq.getMsgtype());
        new NetConnectionReq(Configs.SERVER_IP_ADDRESS + cashOutReq.getMsgtype(),
                new NetConnectionReq.SuccessCallback() {
                    @Override
                    public void onSuccess(String result) {
                        if (successCallback!= null)
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
