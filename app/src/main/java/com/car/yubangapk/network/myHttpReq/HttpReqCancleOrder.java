package com.car.yubangapk.network.myHttpReq;

import com.car.yubangapk.configs.Configs;
import com.car.yubangapk.configs.ErrorCodes;
import com.car.yubangapk.json.bean.OrderDetail.BaseJson;
import com.car.yubangapk.json.formatJson.FormatBaseJson;
import com.car.yubangapk.network.okhttp.OkHttpUtils;
import com.car.yubangapk.network.okhttp.callback.StringCallback;
import com.car.yubangapk.utils.L;

import com.car.yubangapk.utils.toastMgr;

import okhttp3.Call;

/**
 * Created by andy on 16/6/2.
 *
 * 取消订单网络请求
 *
 */
public class HttpReqCancleOrder
{
    private HttpReqCallback mCallback;

    public HttpReqCancleOrder()
    {

    }

    public void setCallback(HttpReqCallback callback)
    {
        this.mCallback = callback;
    }

    public void cancelOrder(String userid, String orderid)
    {
        OkHttpUtils.post()
                .url(Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_CANCEL_ORDER)
                .addParams("commentReq.userid", userid)
                .addParams("commentReq.orderId", orderid)
                .build()
                .execute(new CommentCallback());
        L.i("HttpReqCancleOrder", "取消订单 url = " + Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_CANCEL_ORDER + "?"
                        + "commentReq.userid=" + userid
                        + "&" + "commentReq.orderId=" + orderid
        );
    }


    class CommentCallback extends StringCallback
    {

        @Override
        public void onError(Call call, Exception e) {
            mCallback.onFail(ErrorCodes.ERROR_CODE_NETWORK, ErrorCodes.NETWORK_ERROR);
        }

        @Override
        public void onResponse(String response) {
            L.i("HttpReqCancleOrder", "取消订单 json = " + response);
            FormatBaseJson formatBaseJsonCommonCode = new FormatBaseJson(response);
            BaseJson baseJson = formatBaseJsonCommonCode.getBaseCommon();
            if (baseJson == null)
            {
                mCallback.onFail(ErrorCodes.ERROR_CODE_SERVER_ERROR, ErrorCodes.SERVER_ERROR);
                toastMgr.builder.display("服务器错误", 1);
            }
            else
            {
                if (baseJson.getReturnCode() == 0)
                {
                    mCallback.onSuccess(baseJson);
                }
                else
                {
                    mCallback.onFail(baseJson.getReturnCode(), baseJson.getMessage());
                    toastMgr.builder.display(baseJson.getMessage(), 1);
                }
            }

        }
    }

}
