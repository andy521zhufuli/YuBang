package com.car.yubangapk.json.formatJson;

import com.car.yubangapk.json.bean.BaseJsonCommonBean;
import com.car.yubangapk.json.bean.GetVerifyCodeBean;
import com.car.yubangapk.json.bean.VerifyCodeBean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * VerifyCodeBean:返回验证码的json格式
 *
 * @author andy
 * @version 1.0.6
 * @created 2016-04-02
 */
public class FormatBaseJsonCommonCode {

    private String json;

    public FormatBaseJsonCommonCode(String json)
    {
        this.json = json;
    }

    /**
     * {"isJson":true,"isReturnStr":false,"returnCode":-1,"returneMsg":"SUCCESS","message":"参数错误"}
     * {"isJson":true,"isReturnStr":false,"returnCode":0,"returneMsg":"SUCCESS","message":"发送成功"}
     * json转成类
     * @return
     */
    public BaseJsonCommonBean getBaseCommon()
    {
        BaseJsonCommonBean verifyCodeBean = new BaseJsonCommonBean();

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
