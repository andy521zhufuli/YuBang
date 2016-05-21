package com.car.yubangapk.network.myHttpReq;

import android.content.Context;

import com.car.yubangapk.configs.Configs;
import com.car.yubangapk.configs.ErrorCodes;
import com.car.yubangapk.json.bean.GetVerifyCodeBean;
import com.car.yubangapk.json.formatJson.FormatPayOrder;
import com.car.yubangapk.network.okhttp.OkHttpUtils;
import com.car.yubangapk.network.okhttp.callback.StringCallback;
import com.car.yubangapk.utils.L;
import com.car.yubangapk.utils.toastMgr;

import okhttp3.Call;

/**
 * Created by andy on 16/5/14.
 *
 * 支付订单 选择支付方式选择
 *
 */
public class HttpReqPayOrdersPayMethod
{
    private Context mContext;
    private HttpReqCallback mCallback;
    int mType;

    public HttpReqPayOrdersPayMethod()
    {
    }


    public void setCallback(HttpReqCallback callback)
    {
        this.mCallback = callback;

    }

    /**
     * 拿到启动后配置信息
     */
    public void payOrders(String orderid, String payment, String userid)
    {
        OkHttpUtils.post()
                .url(Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_PAY_ORDER_PAY_METHOD)
                .addParams("orderStatusReq.orderId", orderid)
                .addParams("orderStatusReq.payment", payment)
                .addParams("orderStatusReq.paymentOrg", "0880005")
                .addParams("orderStatusReq.userid", userid)
                .build().execute(new PaymentCallback());
        L.i("payOrders", "支付方式url = " + Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_PAY_ORDER_PAY_METHOD + "?"
                + "orderStatusReq.orderId" + "=" + orderid
                + "&" + "orderStatusReq.payment" + "=" + payment
                + "&" + "orderStatusReq.paymentOrg" + "=" + "0880005"
                + "&" + "orderStatusReq.userid" + "=" + userid)
                ;
    }

    /**
     * 获取配置信息回调
     */
    public class PaymentCallback extends StringCallback
    {

        @Override
        public void onError(Call call, Exception e)
        {
            L.d("PaymentCallback", "支付方式 ====" + e.toString());
            mCallback.onFail(ErrorCodes.ERROR_CODE_NETWORK, ErrorCodes.NETWORK_ERROR);
            toastMgr.builder.display("网络错误", 1);

        }

        @Override
        public void onResponse(String response)
        {
            L.d("PaymentCallback", " 支付方式 json ====" + response);

            FormatPayOrder json2WxShare = new FormatPayOrder(response);
            GetVerifyCodeBean wxShareBean = json2WxShare.getdata();

            if (wxShareBean == null)
            {
                mCallback.onFail(ErrorCodes.ERROR_CODE_SERVER_ERROR, ErrorCodes.SERVER_ERROR);
                toastMgr.builder.display("服务器错误", 1);
            }
            else
            {
                if (wxShareBean.getReturnCode() != 0)
                {
                    toastMgr.builder.display(wxShareBean.getMessage(), 1);
                }
                else
                {
                    mCallback.onSuccess(wxShareBean);
                }
            }

        }
    }
}
