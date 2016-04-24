package com.car.yubangapk.json.formatJson;

import com.car.yubangapk.json.bean.GetVerifyCodeBean;
import com.car.yubangapk.json.bean.Json2RecommendPartnerUploadPhotosBean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by andy on 16/4/24.
 *
 * 推荐合伙人 上传照片
 *
 */
public class Json2RecommendPartnerUploadPhotos
{
    private String json;

    public Json2RecommendPartnerUploadPhotos()
    {

    }
    public Json2RecommendPartnerUploadPhotos(String json)
    {
        this.json = json;
    }

    /**
     * {"isJson":true,"isReturnStr":false,"returnCode":-1,"returneMsg":"SUCCESS","message":"参数错误"}
     * {"isJson":true,"isReturnStr":false,"returnCode":0,"returneMsg":"SUCCESS","message":"发送成功"}
     * json转成类
     */
    public Json2RecommendPartnerUploadPhotosBean getResult()
    {
        Json2RecommendPartnerUploadPhotosBean verifyCodeBean = new Json2RecommendPartnerUploadPhotosBean();

        JSONObject total = null;

        try {
            total = new JSONObject(json);

            boolean isJson;
            boolean isReturnStr;
            int returnCode;
            String returneMsg;
            String message;

            isJson = total.getBoolean("isJson");
            isReturnStr = total.getBoolean("isReturnStr");
            returnCode = total.getInt("returnCode");
            returneMsg = total.getString("returneMsg");
            message = total.getString("message");

            verifyCodeBean.setIsJson(isJson);
            verifyCodeBean.setIsReturnStr(isReturnStr);
            verifyCodeBean.setReturnCode(returnCode);
            verifyCodeBean.setReturneMsg(returneMsg);
            verifyCodeBean.setMessage(message);

        } catch (JSONException e) {
            e.printStackTrace();
            return null;//返回空  服务器错误
        }
        return verifyCodeBean;
    }
}
