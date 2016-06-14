package com.car.yubangapk.network.myHttpReq;

import com.car.yubangapk.configs.Configs;
import com.car.yubangapk.configs.ErrorCodes;
import com.car.yubangapk.json.bean.Json2FirstPageShopBean;
import com.car.yubangapk.json.formatJson.Json2FirstPageShop;
import com.car.yubangapk.network.okhttp.OkHttpUtils;
import com.car.yubangapk.network.okhttp.callback.StringCallback;
import com.car.yubangapk.utils.L;
import com.car.yubangapk.utils.String2UTF8;
import com.car.yubangapk.utils.toastMgr;

import java.util.List;

import okhttp3.Call;

/**
 * Created by andy on 16/6/1.
 *
 * 获取搜索店铺
 *
 */
public class HttpReqSearchShop {
    HttpReqCallback mCallback;

    boolean mIsSubmitOrder = false;

    public HttpReqSearchShop() {

    }

    public void setCallback(HttpReqCallback callback) {
        this.mCallback = callback;
    }


    public void searchShop(int page, int rows, String word, double longitude, double latitude)
    {
        OkHttpUtils.post()
                .url(Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GET_SHOP)
                .addParams("sqlName", "clientSearchShop")
                .addParams("page",page+"")//第几页
                .addParams("rows", rows+"")//一页几条记录数
                .addParams("dataReqModel.args.likeField", "shopName")
                .addParams("dataReqModel.args.shopName", word)
                .addParams("shopReq.lon",longitude + "")
                .addParams("shopReq.lat",latitude + "")
                .build()
                .execute(new SearchShopCallback());

        L.i("HttpReqSearchShop", "获取搜索店铺 url = " + Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GET_SHOP + "?"
                        + "sqlName=" + "clientSearchShop"
                        + "&page=" + page
                        + "&rows=" + rows
                        + "&dataReqModel.args.likeField=" + "shopName"
                        + "&dataReqModel.args.shopName=" + word
                        + "&shopReq.lon=" + longitude
                        + "&shopReq.lat=" + latitude
        );
    }

    class SearchShopCallback extends StringCallback
    {
        @Override
        public void onError(Call call, Exception e) {
            mCallback.onFail(ErrorCodes.ERROR_CODE_NETWORK, ErrorCodes.NETWORK_ERROR);
            toastMgr.builder.display("网络错误", 1);
        }

        @Override
        public void onResponse(String response) {
            L.d("HttpReqSearchShop", "获取搜索店铺 json = " + response);
            Json2FirstPageShop json2FirstPageShop = new Json2FirstPageShop(response);
            List<Json2FirstPageShopBean> json2FirstPageShopBeanList = json2FirstPageShop.getFirstPageShop();
            if (json2FirstPageShopBeanList == null)
            {
                mCallback.onFail(ErrorCodes.ERROR_CODE_SERVER_ERROR, ErrorCodes.SERVER_ERROR);
                toastMgr.builder.display("服务器错误", 1);
            }
            else
            {
                if (json2FirstPageShopBeanList.size() <= 0)
                {
                    mCallback.onFail(ErrorCodes.ERROR_CODE_NO_DATA, ErrorCodes.NO_DATA);
                    toastMgr.builder.display("没有合适的店铺", 1);

                }else if (json2FirstPageShopBeanList.get(0).isHasData() == false)
                {
                    mCallback.onFail(ErrorCodes.ERROR_CODE_NO_DATA, ErrorCodes.NO_DATA);
                    toastMgr.builder.display("没有合适的店铺", 1);
                }
                else
                {
                    mCallback.onSuccess(json2FirstPageShopBeanList);
                }
            }
        }
    }
}
