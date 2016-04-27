package com.car.yubangapk.json.bean;

import com.car.yubangapk.json.Base;

import java.util.List;

/**
 * Created by andy on 16/4/25.
 */
public class Json2MyUserInfoBean extends Base{

    //当用户未登录时候 提示用户未登录
    // json = {"isJson":true,"isReturnStr":false,"returnCode":100,"returneMsg":"SUCCESS","message":"用户未登录"}

    //正常的
    String phoneNum;
    String userName;
    String car;
    String photoName;
    String pathCode;
    //未登录的
    boolean isJson;
    boolean isReturnStr;
    int returnCode;
    String returneMsg;
    String message;
    boolean logined;

    public boolean isLogined() {
        return logined;
    }

    public void setLogined(boolean logined) {
        this.logined = logined;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }

    public String getPhotoName() {
        return photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }

    public String getPathCode() {
        return pathCode;
    }

    public void setPathCode(String pathCode) {
        this.pathCode = pathCode;
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

    public int getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(int returnCode) {
        this.returnCode = returnCode;
    }

}
