package com.car.yubangapk.json.formatJson;

import com.car.yubangapk.json.bean.Json2CarSeriesBean;
import com.car.yubangapk.json.bean.Json2CarYearBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Json2CarYear:汽车生产年份的格式转换成Json2CarYear类
 *
 * @author andy
 * @version 1.0.6
 * @created 2016-04-03
 */
public class Json2CarYear
{
    String json;

    public Json2CarYear()
    {

    }

//    public static String getString(jsonobject , key0){
//        try{
//
//        }catch ()
//    }

    public Json2CarYear(String json)
    {
        this.json = json;
    }


    /**
     *
     * @return List<BannerAd>
     */
    public List<Json2CarYearBean> getCarYearList() {

        List<Json2CarYearBean> json2CarYearBeanList = new ArrayList<Json2CarYearBean>();

        JSONObject total = null;

        try {
            total = new JSONObject(json);
            JSONArray rows = total.getJSONArray("rows");
            int size = rows.length();

            String id;
            String carProduceYear;

            for (int i = 0; i < size; i++)
            {
                JSONObject row = (JSONObject) rows.get(i);

                carProduceYear = row.getString("carProduceYear");
                id = row.getString("id");




                Json2CarYearBean json2CarBrandBean = new Json2CarYearBean();
                json2CarBrandBean.setId(id);
                json2CarBrandBean.setCarProduceYear(carProduceYear);


                json2CarYearBeanList.add(json2CarBrandBean);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;//返回空  服务器错误
        }
        return json2CarYearBeanList;
    }
}
