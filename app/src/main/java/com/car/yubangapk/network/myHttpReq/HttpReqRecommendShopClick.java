package com.car.yubangapk.network.myHttpReq;

import com.car.yubangapk.configs.Configs;
import com.car.yubangapk.configs.ErrorCodes;
import com.car.yubangapk.json.bean.Json2RecommendShopClickBean;
import com.car.yubangapk.json.formatJson.Json2RecommendShopClick;
import com.car.yubangapk.network.okhttp.OkHttpUtils;
import com.car.yubangapk.network.okhttp.builder.PostFormBuilder;
import com.car.yubangapk.network.okhttp.callback.StringCallback;
import com.car.yubangapk.utils.L;
import com.car.yubangapk.utils.toastMgr;

import okhttp3.Call;

/**
 * Created by andy on 16/4/23.
 *
 * 推荐合伙人店铺点击 后相应
 *
 */
public class HttpReqRecommendShopClick
{


//

    private String TAG = HttpReqRecommendShopClick.class.getSimpleName();

    private String mUserId;
    private String mReqMethod;
    private String mName;
    private String mPhone;
    private String mAddressId;

    private HttpReqCallback callback;

    private static final String ARGS_SHOP_ID                = "pointReq.shopId";
    private static final String ARGS_USERID                 = "pointReq.userid";


    public HttpReqRecommendShopClick()
    {

    }

    public void setListener(HttpReqCallback listener)
    {
        this.callback = listener;
    }

    public void getInstallShop(String userid, String shopid)
    {
        PostFormBuilder builder = OkHttpUtils.post().url(Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GET_RECOMMEDN_SHOP_CLICK)
                .addParams(ARGS_SHOP_ID, shopid)
                .addParams(ARGS_USERID, userid)
                .addParams("shopReq.repairServiceId[0]", "e0635993-2f23-428c-9b42-5b53dd5a3734")
                ;
        //TODO 这里要改  一定要改  暂时还没改 问题很严重

        //这里是一个循环


        builder.build().execute(new GetRecommendShopClick());


        L.i(TAG, "推荐合伙人点击 url = " + Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GET_RECOMMEDN_SHOP_CLICK + "?"
                + ARGS_SHOP_ID + "=" + shopid
                + "&" + ARGS_USERID + "=" + userid
        );
    }

    class GetRecommendShopClick extends StringCallback
    {

        @Override
        public void onError(Call call, Exception e) {
            toastMgr.builder.display("网络错误, 请检查网络!", 1);
            callback.onFail(ErrorCodes.ERROR_CODE_NETWORK, ErrorCodes.NETWORK_ERROR);
        }

        @Override
        public void onResponse(String response) {
            L.d(TAG, "获取 install shop json = " + response);

            Json2RecommendShopClick shop = new Json2RecommendShopClick(response);
            Json2RecommendShopClickBean shopBean = shop.getObj();
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

