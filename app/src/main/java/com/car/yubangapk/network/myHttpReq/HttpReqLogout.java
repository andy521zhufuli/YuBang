package com.car.yubangapk.network.myHttpReq;

import android.content.Context;

import com.car.yubangapk.configs.Configs;
import com.car.yubangapk.configs.ErrorCodes;
import com.car.yubangapk.json.bean.GetVerifyCodeBean;
import com.car.yubangapk.json.bean.WxShareBean;
import com.car.yubangapk.json.formatJson.Json2WxShare;
import com.car.yubangapk.json.formatJson.VerifyCode;
import com.car.yubangapk.network.okhttp.OkHttpUtils;
import com.car.yubangapk.network.okhttp.callback.StringCallback;
import com.car.yubangapk.utils.L;
import com.car.yubangapk.utils.toastMgr;

import okhttp3.Call;

/**
 * Created by andy on 16/5/10.
 */
public class HttpReqLogout
{
    private Context mContext;
    private HttpReqCallback mCallback;
    int mType;

    public HttpReqLogout(Context context)
    {
        this.mContext = context;

    }


    public void setCallback(HttpReqCallback callback)
    {
        this.mCallback = callback;
    }

    /**
     * 拿到启动后配置信息
     */
    public void logout(String userid)
    {
        OkHttpUtils.post()
                .url(Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_LOGOUT)
                .addParams("reqModel.userid", userid)
                .build().execute(new ShareCallback());
    }

    /**
     * 获取配置信息回调
     */
    public class ShareCallback extends StringCallback
    {

        @Override
        public void onError(Call call, Exception e)
        {
            L.d("", "logout error====" + e.toString());
            mCallback.onFail(ErrorCodes.ERROR_CODE_NETWORK, ErrorCodes.NETWORK_ERROR);
            toastMgr.builder.display("网络错误", 1);

        }

        @Override
        public void onResponse(String response)
        {
            L.d("", "logout json ====" + response);

            VerifyCode json2WxShare = new VerifyCode(response);
            GetVerifyCodeBean wxShareBean = json2WxShare.getVerifyCodeObj();

            if (wxShareBean == null)
            {
                mCallback.onFail(ErrorCodes.ERROR_CODE_SERVER_ERROR, ErrorCodes.SERVER_ERROR);
                toastMgr.builder.display("服务器错误", 1);
            }
            else
            {
                if (wxShareBean.getReturnCode() != 0)
                {
                    mCallback.onFail(wxShareBean.getReturnCode(), wxShareBean.getMessage());
                    toastMgr.builder.display(wxShareBean.getMessage(), 1);
                }
                else
                {
                    mCallback.onSuccess(wxShareBean);
                }
            }

        }
    }
}
