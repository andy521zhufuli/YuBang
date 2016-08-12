package com.tec.android.network;

import android.content.Context;

import com.sales.vo.GetAboutInfoReq;
import com.tec.android.configs.Configs;
import com.tec.android.utils.L;

/**
 * 获取退货须知 要一个webview去展示这个页面
 */
public class GetAboutInfoReqHttp
{
    private Context mContext;


    public GetAboutInfoReqHttp()
    {

    }

    public GetAboutInfoReqHttp(Context context)
    {
        this.mContext = context;
    }

    public void sendAndGetAboutInfoHtml(DoingSuccessCallback successCallback, DoingFailedCallback failedCallback)
    {
        GetAboutInfoReq getAboutInfoReq = new GetAboutInfoReq();

        String reqJson = ReqSetProperties.setGetAboutInfoReqProperties(getAboutInfoReq, "html");
        L.d("GetAboutInfoReqHttp  sendAndGetAboutInfoHtml +  reqJson = " + reqJson);
        L.d("GetAboutInfoReqHttp  sendAndGetAboutInfoHtml +  ip address = " + Configs.SERVER_IP_ADDRESS + getAboutInfoReq.getMsgtype());
        new NetConnectionReq(Configs.SERVER_IP_ADDRESS + getAboutInfoReq.getMsgtype(), new NetConnectionReq.SuccessCallback() {
            @Override
            public void onSuccess(String result) {

            }
        }, new NetConnectionReq.FailCallback() {
            @Override
            public void onFail(String error) {

            }
        },reqJson
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
