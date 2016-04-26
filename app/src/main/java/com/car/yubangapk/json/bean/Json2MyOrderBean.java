package com.car.yubangapk.json.bean;

import com.car.yubangapk.json.Base;

import java.util.List;

/**
 * Created by andy on 16/4/25.
 */
public class Json2MyOrderBean extends Base{
//    {"total":3,"rows":[
//        {"id":"0ec94834-f3b1-11e5-bd56-28d244001fe5","pathCode":"2","orderStatusName":"等待店家确认","productNum":2,"orderNumber":"10000723","orderName":"小保养-001","orderTime":"2016-03-26 00:00:00","orderMoney":"222","photo":"5f0c5700-aa3b-40cc-bfb8-3b160f507e3d.png"},
//        {"id":"2","pathCode":"","orderStatusName":"等待买家付款","productNum":0,"orderNumber":"10000721","orderName":"大保养","orderTime":"2016-03-26 00:00:00","orderMoney":"1999","photo":""},
//
//        {"id":"ba767580-f336-11e5-a33b-28d244001fe5","pathCode":"","orderStatusName":"等待买家付款","productNum":0,"orderNumber":"10000721","orderName":"大保养","orderTime":"2016-03-26 00:00:00","orderMoney":"1999","photo":""}]}

    int total;

    List<MyOrderBean> rows;

    int returnCode;

    public int getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(int returnCode) {
        this.returnCode = returnCode;
    }

    public List<MyOrderBean> getRows() {
        return rows;
    }

    public void setRows(List<MyOrderBean> rows) {
        this.rows = rows;
    }

    public int getTotal() {


        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
