package com.car.yubangapk.json.formatJson;


import com.car.yubangapk.json.bean.Json2CarSeriesBean;
import com.car.yubangapk.json.bean.Json2LoginBean;
import com.car.yubangapk.json.bean.Json2ShopShowBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Json2ShopShow:门店展示的数据格式化
 *
 * @author andy
 * @version 1.0.6
 * @created 2016-04-06
 */
public class Json2ShopShow {

    String json;

    public Json2ShopShow() {

    }

    public Json2ShopShow(String json) {
        this.json = json;
    }

    /**
     *
     *
     * @return
     */
    public List<Json2ShopShowBean> getShopShowData() {
        List<Json2ShopShowBean> json2ShopShowBeanList = new ArrayList<>();

        JSONObject total = null;
        try {
            total = new JSONObject(json);
            JSONArray rows = total.getJSONArray("rows");
            int size = rows.length();
            String id;
            String skipType;
            String serviceName;


            if (size == 0)
            {
                Json2ShopShowBean json2ShopShowBean = new Json2ShopShowBean();
                json2ShopShowBean.setHasData(false);
                json2ShopShowBeanList.add(json2ShopShowBean);
                return json2ShopShowBeanList;
            }

            for (int i = 0; i < size; i++)
            {
                JSONObject row = (JSONObject) rows.get(i);

                skipType = row.getString("skipType");
                id = row.getString("id");
                serviceName = row.getString("serviceName");



                Json2ShopShowBean json2CarBrandBean = new Json2ShopShowBean();
                json2CarBrandBean.setId(id);
                json2CarBrandBean.setServiceName(serviceName);
                json2CarBrandBean.setSkipType(skipType);

                json2CarBrandBean.setHasData(true);
                json2ShopShowBeanList.add(json2CarBrandBean);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;//返回空  服务器错误
        }
        return json2ShopShowBeanList;

    }
}
