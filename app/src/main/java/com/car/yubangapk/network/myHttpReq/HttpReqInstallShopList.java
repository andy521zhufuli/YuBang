package com.car.yubangapk.network.myHttpReq;

import com.car.yubangapk.configs.Configs;
import com.car.yubangapk.configs.ErrorCodes;
import com.car.yubangapk.json.bean.Json2InstallShopBean;
import com.car.yubangapk.json.bean.Json2InstallShopModelsBean;
import com.car.yubangapk.json.formatJson.Json2InstallShop;
import com.car.yubangapk.network.okhttp.OkHttpUtils;
import com.car.yubangapk.network.okhttp.builder.PostFormBuilder;
import com.car.yubangapk.network.okhttp.callback.StringCallback;
import com.car.yubangapk.utils.L;
import com.car.yubangapk.utils.String2UTF8;
import com.car.yubangapk.utils.toastMgr;

import java.util.List;

import okhttp3.Call;

/**
 * Created by andy on 16/4/20.
 */
public class HttpReqInstallShopList
{

    private String TAG = HttpReqAddress.class.getSimpleName();

    private String mUserId;
    private String mReqMethod;
    private String mName;
    private String mPhone;
    private String mAddressId;

    private HttpReqInstallShopListInterface callback;

    private static final String ARGS_LON                    = "shopReq.lon";//0 1 必填
    private static final String ARGS_LAT                    = "shopReq.lat";//0 1 必填
    private static final String ARGS_CARTYPE                = "shopReq.carType";//0添加，1修改，2删除，3查询，4修改默认地址
    private static final String ARGS_PROVINCE               = "shopReq.province";//2，4必填
    private static final String ARGS_CITY                   = "shopReq.city";
    private static final String ARGS_DISTRICT               = "shopReq.district";
    private static final String ARGS_SHOP_STATUS            = "shopReq.shopStatus";
    private static final String ARGS_USERID                 = "shopReq.userid";
    private static final String ARGS_REPAIR_SERVICE_ID      = "shopReq.repairServiceId[i]";

    public HttpReqInstallShopList(HttpReqInstallShopListInterface listener)
    {
        this.callback = listener;
    }

    public void setListener(HttpReqInstallShopListInterface listener)
    {
        this.callback = listener;
    }

    public void getInstallShop(double lon, double lat,String userid, String carType, String province, String city, String district, String shopStatus)
    {

        province = String2UTF8.getUTF8String(province);
        city = String2UTF8.getUTF8String(city);
        district = String2UTF8.getUTF8String(district);

        PostFormBuilder builder = OkHttpUtils.post().url(Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GET_INSTALL_SHOP)
                .addParams(ARGS_LON,lon + "")
                .addParams(ARGS_LAT, lat + "")
                .addParams(ARGS_CARTYPE, carType)
                .addParams(ARGS_PROVINCE, province)
                .addParams(ARGS_CITY, city)
                .addParams(ARGS_DISTRICT, district)
                .addParams(ARGS_SHOP_STATUS, shopStatus)
                .addParams(ARGS_USERID, userid)
                .addParams("shopReq.repairServiceId[0]", "e0635993-2f23-428c-9b42-5b53dd5a3734")
                ;
        //TODO 这里要改  一定要改  暂时还没改 问题很严重

        //这里是一个循环


        builder.build().execute(new GetInstallShop());


        L.i(TAG, "获取 install shop url = " + Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GET_INSTALL_SHOP + "?"
                + ARGS_LON + "=" + lon
                + "&" + ARGS_LAT + "=" + lat
                + "&" + ARGS_CARTYPE + "=" + carType
                + "&" + ARGS_PROVINCE + "=" + province
                + "&" + ARGS_CITY + "=" + city
                + "&" + ARGS_DISTRICT + "=" + district
                + "&" + ARGS_SHOP_STATUS + "=" + shopStatus
                + "&" + ARGS_USERID + "=" + userid
                + "&" + "shopReq.repairServiceId[0]" + "e0635993-2f23-428c-9b42-5b53dd5a3734"
        );
    }

    class GetInstallShop extends StringCallback
    {

        @Override
        public void onError(Call call, Exception e) {
            toastMgr.builder.display("网络错误, 请检查网络!", 1);
            callback.onGetInstallShopFail(ErrorCodes.ERROR_CODE_NETWORK, ErrorCodes.NETWORK_ERROR);
        }

        @Override
        public void onResponse(String response) {
            L.d(TAG, "获取 install shop json = " + response);

            Json2InstallShop shop = new Json2InstallShop(response);
            Json2InstallShopBean shopBean = shop.getInstallShop();
            if (shopBean == null)
            {
                callback.onGetInstallShopFail(ErrorCodes.ERROR_CODE_LOW_VERSION, ErrorCodes.LOW_VERSION_TO_UPGRADE_APP);
            }
            else
            {
                if (shopBean.getReturnCode() != 0)//不正确 只0正确 那就返回错误码跟消息提示
                {
                    callback.onGetInstallShopFail(shopBean.getReturnCode(), shopBean.getMessage());//没有店铺
                }
                else
                {
                    List<Json2InstallShopModelsBean> shopModels = shopBean.getShopModels();
                    callback.onGetInstallShopSucces(shopModels);
                }

            }




        }
    }


}
