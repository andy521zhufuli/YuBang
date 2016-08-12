package com.tec.android.network;

import android.content.Context;

import com.sales.vo.LoadGoodsListReq;
import com.sales.vo.ModifyAddressInfoReq;
import com.sales.vo.base.AddressInfo;
import com.tec.android.configs.Configs;
import com.tec.android.utils.L;

/**
 * Created by andy on 15/8/20.
 * 修改地址
 */
public class ModifyAddressReqHttp
{
    private Context mContext;
    private LoadGoodsListReq req;

    public ModifyAddressReqHttp()
    {
        super();
    }

    // 代参构造函数
    public ModifyAddressReqHttp(Context context)
    {
        mContext = context;
    }

    /**
     * 修改地址
     * @param successCallback
     * @param failedCallback
     * @param action 添加:add,修改:update
     */
    public void sendAndModifyAddress(final DoingSuccessCallback successCallback, final DoingFailedCallback failedCallback, String action, AddressInfo addressInfo)
    {
        //配置参数
        ModifyAddressInfoReq modifyAddressInfoReq = new ModifyAddressInfoReq();
        String reqJson  = ReqSetProperties.setModifyAddressReqProperties(modifyAddressInfoReq, "json", action, addressInfo);
        L.i("ModifyAddressReqHttp, sendAndModifyAddress json = " + reqJson);
        L.i("ModifyAddressReqHttp sendAndModifyAddress ip address = " + Configs.SERVER_IP_ADDRESS + modifyAddressInfoReq.getMsgtype());
        new NetConnectionReq(Configs.SERVER_IP_ADDRESS + modifyAddressInfoReq.getMsgtype(),
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
