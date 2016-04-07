package com.car.yubangapk.json.formatJson;

import com.car.yubangapk.json.bean.BannerAd;
import com.car.yubangapk.json.bean.Json2CarCompanyBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Json2CarCompany:汽车公司名字的格式转换成Json2CarCompany类
 *
 * @author andy
 * @version 1.0.6
 * @created 2016-04-03
 */
public class Json2CarCompany {


    String json;

    public Json2CarCompany()
    {

    }

    public Json2CarCompany(String json)
    {
        this.json = json;
    }


    /**
     * 返回商城首页bannner的广告链接
     * @return List<BannerAd>
     */
    public List<Json2CarCompanyBean> getCarCompanyList() {

        List<Json2CarCompanyBean> carCompanyBeans = new ArrayList<Json2CarCompanyBean>();

        JSONObject total = null;

        try {
            total = new JSONObject(json);
            JSONArray rows = total.getJSONArray("rows");
            int size = rows.length();
            String companyName;
            String id;

            for (int i = 0; i < size; i++)
            {
                JSONObject row = (JSONObject) rows.get(i);
                companyName = row.getString("companyName");
                id = row.getString("id");


                Json2CarCompanyBean json2CarCompanyBean = new Json2CarCompanyBean();
                json2CarCompanyBean.setId(id);
                json2CarCompanyBean.setCompanyName(companyName);
                carCompanyBeans.add(json2CarCompanyBean);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;//返回空  服务器错误
        }
        return carCompanyBeans;
    }
}
