package com.car.yubangapk.network.myHttpReq;

import com.car.yubangapk.configs.Configs;
import com.car.yubangapk.configs.ErrorCodes;
import com.car.yubangapk.json.bean.Json2MyRecommendPartnersBean;
import com.car.yubangapk.json.bean.OrderDetail.OrderDetailInfo;
import com.car.yubangapk.json.formatJson.FormatJsonOrderDetail.Json2OrderDetail;
import com.car.yubangapk.json.formatJson.Json2MyRecommendPartners;
import com.car.yubangapk.network.okhttp.OkHttpUtils;
import com.car.yubangapk.network.okhttp.callback.StringCallback;
import com.car.yubangapk.utils.L;

import okhttp3.Call;

/**
 * Created by andy on 16/5/7.
 *
 * 获取我的订单  订单详情
 *
 */
public class HttpReqGetMyOrderDetailInfo
{

    String TAG = HttpReqGetMyOrderDetailInfo.class.getSimpleName();

    private static final String ARGS_ORDERID = "orderStatusReq.orderId";

    private static final String ARGS_USER_ID = "orderStatusReq.userid";


    private HttpReqCallback mCallback;

    public HttpReqGetMyOrderDetailInfo()
    {

    }
    /**
     * 设置回调
     * @param callback
     */
    public void setCallback(HttpReqCallback callback)
    {
        this.mCallback = callback;
    }


    public void getMyOrderDetailInfo(String mUserId, String orderId)
    {


        mUserId  = "66a64d1d-a51d-4b2f-a5ee-cff9900f3a52";
        orderId = "fc9b2282-47b6-4e1d-9812-eb1c8f331be7";

        OkHttpUtils.post()
                .url(Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GET_ORDER_DETAIL)
                .addParams(ARGS_ORDERID,orderId)
                .addParams(ARGS_USER_ID, mUserId)

                .build()
                .execute(new GetAddressCallBack());

        L.i(TAG, "获取address url = " + Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GET_ORDER_DETAIL + "?"
                        + ARGS_ORDERID + "=" + orderId
                        + "&" + ARGS_USER_ID + "=" + mUserId
        );
    }



    class GetAddressCallBack extends StringCallback
    {

        @Override
        public void onError(Call call, Exception e) {
            L.d(TAG, "网络错误");
            mCallback.onFail(ErrorCodes.ERROR_CODE_NETWORK, ErrorCodes.NETWORK_ERROR);
        }

        @Override
        public void onResponse(String response) {

            Json2OrderDetail json2OrderDetail = new Json2OrderDetail(response);
            OrderDetailInfo orderDetailInfo = json2OrderDetail.getMyOrderDetailInfo();


            L.d(TAG, "获取订单详情 json = " + response);


            if (orderDetailInfo == null)
            {
                mCallback.onFail(ErrorCodes.ERROR_CODE_NETWORK, ErrorCodes.NETWORK_ERROR);
            }
            else
            {
                if (orderDetailInfo.getReturnCode() == 100)
                {
                    mCallback.onFail(ErrorCodes.ERROR_CODE_NOT_LOGIN, "用户未登录");
                }

                else if (orderDetailInfo.isHasData() == false)
                {
                    mCallback.onFail(ErrorCodes.ERROR_CODE_SERVER_ERROR, ErrorCodes.SERVER_ERROR);
                }
                else
                {
                    mCallback.onSuccess(orderDetailInfo);
                }
            }
        }
    }
}
