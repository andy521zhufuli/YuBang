package com.car.yubangapk.network.myHttpReq;

import com.car.yubangapk.configs.Configs;
import com.car.yubangapk.configs.ErrorCodes;
import com.car.yubangapk.json.FormatJson;
import com.car.yubangapk.json.bean.ShoppingmallSpeciesePicBean;
import com.car.yubangapk.network.okhttp.OkHttpUtils;
import com.car.yubangapk.network.okhttp.callback.StringCallback;
import com.car.yubangapk.utils.L;
import com.car.yubangapk.utils.toastMgr;

import java.util.List;

import okhttp3.Call;

/**
 * Created by andy on 16/4/22.
 *
 * 商城首页 拿logicalService 中间分类
 */
public class HttpReqGetLogicalService {


    private static final String TAG = HttpReqGetLogicalService.class.getSimpleName();
    private HttpReqCallback mCallback;

    public HttpReqGetLogicalService()
    {

    }

    public void setCallback(HttpReqCallback callback)
    {
        this.mCallback = callback;
    }



    /**
     * 网络连接去拿banner的图片
     */
    public void getLogicalService()
    {
        /**
         * 去拿轮播图片
         */
        OkHttpUtils.post()
                .url(Configs.IP_ADDRESS+Configs.IP_ADDRESS_ACTION_GETDATA)
                .addParams("sqlName", "clientSearchLogicalService")
                .addParams("dataReqModel.args.needTotal","needTotal")
                .build()
                .execute(new MallSpecieseCallback());
        L.i(TAG, "Species url = " + Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GETDATA + "?" + "sqlName=clientSearchLogicalService&dataReqModel.args.needTotal=clientSearchLogicalService");

    }
    /**
     * 中部图标 分类
     */
    public class MallSpecieseCallback extends StringCallback
    {

        @Override
        public void onError(Call call, Exception e) {
            toastMgr.builder.display("网络连接有问题, 请教检查您的网络设置",1);
            mCallback.onFail(ErrorCodes.ERROR_CODE_NETWORK, ErrorCodes.NETWORK_ERROR);
        }

        @Override
        public void onResponse(String response) {
            L.d(TAG + "logical service  json", response);
            FormatJson formatJson = new FormatJson(response);
            List<ShoppingmallSpeciesePicBean> picList;
            picList = formatJson.getShoppingMallSpeciesePic();
            if (picList == null)
            {
                mCallback.onFail(ErrorCodes.ERROR_CODE_LOW_VERSION, ErrorCodes.LOW_VERSION_TO_UPGRADE_APP);
                toastMgr.builder.display("服务器错误,请稍后再试,", 1);
                return;
            }
            else
            {
                mCallback.onSuccess(picList);
            }
        }

        @Override
        public void inProgress(float progress) {
            super.inProgress(progress);
            L.d(TAG + "MallSpecieseCallback  progress" + "  " + progress * 100);
        }
    }









}
