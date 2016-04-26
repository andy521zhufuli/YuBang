package com.car.yubangapk.network.myHttpReq;

import com.car.yubangapk.configs.Configs;
import com.car.yubangapk.configs.ErrorCodes;
import com.car.yubangapk.json.bean.Json2MyOrderBean;
import com.car.yubangapk.json.bean.Json2MyRecommendPartnersBean;
import com.car.yubangapk.json.formatJson.Json2MyOrder;
import com.car.yubangapk.json.formatJson.Json2MyRecommendPartners;
import com.car.yubangapk.network.okhttp.OkHttpUtils;
import com.car.yubangapk.network.okhttp.callback.StringCallback;
import com.car.yubangapk.utils.L;

import okhttp3.Call;

/**
 * Created by andy on 16/4/26.
 *
 * 拿到我推荐的合伙人列表
 *
 */
public class HttpReqGetMyRecommendPartners
{
    private String TAG = HttpReqAddress.class.getSimpleName();

    private String mUserId;
    private String mPage;
    private String mRows;
    private String mOrderStatus;
    private String mAddressId;

    private HttpReqCallback callback;


    private static final String ARGS_sqlname = "sqlName";// = clientUserRecShop
    private static final String ARGS_page = "page";//第几页
    private static final String ARGS_rows = "rows";//一页几条记录数
    private static final String ARGS_recommend_id = "dataReqModel.args.shopReferee";//用户id
    private static final String ARGS_userid = "dataReqModel.userid";


    /**
     * 构造函数
     * @param
     * @param
     * @param
     * @param
     * @param
     */
    public HttpReqGetMyRecommendPartners()
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

    public void getRecommendPartners(String userid, String Ppage, String rows, String orderStatus)
    {


        if (orderStatus == null)
        {
            OkHttpUtils.post()
                    .url(Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GETDATA)
                    .addParams(ARGS_sqlname, "clientUserRecShop")
                    .addParams(ARGS_page, Ppage)
                    .addParams(ARGS_rows, rows)
                    .addParams(ARGS_recommend_id, userid)
                    .addParams(ARGS_userid, userid)

                    .build()
                    .execute(new GetAddressCallBack());

            L.i(TAG, "获取订单 url = " + Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GETDATA + "?"
                            + ARGS_sqlname + "=" + "clientUserRecShop"
                            + "&" + ARGS_page + "=" + Ppage
                            + "&" + ARGS_rows + "=" + rows
                            + "&" + ARGS_userid + "=" + userid
            );
        }
        else
        {
            OkHttpUtils.post()
                    .url(Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GETDATA)
                    .addParams(ARGS_sqlname, "clientUserRecShop")
                    .addParams(ARGS_page, Ppage)
                    .addParams(ARGS_rows, rows)
                    .addParams(ARGS_recommend_id, userid)
                    .addParams(ARGS_userid, userid)

                    .build()
                    .execute(new GetAddressCallBack());

            L.i(TAG, "获取订单 url = " + Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GETDATA + "?"
                            + ARGS_sqlname + "=" + "clientUserRecShop"
                            + "&" + ARGS_page + "=" + Ppage
                            + "&" + ARGS_rows + "=" + Ppage
                            + "&" + ARGS_recommend_id + "=" + Ppage
                            + "&" + ARGS_userid + "=" + userid
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
            //{"total":53,"rows":[{id ,shopName,shopAddress,shopPhoto,pathCode,phoneNum}]}无shopPhoto显示yubang图标，无其他字段丢弃


            Json2MyRecommendPartners json2MyRecommendPartners = new Json2MyRecommendPartners(response);
            Json2MyRecommendPartnersBean recommendPartnersBean = json2MyRecommendPartners.getMyRecommendPartners();


            L.d(TAG, "address json = " + response);


            if (recommendPartnersBean == null)
            {
                callback.onFail(ErrorCodes.ERROR_CODE_SERVER_ERROR, ErrorCodes.SERVER_ERROR);
            }
            else
            {
                if (recommendPartnersBean.getReturnCode() == 100)
                {
                    callback.onFail(ErrorCodes.ERROR_CODE_NOT_LOGIN, "用户未登录");
                }

                else if (recommendPartnersBean.isHasData() == false)
                {
                    callback.onFail(ErrorCodes.ERROR_CODE_SERVER_ERROR, ErrorCodes.SERVER_ERROR);
                }
                else
                {
                    callback.onSuccess(recommendPartnersBean);
                }
            }
        }
    }
}
