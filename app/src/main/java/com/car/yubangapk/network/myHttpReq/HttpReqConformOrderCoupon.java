package com.car.yubangapk.network.myHttpReq;

import com.car.yubangapk.configs.Configs;
import com.car.yubangapk.configs.ErrorCodes;
import com.car.yubangapk.json.bean.Json2CouponBean;
import com.car.yubangapk.json.bean.Json2InstallShopModelsBean;
import com.car.yubangapk.json.bean.Json2ProductPackageBean;
import com.car.yubangapk.json.formatJson.Json2Coupon;
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
 * Created by andy on 16/4/21.
 *
 * 确认订单  优惠券 查询
 *
 *
 */
public class HttpReqConformOrderCoupon
{

    private HttpReqConformOrderCouponInterface mCallback;

    public HttpReqConformOrderCoupon()
    {

    }

    public void setCallback(HttpReqConformOrderCouponInterface callback)
    {
        this.mCallback = callback;
    }



    public void getUseableCoupon(String userid, String carType, List<Json2ProductPackageBean> productDetail, Json2InstallShopModelsBean mInstallShopBean)
    {
        String shopid = mInstallShopBean.getShopId();

        PostFormBuilder builder = OkHttpUtils.post().url(Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GET_COUPON)
                .addParams("orderReq.userid", userid)
                .addParams("orderReq.shopId", shopid)
                .addParams("orderReq.carType", carType);

        String url = Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GET_COUPON + "?orderReq.userid=" + userid;
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
            //分类了的产品包
            List<Json2ProductPackageBean> categoryProductPackage = category.get(ppId);
            //设置产品包id
            builder = builder.addParams("orderReq.packages[" + keysize + "].productPackageId",ppId);
            url += "&orderReq.packages[" + keysize + "].productPackageId=" + ppId;
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
        builder.build().execute(new GetCoupon());
        L.i(HttpReqConformOrderCoupon.class.getName(), "获取可用优惠券url = " + url);

    }


    class GetCoupon extends StringCallback
    {

        @Override
        public void onError(Call call, Exception e) {
            mCallback.onFail(ErrorCodes.ERROR_CODE_NETWORK, ErrorCodes.NETWORK_ERROR);
        }

        @Override
        public void onResponse(String response) {
            L.i(HttpReqConformOrderCoupon.class.getName(), "获取可用优惠券json = " + response);
            Json2Coupon json2Coupon = new Json2Coupon(response);
            Json2CouponBean json2CouponBean = json2Coupon.getCoupon();
            if (json2CouponBean == null)
            {
                mCallback.onFail(ErrorCodes.ERROR_CODE_LOW_VERSION, ErrorCodes.LOW_VERSION_TO_UPGRADE_APP);
            }
            else
            {
                if (json2CouponBean.isHasData() == false)
                {
                    //没有数据
                    mCallback.onFail(ErrorCodes.ERROR_CODE_NO_DATA, "没有可用优惠券");
                }
                else if (json2CouponBean.getReturnCode() != 0)
                {
                    //
                    mCallback.onFail(ErrorCodes.ERROR_CODE_SERVER_ERROR, ErrorCodes.SERVER_ERROR);
                }
                else
                {
                    mCallback.onSuccess(json2CouponBean);
                }
            }

        }
    }
}
