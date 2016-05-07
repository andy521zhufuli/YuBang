package com.car.yubangapk.json.bean.OrderDetail;

/**
 * Created by andy on 16/5/7.
 */
public class OrderPrice {
//    "orderId": "0ec94834-f3b1-11e5-bd56-28d244001fe5",
//            "orderNum": "10000723",
//            "totalPrice": 222,
//            "installCoast": 0,
//            "couponPrice": 0

    String orderId;
    String orderNum;
    double totalPrice;
    double installCoast;
    double couponPrice;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getInstallCoast() {
        return installCoast;
    }

    public void setInstallCoast(double installCoast) {
        this.installCoast = installCoast;
    }

    public double getCouponPrice() {
        return couponPrice;
    }

    public void setCouponPrice(double couponPrice) {
        this.couponPrice = couponPrice;
    }
}
