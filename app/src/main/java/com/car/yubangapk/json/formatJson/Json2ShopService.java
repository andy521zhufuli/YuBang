package com.car.yubangapk.json.formatJson;


import com.car.yubangapk.json.bean.Json2ShopServiceBean;

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
public class Json2ShopService {

    String json;

    public Json2ShopService() {

    }

    public Json2ShopService(String json) {
        this.json = json;
    }

    /**
     *
     *
     * @return
     */
    public List<Json2ShopServiceBean> getShopShowData() {
        List<Json2ShopServiceBean> json2ShopServiceBeanList = new ArrayList<>();

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
                Json2ShopServiceBean json2ShopServiceBean = new Json2ShopServiceBean();
                json2ShopServiceBean.setHasData(false);
                json2ShopServiceBeanList.add(json2ShopServiceBean);
                return json2ShopServiceBeanList;
            }

            for (int i = 0; i < size; i++)
            {
                JSONObject row = (JSONObject) rows.get(i);

                skipType = row.getString("skipType");
                id = row.getString("id");
                serviceName = row.getString("serviceName");



                Json2ShopServiceBean json2CarBrandBean = new Json2ShopServiceBean();
                json2CarBrandBean.setId(id);
                json2CarBrandBean.setServiceName(serviceName);
                json2CarBrandBean.setSkipType(skipType);

                json2CarBrandBean.setHasData(true);
                json2ShopServiceBeanList.add(json2CarBrandBean);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;//返回空  服务器错误
        }
        return json2ShopServiceBeanList;

    }
}
