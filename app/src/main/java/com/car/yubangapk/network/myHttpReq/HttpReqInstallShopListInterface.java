package com.car.yubangapk.network.myHttpReq;

import com.car.yubangapk.json.bean.Json2InstallShopModelsBean;

import java.util.List;

/**
 * Created by andy on 16/4/20.
 */
public interface HttpReqInstallShopListInterface {
    void onGetInstallShopSucces(List<Json2InstallShopModelsBean> shopModels);//得到的产品包列表
    void onGetInstallShopFail(int errorCode, String message);//失败的理由
}
