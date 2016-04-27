package com.car.yubangapk.json.formatJson;


import com.car.yubangapk.json.bean.Json2CarBrandBean;
import com.car.yubangapk.json.bean.Json2ProductPackageBean;
import com.car.yubangapk.utils.L;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Json2ProductPackage:产皮包信息
 *
 * @author andy
 * @version 1.0.6
 * @created 2016-04-07
 */
/**
 * Created by andy on 16/4/7.
 */
public class Json2ProductPackage {





    String json;

    public Json2ProductPackage()
    {

    }

    private Object getString(String jsonobject , Object object)
    {

        if (object instanceof JSONArray)
        {

        }

//        try
//        {
//
//
//        }
//        catch (JSONException e)
//        {
//
//        }
        return null;
    }

    public Json2ProductPackage(String json)
    {
        this.json = json;
    }


    /**
     *
     * @return List<BannerAd>
     */
    public List<Json2ProductPackageBean> getProductPackage() {

        List<Json2ProductPackageBean> carBrandBeans = new ArrayList<Json2ProductPackageBean>();

        JSONObject total = null;

        try {
            total = new JSONObject(json);
            JSONArray rows = total.getJSONArray("rows");
            int size = rows.length();
            String categoryName;
            double retailPrice;
            String pathCode;
            String productName;
            int productAmount;
            String photoName;
            String productShow;
            String category;
            String productCode;
            String id;

            if (size == 0)
            {
                Json2ProductPackageBean json2ProductPackageBean = new Json2ProductPackageBean();
                json2ProductPackageBean.setHasData(false);
                carBrandBeans.add(json2ProductPackageBean);
                return carBrandBeans;
            }

            for (int i = 0; i < size; i++)
            {

                JSONObject row =  rows.getJSONObject(i);
                categoryName = row.getString("categoryName");
                retailPrice = row.getDouble("retailPrice");
                pathCode = row.getString("pathCode");
                productName = row.getString("productName");
                productAmount = row.getInt("productAmount");
                try
                {
                    photoName = row.getString("photoName");

                }
                catch (JSONException e)
                {
                    photoName = "0";
                }
                productShow = row.getString("productShow");
                category = row.getString("category");
                productCode = row.getString("productCode");
                id = row.getString("id");

                Json2ProductPackageBean json2ProductPackageBean = new Json2ProductPackageBean();
                json2ProductPackageBean.setCategoryName(categoryName);
                json2ProductPackageBean.setRetailPrice(retailPrice);
                json2ProductPackageBean.setPathCode(pathCode);
                json2ProductPackageBean.setProductName(productName);
                json2ProductPackageBean.setProductAmount(productAmount);
                json2ProductPackageBean.setPhotoName(photoName);
                json2ProductPackageBean.setProductShow(productShow);
                json2ProductPackageBean.setCategory(category);
                json2ProductPackageBean.setProductCode(productCode);
                json2ProductPackageBean.setId(id);
                json2ProductPackageBean.setHasData(true);

                carBrandBeans.add(json2ProductPackageBean);





            }
        } catch (JSONException e) {
            e.printStackTrace();
            L.d("return null");
            return null;//返回空  服务器错误
        }
        L.d("return not null");
        return carBrandBeans;
    }
}
