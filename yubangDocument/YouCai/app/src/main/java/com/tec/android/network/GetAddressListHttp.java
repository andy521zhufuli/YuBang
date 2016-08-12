package com.tec.android.network;

import android.content.Context;

import com.sales.vo.GetAccountInfoReq;
import com.sales.vo.GetAddressListReq;
import com.tec.android.configs.Configs;
import com.tec.android.utils.L;

/**
 * Created by andy on 15/8/8.
 */
public class GetAddressListHttp
{

    private Context mContext;


    public GetAddressListHttp()
    {

    }

    public GetAddressListHttp(Context context)
    {
        this.mContext = context;
    }



    /**
     * 拿到用户个人信息的html形式
     * @param successCallback 成功回调
     * @param failedCallback 失败回调
     */
    public void sendAndGetAddressListJson(final DoingSuccessCallback successCallback, final DoingFailedCallback failedCallback)
    {
        //返回html形式的报文消息

        GetAddressListReq getAddressListReq = new GetAddressListReq();
        final String reqJson = ReqSetProperties.setGetAddressListReqProperties(getAddressListReq,"json");
        L.i("GetAddressListHttp sendAndGetAddressListJson reqJson = " + reqJson);
        L.i("GetAddressListHttp sendAndGetAddressListJson ip address = " + Configs.SERVER_IP_ADDRESS + getAddressListReq.getMsgtype());
        new NetConnectionReq(Configs.SERVER_IP_ADDRESS + getAddressListReq.getMsgtype(),
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
