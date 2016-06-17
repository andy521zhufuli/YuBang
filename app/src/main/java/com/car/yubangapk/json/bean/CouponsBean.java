package com.car.yubangapk.json.bean;

import com.car.yubangapk.json.Base;
import com.car.yubangapk.json.bean.OrderDetail.BaseJson;

/**
 * Created by andy on 16/4/21.
 */
public class CouponsBean extends BaseJson
{
    //"coupons":[
    //{"couponId":"1","couponName":"总价优惠卷","regulationReach":10,"regulationSubtract":1,"startDate":"2016-04-10 20:13:36.0","endDate":"2016-08-10 20:13:39.0"},
    //{"couponId":"2","couponName":"总价优惠卷1","regulationReach":2,"regulationSubtract":1,"startDate":"2016-04-04 19:01:15.0","endDate":"2016-08-31 19:01:19.0"},
    //{"couponId":"3","couponName":"总价优惠卷1","regulationReach":2,"regulationSubtract":1,"startDate":"2016-04-04 19:01:15.0","endDate":"2016-08-31 19:01:19.0"}]
    //
    //    {"totalPrice":0.0,"couponPrice":0.0,"installationCoast":0.0,"payPrice":0.0,
//         "isJson":true,"isReturnStr":false,"returnCode":0,"returneMsg":"SUCCESS"}
//
//         "coupons":[{"couponId":"1","couponName":"总价优惠卷","regulationReach":10,"regulationSubtract":1,"startDate":"2016-04-10 20:13:36.0","endDate":"2016-08-10 20:13:39.0"},
//         {"couponId":"2","couponName":"总价优惠卷1","regulationReach":2,"regulationSubtract":1,"startDate":"2016-04-04 19:01:15.0","endDate":"2016-08-31 19:01:19.0"},
//         {"couponId":"3","couponName":"总价优惠卷1","regulationReach":2,"regulationSubtract":1,"startDate":"2016-04-04 19:01:15.0","endDate":"2016-08-31 19:01:19.0"}],



    String couponId;
    String couponName;
    int regulationReach;
    int regulationSubtract;
    String startDate;
    String endDate;

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartDate() {

        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public int getRegulationSubtract() {

        return regulationSubtract;
    }

    public void setRegulationSubtract(int regulationSubtract) {
        this.regulationSubtract = regulationSubtract;
    }

    public int getRegulationReach() {

        return regulationReach;
    }

    public void setRegulationReach(int regulationReach) {
        this.regulationReach = regulationReach;
    }

    public String getCouponName() {

        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public String getCouponId() {

        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }
}
