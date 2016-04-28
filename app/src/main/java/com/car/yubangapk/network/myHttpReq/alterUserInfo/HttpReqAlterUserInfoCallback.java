package com.car.yubangapk.network.myHttpReq.alterUserInfo;

/**
 * Created by andy on 16/4/28.
 */
public interface HttpReqAlterUserInfoCallback
{
    void onFail(int errorCode, String message);
    void onSuccess(Object object);
}
