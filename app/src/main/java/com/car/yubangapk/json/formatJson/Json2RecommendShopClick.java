package com.car.yubangapk.json.formatJson;

import com.car.yubangapk.json.bean.GetVerifyCodeBean;
import com.car.yubangapk.json.bean.Json2RecommendShopClickBean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by andy on 16/4/23.
 */
public class Json2RecommendShopClick
{



    private String json;

    public Json2RecommendShopClick()
    {

    }
    public Json2RecommendShopClick(String json)
    {
        this.json = json;
    }

    public Json2RecommendShopClickBean getObj()
    {

        Json2RecommendShopClickBean clickBean = new Json2RecommendShopClickBean();


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

            clickBean.setIsJson(isJson);
            clickBean.setIsReturnStr(isReturnStr);
            clickBean.setReturnCode(returnCode);
            clickBean.setReturneMsg(returneMsg);
            clickBean.setMessage(message);

        } catch (JSONException e) {
            e.printStackTrace();
            return null;//返回空  服务器错误
        }
        return clickBean;
    }
}
