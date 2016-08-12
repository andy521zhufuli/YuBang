package com.car.yubangapk.json.formatJson.formatWallet;

import com.car.yubangapk.json.JSONUtils;
import com.car.yubangapk.json.bean.wallet.CouponRow;
import com.car.yubangapk.json.bean.wallet.Json2MyCouponBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andy on 16/6/23.
 */
public class Json2MyCoupon
{
    String json;

    public Json2MyCoupon(String json)
    {
        this.json = json;
    }

    public Json2MyCouponBean getMyCoupon() {
        Json2MyCouponBean json2MyCouponBean = new Json2MyCouponBean();
        int total;
        List<CouponRow> rows = new ArrayList<>();
        boolean isJson;
        boolean isReturnStr;
        int returnCode;
        String returneMsg;
        String message;



        String startDate;
        String couponName;
        String scope;//0 总价  1商铺     2产品包  3产品
        int    regulationSubtract;
        String endDate;
        int    regulationReach;
        String subCouponid;
        String flagName;

        JSONObject totalJson = null;
        try {
            totalJson = new JSONObject(json);
            returnCode = JSONUtils.getInt(totalJson, "returnCode", 0);
            if (returnCode == 0)
            {
                //json中没有这个字段   说明是对的
                total = JSONUtils.getInt(totalJson, "total", 0);
                JSONArray array = JSONUtils.getJSONArray(totalJson, "rows", null);
                int size = array.length();
                if (size == 0)
                {
                    json2MyCouponBean.setReturnCode(0);
                    json2MyCouponBean.setHasData(false);
                    return json2MyCouponBean;
                }
                else
                {
                    for (int i = 0; i < size; i++)
                    {
                        CouponRow couponRow = new CouponRow();
                        JSONObject object = (JSONObject) array.get(i);

                        startDate = JSONUtils.getString(object, "startDate", "");
                        couponName = JSONUtils.getString(object, "couponName", "");
                        scope = JSONUtils.getString(object, "scope", "");
                        regulationSubtract = JSONUtils.getInt(object, "regulationSubtract", 0);
                        endDate = JSONUtils.getString(object, "endDate", "");
                        regulationReach = JSONUtils.getInt(object, "regulationReach", 0);
                        subCouponid = JSONUtils.getString(object, "subCouponid", "");
                        flagName = JSONUtils.getString(object, "flagName", "");

                        couponRow.setStartDate(startDate);
                        couponRow.setCouponName(couponName);
                        couponRow.setScope(scope);
                        couponRow.setRegulationSubtract(regulationSubtract);
                        couponRow.setEndDate(endDate);
                        couponRow.setRegulationReach(regulationReach);
                        couponRow.setSubCouponid(subCouponid);
                        couponRow.setFlagName(flagName);
                        rows.add(couponRow);
                    }
                    json2MyCouponBean.setReturnCode(0);
                    json2MyCouponBean.setRows(rows);
                    json2MyCouponBean.setTotal(total);
                    json2MyCouponBean.setHasData(true);
                    return json2MyCouponBean;
                }
            } else if (returnCode != 0) {
                json2MyCouponBean.setReturnCode( returnCode);
                String message1 = JSONUtils.getString(totalJson, "message", "");
                json2MyCouponBean.setMessage(message1);
                json2MyCouponBean.setHasData(false);
                return json2MyCouponBean;
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
            return null;
        }
        return json2MyCouponBean;
    }
}
