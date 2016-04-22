package com.car.yubangapk.network.myHttpReq;

import com.car.yubangapk.json.bean.Json2ProductPackageIdBean;
import com.car.yubangapk.json.bean.Json2ShopServiceBean;
import com.car.yubangapk.json.bean.Json2ShoppingmallBottomPicsBean;

import java.util.List;

/**
 * Created by andy on 16/4/22.
 *
 * 从门店进入产品包
 *      获取可修改产品包的数量
 */
public interface HttpReqModifiableCountFromShopCallback
{

    void onFail(int errorCode, String message);
    void onSuccess(List<Json2ShopServiceBean> mModifyableSHopItemList);

}
