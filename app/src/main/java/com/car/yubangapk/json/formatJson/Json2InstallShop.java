package com.car.yubangapk.json.formatJson;

import com.car.yubangapk.json.bean.Json2InstallShopBean;
import com.car.yubangapk.json.bean.Json2InstallShopModelsBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 转换json  确认订单界面的安装门店
 */
public class Json2InstallShop {

// {
// "shopModels":[],
// "isJson":true,"isReturnStr":false,"returnCode":-2,"returneMsg":"SUCCESS","message":"该区域无店铺"}

/**
 *  {"shopModels":[],"isJson":true,"isReturnStr":false,"returnCode":-2,"returneMsg":"SUCCESS","message":"该区域无店铺"}
 *
 *
 * shopModels":[
 *          {"shopId":"10658811-ea3c-11e5-81df-28d244001fe5",
 *          "shopName":"宇邦旗舰店",
 *          "distance":7654.0,
 *          "shopAddress":"广东广州番禺",
 *          "phoneNum":"13454678764",
 *          "pathCode":"6",
 *          "photoName":"c162f805-1796-4ad1-8ead-4539ae795faf_LPP_1.jpg","order":0},
 *
 *          {"shopId":"0513ba78-0ce8-4e00-b6c5-168d5544c4ef","shopName":"强哥维修","distance":7654.0,"shopAddress":"广东广州番禺",
 *          "phoneNum":"13454678764","pathCode":"6","photoName":"c162f805-1796-4ad1-8ead-4539ae795faf_LPP_1.jpg","order":1}],
 * "isJson":true,
 * "isReturnStr":false,
 * "returnCode":0,
 * "returneMsg":"SUCCESS",
 * "message":"查找店铺成功！"}
 */


    private String mJson;

    public Json2InstallShop(String json)
    {
        this.mJson = json;
    }

    /**
     * 获取安装店铺
     * @return
     */
    public Json2InstallShopBean getInstallShop()
    {
        Json2InstallShopBean installShopBean = new Json2InstallShopBean();
        List<Json2InstallShopModelsBean> shopModelsBeen = new ArrayList<>();
        JSONObject total = null;

        try {
            total = new JSONObject(mJson);

            int returnCode = total.getInt("returnCode");
            boolean isJson = total.getBoolean("isJson");
            boolean isReturnStr = total.getBoolean("isReturnStr");
            String returneMsg = total.getString("returneMsg");
            String message = total.getString("message");



            installShopBean.setReturnCode(returnCode);
            installShopBean.setMessage(message);

            if (returnCode == 100)
            {
                return installShopBean;
            }


            JSONArray shopModels = total.getJSONArray("shopModels");




            int size = shopModels.length();
            if (size == 0)
            {
                installShopBean.setHasData(false);
                return installShopBean;
            }
            else
            {
                for (int i = 0; i < size; i++) {
                    JSONObject shopModel = shopModels.getJSONObject(i);
                    Json2InstallShopModelsBean model = new Json2InstallShopModelsBean();
                    String          shopId;
                    String          shopName;
                    double          distance;
                    String          shopAddress;
                    String          phoneNum;
                    String          pathCode;
                    String          photoName;
                    double          lat;
                    double          lon;
                    String          assess;
                    String          orderNum;
                    String          star;
                    int             order;
                    int             own;

                    try
                    {
                        shopId          = shopModel.getString("shopId");
                        shopName        = shopModel.getString("shopName");
                        distance        = shopModel.getDouble("distance");
                        shopAddress     = shopModel.getString("shopAddress");
                        phoneNum        = shopModel.getString("phoneNum");
                        pathCode        = shopModel.getString("pathCode");
                        photoName       = shopModel.getString("photoName");
                        lat             = shopModel.getDouble("lat");
                        lon             = shopModel.getDouble("lon");
                        assess          = shopModel.getString("assess");
                        orderNum        = shopModel.getString("orderNum");
                        star            = shopModel.getString("star");
                        order           = shopModel.getInt("order");
                        own             = shopModel.getInt("own");


                        model.setShopId(shopId);
                        model.setShopName(shopName);
                        model.setDistance(distance);
                        model.setShopAddress(shopAddress);
                        model.setPhoneNum(phoneNum);
                        model.setPathCode(pathCode);
                        model.setPhotoName(photoName);
                        model.setLat(lat);
                        model.setLon(lon);
                        model.setAssess(assess);
                        model.setOrderNum(orderNum);
                        model.setStar(star);
                        model.setOrder(order);
                        model.setOwn(own);

                        model.setHasData(true);
                    }
                    catch (JSONException e)
                    {
                        continue;
                    }



                    shopModelsBeen.add(model);
                }
                installShopBean.setHasData(true);
                installShopBean.setShopModels(shopModelsBeen);
            }


        }
        catch (JSONException e)
        {
            return null;
        }

        return installShopBean;
    }


}
