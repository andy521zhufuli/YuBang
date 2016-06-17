package com.car.yubangapk.network.myHttpReq.wallet;

import com.car.yubangapk.configs.Configs;
import com.car.yubangapk.configs.ErrorCodes;
import com.car.yubangapk.json.bean.OrderDetail.BaseJson;
import com.car.yubangapk.json.bean.wallet.Json2MyWalletBean;
import com.car.yubangapk.json.formatJson.FormatBaseJson;
import com.car.yubangapk.json.formatJson.formatWallet.Json2MyWallet;
import com.car.yubangapk.network.myHttpReq.HttpReqCallback;
import com.car.yubangapk.network.okhttp.OkHttpUtils;
import com.car.yubangapk.network.okhttp.callback.StringCallback;
import com.car.yubangapk.utils.L;
import com.car.yubangapk.utils.toastMgr;

import okhttp3.Call;

/**
 * Created by andy on 16/6/17.
 *
 * 获取用户钱包
 */
public class HttpReqGetUserWallet
{
    private HttpReqCallback mCallback;

    public HttpReqGetUserWallet()
    {

    }

    public void setCallback(HttpReqCallback callback)
    {
        this.mCallback = callback;
    }

    public void getUserWallet(String userid)
    {
        OkHttpUtils.post()
                .url(Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GET_USER_WALLET)
                .addParams("reqModel.userid", userid)
                .build()
                .execute(new CommentCallback());
        L.i("HttpReqGetUserWallet", "获取用户钱包 url = " + Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GET_USER_WALLET + "?"
                        + "reqModel.userid=" + userid

        );
    }

    class CommentCallback extends StringCallback
    {

        @Override
        public void onError(Call call, Exception e) {
            mCallback.onFail(ErrorCodes.ERROR_CODE_NETWORK, ErrorCodes.NETWORK_ERROR);
        }

        @Override
        public void onResponse(String response) {
            L.i("HttpReqGetUserWallet", "获取用户钱包 json = " + response);
            Json2MyWallet myWallet = new Json2MyWallet(response);
            Json2MyWalletBean myWalletBean = myWallet.getMyWallet();

            if (myWalletBean == null)
            {
                mCallback.onFail(ErrorCodes.ERROR_CODE_SERVER_ERROR, ErrorCodes.SERVER_ERROR);
                toastMgr.builder.display("服务器错误", 1);
            }
            else
            {
                if (myWalletBean.getReturnCode() == 0)
                {
                    mCallback.onSuccess(myWalletBean);
                }
                else
                {
                    mCallback.onFail(myWalletBean.getReturnCode(), myWalletBean.getMessage());
                    toastMgr.builder.display(myWalletBean.getMessage(), 1);
                }
            }

        }
    }
}
