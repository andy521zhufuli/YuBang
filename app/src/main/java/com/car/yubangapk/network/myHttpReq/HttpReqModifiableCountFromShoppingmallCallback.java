package com.car.yubangapk.network.myHttpReq;

import com.car.yubangapk.configs.Configs;
import com.car.yubangapk.json.bean.Json2ProductPackageIdBean;
import com.car.yubangapk.json.bean.Json2ShoppingmallBottomPicsBean;
import com.car.yubangapk.json.formatJson.Json2ProductPackageId;
import com.car.yubangapk.json.formatJson.Json2ShoppingmallBottomPics;
import com.car.yubangapk.network.okhttp.OkHttpUtils;
import com.car.yubangapk.network.okhttp.callback.MyStringCallback;
import com.car.yubangapk.utils.L;
import com.car.yubangapk.utils.toastMgr;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by andy on 16/4/22.
 *
 * 从商城进入产品包
 *      获取可修改产品包的数量
 */
public interface HttpReqModifiableCountFromShoppingmallCallback
{

    void onFail(int errorCode, String message);
    void onSuccess(List<Json2ProductPackageIdBean> mModifyableItemList, List<Json2ShoppingmallBottomPicsBean> mModifyableItemShoppingmallBottomPicBeanList);

//    private List<Json2ProductPackageIdBean> mModifyableItemList = new ArrayList<>();
    //传递给可修改的那一页
//    private List<Json2ShoppingmallBottomPicsBean> mModifyableItemShoppingmallBottomPicBeanList;
}
