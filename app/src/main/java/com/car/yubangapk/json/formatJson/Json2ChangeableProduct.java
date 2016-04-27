package com.car.yubangapk.json.formatJson;


import com.car.yubangapk.json.bean.Json2ChangeableProductBean;
import com.car.yubangapk.json.bean.Json2ProductPackageBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Json2ChangeableProduct:可更换信息
 *
 * @author andy
 * @version 1.0.6
 * @created 2016-04-10
 */
public class Json2ChangeableProduct {





    String json;

    public Json2ChangeableProduct()
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

    public Json2ChangeableProduct(String json)
    {
        this.json = json;
    }


    /**
     *
     * @return List<BannerAd>
     */
    public List<Json2ChangeableProductBean> getChangeableProduct() {

        List<Json2ChangeableProductBean> carBrandBeans = new ArrayList<Json2ChangeableProductBean>();

        JSONObject total = null;

        try {
            total = new JSONObject(json);
            JSONArray rows = total.getJSONArray("rows");
            int size = rows.length();
            String categoryName;
            double retailPrice;
            String pathCode;
            String productName;

            String photoName;
            String productShow;
            String category;
            String productCode;

            if (size == 0)
            {
                Json2ChangeableProductBean json2ProductPackageBean = new Json2ChangeableProductBean();
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

                try
                {
                    photoName = row.getString("photoName");
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                    photoName = "0";
                }

                productShow = row.getString("productShow");
                category = row.getString("category");
                productCode = row.getString("productCode");

                Json2ChangeableProductBean json2ProductPackageBean = new Json2ChangeableProductBean();
                json2ProductPackageBean.setCategoryName(categoryName);
                json2ProductPackageBean.setRetailPrice(retailPrice);
                json2ProductPackageBean.setPathCode(pathCode);
                json2ProductPackageBean.setProductName(productName);

                json2ProductPackageBean.setPhotoName(photoName);
                json2ProductPackageBean.setProductShow(productShow);
                json2ProductPackageBean.setCategory(category);
                json2ProductPackageBean.setProductCode(productCode);

                json2ProductPackageBean.setHasData(true);

                carBrandBeans.add(json2ProductPackageBean);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;//返回空  服务器错误
        }
        return carBrandBeans;
    }
}
