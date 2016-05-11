package com.car.yubangapk.json.formatJson;

import com.car.yubangapk.json.JSONUtils;

import com.car.yubangapk.json.bean.WxShareBean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by andy on 16/5/10.
 */
public class Json2WxShare
{
    private String json;

    public Json2WxShare(String json)
    {
        this.json = json;
    }

    public WxShareBean getWxShareBean()
    {
        WxShareBean verifyCodeBean = new WxShareBean();

        JSONObject total = null;

        try {
            total = new JSONObject(json);

            boolean isJson;
            boolean isReturnStr;
            int returnCode;
            String returneMsg;
            String message;


            String url = JSONUtils.getString(total, "url", "");
            String title  = JSONUtils.getString(total, "title", "");
            String dest = JSONUtils.getString(total, "dest", "");
            String buildTransaction = JSONUtils.getString(total, "buildTransaction", "");
            String imageUrl = JSONUtils.getString(total, "imageUrl", "");

            verifyCodeBean.setUrl(url);
            verifyCodeBean.setTitle(title);
            verifyCodeBean.setDest(dest);
            verifyCodeBean.setBuildTransaction(buildTransaction);
            verifyCodeBean.setImageUrl(imageUrl);

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
