package com.car.yubangapk.json.bean;

import com.car.yubangapk.json.Base;

/**
 * Created by andy on 16/4/25.
 */
public class MyOrderBean extends Base
{
//    {"total":3,"rows":[
//{"id":"0ec94834-f3b1-11e5-bd56-28d244001fe5",
// "pathCode":"2",
// "orderStatusName":"等待店家确认",
// "productNum":2,
// "orderNumber":"10000723",
// "orderName":"小保养-001",
// "orderTime":"2016-03-26 00:00:00",
// "orderMoney":"222",
// "photo":"5f0c5700-aa3b-40cc-bfb8-3b160f507e3d.png"},
//        {"id":"2","pathCode":"","orderStatusName":"等待买家付款","productNum":0,"orderNumber":"10000721","orderName":"大保养","orderTime":"2016-03-26 00:00:00","orderMoney":"1999","photo":""},
//
//        {"id":"ba767580-f336-11e5-a33b-28d244001fe5","pathCode":"","orderStatusName":"等待买家付款","productNum":0,"orderNumber":"10000721","orderName":"大保养","orderTime":"2016-03-26 00:00:00","orderMoney":"1999","photo":""}]}
    String id;
    String pathCode;
    String orderStatusName;
    String productNum;
    String orderNumber;
    String orderName;
    String orderTime;
    String orderMoney;
    String photo;

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getProductNum() {

        return productNum;
    }

    public void setProductNum(String productNum) {
        this.productNum = productNum;
    }

    public String getPathCode() {
        return pathCode;
    }

    public void setPathCode(String pathCode) {
        this.pathCode = pathCode;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderName() {

        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getOrderStatusName() {

        return orderStatusName;
    }

    public void setOrderStatusName(String orderStatusName) {
        this.orderStatusName = orderStatusName;
    }

    public String getOrderTime() {

        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrderMoney() {

        return orderMoney;
    }

    public void setOrderMoney(String orderMoney) {
        this.orderMoney = orderMoney;
    }

    public String getId() {

        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
