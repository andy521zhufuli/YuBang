package com.car.yubangapk.json.formatJson;

import com.car.yubangapk.json.Base;
import com.car.yubangapk.json.JSONUtils;
import com.car.yubangapk.json.bean.Json2HorsePowerBean;
import com.car.yubangapk.json.bean.Json2MotivationBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andy on 16/6/13.
 *
 * 解析马力的json信息
 *
 */
public class Json2Motivation extends Base
{
    String json;

    public Json2Motivation(String json)
    {
        this.json = json;
    }

    public List<Json2MotivationBean> getMotivation() {

        List<Json2MotivationBean> horsepowerBeanList = new ArrayList<>();


        JSONArray total = null;
        try {
            total = new JSONArray(json);

            int length = total.length();
            if (length == 0)
            {
                Json2MotivationBean cityBean  = new Json2MotivationBean();
                cityBean.setHasData(false);
                horsepowerBeanList.add(cityBean);
                return horsepowerBeanList;
            }
            for (int i = 0; i < length; i++)
            {
                JSONObject item = (JSONObject) total.get(i);
                Json2MotivationBean cityBean  = new Json2MotivationBean();


                cityBean.setMotivation(JSONUtils.getString(item, "motivation", ""));
                cityBean.setHasData(true);
                horsepowerBeanList.add(cityBean);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;//异常 就一定是服务器给我的参数不对
        }
        return horsepowerBeanList;

    }
}
