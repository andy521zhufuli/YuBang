package com.tec.android.network;

import android.content.Context;

import com.sales.vo.LoadGoodsListReq;
import com.sales.vo.ModifyAddressInfoReq;
import com.sales.vo.ModifyOrderReq;
import com.sales.vo.base.AddressInfo;
import com.tec.android.configs.Configs;
import com.tec.android.utils.L;

/**
 * Created by andy on 15/8/29.
 * 修改订单
 *      比如 修改地址
 */
public class ModifyOrderReqHttp {

    private Context mContext;
    private LoadGoodsListReq req;

    public ModifyOrderReqHttp()
    {
        super();
    }

    // 代参构造函数
    public ModifyOrderReqHttp(Context context)
    {
        mContext = context;
    }

    /**
     * 修改地址
     * @param successCallback
     * @param failedCallback
     * @param action
     */
    public void sendAndModifyOrder(final DoingSuccessCallback successCallback,
                                   final DoingFailedCallback failedCallback,
                                   String action,
                                   String orderid,
                                   AddressInfo addressInfo)
    {
        ModifyOrderReq modifyOrderReq = new ModifyOrderReq();
        String  reqJson = ReqSetProperties.setModifyOrderReqProperties(modifyOrderReq, "html", orderid,action,  addressInfo);
        //配置参数
        L.i("ModifyOrderReqHttp, sendAndModifyOrder json = " + reqJson);
        L.i("ModifyOrderReqHttp sendAndModifyOrder ip address = " + Configs.SERVER_IP_ADDRESS + modifyOrderReq.getMsgtype());
        new NetConnectionReq(Configs.SERVER_IP_ADDRESS + modifyOrderReq.getMsgtype(),
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
     * 确认收货
     * @param successCallback
     * @param failedCallback
     * @param action
     * @param orderid
     */
    public void sendAndModifyOrderReceive(final DoingSuccessCallback successCallback,
                                   final DoingFailedCallback failedCallback,
                                   String action,
                                   String orderid
                                   )
    {
        ModifyOrderReq modifyOrderReq = new ModifyOrderReq();
        String  reqJson = ReqSetProperties.setModifyOrderReceiveReqProperties(modifyOrderReq, "html", orderid,action);
        //配置参数
        L.i("ModifyOrderReqHttp, sendAndModifyOrder json = " + reqJson);
        L.i("ModifyOrderReqHttp sendAndModifyOrder ip address = " + Configs.SERVER_IP_ADDRESS + modifyOrderReq.getMsgtype());
        new NetConnectionReq(Configs.SERVER_IP_ADDRESS + modifyOrderReq.getMsgtype(),
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
