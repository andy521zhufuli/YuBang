package com.car.yubangapk.json.bean;


import com.car.yubangapk.json.Base;

import java.util.List;

/**
 * Created by andy on 16/4/16.
 */
public class Json2AddressBean extends Base{


    //{"defaultAddress":{},"addresses":[],"isJson":true,"isReturnStr":false,"returnCode":-100,"returneMsg":"SUCCESS","message":"服务器错误"}
    // {"defaultAddress":{},"addresses":[],"isJson":true,"isReturnStr":false,"returnCode":1,"returneMsg":"SUCCESS","message":"该用户无收货地址"}

    //address json = {"defaultAddress":{"id":"0","CUserid":"bf8eb70b-c29f-41c8-a9bc-7e7dd56880bb","name":"朱福利","phone":"123456789","isDefaule":1},
    // "addresses":[
    // {"id":"0","CUserid":"bf8eb70b-c29f-41c8-a9bc-7e7dd56880bb","name":"朱福利","phone":"123456789","isDefaule":1}
    // ],"isJson":true,"isReturnStr":false,"returnCode":0,"returneMsg":"SUCCESS"}

    Json2DefaultAddressBean defaultAddress;



    List<AddressBean> addresses;

    boolean isJson;
    boolean isReturnStr;
    int returnCode;
    String returneMsg;

    public List<AddressBean> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<AddressBean> addresses) {
        this.addresses = addresses;
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

    public Json2DefaultAddressBean getDefaultAddress() {

        return defaultAddress;
    }

    public void setDefaultAddress(Json2DefaultAddressBean defaultAddress) {
        this.defaultAddress = defaultAddress;
    }
}
