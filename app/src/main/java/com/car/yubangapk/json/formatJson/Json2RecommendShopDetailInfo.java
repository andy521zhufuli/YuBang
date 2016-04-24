package com.car.yubangapk.json.formatJson;

import com.car.yubangapk.json.bean.GetVerifyCodeBean;
import com.car.yubangapk.json.bean.Json2RecommendShopDetailInfoBean;
import com.car.yubangapk.json.bean.VerifyCodeBean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by andy on 16/4/23.
 *
 * 发现--推荐合伙人---上传信息
 *
 */


public class Json2RecommendShopDetailInfo
{

    private String json;

    public Json2RecommendShopDetailInfo()
    {

    }
    public Json2RecommendShopDetailInfo(String json)
    {
        this.json = json;
    }

    /**
     * //{
     // "isJson":true,
     // "isReturnStr":false,
     // "returnCode":0,
     // "returneMsg":"SUCCESS",
     // "message":""
     // ,shopId:””}提示shopId需要保存后面还要用
     * @return List<ShoppingmallPicBean>
     */
    public Json2RecommendShopDetailInfoBean getBean()
    {
        Json2RecommendShopDetailInfoBean shopDetailInfoBean = new Json2RecommendShopDetailInfoBean();

        JSONObject total = null;

        try {
            total = new JSONObject(json);

            boolean isJson;
            boolean isReturnStr;
            int returnCode;
            String returneMsg;
            String message;
            String shopId;


            isJson = total.getBoolean("isJson");
            isReturnStr = total.getBoolean("isReturnStr");
            returnCode = total.getInt("returnCode");

            if (returnCode == 100)
            {
                shopDetailInfoBean.setHasData(false);
                shopDetailInfoBean.setReturnCode(returnCode);
                return shopDetailInfoBean;
            }
            returneMsg = total.getString("returneMsg");
            message = total.getString("message");
            shopId = total.getString("shopId");

            shopDetailInfoBean.setIsJson(isJson);
            shopDetailInfoBean.setIsReturnStr(isReturnStr);
            shopDetailInfoBean.setReturnCode(returnCode);
            shopDetailInfoBean.setReturneMsg(returneMsg);
            shopDetailInfoBean.setMessage(message);
            shopDetailInfoBean.setShopId(shopId);
            shopDetailInfoBean.setHasData(true);

        } catch (JSONException e) {
            e.printStackTrace();
            return null;//返回空  服务器错误
        }
        return shopDetailInfoBean;
    }


}
