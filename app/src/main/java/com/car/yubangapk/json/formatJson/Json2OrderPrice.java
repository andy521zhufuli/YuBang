package com.car.yubangapk.json.formatJson;


import com.car.yubangapk.json.bean.Json2OrderPriceBean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by andy on 16/4/22.
 *
 * 转换成订单价格
 *
 */
public class Json2OrderPrice {


    private double totalPrice = 0;//总价格
    private double couponPrice = 0;//优惠卷价格
    private double installationCoast = 0;//安装费
    private double payPrice = 0;//需要支付价格

    private String mJson;

    public Json2OrderPrice(String json) {
        this.mJson = json;
    }

    public Json2OrderPriceBean getOrderPrice()
    {
        Json2OrderPriceBean orderPriceBean = new Json2OrderPriceBean();

        JSONObject total = null;

        try {
            total = new JSONObject(mJson);

            boolean isJson;
            boolean isReturnStr;
            int returnCode;
            String returneMsg;
            String message;


            totalPrice = total.getDouble("totalPrice");
            couponPrice = total.getDouble("couponPrice");
            installationCoast = total.getDouble("installationCoast");
            payPrice = total.getDouble("payPrice");



//            isJson = total.getBoolean("isJson");
//            isReturnStr = total.getBoolean("isReturnStr");
//            returnCode = total.getInt("returnCode");
//            returneMsg = total.getString("returneMsg");
//            message = total.getString("message");



//            orderPriceBean.setIsJson(isJson);
//            orderPriceBean.setIsReturnStr(isReturnStr);
//            orderPriceBean.setReturnCode(returnCode);
//            orderPriceBean.setReturneMsg(returneMsg);
//            orderPriceBean.setMessage(message);
            orderPriceBean.setTotalPrice(totalPrice);
            orderPriceBean.setCouponPrice(couponPrice);
            orderPriceBean.setInstallationCoast(installationCoast);
            orderPriceBean.setPayPrice(payPrice);
            orderPriceBean.setHasData(true);


        } catch (JSONException e) {
            e.printStackTrace();
            return null;//返回空  服务器错误
        }
        return orderPriceBean;
    }

}
