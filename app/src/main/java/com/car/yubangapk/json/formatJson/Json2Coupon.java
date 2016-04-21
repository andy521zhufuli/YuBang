package com.car.yubangapk.json.formatJson;

import com.car.yubangapk.json.bean.CouponsBean;
import com.car.yubangapk.json.bean.Json2CouponBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andy on 16/4/21.
 */


//{
// "totalPrice          ":0.0,
// "couponPrice         ":0.0,
// "installationCoast   ":0.0,
// "payPrice            ":0.0,
// "coupons             ":[],
// "isJson              ":true,
// "isReturnStr         ":false,
// "returnCode          ":-2,
// "returneMsg              ":"SUCCESS",
// "message             ":"参数错误"}

//    {"totalPrice":0.0,"couponPrice":0.0,"installationCoast":0.0,"payPrice":0.0,

//         "isJson":true,"isReturnStr":false,"returnCode":0,"returneMsg":"SUCCESS"}
//
//         "coupons":[{"couponId":"1","couponName":"总价优惠卷","regulationReach":10,"regulationSubtract":1,"startDate":"2016-04-10 20:13:36.0","endDate":"2016-08-10 20:13:39.0"},
//         {"couponId":"2","couponName":"总价优惠卷1","regulationReach":2,"regulationSubtract":1,"startDate":"2016-04-04 19:01:15.0","endDate":"2016-08-31 19:01:19.0"},
//         {"couponId":"3","couponName":"总价优惠卷1","regulationReach":2,"regulationSubtract":1,"startDate":"2016-04-04 19:01:15.0","endDate":"2016-08-31 19:01:19.0"}],


public class Json2Coupon
{
    private String mJson;

    public Json2Coupon(String json)
    {
        this.mJson = json;
    }

    /**
     * 获取安装店铺
     * @return
     */
    public Json2CouponBean getCoupon()
    {

        Json2CouponBean json2CouponBean = new Json2CouponBean();
        List<CouponsBean> couponsBeanList = new ArrayList<>();

        JSONObject total = null;

        try {
            total = new JSONObject(mJson);

            int returnCode = total.getInt("returnCode");
            boolean isJson = total.getBoolean("isJson");
            boolean isReturnStr = total.getBoolean("isReturnStr");
            String returneMsg = total.getString("returneMsg");
            double totalPrice = total.getDouble("totalPrice");
            double couponPrice = total.getDouble("couponPrice");
            double installationCoast = total.getDouble("installationCoast");
            double payPrice = total.getDouble("payPrice");


            json2CouponBean.setReturnCode(returnCode);
            json2CouponBean.setJson(isJson);

            json2CouponBean.setReturnStr(isReturnStr);
            json2CouponBean.setReturneMsg(returneMsg);
            json2CouponBean.setTotalPrice(totalPrice);
            json2CouponBean.setCouponPrice(couponPrice);
            json2CouponBean.setInstallationCoast(installationCoast);
            json2CouponBean.setPayPrice(payPrice);

            JSONArray coupons = total.getJSONArray("coupons");
            int size = coupons.length();
            if (size == 0)
            {
                json2CouponBean.setHasData(false);
                return json2CouponBean;
            }
            else
            {
                json2CouponBean.setHasData(true);
                for (int i = 0; i < size; i++) {
                    JSONObject coupon = coupons.getJSONObject(i);
                    CouponsBean couponsBean = new CouponsBean();
                    String couponId;
                    String couponName;
                    int regulationReach;
                    int regulationSubtract;
                    String startDate;
                    String endDate;

                    couponId          = coupon.getString("couponId");
                    couponName        = coupon.getString("couponName");
                    regulationReach        = coupon.getInt("regulationReach");
                    regulationSubtract     = coupon.getInt("regulationSubtract");
                    startDate        = coupon.getString("startDate");
                    endDate        = coupon.getString("endDate");


                    couponsBean.setCouponId(couponId);
                    couponsBean.setCouponName(couponName);
                    couponsBean.setRegulationReach(regulationReach);
                    couponsBean.setRegulationSubtract(regulationSubtract);
                    couponsBean.setStartDate(startDate);
                    couponsBean.setEndDate(endDate);
                    couponsBeanList.add(couponsBean);
                }
                json2CouponBean.setCoupons(couponsBeanList);
            }


        }
        catch (JSONException e)
        {
            return null;
        }

        return json2CouponBean;
    }
}
