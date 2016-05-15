package com.car.yubangapk.json.formatJson;

import com.car.yubangapk.json.JSONUtils;
import com.car.yubangapk.json.bean.GetVerifyCodeBean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by andy on 16/5/15.
 */
public class FormatPayOrder {
    private String json;

    public FormatPayOrder(String json)
    {
        this.json = json;
    }

    /**
     * {"isJson":true,"isReturnStr":false,"returnCode":-1,"returneMsg":"SUCCESS","message":"参数错误"}
     * {"isJson":true,"isReturnStr":false,"returnCode":0,"returneMsg":"SUCCESS","message":"发送成功"}
     * json转成类
     * @return
     */
    public GetVerifyCodeBean getdata()
    {
        GetVerifyCodeBean verifyCodeBean = new GetVerifyCodeBean();

        JSONObject total = null;

        try {
            total = new JSONObject(json);

            boolean isJson;
            boolean isReturnStr;
            int returnCode;
            String returneMsg;
            String message;

            isJson = JSONUtils.getBoolean(total, "isJson", false);
            isReturnStr = JSONUtils.getBoolean(total, "isReturnStr", false);
            returnCode = JSONUtils.getInt(total, "returnCode", 0);

            returneMsg = JSONUtils.getString(total, "returneMsg", "");

            message = JSONUtils.getString(total, "message", "");

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
