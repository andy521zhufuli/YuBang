package com.car.yubangapk.json.formatJson;

import com.car.yubangapk.json.bean.Json2MyOrderBean;
import com.car.yubangapk.json.bean.Json2MyUserInfoBean;
import com.car.yubangapk.json.bean.MyOrderBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andy on 16/4/25.
 */
public class Json2MyUserInfo
{


    private String json;

    public Json2MyUserInfo(String json)

    {
        this.json = json;
    }

    public Json2MyUserInfoBean getUserInfo()
    {
        Json2MyUserInfoBean userInfoBean = new Json2MyUserInfoBean();


        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(json);

            String phoneNum;
            String userName;
            String car;
            String photoName;
            String pathCode;


            phoneNum = jsonObject.getString("phoneNum");
            userName = jsonObject.getString("userName");
            car = jsonObject.getString("car");
            photoName = jsonObject.getString("photoName");
            pathCode = jsonObject.getString("pathCode");

            userInfoBean.setLogined(true);
            userInfoBean.setPhoneNum(phoneNum);
            userInfoBean.setUserName(userName);
            userInfoBean.setCar(car);
            userInfoBean.setPhotoName(photoName);
            userInfoBean.setPathCode(pathCode);
            userInfoBean.setHasData(true);


        } catch (JSONException e) {
            e.printStackTrace();
            try {
                //{"isJson":true,"isReturnStr":false,"returnCode":100,"returneMsg":"SUCCESS","message":"用户未登录"}
                JSONObject total1;
                total1 = new JSONObject(json);
                boolean isJson;
                boolean isReturnStr;
                int returnCode;
                String returneMsg;
                String message;



                isJson = total1.getBoolean("isJson");
                isReturnStr = total1.getBoolean("isReturnStr");
                returnCode = total1.getInt("returnCode");
                returneMsg = total1.getString("returneMsg");
                message = total1.getString("message");

                userInfoBean.setReturnCode(returnCode);
                userInfoBean.setHasData(false);
                if (returnCode == 100 )
                {
                    userInfoBean.setLogined(false);
                }
                else
                {

                }


                return  userInfoBean;

            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            return null;//返回空  服务器错误
        }



        return userInfoBean;
    }


}
