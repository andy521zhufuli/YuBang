package com.car.yubangapk.json.formatJson;

import com.car.yubangapk.json.bean.Json2CarCapacityBean;
import com.car.yubangapk.json.bean.Json2CarSeriesBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Json2CarCapacity:汽车排量的格式转换成Json2CarCapacity类
 *
 * @author andy
 * @version 1.0.6
 * @created 2016-04-03
 */
public class Json2CarCapacity
{

    String json;

    public Json2CarCapacity()
    {

    }

//    public static String getString(jsonobject , key0){
//        try{
//
//        }catch ()
//    }

    public Json2CarCapacity(String json)
    {
        this.json = json;
    }


    /**
     *
     * @return List<BannerAd>
     */
    public List<Json2CarCapacityBean> getCarcapacityList() {

        List<Json2CarCapacityBean> json2CarCapacityList = new ArrayList<Json2CarCapacityBean>();

        JSONObject total = null;

        try {
            total = new JSONObject(json);
            JSONArray rows = total.getJSONArray("rows");
            int size = rows.length();

            String id;
            String capacityName;

            for (int i = 0; i < size; i++)
            {
                JSONObject row = (JSONObject) rows.get(i);

                capacityName = row.getString("capacityName");
                id = row.getString("id");




                Json2CarCapacityBean json2CarBrandBean = new Json2CarCapacityBean();
                json2CarBrandBean.setId(id);
                json2CarBrandBean.setCapacityName(capacityName);


                json2CarCapacityList.add(json2CarBrandBean);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;//返回空  服务器错误
        }
        return json2CarCapacityList;
    }
}
