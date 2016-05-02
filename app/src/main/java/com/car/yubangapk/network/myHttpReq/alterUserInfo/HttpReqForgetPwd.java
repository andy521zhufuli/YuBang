package com.car.yubangapk.network.myHttpReq.alterUserInfo;

import com.car.yubangapk.configs.Configs;
import com.car.yubangapk.configs.ErrorCodes;
import com.car.yubangapk.json.bean.GetVerifyCodeBean;
import com.car.yubangapk.json.bean.VerifyCodeBean;
import com.car.yubangapk.json.formatJson.VerifyCode;
import com.car.yubangapk.network.okhttp.OkHttpUtils;
import com.car.yubangapk.network.okhttp.builder.PostFormBuilder;
import com.car.yubangapk.network.okhttp.callback.StringCallback;
import com.car.yubangapk.utils.L;
import com.car.yubangapk.utils.toastMgr;

import okhttp3.Call;

/**
 * Created by andy on 16/4/28.
 *
 * 修改密码 获取验证码
 *
 */
public class HttpReqForgetPwd extends StringCallback
{

    String PHONENUM = "userReq.phoneNum";       //
    String PWD_NEW = "userReq.passWorld";

    String VERIFY_CODE = "userReq.verfityCode";
    String ACTION_CODE = "userReq.actionType";  //这个0 为修改   1为获取验证码
    //returnCode说明0成功，-2参数错误，-3用户不存在，-4修改密码参数错误，-5手机号已存在，-6密码或验证码错误

    HttpReqAlterUserInfoCallback mCallback;


    public void setCallback(HttpReqAlterUserInfoCallback callback)
    {
        this.mCallback = callback;
    }

    public HttpReqForgetPwd()
    {

    }

    int type = -1;

    public void getVerifyCode(String phoneNum)
    {
        PostFormBuilder builder = OkHttpUtils.post().url(Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_FORGET_PWD);
        builder = builder.addParams(PHONENUM, phoneNum);
        builder = builder.addParams(ACTION_CODE, "1");
        builder.build().execute(this);
        String url = Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_FORGET_PWD + "?" + PHONENUM + "=" + phoneNum;
        type = 1;
        url += "&" + ACTION_CODE + "=1";
        L.d("重置密码,获取验证码  url = " + url);
    }


    public void resetPwd(String phoneNum, String verfiyCode, String newPwd)
    {
        PostFormBuilder builder = OkHttpUtils.post().url(Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_FORGET_PWD);
        builder = builder.addParams(PHONENUM, phoneNum);
        builder = builder.addParams(VERIFY_CODE, verfiyCode);
        builder = builder.addParams(PWD_NEW, newPwd);
        builder = builder.addParams(ACTION_CODE, "0");
        String url = Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_FORGET_PWD + "?" + PHONENUM + "=" + phoneNum;
        url += "&" + VERIFY_CODE + "=" + verfiyCode;
        url += "&" + PWD_NEW + "=" + newPwd;
        url += "&" + ACTION_CODE + "=0";
        L.d("重置密码,  url = " + url);
        type = 0;
        builder.build().execute(this);
    }

    @Override
    public void onError(Call call, Exception e) {
        mCallback.onFail(ErrorCodes.ERROR_CODE_NETWORK, ErrorCodes.NETWORK_ERROR);
        toastMgr.builder.display(ErrorCodes.NETWORK_ERROR, 1);
    }

    @Override
    public void onResponse(String response) {
        L.d("重置密码,  json = " + response);
        VerifyCode verifyCode = new VerifyCode(response);
        GetVerifyCodeBean bean = verifyCode.getVerifyCodeObj();

        if (bean.getReturnCode() == 0)
        {
            mCallback.onSuccess(bean, type);
        }
        else if (bean.getReturnCode() == -4)
        {
            mCallback.onFail(-4, "用户帐号异常，请联系客服");
            toastMgr.builder.display("用户帐号异常，请联系客服", 1);
        }
        else if (bean.getReturnCode() == -7)
        {
            mCallback.onFail(-7, "验证码已经发送,请勿多次发送");
            toastMgr.builder.display("验证码已经发送,请勿多次发送", 1);
        }
        else
        {
            mCallback.onFail(bean.getReturnCode(), bean.getMessage());
        }

    }
}
