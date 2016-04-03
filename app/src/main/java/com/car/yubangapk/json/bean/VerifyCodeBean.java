package com.car.yubangapk.json.bean;

/**
 * VerifyCodeBean:返回验证码的json格式
 *
 * @author andy
 * @version 1.0.6
 * @created 2016-04-02
 */
public class VerifyCodeBean {


    public boolean isJson() {
        return isJson;
    }

    public void setIsJson(boolean isJson) {
        this.isJson = isJson;
    }

    //{"isJson":true,"isReturnStr":false,"returnCode":0,"returneMsg":"SUCCESS","message":"发送成功"}
    private boolean isJson;
    private boolean isReturnStr;

    public boolean isReturnStr() {
        return isReturnStr;
    }

    public void setIsReturnStr(boolean isReturnStr) {
        this.isReturnStr = isReturnStr;
    }

    private int returnCode;

    public int getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(int returnCode) {
        this.returnCode = returnCode;
    }

    private String returneMsg;

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

    private String message;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    private String userid;
}
