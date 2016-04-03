package com.car.yubangapk.json.formatJson;

import com.car.yubangapk.json.bean.Json2CarBrandBean;
import com.car.yubangapk.json.bean.Json2CarCompanyBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Json2CarBrand:汽车品牌名字的格式转换成Json2CarBrand类
 *
 * @author andy
 * @version 1.0.6
 * @created 2016-04-03
 */
public class Json2CarBrand {



    String json;

    public Json2CarBrand()
    {

    }

//    public static String getString(jsonobject , key0){
//        try{
//
//        }catch ()
//    }

    public Json2CarBrand(String json)
    {
        this.json = json;
    }


    /**
     *
     * @return List<BannerAd>
     */
    public List<Json2CarBrandBean> getBrandList() {

        List<Json2CarBrandBean> carBrandBeans = new ArrayList<Json2CarBrandBean>();

        JSONObject total = null;

        try {
            total = new JSONObject(json);
            JSONArray rows = total.getJSONArray("rows");
            int size = rows.length();
            String pathCode;
            String id;
            String carBrandName;

            for (int i = 0; i < size; i++)
            {
                JSONObject row = (JSONObject) rows.get(i);

                carBrandName = row.getString("carBrandName");
                id = row.getString("id");
                pathCode = row.getString("pathCode");



                Json2CarBrandBean json2CarBrandBean = new Json2CarBrandBean();
                json2CarBrandBean.setId(id);
                json2CarBrandBean.setCarBrandName(carBrandName);
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
