package com.car.yubangapk.json.formatJson;

import com.car.yubangapk.json.bean.Json2MyOrderBean;
import com.car.yubangapk.json.bean.Json2MyRecommendPartnersBean;
import com.car.yubangapk.json.bean.MyOrderBean;
import com.car.yubangapk.json.bean.MyRecommendPartnersBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andy on 16/4/26.
 */
public class Json2MyRecommendPartners
{


    //{"total":53,"rows":[{id ,shopName,shopAddress,shopPhoto,pathCode,phoneNum}]}无shopPhoto显示yubang图标，无其他字段丢弃
    private String json;

    public  Json2MyRecommendPartners(String  json)

    {
        this.json = json;
    }

    public Json2MyRecommendPartnersBean getMyRecommendPartners()
    {
        Json2MyRecommendPartnersBean json2MyRecommendPartnersBean = new Json2MyRecommendPartnersBean();


        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(json);
            int total = jsonObject.getInt("total");
            if (total == 0)
            {
                json2MyRecommendPartnersBean.setTotal(total);
                json2MyRecommendPartnersBean.setHasData(false);
                return json2MyRecommendPartnersBean;
            }
            JSONArray rows = jsonObject.getJSONArray("rows");
            int size = rows.length();
            String id;
            String shopName;
            String shopAddress;
            String shopPhoto;
            String pathCode;
            String phoneNum;


            List<MyRecommendPartnersBean> partnersBeans = new ArrayList<>();
            for (int i = 0; i < size; i++)
            {
                try
                {
                    JSONObject row = (JSONObject) rows.get(i);
                    id = row.getString("id");
                    shopName = row.getString("shopName");
                    shopAddress = row.getString("shopAddress");
                    shopPhoto = row.getString("shopPhoto");

                    pathCode = row.getString("pathCode");
                    phoneNum = row.getString("phoneNum");


                    MyRecommendPartnersBean orderBean = new MyRecommendPartnersBean();
                    orderBean.setId(id);
                    orderBean.setShopName(shopName);
                    orderBean.setShopAddress(shopAddress);
                    orderBean.setShopPhoto(shopPhoto);
                    orderBean.setPathCode(pathCode);
                    orderBean.setPhoneNum(phoneNum);

                    orderBean.setHasData(true);

                    partnersBeans.add(orderBean);
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                    continue;
                }
            }
            json2MyRecommendPartnersBean.setHasData(true);
            json2MyRecommendPartnersBean.setRows(partnersBeans);
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

                json2MyRecommendPartnersBean.setReturnCode(returnCode);
                return  json2MyRecommendPartnersBean;

            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            return null;//返回空  服务器错误
        }

        return json2MyRecommendPartnersBean;
    }
}
