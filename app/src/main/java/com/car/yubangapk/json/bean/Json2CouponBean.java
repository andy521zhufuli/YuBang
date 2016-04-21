package com.car.yubangapk.json.bean;

import com.car.yubangapk.json.Base;

import java.util.List;

/**
 * Created by andy on 16/4/21.
 */
public class Json2CouponBean extends Base
{
    //{
    // "totalPrice          ":0.0,
    // "couponPrice         ":0.0,
    // "installationCoast   ":0.0,
    // "payPrice            ":0.0,
    // "coupons             ":[],
    // "isJson              ":true,
    // "isReturnStr         ":false,
    // "returnCode          ":-2,
    // "returneMsg              ":"SUCCESS",
    // "message             ":"参数错误"}
//    {"totalPrice":0.0,"couponPrice":0.0,"installationCoast":0.0,"payPrice":0.0,
//         "isJson":true,"isReturnStr":false,"returnCode":0,"returneMsg":"SUCCESS"}
//
//         "coupons":[{"couponId":"1","couponName":"总价优惠卷","regulationReach":10,"regulationSubtract":1,"startDate":"2016-04-10 20:13:36.0","endDate":"2016-08-10 20:13:39.0"},
//         {"couponId":"2","couponName":"总价优惠卷1","regulationReach":2,"regulationSubtract":1,"startDate":"2016-04-04 19:01:15.0","endDate":"2016-08-31 19:01:19.0"},
//         {"couponId":"3","couponName":"总价优惠卷1","regulationReach":2,"regulationSubtract":1,"startDate":"2016-04-04 19:01:15.0","endDate":"2016-08-31 19:01:19.0"}],



    double      totalPrice;
    double      couponPrice;
    double      installationCoast;
    double      payPrice;
    List<CouponsBean> coupons;
    boolean     isJson;
    boolean     isReturnStr;
    int         returnCode;
    String      returneMsg;
    String      message;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReturneMsg() {

        return returneMsg;
    }

    public void setReturneMsg(String returneMsg) {
        this.returneMsg = returneMsg;
    }

    public int getReturnCode() {

        return returnCode;
    }

    public void setReturnCode(int returnCode) {
        this.returnCode = returnCode;
    }

    public boolean isReturnStr() {

        return isReturnStr;
    }

    public void setReturnStr(boolean returnStr) {
        isReturnStr = returnStr;
    }

    public boolean isJson() {

        return isJson;
    }

    public void setJson(boolean json) {
        isJson = json;
    }

    public List<CouponsBean> getCoupons() {

        return coupons;
    }

    public void setCoupons(List<CouponsBean> coupons) {
        this.coupons = coupons;
    }

    public double getPayPrice() {

        return payPrice;
    }

    public void setPayPrice(double payPrice) {
        this.payPrice = payPrice;
    }

    public double getInstallationCoast() {

        return installationCoast;
    }

    public void setInstallationCoast(double installationCoast) {
        this.installationCoast = installationCoast;
    }

    public double getCouponPrice() {

        return couponPrice;
    }

    public void setCouponPrice(double couponPrice) {
        this.couponPrice = couponPrice;
    }

    public double getTotalPrice() {

        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
