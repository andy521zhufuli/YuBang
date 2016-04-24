package com.car.yubangapk.json.bean;

import com.car.yubangapk.json.Base;

/**
 * Created by andy on 16/4/23.
 *
 * 发现--推荐合伙人---上传信息
 *
 */
public class Json2RecommendShopDetailInfoBean extends Base
{

    //{
    // "isJson":true,
    // "isReturnStr":false,
    // "returnCode":0,
    // "returneMsg":"SUCCESS",
    // "message":""
    // ,shopId:””}提示shopId需要保存后面还要用

    private boolean isJson;
    private boolean isReturnStr;
    private int returnCode;
    private String returneMsg;
    private String message;
    private String shopId;

    public boolean isJson() {
        return isJson;
    }

    public void setIsJson(boolean isJson) {
        this.isJson = isJson;
    }

    //{"isJson":true,"isReturnStr":false,"returnCode":0,"returneMsg":"SUCCESS","message":"发送成功"}


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

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }
}
