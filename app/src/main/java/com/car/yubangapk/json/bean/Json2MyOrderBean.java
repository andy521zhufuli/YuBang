package com.car.yubangapk.json.bean;

import com.car.yubangapk.json.Base;

import java.util.List;

/**
 * Created by andy on 16/4/25.
 */
public class Json2MyOrderBean extends Base{
    //{"total":3,"rows":[
    // {"id":"0ec94834-f3b1-11e5-bd56-28d244001fe5","orderMoney":"222","orderTime":"2016-03-26 00:00:00","orderStatusName":"等待店家确认","orderName":"小保养-001","orderNumber":"10000723"},
    // {"id":"2","orderMoney":"1999","orderTime":"2016-03-26 00:00:00","orderStatusName":"等待买家付款","orderName":"大保养","orderNumber":"10000721"},
    // {"id":"ba767580-f336-11e5-a33b-28d244001fe5","orderMoney":"1999","orderTime":"2016-03-26 00:00:00","orderStatusName":"等待买家付款","orderName":"大保养","orderNumber":"10000721"}]}

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
