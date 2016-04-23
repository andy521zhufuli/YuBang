package com.car.yubangapk.json.bean;

import com.car.yubangapk.json.Base;

/**
 * Created by andy on 16/4/22.
 *
 * 订单价格
 */
public class Json2OrderPriceBean extends Base
{

    boolean isJson;
    boolean isReturnStr;
    int returnCode;
    String returneMsg;
    String message;
    private double totalPrice=0;//总价格

    private double couponPrice=0;//优惠卷价格

    private double installationCoast=0;//安装费

    private double payPrice=0;//需要支付价格

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
