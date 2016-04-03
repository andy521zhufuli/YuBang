package com.car.yubangapk.json.bean;

/**
 * LoginBean:登陆
 *
 * @author andy
 * @version 1.0.6
 * @created 2016-04-02
 */
public class Json2LoginBean {
    //{"userid":"","carType":"","name":"","status":"","isJson":true,"isReturnStr":false,"returnCode":-2,"returneMsg":"SUCCESS","message":"验证码错误"}

    private String userid;
    private boolean isJson;
    private boolean isReturnStr;
    private int returnCode;//  0未审核   1 审核   合伙人状态 0.审核中 1.已通过 2.不通过 3.已忽略 4.已屏蔽 5.等待上传资质
    private String returneMsg;
    private String message;
    private String carType;
    private String name;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

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



    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }


}
