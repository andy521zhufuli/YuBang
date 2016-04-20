package com.car.yubangapk.network.myHttpReq;

import com.car.yubangapk.json.bean.Json2AddressBean;

/**
 * Created by andy on 16/4/16.
 */
public interface httpReqAddressInterface {

    void onGetAddressSucces(Json2AddressBean addressBean);//得到的产品包列表
    void onGetAddressFail(int errorCode);//失败的理由
}
