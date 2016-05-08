package com.car.yubangapk.json.formatJson.FormatJsonOrderDetail;

import com.car.yubangapk.json.JSONUtils;
import com.car.yubangapk.json.bean.Json2AddressBean;
import com.car.yubangapk.json.bean.OrderDetail.OrderAddress;
import com.car.yubangapk.json.bean.OrderDetail.OrderDetailInfo;
import com.car.yubangapk.json.bean.OrderDetail.OrderPackageModels;
import com.car.yubangapk.json.bean.OrderDetail.OrderPrice;
import com.car.yubangapk.json.bean.OrderDetail.OrderProductModels;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andy on 16/5/7.
 */
public class Json2OrderDetail
{

    String json;

    public Json2OrderDetail(String json)
    {
        this.json = json;
    }

    public static String UNDEFINED = "未定义";
    public OrderDetailInfo getMyOrderDetailInfo() {

        OrderDetailInfo orderDetailInfo = new OrderDetailInfo();

        boolean isJson;
        boolean isReturnStr;
        int returnCode;
        String returneMsg;
        String message;

        String carName;
        String shopName;
        String installTime;
        String orderStatus;

        OrderAddress orderAddress = new OrderAddress();
        String name;
        String phone;
        String carNum;


        String faultDescription;//故障描述
        String detectionResult;//检测结果
        String maintenanceSuggestion;//维修建议

        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(json);
            isJson = JSONUtils.getBoolean(jsonObject, "isJson", false);
            isReturnStr = JSONUtils.getBoolean(jsonObject, "isReturnStr", false);
            returnCode = JSONUtils.getInt(jsonObject, "returnCode", 0XFFFF);
            returneMsg = JSONUtils.getString(jsonObject, "returneMsg", UNDEFINED);
            message = JSONUtils.getString(jsonObject, "message", UNDEFINED);

            if (returnCode != 0)
            {
                orderDetailInfo.setReturnCode(returnCode);
                orderDetailInfo.setHasData(false);
                orderDetailInfo.setMessage(message);
                return orderDetailInfo;
            }

            carName = JSONUtils.getString(jsonObject, "carName", UNDEFINED);
            shopName = JSONUtils.getString(jsonObject, "shopName", UNDEFINED);
            installTime = JSONUtils.getString(jsonObject, "installTime", UNDEFINED);
            orderStatus = JSONUtils.getString(jsonObject, "orderStatus", UNDEFINED);

            faultDescription = JSONUtils.getString(jsonObject, "faultDescription", UNDEFINED);
            detectionResult = JSONUtils.getString(jsonObject, "detectionResult", UNDEFINED);
            maintenanceSuggestion = JSONUtils.getString(jsonObject, "maintenanceSuggestion", UNDEFINED);



            orderDetailInfo.setIsJson(isJson);
            orderDetailInfo.setIsReturnStr(isReturnStr);
            orderDetailInfo.setReturnCode(returnCode);
            orderDetailInfo.setReturneMsg(returneMsg);
            orderDetailInfo.setMessage(message);

            orderDetailInfo.setCarName(carName);
            orderDetailInfo.setShopName(shopName);
            orderDetailInfo.setInstallTime(installTime);
            orderDetailInfo.setOrderStatus(orderStatus);

            orderDetailInfo.setFaultDescription(faultDescription);
            orderDetailInfo.setDetectionResult(detectionResult);
            orderDetailInfo.setMaintenanceSuggestion(maintenanceSuggestion);





            JSONObject addressObjuect = JSONUtils.getJSONObject(jsonObject, "orderAddress", null);


            name = JSONUtils.getString(addressObjuect, "name", UNDEFINED);
            phone  = JSONUtils.getString(addressObjuect, "phone", UNDEFINED);
            carNum  = JSONUtils.getString(addressObjuect, "carNum", UNDEFINED);
            orderAddress.setName(name);
            orderAddress.setPhone(phone);
            orderAddress.setCarNum(carNum);
            orderDetailInfo.setOrderAddress(orderAddress);




            OrderPrice orderPrice = new OrderPrice();
            JSONObject orderPriceObject = JSONUtils.getJSONObject(jsonObject, "orderPrice", null);
            String orderId;
            String orderNum;
            double totalPrice;
            double installCoast;
            double couponPrice;
            orderId = JSONUtils.getString(orderPriceObject, "orderId", UNDEFINED);
            orderNum = JSONUtils.getString(orderPriceObject, "orderNum", UNDEFINED);
            totalPrice = JSONUtils.getDouble(orderPriceObject, "totalPrice", 0);
            installCoast = JSONUtils.getDouble(orderPriceObject, "installCoast", 0);
            couponPrice = JSONUtils.getDouble(orderPriceObject, "couponPrice", 0);
            orderPrice.setOrderId(orderId);
            orderPrice.setOrderNum(orderNum);
            orderPrice.setTotalPrice(totalPrice);
            orderPrice.setInstallCoast(installCoast);
            orderPrice.setCouponPrice(couponPrice);

            orderDetailInfo.setOrderPrice(orderPrice);

            List<OrderPackageModels> orderPackageModelsList = new ArrayList<>();

            //获取产品包数组
            JSONArray packageModelsArray = JSONUtils.getJSONArray(jsonObject,"orderPackageModels", null);
            //产品包里面的产品
            List<OrderProductModels> productModelsList = new ArrayList<>();
            int size = packageModelsArray.length();
            for (int i = 0; i < size; i++) {

                String packageName;
                String photoName;
                String pathCode;

                OrderPackageModels orderPackageModels  = new OrderPackageModels();
                JSONObject object = packageModelsArray.getJSONObject(i);
                packageName = JSONUtils.getString(object, "packageName", UNDEFINED);
                photoName = JSONUtils.getString(object, "photoName", UNDEFINED);
                pathCode = JSONUtils.getString(object, "pathCode", UNDEFINED);
                //拿到产品包里面的产品
                JSONArray prodcutArray = JSONUtils.getJSONArray(object, "orderProductModels", null);
                int productSize = prodcutArray.length();

                for (int j = 0; j < productSize; j++) {
                    String productId1;
                    String productName1;
                    double price1;
                    int    num1;
                    String pathCode1;
                    String photoName1;
                    String des1;

                    OrderProductModels orderProductModels = new OrderProductModels();
                    JSONObject productObject = prodcutArray.getJSONObject(j);

                    productId1 = JSONUtils.getString(productObject, "productId", UNDEFINED);
                    productName1 = JSONUtils.getString(productObject, "productName", UNDEFINED);
                    price1 = JSONUtils.getDouble(productObject, "price", 0);
                    num1 = JSONUtils.getInt(productObject, "num", 0);
                    pathCode1 = JSONUtils.getString(productObject, "pathCode", UNDEFINED);
                    photoName1 = JSONUtils.getString(productObject, "photoName", UNDEFINED);
                    des1 = JSONUtils.getString(productObject, "des", UNDEFINED);

                    orderProductModels.setProductId(productId1);
                    orderProductModels.setProductName(productName1);
                    orderProductModels.setPrice(price1);
                    orderProductModels.setNum(num1);
                    orderProductModels.setPathCode(pathCode1);
                    orderProductModels.setPhotoName(photoName1);
                    orderProductModels.setDes(des1);
                    productModelsList.add(orderProductModels);
                }
                orderPackageModels.setPackageName(packageName);
                orderPackageModels.setPhotoName(photoName);
                orderPackageModels.setPathCode(pathCode);
                orderPackageModels.setOrderProductModels(productModelsList);

                orderPackageModelsList.add(orderPackageModels);
            }
            orderDetailInfo.setHasData(true);
            orderDetailInfo.setOrderPackageModels(orderPackageModelsList);


        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return orderDetailInfo;
    }

}
