package com.car.yubangapk.json.formatJson;

import com.car.yubangapk.json.bean.Json2LoginBean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Json2Login:返回验证码的json格式转换成login类
 *
 * @author andy
 * @version 1.0.6
 * @created 2016-04-03
 */
public class Json2Login {


    String json;

    public Json2Login()
    {

    }

    public Json2Login(String json)
    {
        this.json = json;
    }

    /**
     * 验证验证码  验证拿回来的验证码  转成类
     * @return
     */
    public Json2LoginBean getLoginObj()
    {
        Json2LoginBean loginBean = new Json2LoginBean();

        JSONObject total = null;

        try {
            total = new JSONObject(json);

            boolean isJson;
            boolean isReturnStr;
            int returnCode;
            String returneMsg;
            String message;
            String userid;

            String carType;
            String name;
            String status;

            isJson = total.getBoolean("isJson");
            isReturnStr = total.getBoolean("isReturnStr");
            returnCode = total.getInt("returnCode");
            returneMsg = total.getString("returneMsg");
            message = total.getString("message");
            userid = total.getString("userid");
            carType = total.getString("carType");
            name = total.getString("name");
            status = total.getString("status");


            loginBean.setIsJson(isJson);
            loginBean.setIsReturnStr(isReturnStr);
            loginBean.setReturnCode(returnCode);
            loginBean.setReturneMsg(returneMsg);
            loginBean.setMessage(message);
            loginBean.setUserid(userid);
            loginBean.setCarType(carType);
            loginBean.setName(name);
            loginBean.setStatus(status);

        } catch (JSONException e) {
            e.printStackTrace();
            return null;//返回空  服务器错误
        }
        return loginBean;
    }


}
