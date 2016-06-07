package com.car.yubangapk.json.formatJson;

import com.car.yubangapk.configs.Configs;
import com.car.yubangapk.json.JSONUtils;
import com.car.yubangapk.json.bean.Json2ProductPackageIdBean;
import com.car.yubangapk.json.bean.Json2ProvinceBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andy on 16/6/4.
 */
public class Json2Province
{
    String json;

    public Json2Province(String json)
    {
        this.json = json;
    }

    public List<Json2ProvinceBean> getProvince() {

        List<Json2ProvinceBean> provinceBeanList = new ArrayList<>();


        JSONArray total = null;
        try {
            total = new JSONArray(json);

            int length = total.length();
            if (length == 0)
            {
                Json2ProvinceBean provinceBean  = new Json2ProvinceBean();
                provinceBean.setHasData(false);
                provinceBeanList.add(provinceBean);
                return provinceBeanList;
            }
            for (int i = 0; i < length; i++)
            {
                JSONObject item = (JSONObject) total.get(i);
                Json2ProvinceBean provinceBean  = new Json2ProvinceBean();

                provinceBean.setPARENT_ID(JSONUtils.getInt(item, "PARENT_ID", 0));
                provinceBean.setREGION_ID(JSONUtils.getInt(item, "REGION_ID", 0));
                provinceBean.setREGION_NAME(JSONUtils.getString(item, "REGION_NAME", ""));
                provinceBean.setHasData(true);
                provinceBeanList.add(provinceBean);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;//异常 就一定是服务器给我的参数不对
        }
        return provinceBeanList;

    }
}
