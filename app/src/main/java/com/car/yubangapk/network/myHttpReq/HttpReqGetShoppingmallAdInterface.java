package com.car.yubangapk.network.myHttpReq;

import com.car.yubangapk.json.bean.ShoppingmallAd;

import java.util.List;

/**
 * Created by andy on 16/4/22.
 */
public interface HttpReqGetShoppingmallAdInterface {

    void onSuccess(List<ShoppingmallAd> bannerad);
    void onFail(int errorCode, String message);

}
