package com.car.yubangapk.json.formatJson;

import com.car.yubangapk.json.bean.Json2ProductPackageIdBean;
import com.car.yubangapk.json.bean.Json2ShoppingmallBottomPicsBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Json2ShoppingmallBottomPics:商城,分类8图标下面的每一个栏目的几个图片信息
 *
 * @author andy
 * @version 1.0.6
 * @created 2016-04-08
 */
public class Json2ShoppingmallBottomPics {

    String json;

    public Json2ShoppingmallBottomPics()
    {

    }

//    public static String getString(jsonobject , key0){
//        try{
//
//        }catch ()
//    }

    public Json2ShoppingmallBottomPics(String json)
    {
        this.json = json;
    }

    public List<Json2ShoppingmallBottomPicsBean> getShoppingmallBottomPics() {

        List<Json2ShoppingmallBottomPicsBean> json2ProductIdBeanList = new ArrayList<Json2ShoppingmallBottomPicsBean>();

        JSONObject total = null;
        try {
            total = new JSONObject(json);
            JSONArray rows = total.getJSONArray("rows");
            int size = rows.length();
            String id;
            String logicalService;
            String pathCode;
            String photoName;
            String serviceCode;
            int repairServiceSort;
            String serviceName;

            if (size == 0)
            {
                Json2ShoppingmallBottomPicsBean pageTabsBean = new Json2ShoppingmallBottomPicsBean();
                pageTabsBean.setHasData(false);
                json2ProductIdBeanList.add(pageTabsBean);
                return json2ProductIdBeanList;
            }

            for (int i= 0; i < size; i++)
            {
                Json2ShoppingmallBottomPicsBean pageTabsBean = new Json2ShoppingmallBottomPicsBean();
                JSONObject object = rows.getJSONObject(i);
                id = object.getString("id");
                logicalService = object.getString("logicalService");
                pathCode = object.getString("pathCode");

                photoName = object.getString("photoName");
                serviceCode = object.getString("serviceCode");
                repairServiceSort = object.getInt("repairServiceSort");
                serviceName = object.getString("serviceName");

                pageTabsBean.setId(id);
                pageTabsBean.setLogicalService(logicalService);
                pageTabsBean.setPathCode(pathCode);
                pageTabsBean.setPhotoName(photoName);
                pageTabsBean.setServiceCode(serviceCode);
                pageTabsBean.setRepairServiceSort(repairServiceSort);
                pageTabsBean.setServiceName(serviceName);
                pageTabsBean.setHasData(true);

                json2ProductIdBeanList.add(pageTabsBean);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            return null;//异常 就一定是服务器给我的参数不对
        }
        return json2ProductIdBeanList;

    }
}
