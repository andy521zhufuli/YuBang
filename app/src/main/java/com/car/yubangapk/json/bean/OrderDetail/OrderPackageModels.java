package com.car.yubangapk.json.bean.OrderDetail;

import java.util.List;

/**
 * Created by andy on 16/5/7.
 *
 * 我的订单里面的产品包
 *
 */
public class OrderPackageModels
{
//    "packageName": "小保养",
//            "photoName": "27932ef4-7cfe-4f0a-afe8-2663fe073ea8.jpg",
//            "pathCode": "2",
//            "orderProductModels": //产品包里面的产品
    String packageName;
    String photoName;
    String pathCode;

    List<OrderProductModels> orderProductModels;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getPhotoName() {
        return photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }

    public String getPathCode() {
        return pathCode;
    }

    public void setPathCode(String pathCode) {
        this.pathCode = pathCode;
    }

    public List<OrderProductModels> getOrderProductModels() {
        return orderProductModels;
    }

    public void setOrderProductModels(List<OrderProductModels> orderProductModels) {
        this.orderProductModels = orderProductModels;
    }
}
