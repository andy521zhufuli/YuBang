package com.car.yubangapk.json.formatJson;

import com.car.yubangapk.json.bean.Json2CarYearBean;
import com.car.yubangapk.json.bean.Json2FirstPageTabsBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Json2FirstPageTabs:首页顶部tabs
 *
 * @author andy
 * @version 1.0.6
 * @created 2016-04-05
 */
public class Json2FirstPageTabs {



    //    [
    //        {"id":"1df3cd46-f2e5-40f1-b408-266a7d59a8b6","pathCode":"1","sort":"1","skipType":"2","photoName":"bab3355b-ac05-42c9-b669-cab7c3d836aa.png","serviceName":"保养维护"},
    //        {"id":"841ffbca-8063-4561-b7f3-9193f43ff731","pathCode":"1","sort":"2","skipType":"2","photoName":"74b87f9c-c54c-42f3-9a9b-b3ef0005acf9.png","serviceName":"电子电路"},
    //        {"id":"76e787e5-123c-4bde-8abd-e758f268f706","pathCode":"1","sort":"3","skipType":"2","photoName":"eaec2fcf-1ece-4e30-8a41-065b2940b785.png","serviceName":"发动机件"},
    //        {"id":"91f3b4a2-537e-42c7-a9f5-bed66ae5ef46","pathCode":"1","sort":"4","skipType":"2","photoName":"c1f700aa-695c-4383-aa77-2374a63e06f8.png","serviceName":"打黄油"},
    //        {"id":"4792a819-67ac-47e1-ad42-ab5f6ee3f854","pathCode":"1","sort":"5","skipType":"2","photoName":"299d4112-d903-471f-87b8-2108ce35238a.png","serviceName":"底盘配件"},
    //        {"id":"f10b454f-0ae0-4747-a5ca-57f6a12a979a","pathCode":"1","sort":"6","skipType":"2","photoName":"f8c7b5a5-36a6-48cd-bec5-a36fef2f0cca.png","serviceName":"车架配件"},
    //        {"id":"150091b7-0e11-489c-ac34-bdaa63711bcd","pathCode":"1","sort":"7","skipType":"2","photoName":"625aaef7-153c-4dee-83e7-a6b66a92a433.png","serviceName":"拖架配件"}
    //    ]


    String json;

    public Json2FirstPageTabs()
    {

    }

//    public static String getString(jsonobject , key0){
//        try{
//
//        }catch ()
//    }

    public Json2FirstPageTabs(String json)
    {
        this.json = json;
    }

    public List<Json2FirstPageTabsBean> getFirstPageTabs() {

        List<Json2FirstPageTabsBean> json2FirstPageTabsBeanList = new ArrayList<Json2FirstPageTabsBean>();

        JSONArray total = null;
        try {
            total = new JSONArray(json);
            String id;
            String pathCode;
            String sort;
            String skipType;
            String photoName;
            String serviceName;

            int size = total.length();

            //里面没有数据   就返回一个没有数据的
            if (size == 0)
            {
                Json2FirstPageTabsBean pageTabsBean = new Json2FirstPageTabsBean();
                pageTabsBean.setHasData(false);
                json2FirstPageTabsBeanList.add(pageTabsBean);
                return json2FirstPageTabsBeanList;
            }
            for (int i= 0; i < size; i++)
            {
                Json2FirstPageTabsBean pageTabsBean = new Json2FirstPageTabsBean();
                JSONObject object = total.getJSONObject(i);
                id = object.getString("id");
                pathCode = object.getString("pathCode");
                sort = object.getString("sort");
                skipType = object.getString("skipType");
                photoName = object.getString("photoName");
                serviceName = object.getString("serviceName");


                pageTabsBean.setId(id);
                pageTabsBean.setPathCode(pathCode);
                pageTabsBean.setSort(sort);
                pageTabsBean.setSkipType(skipType);
                pageTabsBean.setPhotoName(photoName);
                pageTabsBean.setServiceName(serviceName);
                pageTabsBean.setHasData(true);
                char[] name = serviceName.toCharArray();
                if (name.length < 2)
                {
                    //name的长度小于2
                    return null;
                }
                String usefullname = "";
                for (int j = 0; i < 2; i++)
                {
                    usefullname += name[i];
                }
                pageTabsBean.setUsefullServiceName(usefullname);
                json2FirstPageTabsBeanList.add(pageTabsBean);
            }




        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return json2FirstPageTabsBeanList;

    }




}
