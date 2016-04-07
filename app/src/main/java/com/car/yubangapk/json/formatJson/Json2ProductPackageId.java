package com.car.yubangapk.json.formatJson;

import com.car.yubangapk.json.bean.Json2ProductPackageIdBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andy on 16/4/7.
 */
public class Json2ProductPackageId {

    String json;

    public Json2ProductPackageId()
    {

    }

//    public static String getString(jsonobject , key0){
//        try{
//
//        }catch ()
//    }

    public Json2ProductPackageId(String json)
    {
        this.json = json;
    }

    public List<Json2ProductPackageIdBean> getProductIds() {

        List<Json2ProductPackageIdBean> json2ProductIdBeanList = new ArrayList<Json2ProductPackageIdBean>();

        JSONObject total = null;
        try {
            total = new JSONObject(json);
            JSONArray rows = total.getJSONArray("rows");
            int size = rows.length();
            String id;
            String pathCode;
            String sort;
            String skipType;
            String photoName;
            String serviceName;
            String packageName;
            String packageCode;

            if (size == 0)
            {
                Json2ProductPackageIdBean pageTabsBean = new Json2ProductPackageIdBean();
                pageTabsBean.setHasData(false);
                json2ProductIdBeanList.add(pageTabsBean);
                return json2ProductIdBeanList;
            }

            for (int i= 0; i < size; i++)
            {
                Json2ProductPackageIdBean pageTabsBean = new Json2ProductPackageIdBean();
                JSONObject object = rows.getJSONObject(i);
                id = object.getString("id");
                packageName = object.getString("packageName");
                packageCode = object.getString("packageCode");

                pageTabsBean.setId(id);

                pageTabsBean.setPackageCode(packageCode);
                pageTabsBean.setPackageName(packageName);
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
