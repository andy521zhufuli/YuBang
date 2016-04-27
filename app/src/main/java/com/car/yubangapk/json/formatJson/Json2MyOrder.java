package com.car.yubangapk.json.formatJson;

import com.car.yubangapk.json.bean.Json2MyOrderBean;
import com.car.yubangapk.json.bean.MyOrderBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andy on 16/4/25.
 */
public class Json2MyOrder
{
    //{"total":3,"rows":[
    // {"id":"0ec94834-f3b1-11e5-bd56-28d244001fe5","orderMoney":"222","orderTime":"2016-03-26 00:00:00","orderStatusName":"等待店家确认","orderName":"小保养-001","orderNumber":"10000723"},
    // {"id":"2","orderMoney":"1999","orderTime":"2016-03-26 00:00:00","orderStatusName":"等待买家付款","orderName":"大保养","orderNumber":"10000721"},
    // {"id":"ba767580-f336-11e5-a33b-28d244001fe5","orderMoney":"1999","orderTime":"2016-03-26 00:00:00","orderStatusName":"等待买家付款","orderName":"大保养","orderNumber":"10000721"}]}

    private String json;

    public  Json2MyOrder(String  json)

    {
        this.json = json;
    }

    public Json2MyOrderBean getMyorder()
    {
        Json2MyOrderBean order = new Json2MyOrderBean();

        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(json);
            int total = jsonObject.getInt("total");
            if (total == 0)
            {
                order.setTotal(total);
                order.setHasData(false);
                return order;
            }
            JSONArray rows = jsonObject.getJSONArray("rows");
            int size = rows.length();
            String id;
            String pathCode;
            String orderStatusName;
            String productNum;
            String orderNumber;
            String orderName;
            String orderTime;
            String orderMoney;
            String photo;


            List<MyOrderBean> orderBeansList = new ArrayList<>();

            for (int i = 0; i < size; i++)
            {

                JSONObject row = (JSONObject) rows.get(i);
                id = row.getString("id");
                orderMoney = row.getString("orderMoney");
                orderTime = row.getString("orderTime");
                orderStatusName = row.getString("orderStatusName");
                orderName = row.getString("orderName");
                orderNumber = row.getString("orderNumber");
                pathCode = row.getString("pathCode");
                productNum = row.getString("productNum");
                photo = row.getString("photo");


                MyOrderBean orderBean = new MyOrderBean();
                orderBean.setId(id);
                orderBean.setOrderMoney(orderMoney);
                orderBean.setOrderTime(orderTime);
                orderBean.setOrderStatusName(orderStatusName);
                orderBean.setOrderName(orderName);
                orderBean.setOrderNumber(orderNumber);
                orderBean.setPathCode(pathCode);
                orderBean.setProductNum(productNum);
                orderBean.setPhoto(photo);

                orderBean.setHasData(true);

                orderBeansList.add(orderBean);
            }


            order.setHasData(true);


            order.setRows(orderBeansList);
        } catch (JSONException e) {
            e.printStackTrace();
            try {
                //{"isJson":true,"isReturnStr":false,"returnCode":100,"returneMsg":"SUCCESS","message":"用户未登录"}
                JSONObject total1;
                total1 = new JSONObject(json);
                boolean isJson;
                boolean isReturnStr;
                int returnCode;
                String returneMsg;
                String message;



                isJson = total1.getBoolean("isJson");
                isReturnStr = total1.getBoolean("isReturnStr");
                returnCode = total1.getInt("returnCode");
                returneMsg = total1.getString("returneMsg");
                message = total1.getString("message");

                order.setReturnCode(returnCode);
                return  order;

            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            return null;//返回空  服务器错误
        }



        return order;
    }


}
