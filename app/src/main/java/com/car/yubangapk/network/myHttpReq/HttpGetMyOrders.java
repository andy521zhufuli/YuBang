package com.car.yubangapk.network.myHttpReq;

import com.car.yubangapk.configs.Configs;
import com.car.yubangapk.configs.ErrorCodes;
import com.car.yubangapk.json.bean.Json2AddressBean;
import com.car.yubangapk.json.bean.Json2MyOrderBean;
import com.car.yubangapk.json.formatJson.Json2Address;
import com.car.yubangapk.json.formatJson.Json2MyOrder;
import com.car.yubangapk.network.okhttp.OkHttpUtils;
import com.car.yubangapk.network.okhttp.callback.StringCallback;
import com.car.yubangapk.utils.L;

import okhttp3.Call;

/**
 * Created by andy on 16/4/25.
 *
 * 去拿订单
 *
 */
public class HttpGetMyOrders
{
    private String TAG = HttpGetMyOrders.class.getSimpleName();

    private String mUserId;
    private String mPage;
    private String mRows;
    private String mOrderStatus;
    private String mAddressId;

    private HttpReqCallback callback;


    private static final String ARGS1 = "sqlName";// = clientUserOrder
    private static final String ARGS2 = "page";//第几页
    private static final String ARGS3 = "rows";//一页几条记录数
    private static final String ARGS4 = "dataReqModel.args.orderStatus";//订单状态，参考上图
    private static final String ARGS5 = "dataReqModel.userid";


    /**
     * 构造函数
     * @param
     * @param
     * @param
     * @param
     * @param
     */
    public HttpGetMyOrders()
    {

    }

    /**
     * 设置回调
     * @param callback
     */
    public void setCallback(HttpReqCallback callback)
    {
        this.callback = callback;
    }
    public void getOrders(String userid, String Ppage, String rows, String orderStatus)
    {


        if (orderStatus == null)
        {
            OkHttpUtils.post()
                    .url(Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GET_USER_ORDER)
                    .addParams(ARGS1, "clientUserOrder")
                    .addParams(ARGS2, Ppage)
                    .addParams(ARGS3, rows)
                    .addParams(ARGS5, userid)

                    .build()
                    .execute(new GetAddressCallBack());

            L.i(TAG, "获取订单 url = " + Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GET_USER_ORDER + "?"
                            + ARGS1 + "=" + "clientUserOrder"
                            + "&" + ARGS2 + "=" + Ppage
                            + "&" + ARGS3 + "=" + rows
                            + "&" + ARGS5 + "=" + userid
            );
        }
        else
        {
            OkHttpUtils.post()
                    .url(Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GET_USER_ORDER)
                    .addParams(ARGS1, "clientUserOrder")
                    .addParams(ARGS2, Ppage)
                    .addParams(ARGS3, rows)
                    .addParams(ARGS4, orderStatus)
                    .addParams(ARGS5, userid)

                    .build()
                    .execute(new GetAddressCallBack());

            L.i(TAG, "获取订单 url = " + Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GET_USER_ORDER + "?"
                            + ARGS1 + "=" + "clientUserOrder"
                            + "&" + ARGS2 + "=" + Ppage
                            + "&" + ARGS3 + "=" + rows
                            + "&" + ARGS4 + "=" + orderStatus
                            + "&" + ARGS5 + "=" + userid
            );
        }
    }


    class GetAddressCallBack extends StringCallback
    {

        @Override
        public void onError(Call call, Exception e) {
            L.d(TAG, "网络错误");
            callback.onFail(ErrorCodes.ERROR_CODE_NETWORK, ErrorCodes.NETWORK_ERROR);
        }

        @Override
        public void onResponse(String response) {
            //{"total":3,"rows":[
            // {"id":"0ec94834-f3b1-11e5-bd56-28d244001fe5","orderMoney":"222","orderTime":"2016-03-26 00:00:00","orderStatusName":"等待店家确认","orderName":"小保养-001","orderNumber":"10000723"},
            // {"id":"2","orderMoney":"1999","orderTime":"2016-03-26 00:00:00","orderStatusName":"等待买家付款","orderName":"大保养","orderNumber":"10000721"},
            // {"id":"ba767580-f336-11e5-a33b-28d244001fe5","orderMoney":"1999","orderTime":"2016-03-26 00:00:00","orderStatusName":"等待买家付款","orderName":"大保养","orderNumber":"10000721"}]}

            Json2MyOrder myOrder = new Json2MyOrder(response);
            Json2MyOrderBean orderBean = myOrder.getMyorder();

            L.d(TAG, "获取订单 json = " + response);


            if (orderBean == null)
            {
                callback.onFail(ErrorCodes.ERROR_CODE_SERVER_ERROR, ErrorCodes.SERVER_ERROR);
            }
            else
            {
                if (orderBean.getReturnCode() == 100)
                {
                    callback.onFail(ErrorCodes.ERROR_CODE_NOT_LOGIN, "用户未登录");
                }

                else if (orderBean.isHasData() == false)
                {
                    callback.onFail(ErrorCodes.ERROR_CODE_SERVER_ERROR, ErrorCodes.SERVER_ERROR);
                }
                else if (orderBean.getRows().size() == 0)
                {
                    callback.onFail(ErrorCodes.ERROR_CODE_NO_DATA, "没有数据");
                }
                else
                {
                    callback.onSuccess(orderBean);
                }
            }
        }
    }
}
