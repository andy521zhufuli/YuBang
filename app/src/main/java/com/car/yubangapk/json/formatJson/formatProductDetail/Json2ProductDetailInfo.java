package com.car.yubangapk.json.formatJson.formatProductDetail;

import com.car.yubangapk.json.JSONUtils;
import com.car.yubangapk.json.bean.productDetail.Json2ProductDetailInfoBean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by andy on 16/5/9.
 *
 * 产品包里面单个产品的详情
 *
 */
public class Json2ProductDetailInfo
{


    private String json;

    public Json2ProductDetailInfo(String response)
    {
        this.json = response;
    }

    public Json2ProductDetailInfoBean getProductDetailInfo()
    {
        Json2ProductDetailInfoBean productDetailInfo = new Json2ProductDetailInfoBean();

        boolean isJson;
        boolean isReturnStr;
        int returnCode;
        String returneMsg;
        String message;

        String productId;
        String productPhoto;
        String pathCode;
        String productName;
        double productPrice;
        int    buyNum;


        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(json);

            isJson = JSONUtils.getBoolean(jsonObject, "isJson", false);
            isReturnStr = JSONUtils.getBoolean(jsonObject, "isReturnStr", false);
            returnCode = JSONUtils.getInt(jsonObject, "returnCode", JSONUtils.ERROR_INT);
            returneMsg = JSONUtils.getString(jsonObject, "returneMsg", JSONUtils.UNDEFINED);
            message = JSONUtils.getString(jsonObject, "message", JSONUtils.UNDEFINED);

            productId = JSONUtils.getString(jsonObject, "productId", JSONUtils.UNDEFINED);
            productPhoto = JSONUtils.getString(jsonObject, "productPhoto", JSONUtils.UNDEFINED);
            pathCode = JSONUtils.getString(jsonObject, "pathCode", JSONUtils.UNDEFINED);
            productName = JSONUtils.getString(jsonObject, "productName", JSONUtils.UNDEFINED);
            productPrice = JSONUtils.getDouble(jsonObject, "productPrice", 0);
            buyNum = JSONUtils.getInt(jsonObject, "buyNum", 0);

            productDetailInfo.setIsJson(isJson);
            productDetailInfo.setIsReturnStr(isReturnStr);
            productDetailInfo.setReturnCode(returnCode);
            productDetailInfo.setReturneMsg(returneMsg);
            productDetailInfo.setMessage(message);

            productDetailInfo.setProductId(productId);
            productDetailInfo.setProductPhoto(productPhoto);
            productDetailInfo.setPathCode(pathCode);
            productDetailInfo.setProductName(productName);
            productDetailInfo.setProductPrice(productPrice);
            productDetailInfo.setBuyNum(buyNum);
            productDetailInfo.setHasData(true);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
            //这里会返回null  是因为 第一句解析的时候就错了  服务器返回的不是json
            return null;
        }
        return productDetailInfo;
    }
}
