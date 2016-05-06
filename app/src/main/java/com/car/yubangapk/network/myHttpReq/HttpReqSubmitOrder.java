package com.car.yubangapk.network.myHttpReq;

import com.car.yubangapk.configs.Configs;
import com.car.yubangapk.configs.ErrorCodes;
import com.car.yubangapk.json.bean.CouponsBean;
import com.car.yubangapk.json.bean.Json2DefaultAddressBean;
import com.car.yubangapk.json.bean.Json2InstallShopModelsBean;
import com.car.yubangapk.json.bean.Json2OrderPriceBean;
import com.car.yubangapk.json.bean.Json2ProductPackageBean;
import com.car.yubangapk.json.formatJson.Json2OrderPrice;
import com.car.yubangapk.network.okhttp.OkHttpUtils;
import com.car.yubangapk.network.okhttp.builder.PostFormBuilder;
import com.car.yubangapk.network.okhttp.callback.StringCallback;
import com.car.yubangapk.utils.L;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by andy on 16/4/22.
 *
 * 获取订单价格   跟提交订单
 *
 */
public class HttpReqSubmitOrder
{


    HttpReqCallback mCallback;

    boolean mIsSubmitOrder = false;

    public HttpReqSubmitOrder()
    {

    }

    public  void setCallback(HttpReqCallback callback)
    {
        this.mCallback = callback;
    }


    /**
     *
     * @param userid
     * @param carType
     * @param productDetail
     * @param mInstallShopBean
     * @param couponsBean
     * @param addressBean  isSubmitOrder=true的时候时候才要传
     * @param installtime  isSubmitOrder=ture的时候才要穿
     * @param isSubmitOrder true 代表提交订单, false 代表获取订单价格
     */
    public void getOrderPrice(String userid, String carType, List<Json2ProductPackageBean> productDetail, Json2InstallShopModelsBean mInstallShopBean,
                              CouponsBean couponsBean, Json2DefaultAddressBean addressBean, String installtime, boolean isSubmitOrder)
    {
        String shopid = mInstallShopBean.getShopId();

        PostFormBuilder builder;
        if (isSubmitOrder == true)
        {
            builder = OkHttpUtils.post().url(Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_SUBMIT_ORDER);
        }
        else
        {
            builder = OkHttpUtils.post().url(Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GET_ORDER_PRICE);
        }


        builder = builder.addParams("orderReq.userid", userid);
        builder = builder.addParams("orderReq.shopId", shopid);
        builder = builder.addParams("orderReq.carType", carType);

        String url = Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GET_ORDER_PRICE + "?orderReq.userid=" + userid;
        url += "&orderReq.shopId=" + shopid;
        url += "&orderReq.carType=" + carType;

        //这里是一个循环

        Map<String, List<Json2ProductPackageBean>> category = new HashMap<>();
        for (Json2ProductPackageBean bean:productDetail)
        {

            if (category.containsKey(bean.getProductPackageId()))
            {
                List<Json2ProductPackageBean> c = category.get(bean.getProductPackageId());
                c.add(bean);
                category.put(bean.getProductPackageId(), c);

            }
            else
            {
                List<Json2ProductPackageBean> categrory1 = new ArrayList<>();
                categrory1.add(bean);
                category.put(bean.getProductPackageId(), categrory1);
            }
        }

        int keysize = 0;
        for (String key : category.keySet())
        {
            String ppId = key;
            String repairService = category.get(key).get(0).getRepairService();
            //分类了的产品包
            List<Json2ProductPackageBean> categoryProductPackage = category.get(ppId);
            //设置产品包id
            builder = builder.addParams("orderReq.packages[" + keysize + "].productPackageId",ppId);
            builder = builder.addParams("orderReq.packages[" + keysize + "].repairServiceId",repairService);

            url += "&orderReq.packages[" + keysize + "].productPackageId=" + ppId;
            url += "&orderReq.packages[" + keysize + "].repairServiceId=" + repairService;
            int size = categoryProductPackage.size();
            for (int i = 0; i < size; i++)
            {
                builder = builder.addParams("orderReq.packages[" + keysize + "].products[" + i + "].productId" ,categoryProductPackage.get(i).getId());
                url += "&orderReq.packages[" + keysize + "].products[" + i + "].productId=" + categoryProductPackage.get(i).getId();

                builder = builder.addParams("orderReq.packages[" + keysize + "].products[" + i + "].price" ,categoryProductPackage.get(i).getRetailPrice() + "");
                url += "&orderReq.packages[" + keysize + "].products[" + i + "].price=" + categoryProductPackage.get(i).getRetailPrice();

                builder = builder.addParams("orderReq.packages[" + keysize + "].products[" + i + "].num" ,categoryProductPackage.get(i).getProductAmount() + "");
                url += "&orderReq.packages[" + keysize + "].products[" + i + "].num=" + categoryProductPackage.get(i).getProductAmount();
            }
            keysize++;
        }
        if (couponsBean == null)
        {
            builder = builder.addParams("orderReq.couponId", "");
            url += "&orderReq.couponId=" + "";
        }
        else
        {
            String name = couponsBean.getCouponName();
            if (name.equals("不使用"))
            {
                builder = builder.addParams("orderReq.couponId", "");
                url += "&orderReq.couponId=" + "";
            }
            else
            {
                builder = builder.addParams("orderReq.couponId", couponsBean.getCouponId());
                url += "&orderReq.couponId=" + couponsBean.getCouponId();
            }
        }


        if (isSubmitOrder== false)
        {
            //获取订单价格, 这两个参数就不需要传递
        }
        else
        {
            //提交订单
            builder = builder.addParams("orderReq.addressId", addressBean.getId());
            url += "&orderReq.addressId=" + addressBean.getId();
            builder = builder.addParams("orderReq.installTime", installtime);
            url += "&orderReq.installTime=" + installtime;
        }


        builder.build().execute(new GetOrderPrice());
        L.i(HttpReqConformOrderCoupon.class.getName(), "获取提交订单url = " + url);
    }





    class GetOrderPrice extends StringCallback
    {

        @Override
        public void onError(Call call, Exception e) {
            mCallback.onFail(ErrorCodes.ERROR_CODE_NETWORK, ErrorCodes.NETWORK_ERROR);
        }

        @Override
        public void onResponse(String response) {
            L.i("提交订单, json = ", response);
            if (mIsSubmitOrder == false)
            {
                //获取订单价格
            }
            else
            {
                //提交订单
            }
            Json2OrderPrice json2OrderPrice = new Json2OrderPrice(response);
            Json2OrderPriceBean orderPrice = json2OrderPrice.getOrderPrice();
            if (orderPrice == null)
            {
                mCallback.onFail(ErrorCodes.ERROR_CODE_LOW_VERSION, ErrorCodes.LOW_VERSION_TO_UPGRADE_APP);
            }
            else
            {
                if (orderPrice.getReturnCode() == 0)
                {
                    if (orderPrice.isHasData() == false)
                    {
                        mCallback.onFail(ErrorCodes.ERROR_CODE_SERVER_ERROR, ErrorCodes.SERVER_ERROR);
                    }
                    else
                    {
                        mCallback.onSuccess(orderPrice);
                    }
                }
                else
                {
                    mCallback.onFail(ErrorCodes.ERROR_CODE_SERVER_ERROR, orderPrice.getMessage());
                }
            }

        }
    }
}
