package com.car.yubangapk.network.myHttpReq;

/**
 * Created by andy on 16/4/22.
 *
 */
public interface HttpReqShareCallback {

    void onFail(int errorCode, String message);
    void onSuccess(Object object, int type);

}
