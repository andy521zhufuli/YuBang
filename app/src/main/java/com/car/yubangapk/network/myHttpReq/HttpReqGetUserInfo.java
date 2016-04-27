package com.car.yubangapk.network.myHttpReq;

import com.car.yubangapk.configs.Configs;
import com.car.yubangapk.configs.ErrorCodes;
import com.car.yubangapk.json.bean.Json2InstallShopBean;
import com.car.yubangapk.json.bean.Json2InstallShopModelsBean;
import com.car.yubangapk.json.bean.Json2MyUserInfoBean;
import com.car.yubangapk.json.formatJson.Json2MyUserInfo;
import com.car.yubangapk.network.okhttp.OkHttpUtils;
import com.car.yubangapk.network.okhttp.builder.PostFormBuilder;
import com.car.yubangapk.network.okhttp.callback.StringCallback;
import com.car.yubangapk.utils.L;

import java.util.List;

import okhttp3.Call;

/**
 * Created by andy on 16/4/27.
 *
 * 我的界面  已经登录 去h后台拿数据 *
 */
public class HttpReqGetUserInfo
{


    private String TAG = HttpReqGetUserInfo.class.getSimpleName();



    private HttpReqCallback callback;


    private static final String ARGS_USERID                 = "req.userid";

    public HttpReqGetUserInfo()
    {
    }

    public void setListener(HttpReqCallback listener)
    {
        this.callback = listener;
    }

    public void getUserInfo(String userid)
    {

        PostFormBuilder builder = OkHttpUtils.post().url(Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GET_USER_INFO)
                .addParams(ARGS_USERID, userid);
        builder.build().execute(new GetUserInfo());

        L.i(TAG, "获取 userInfo url = " + Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GET_USER_INFO + "?"
                        +  ARGS_USERID + "=" + userid
        );
    }

    class GetUserInfo extends StringCallback
    {

        @Override
        public void onError(Call call, Exception e) {

            callback.onFail(ErrorCodes.ERROR_CODE_NETWORK, ErrorCodes.NETWORK_ERROR);
        }

        @Override
        public void onResponse(String response) {
            L.d(TAG, "获取 userInfo json = " + response);

            Json2MyUserInfo userInfo = new Json2MyUserInfo(response);
            Json2MyUserInfoBean userInfoBean = userInfo.getUserInfo();
            if (userInfoBean == null)
            {
                callback.onFail(ErrorCodes.ERROR_CODE_SERVER_ERROR, ErrorCodes.SERVER_ERROR);
            }
            else
            {
                if (userInfoBean.isLogined() == false)//不正确 只0正确 那就返回错误码跟消息提示
                {
                    callback.onFail(ErrorCodes.ERROR_CODE_NOT_LOGIN, ErrorCodes.NOT_LOGIN);//没有店铺
                }
                else
                {
                    callback.onSuccess(userInfoBean);
                }

            }
        }
    }
}
