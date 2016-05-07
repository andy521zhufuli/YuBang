package com.car.yubangapk.json.bean.OrderDetail;

import com.car.yubangapk.json.Base;

/**
 * Created by andy on 16/5/2.
 */
public class BaseJson extends Base
{
    //{"isJson":true,
    // "isReturnStr":false,
    // "returnCode":0,
    // "returneMsg":"SUCCESS",
    // "message":"发送成功"}

    boolean isJson;

    boolean isReturnStr;

    int returnCode;

    String returneMsg;

    String message;

    public boolean isJson() {
        return isJson;
    }

    public void setIsJson(boolean isJson) {
        this.isJson = isJson;
    }

    public boolean isReturnStr() {
        return isReturnStr;
    }

    public void setIsReturnStr(boolean isReturnStr) {
        this.isReturnStr = isReturnStr;
    }

    public int getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(int returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturneMsg() {
        return returneMsg;
    }

    public void setReturneMsg(String returneMsg) {
        this.returneMsg = returneMsg;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
