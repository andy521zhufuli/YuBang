package com.car.yubangapk.network.myHttpReq;

import com.car.yubangapk.configs.Configs;
import com.car.yubangapk.configs.ErrorCodes;
import com.car.yubangapk.json.bean.Json2RecommendShopDetailInfoBean;
import com.car.yubangapk.json.bean.RecommendPartnerShopBaseInfo;
import com.car.yubangapk.json.formatJson.Json2RecommendShopDetailInfo;
import com.car.yubangapk.network.okhttp.OkHttpUtils;
import com.car.yubangapk.network.okhttp.builder.PostFormBuilder;
import com.car.yubangapk.network.okhttp.callback.StringCallback;
import com.car.yubangapk.utils.L;
import com.car.yubangapk.utils.String2UTF8;
import okhttp3.Call;

/**
 * Created by andy on 16/4/23.
 *
 * 推荐合伙人, 详细信息上传
 *
 */
public class HttpReqRecommendDetailInfo
{

    private String TAG = HttpReqAddress.class.getSimpleName();


    private HttpReqCallback callback;

    private static final String ARGS_USERID                 = "shopReq.userid";
    private static final String ARGS_CONTACT_PHONE          = "shopReq.contactsPhone";
    private static final String ARGS_CONTACT                = "shopReq.contacts";
    private static final String ARGS_ADDRESS                = "shopReq.shopAddress";

    private static final String ARGS_LON                    = "shopReq.shopLongitude";//
    private static final String ARGS_LAT                    = "shopReq.shopLatitude";//

    private static final String ARGS_PROVINCE               = "shopReq.shopProvince";//2，4必填
    private static final String ARGS_CITY                   = "shopReq.shopCity";
    private static final String ARGS_DISTRICT               = "shopReq.shopDistrict";
    private static final String ARGS_RECOMMEDN_ID           = "shopReq.recommendedId";
    private static final String ARGS_SHOP_NAME              = "shopReq.shopName";


    public HttpReqRecommendDetailInfo( )
    {
    }

    public void setListener(HttpReqCallback listener)
    {
        this.callback = listener;
    }

    public void putRecommendShopInfo(RecommendPartnerShopBaseInfo shopBaseInfo)
    {
        String userid  = shopBaseInfo.getUserid();
        String contactName = shopBaseInfo.getContactName();
        String contactPhone = shopBaseInfo.getContactPhone();
        double lon = shopBaseInfo.getLon();
        double lat = shopBaseInfo.getLat();
        String province = shopBaseInfo.getProvince();
        String city = shopBaseInfo.getCity();
        String district = shopBaseInfo.getDistrict();
        String address = shopBaseInfo.getAddress();
        String shopName = shopBaseInfo.getShopName();


        province = String2UTF8.getUTF8String(province);
        city = String2UTF8.getUTF8String(city);
        district = String2UTF8.getUTF8String(district);

        PostFormBuilder builder = OkHttpUtils.post().url(Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GET_RECOMMEDN_SHOP_PUT_DETAIL_INFO)
                .addParams(ARGS_USERID, userid)
                .addParams(ARGS_CONTACT_PHONE, contactPhone)
                .addParams(ARGS_CONTACT, contactName)
                .addParams(ARGS_ADDRESS, address)
                .addParams(ARGS_LON,lon + "")
                .addParams(ARGS_LAT, lat + "")
                .addParams(ARGS_PROVINCE, province)
                .addParams(ARGS_CITY, city)
                .addParams(ARGS_DISTRICT, district)
                .addParams(ARGS_RECOMMEDN_ID, userid)
                .addParams(ARGS_SHOP_NAME, shopName)

                ;
        builder.build().execute(new GetInstallShop());


        L.i(TAG, "获取 install shop url = " + Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GET_RECOMMEDN_SHOP_PUT_DETAIL_INFO + "?"
                + ARGS_LON + "=" + lon
                + "&" + ARGS_LAT + "=" + lat
                + "&" + ARGS_PROVINCE + "=" + province
                + "&" + ARGS_CITY + "=" + city
                + "&" + ARGS_DISTRICT + "=" + district
                + "&" + ARGS_USERID + "=" + userid
                + "&" + ARGS_CONTACT_PHONE + "=" + contactPhone
                + "&" + ARGS_CONTACT + "=" + contactName
                + "&" + ARGS_ADDRESS + "=" + address
                + "&" + ARGS_RECOMMEDN_ID + "=" + userid
                + "&" + ARGS_SHOP_NAME + "=" + shopName
        );
    }

    class GetInstallShop extends StringCallback
    {

        @Override
        public void onError(Call call, Exception e) {
            callback.onFail(ErrorCodes.ERROR_CODE_NETWORK, ErrorCodes.NETWORK_ERROR);
        }

        @Override
        public void onResponse(String response) {
            L.d(TAG, "获取 install shop json = " + response);

            Json2RecommendShopDetailInfo shop = new Json2RecommendShopDetailInfo(response);
            Json2RecommendShopDetailInfoBean shopBean = shop.getBean();
            if (shopBean == null)
            {
                callback.onFail(ErrorCodes.ERROR_CODE_LOW_VERSION, ErrorCodes.LOW_VERSION_TO_UPGRADE_APP);
            }
            else
            {
                if (shopBean.getReturnCode() != 0)//不正确 只0正确 那就返回错误码跟消息提示
                {
                    callback.onFail(shopBean.getReturnCode(), shopBean.getMessage());//没有店铺
                }
                else
                {
                    callback.onSuccess(shopBean);
                }

            }
        }
    }

}
