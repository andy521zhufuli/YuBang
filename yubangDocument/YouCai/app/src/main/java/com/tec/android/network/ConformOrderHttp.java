package com.tec.android.network;

import android.content.Context;

import com.sales.common.until.CommonDefs;
import com.sales.vo.GetOrderReq;
import com.sales.vo.ModifyOrderReq;
import com.sales.vo.base.OrderGoods;
import com.sales.vo.base.SalesMsgUtils;
import com.tec.android.configs.Configs;
import com.tec.android.utils.L;
import com.tec.android.utils.SPUtils;

import java.util.List;

/**
 * Created by andy on 15/8/5.
 */
public class ConformOrderHttp {

    private Context mContext;


    public ConformOrderHttp()
    {

    }

    public ConformOrderHttp(Context context)
    {
        this.mContext = context;
    }


    /**
     * 业务逻辑 : 购物车里面点击去结算 首先提交订单  拿到订单id
     *
     * @param list 订单list
     * @param successCallback 回调函数
     * @param failedCallback 回调函数
     */
    public void sendAndGetOrderID(List<OrderGoods> list,  final DoingSuccessCallback successCallback, final DoingFailedCallback failedCallback)
    {
        ModifyOrderReq modifyOrderReq = new ModifyOrderReq();
        modifyOrderReq.setSeq("111");
        modifyOrderReq.setUserid("1");
        modifyOrderReq.setAuthorizedtoken("111");
        modifyOrderReq.setAction(CommonDefs.ORDER_ACT_ADD);

        modifyOrderReq.setGoodlist(list);

        String reqJson = SalesMsgUtils.toJson(modifyOrderReq);
        L.i("ConformOrderHttp sendAndGetOrderID reqJson = " + reqJson);
        L.i("ConformOrderHttp sendAndGetOrderID ip address = " + Configs.SERVER_IP_ADDRESS + modifyOrderReq.getMsgtype());
        new NetConnectionReq(Configs.SERVER_IP_ADDRESS + modifyOrderReq.getMsgtype(), new NetConnectionReq.SuccessCallback() {
            @Override
            public void onSuccess(String result) {
                if (successCallback != null)
                {
                    successCallback.onSuccess(result);
                }
            }
        }, new NetConnectionReq.FailCallback() {
            @Override
            public void onFail(String error) {
                if (failedCallback != null)
                {
                    failedCallback.onFail(error);
                }
            }
        },reqJson
        );
    }

    /**
     * 只通过订单id去拿到订单界面
     * @param orderId
     * @param successCallback
     * @param failedCallback
     */
    public void sendAndGetOrderHtml(String orderId, final DoingSuccessCallback successCallback, final DoingFailedCallback failedCallback)
    {
        GetOrderReq getOrderReq = new GetOrderReq();
        //还有什么用户id什么吊东西的 看后来怎么改吧
        String reqJson = ReqSetProperties.setGetOrderReqProperties(getOrderReq, orderId, "html");
        L.i("ConformOrderHttp sendAndGetOrderHtml reqJson = " + reqJson);
        L.i("ConformOrderHttp sendAndGetOrderHtml ip address = " + Configs.SERVER_IP_ADDRESS + getOrderReq.getMsgtype());
        new NetConnectionReq(Configs.SERVER_IP_ADDRESS + getOrderReq.getMsgtype(),
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
