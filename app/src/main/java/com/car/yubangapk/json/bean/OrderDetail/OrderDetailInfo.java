package com.car.yubangapk.json.bean.OrderDetail;

import com.car.yubangapk.json.bean.BaseJsonCommonBean;

import java.util.List;

/**
 * Created by andy on 16/5/7.
 */
public class OrderDetailInfo extends BaseJson
{
//    {
//            "isJson": true,
//            "isReturnStr": false,
//            "returnCode": 0,
//            "returneMsg": "SUCCESS",
//            "message": "查询成功"
//
//            "carName": "美丽小车",
//            "shopName": "宇邦旗舰店",
//            "installTime": "2016-09-01 00:00:00.0",
//            "orderStatus": "7",
//
//
//            "orderPrice": {
//            "orderId": "0ec94834-f3b1-11e5-bd56-28d244001fe5",
//                "orderNum": "10000723",
//                "totalPrice": 222,
//                "installCoast": 0,
//                "couponPrice": 0
//    }
//        "orderAddress": {
//                "name": "1111",
//                "phone": "福利哥地址",
//                "carNum": "1111"
//    },
//
//        "orderPackageModels": //产品包
//        [
//        {
//            "packageName": "小保养",
//                "photoName": "27932ef4-7cfe-4f0a-afe8-2663fe073ea8.jpg",
//                "pathCode": "2",
//                "orderProductModels": //产品包里面的
//            [
//            {
//                "productId": "028be795-b7ce-419d-8883-0bee934f6d33",
//                    "productName": "不告诉你",
//                    "price": 2222,
//                    "num": 1,
//                    "pathCode": "2",
//                    "photoName": "4e304edf-d3f6-43c9-b751-3dba522d3254.jpg",
//                    "des": "半合成"
//            },
//            {
//                "productId": "04f88f9e-795f-49a3-bbae-012e000320bc",
//                    "productName": "不告诉你",
//                    "price": 2222,
//                    "num": 1,
//                    "pathCode": "2",
//                    "photoName": "27932ef4-7cfe-4f0a-afe8-2663fe073ea8.jpg",
//                    "des": "半合成"
//            }
//            ]
//        }
//        ]
//
//    faultDescription
//            detectionResult
//    maintenanceSuggestion
//
//    }

    String carName;
    String shopName;
    String installTime;
    String orderStatus;

    OrderPrice orderPrice;

    OrderAddress orderAddress;

    List<OrderPackageModels> orderPackageModels;

    String faultDescription;//故障描述
    String detectionResult;//检测结果
    String maintenanceSuggestion;//维修建议


    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getInstallTime() {
        return installTime;
    }

    public void setInstallTime(String installTime) {
        this.installTime = installTime;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public OrderPrice getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(OrderPrice orderPrice) {
        this.orderPrice = orderPrice;
    }

    public OrderAddress getOrderAddress() {
        return orderAddress;
    }

    public void setOrderAddress(OrderAddress orderAddress) {
        this.orderAddress = orderAddress;
    }

    public List<OrderPackageModels> getOrderPackageModels() {
        return orderPackageModels;
    }

    public void setOrderPackageModels(List<OrderPackageModels> orderPackageModels) {
        this.orderPackageModels = orderPackageModels;
    }


    public String getFaultDescription() {
        return faultDescription;
    }

    public void setFaultDescription(String faultDescription) {
        this.faultDescription = faultDescription;
    }

    public String getDetectionResult() {
        return detectionResult;
    }

    public void setDetectionResult(String detectionResult) {
        this.detectionResult = detectionResult;
    }

    public String getMaintenanceSuggestion() {
        return maintenanceSuggestion;
    }

    public void setMaintenanceSuggestion(String maintenanceSuggestion) {
        this.maintenanceSuggestion = maintenanceSuggestion;
    }
}
