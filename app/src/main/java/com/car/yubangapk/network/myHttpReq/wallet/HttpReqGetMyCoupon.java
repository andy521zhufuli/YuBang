package com.car.yubangapk.network.myHttpReq.wallet;

import com.car.yubangapk.configs.Configs;
import com.car.yubangapk.configs.ErrorCodes;
import com.car.yubangapk.json.bean.wallet.Json2MyCouponBean;
import com.car.yubangapk.json.bean.wallet.Json2MyWalletBean;
import com.car.yubangapk.json.formatJson.formatWallet.Json2MyCoupon;
import com.car.yubangapk.json.formatJson.formatWallet.Json2MyWallet;
import com.car.yubangapk.network.myHttpReq.HttpReqCallback;
import com.car.yubangapk.network.okhttp.OkHttpUtils;
import com.car.yubangapk.network.okhttp.callback.StringCallback;
import com.car.yubangapk.utils.L;
import com.car.yubangapk.utils.toastMgr;
import okhttp3.Call;

/**
 * Created by andy on 16/6/22.
 *
 * 获取我的优惠券
 *
 */
public class HttpReqGetMyCoupon {

    private HttpReqCallback mCallback;

    public HttpReqGetMyCoupon()
    {

    }

    public void setCallback(HttpReqCallback callback)
    {
        this.mCallback = callback;
    }

    public void getMyCoupon(String userid, int page, int rows, String type, String flag, int scrop)
    {
        if (type.equals("全部"))

        {
            OkHttpUtils.post()
                    .url(Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GET_MY_COUPON)
                    .addParams("sqlName", "clientGetMyCoupon")
                    .addParams("page", page + "")//第几页
                    .addParams("rows", rows + "")//一页几条记录
                    //.addParams("dataReqModel.args.scope", "0")
                    .addParams("dataReqModel.userid", userid)
                    //.addParams("dataReqModel.args.flagName", flag)//优惠券状态, 未使用 已使用 已过期
                    .build()
                    .execute(new GetCouponCallback());
            L.i("HttpReqGetMyCoupon", "获取用户优惠券 url = " + Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GET_MY_COUPON + "?"
                            + "sqlName=" + "clientGetMyCoupon"
                            + "&page=" + page
                            + "&rows=" + rows
                            //+ "&dataReqModel.args.scope=" + type
                            + "&dataReqModel.args.userid=" + userid
                            //+ "&dataReqModel.args.flagName=" + flag

            );
        }
        else
        {
            OkHttpUtils.post()
                    .url(Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GET_MY_COUPON)
                    .addParams("sqlName", "clientGetMyCoupon")
                    .addParams("page", page + "")//第几页
                    .addParams("rows", rows + "")//一页几条记录
                    .addParams("dataReqModel.args.scope", scrop + "")
                    .addParams("dataReqModel.userid", userid)
                    .addParams("dataReqModel.args.flagName", flag)//优惠券状态, 未使用 已使用 已过期
                    .build()
                    .execute(new GetCouponCallback());
            L.i("HttpReqGetMyCoupon", "获取用户优惠券 url = " + Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GET_MY_COUPON + "?"
                            + "sqlName=" + "clientGetMyCoupon"
                            + "&page=" + page
                            + "&rows=" + rows
                            + "&dataReqModel.args.scope=" + "0"
                            + "&dataReqModel.args.userid=" + userid
                            + "&dataReqModel.args.flagName=" + flag

            );
        }

    }

    class GetCouponCallback extends StringCallback
    {

        @Override
        public void onError(Call call, Exception e) {
            mCallback.onFail(ErrorCodes.ERROR_CODE_NETWORK, ErrorCodes.NETWORK_ERROR);
        }

        @Override
        public void onResponse(String response) {
            L.i("HttpReqGetMyCoupon", "获取用户优惠券 json = " + response);
            Json2MyCoupon json2MyCoupon = new Json2MyCoupon(response);
            Json2MyCouponBean json2MyCouponMyCoupon = json2MyCoupon.getMyCoupon();

            if (json2MyCouponMyCoupon == null)
            {
                mCallback.onFail(ErrorCodes.ERROR_CODE_SERVER_ERROR, ErrorCodes.SERVER_ERROR);
            }
            else
            {
                if (json2MyCouponMyCoupon.getReturnCode() == 0)
                {

                    if (json2MyCouponMyCoupon.isHasData() == true)
                    {
                        mCallback.onSuccess(json2MyCouponMyCoupon);
                    }
                    else
                    {
                        mCallback.onFail(ErrorCodes.ERROR_CODE_NO_DATA, ErrorCodes.NO_DATA);
                    }

                } else {
                    mCallback.onFail(json2MyCouponMyCoupon.getReturnCode(), json2MyCouponMyCoupon.getMessage());
                    toastMgr.builder.display(json2MyCouponMyCoupon.getMessage(), 1);
                }
            }

        }
    }
}
