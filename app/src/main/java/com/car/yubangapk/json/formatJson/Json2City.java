package com.car.yubangapk.json.formatJson;

import com.car.yubangapk.json.JSONUtils;
import com.car.yubangapk.json.bean.Json2CityBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andy on 16/6/4.
 */
public class Json2City
{
    String json;

    public Json2City(String json)
    {
        this.json = json;
    }

    public List<Json2CityBean> getCity() {

        List<Json2CityBean> cityBeanList = new ArrayList<>();


        JSONArray total = null;
        try {
            total = new JSONArray(json);

            int length = total.length();
            if (length == 0)
            {
                Json2CityBean cityBean  = new Json2CityBean();
                cityBean.setHasData(false);
                cityBeanList.add(cityBean);
                return cityBeanList;
            }
            for (int i = 0; i < length; i++)
            {
                JSONObject item = (JSONObject) total.get(i);
                Json2CityBean cityBean  = new Json2CityBean();

                cityBean.setPARENT_ID(JSONUtils.getInt(item, "REGION_ID", 0));
                cityBean.setREGION_ID(JSONUtils.getInt(item, "PARENT_ID", 0));
                cityBean.setREGION_NAME(JSONUtils.getString(item, "REGION_NAME", ""));
                cityBean.setHasData(true);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;//异常 就一定是服务器给我的参数不对
        }
        return cityBeanList;

    }
}
