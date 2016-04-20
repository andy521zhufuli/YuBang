package com.car.yubangapk.json.bean;

import com.car.yubangapk.json.Base;

import java.util.List;

/**
 * 确认订单里面的安装门店
 */
public class Json2InstallShopBean extends Base {
    //{"shopModels":[],"isJson":true,"isReturnStr":false,"returnCode":-2,"returneMsg":"SUCCESS","message":"该区域无店铺"}
    /**
     *  {"shopModels":[],"isJson":true,"isReturnStr":false,"returnCode":-2,"returneMsg":"SUCCESS","message":"该区域无店铺"}
     *
     *
     * shopModels":[
     *          {"shopId":"10658811-ea3c-11e5-81df-28d244001fe5",
     *          "shopName":"宇邦旗舰店",
     *          "distance":7654.0,
     *          "shopAddress":"广东广州番禺",
     *          "phoneNum":"13454678764",
     *          "pathCode":"6",
     *          "photoName":"c162f805-1796-4ad1-8ead-4539ae795faf_LPP_1.jpg","order":0},
     *
     *          {"shopId":"0513ba78-0ce8-4e00-b6c5-168d5544c4ef","shopName":"强哥维修","distance":7654.0,"shopAddress":"广东广州番禺",
     *          "phoneNum":"13454678764","pathCode":"6","photoName":"c162f805-1796-4ad1-8ead-4539ae795faf_LPP_1.jpg","order":1}],
     * "isJson":true,
     * "isReturnStr":false,
     * "returnCode":0,
     * "returneMsg":"SUCCESS",
     * "message":"查找店铺成功！"}
     */


    private List<Json2InstallShopModelsBean> shopModels;
    private boolean                          isJson;
    private boolean                          isReturnStr;
    private int                              returnCode;
    private String                           returneMsg;
    private String                           message;

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

    public List<Json2InstallShopModelsBean> getShopModels() {

        return shopModels;
    }

    public void setShopModels(List<Json2InstallShopModelsBean> shopModels) {
        this.shopModels = shopModels;
    }
}
