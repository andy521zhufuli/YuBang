package com.car.yubangapk.json.formatJson;

import com.car.yubangapk.json.bean.Json2CarBrandBean;
import com.car.yubangapk.json.bean.Json2CarSeriesBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andy on 16/4/4.
 */
public class Json2CarSeries {


    String json;

    public Json2CarSeries()
    {

    }

//    public static String getString(jsonobject , key0){
//        try{
//
//        }catch ()
//    }

    public Json2CarSeries(String json)
    {
        this.json = json;
    }


    /**
     *
     * @return List<BannerAd>
     */
    public List<Json2CarSeriesBean> getSeriesList() {

        List<Json2CarSeriesBean> carBrandBeans = new ArrayList<Json2CarSeriesBean>();

        JSONObject total = null;

        try {
            total = new JSONObject(json);
            JSONArray rows = total.getJSONArray("rows");
            int size = rows.length();
            String pathCode;
            String id;
            String carSeriesName;

            for (int i = 0; i < size; i++)
            {
                JSONObject row = (JSONObject) rows.get(i);

                carSeriesName = row.getString("carSeriesName");
                id = row.getString("id");
                pathCode = row.getString("pathCode");



                Json2CarSeriesBean json2CarBrandBean = new Json2CarSeriesBean();
                json2CarBrandBean.setId(id);
                json2CarBrandBean.setCarSeriesName(carSeriesName);
                json2CarBrandBean.setPathCode(pathCode);

                carBrandBeans.add(json2CarBrandBean);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;//返回空  服务器错误
        }
        return carBrandBeans;
    }
}
