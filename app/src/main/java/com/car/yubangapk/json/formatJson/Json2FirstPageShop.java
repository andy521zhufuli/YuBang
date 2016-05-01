package com.car.yubangapk.json.formatJson;


import android.provider.Settings;

import com.car.yubangapk.json.JSONUtils;
import com.car.yubangapk.json.bean.Json2CarYearBean;
import com.car.yubangapk.json.bean.Json2FirstPageShopBean;
import com.car.yubangapk.json.bean.Json2FirstPageTabsBean;
import com.car.yubangapk.json.bean.SysConfig;
import com.car.yubangapk.utils.L;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Json2FirstPageShop:首页店铺
 *
 * @author andy
 * @version 1.0.6
 * @created 2016-04-05
 */
public class Json2FirstPageShop {

    String json;

    public Json2FirstPageShop()
    {

    }

//    public static String getString(jsonobject , key0){
//        try{
//
//        }catch ()
//    }

    public Json2FirstPageShop(String json)
    {
        this.json = json;
    }

    public List<Json2FirstPageShopBean> getFirstPageShop() {

        long startTime = System.currentTimeMillis();


        List<Json2FirstPageShopBean> json2FirstPageShopBeanList = new ArrayList<Json2FirstPageShopBean>();

        JSONObject total = null;


        try {
            total = new JSONObject(json);
            JSONArray rows = total.getJSONArray("rows");
            int size = rows.length();
            if (size == 0)
            {
                Json2FirstPageShopBean pageShopBean1 = new Json2FirstPageShopBean();
                pageShopBean1.setId("0");
                json2FirstPageShopBeanList.add(pageShopBean1);
                return json2FirstPageShopBeanList;
            }
            String id;
            String shopAddress;
            String phoneNum;
            String shopPhoto;
            String pathCode;
            String shopName;
            String company;
            double shopLatitude;
            double shopLongitude;
            String star;
            double distance;
            int order;
            long orderNum;
            for (int i = 0; i < size; i++)
            {
                Json2FirstPageShopBean pageShopBean = new Json2FirstPageShopBean();
                JSONObject row = (JSONObject) rows.get(i);


                //容灾  当有任何一个字段没有的时候   我就忽略这个店铺

                id = row.getString("id");
                shopAddress     = row.getString("shopAddress");
                phoneNum        = row.getString("phoneNum");
                shopPhoto       = row.getString("shopPhoto");
                pathCode        = row.getString("pathCode");
                shopName        = row.getString("shopName");
                company         = row.getString("company");
                shopLatitude    = row.getDouble("shopLatitude");
                shopLongitude   = row.getDouble("shopLongitude");
                star            = row.getString("star");
                order           = row.getInt("order");
                distance        = row.getDouble("distance");
                orderNum        = row.getLong("orderNum");


//                shopAddress     = JSONUtils.getString(row, "shopAddress", "");
//                phoneNum        = JSONUtils.getString(row, "phoneNum", "");
//                shopPhoto       = JSONUtils.getString(row,"shopPhoto", "");
//                pathCode        = JSONUtils.getString(row,"pathCode", "");
//                shopName        = JSONUtils.getString(row, "shopName", "");
//                company         = JSONUtils.getString(row, "company", "");
//                shopLatitude    = JSONUtils.getDouble(row, "shopLatitude", 0);
//                shopLongitude   = JSONUtils.getDouble(row, "shopLongitude", 0);
//                star            = JSONUtils.getString(row, "star", "0");
//                order           = JSONUtils.getInt(row, "order", 0);
//                distance        = JSONUtils.getDouble(row, "distance", 0);
//                orderNum        = JSONUtils.getLong(row, "orderNum", 0);


                pageShopBean.setId(id);
                pageShopBean.setShopAddress(shopAddress);
                pageShopBean.setPhoneNum(phoneNum);
                pageShopBean.setShopPhoto(shopPhoto);
                pageShopBean.setPathCode(pathCode);
                pageShopBean.setShopName(shopName);
                pageShopBean.setCompany(company);
                pageShopBean.setShopLatitude(shopLatitude);
                pageShopBean.setShopLongitude(shopLongitude);
                pageShopBean.setStar(star);
                pageShopBean.setOrder(order);
                pageShopBean.setDistance(distance);
                pageShopBean.setOrderNum(orderNum);
                json2FirstPageShopBeanList.add(pageShopBean);
            }
            long stopTime = System.currentTimeMillis();
            L.d("json解析时间差 = " + (stopTime - startTime));
        } catch (JSONException e) {

            e.printStackTrace();
            return null;
        }

        return json2FirstPageShopBeanList;



    }
}
