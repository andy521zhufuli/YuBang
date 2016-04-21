package com.car.yubangapk.network.myHttpReq;

import com.car.yubangapk.json.bean.Json2CouponBean;

/**
 * Created by andy on 16/4/21.
 */
public interface HttpReqConformOrderCouponInterface
{
    void onSuccess(Json2CouponBean json2CouponBean);
    void onFail(int errorCode, String message);
}
