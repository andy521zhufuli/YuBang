package com.car.yubangapk.network.myHttpReq;

import com.car.yubangapk.json.bean.BannerAd;

import java.util.List;

/**
 * Created by andy on 16/4/22.
 */
public interface HttpReqGetShoppingmallBannerInterface {

    void onSuccess(List<BannerAd> bannerad);
    void onFail(int errorCode, String message);

}
