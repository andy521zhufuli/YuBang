package com.car.yubangapk.network.myHttpReq.alterUserInfo;

import com.car.yubangapk.configs.Configs;
import com.car.yubangapk.configs.ErrorCodes;
import com.car.yubangapk.json.bean.GetVerifyCodeBean;
import com.car.yubangapk.json.bean.Json2LoginBean;
import com.car.yubangapk.json.bean.VerifyCodeBean;
import com.car.yubangapk.json.formatJson.Json2Login;
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
 * 修改个人信息
 *
 */
public class HttpReqAlterUserInfo extends StringCallback
{


//    Json
  //  {returnCode=,message=,}

    String USERID = "userReq.userid";           //用户id必填
    String USERNAME = "userReq.userName";       //修改用户名时必填	新用户名
    String PHONENUM = "userReq.phoneNum";       //修改电话号码时必填	新电话号码
    String PASSWORD = "userReq.passWorld";      //修改密码时必填	新密码
    String OLD_PASSWORD = "userReq.oldPassWorld";//修改密码时必填	旧密码
    String VERIFY_CODE = "userReq.verfityCode"; //修改密码时必填	修改密码的验证吗
    String ACTION_CODE = "userReq.actionType";  //这个0 为修改   1为获取验证码
    //returnCode说明0成功，-2参数错误，-3用户不存在，-4修改密码参数错误，-5手机号已存在，-6密码或验证码错误


    HttpReqAlterUserInfoCallback mCallback;


    public void setCallback(HttpReqAlterUserInfoCallback callback)
    {
        this.mCallback = callback;
    }

    /**
     *
     * @param userid 必填
     * @param userName
     * @param phoneNum
     * @param password
     * @param old_password
     * @param verifyCode
     */
    public void alterUserPwd(String userid, String userName, String phoneNum, String password, String old_password, String verifyCode, String actionType)
    {


        PostFormBuilder builder = OkHttpUtils.post().url(Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_ALTER_USER_INFO);
        builder = builder.addParams(USERID, userid);
        if (userName != null)
        {
            builder = builder.addParams(USERNAME, userName);
        }
        if (phoneNum != null)
        {
            builder = builder.addParams(PHONENUM, phoneNum);
        }

        if (password != null)
        {
            builder = builder.addParams(PASSWORD, password);
        }


        if (old_password != null)
        {
            builder = builder.addParams(OLD_PASSWORD, old_password);
        }

        if (verifyCode != null)
        {
            builder = builder.addParams(VERIFY_CODE, verifyCode);
        }

        builder = builder.addParams(ACTION_CODE, actionType);

        builder.build().execute(new ALterUserInfoCallback());
    }


    public void alterUserBaseInfo(String userid, String userName, String phoneNum, String actionType)
    {
        PostFormBuilder builder = OkHttpUtils.post().url(Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_ALTER_USER_INFO);
        builder = builder.addParams(USERID, userid);
        String url = Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_ALTER_USER_INFO + "?" + USERID + "=" + userid;
        if (userName != null)
        {
            builder = builder.addParams(USERNAME, userName);
            url += "&" + USERNAME + "=" + userName;
        }
        if (phoneNum != null)
        {
            builder = builder.addParams(PHONENUM, phoneNum);
            url += "&" + PHONENUM + "=" + phoneNum;
        }


        builder = builder.addParams(ACTION_CODE, actionType);
        url += "&" + ACTION_CODE + "=" + actionType;
        L.d("修改用户名, 手机号 url = " + url);
        builder.build().execute(this);
    }


    @Override
    public void onError(Call call, Exception e) {
        mCallback.onFail(ErrorCodes.ERROR_CODE_NETWORK, ErrorCodes.NETWORK_ERROR);
    }

    @Override
    public void onResponse(String response) {
        L.d("修改用户名, 手机号 json = " + response);
        Json2Login login = new Json2Login(response);
        Json2LoginBean bean = login.getLoginObj();

        if (bean == null)
        {
            mCallback.onFail(ErrorCodes.ERROR_CODE_SERVER_ERROR, ErrorCodes.SERVER_ERROR);
        }
        else
        {
            if (bean.getReturnCode() == -1)
            {
                mCallback.onFail(ErrorCodes.ERROR_CODE_UPLOAD_USER_PIC_PARAM_ERROR, "参数错误");
                toastMgr.builder.display("参数错误",1);
            }
            else if (bean.getReturnCode() == -2)
            {
                mCallback.onFail(ErrorCodes.ERROR_CODE_PARAM_ERROR, "参数错误");
                toastMgr.builder.display("参数错误",1);
            }
            else if (bean.getReturnCode() == -3)
            {
                mCallback.onFail(ErrorCodes.ERROR_CODE_USER_NOT_EXAIT, "用户不存在");
                toastMgr.builder.display("用户不存在", 1);
            }

            else if (bean.getReturnCode() == -4)
            {
                mCallback.onFail(ErrorCodes.ERROR_CODE_USER_OLD_PWD_ERROR, "原始密码错误");
                toastMgr.builder.display("原始密码错误", 1);
            }
            else if (bean.getReturnCode() == -5)
            {
                mCallback.onFail(ErrorCodes.ERROR_CODE_USER_PHONE_NUM_EXISTED, "手机号码已存在");
                toastMgr.builder.display("手机号码已存在", 1);
            }
            else if (bean.getReturnCode() == -6)
            {
                mCallback.onFail(ErrorCodes.ERROR_CODE_USER_PHONE_VERIFY_CODE_EREROR, "验证码错误");
                toastMgr.builder.display("验证码错误", 1);
            }
            else if (bean.getReturnCode() == -7)
            {
                mCallback.onFail(ErrorCodes.ERROR_CODE_USER_VERIFY_CODE_SEND_FIAL, "验证码发送失败");
                toastMgr.builder.display("验证码发送失败", 1);
            }
            else if (bean.getReturnCode() == 0)
            {
                mCallback.onSuccess(bean);
            }
        }
    }


    class ALterUserInfoCallback extends StringCallback
    {

        @Override
        public void onError(Call call, Exception e) {
            mCallback.onFail(ErrorCodes.ERROR_CODE_NETWORK, ErrorCodes.NETWORK_ERROR);
            toastMgr.builder.display(ErrorCodes.NETWORK_ERROR, 1);
        }

        @Override
        public void onResponse(String response) {
            L.d("修改用户信息 json = ", response);
            VerifyCode verifyCode = new VerifyCode(response);
            GetVerifyCodeBean codeBean = verifyCode.getVerifyCodeObj();
            if (codeBean == null)
            {
                mCallback.onFail(ErrorCodes.ERROR_CODE_SERVER_ERROR, ErrorCodes.SERVER_ERROR);
                toastMgr.builder.display("服务器错误", 1);
            }
            else
            {
                if (codeBean.getReturnCode() == -2)
                {
                    mCallback.onFail(ErrorCodes.ERROR_CODE_PARAM_ERROR, "参数错误");
                    toastMgr.builder.display("参数错误",1);
                }
                else if (codeBean.getReturnCode() == -3)
                {
                    mCallback.onFail(ErrorCodes.ERROR_CODE_USER_NOT_EXAIT, "用户不存在");
                    toastMgr.builder.display("用户不存在", 1);
                }

                else if (codeBean.getReturnCode() == -4)
                {
                    mCallback.onFail(ErrorCodes.ERROR_CODE_USER_OLD_PWD_ERROR, "原始密码错误");
                    toastMgr.builder.display("原始密码错误", 1);
                }
                else if (codeBean.getReturnCode() == -5)
                {
                    mCallback.onFail(ErrorCodes.ERROR_CODE_USER_PHONE_NUM_EXISTED, "手机号码已存在");
                    toastMgr.builder.display("手机号码已存在", 1);
                }
                else if (codeBean.getReturnCode() == -6)
                {
                    mCallback.onFail(ErrorCodes.ERROR_CODE_USER_PHONE_VERIFY_CODE_EREROR, "验证码错误");
                    toastMgr.builder.display("验证码错误", 1);
                }
                else if (codeBean.getReturnCode() == -7)
                {
                    mCallback.onFail(ErrorCodes.ERROR_CODE_USER_VERIFY_CODE_SEND_FIAL, "验证码发送失败");
                    toastMgr.builder.display("验证码发送失败", 1);
                }
                else if (codeBean.getReturnCode() == 0)
                {
                    mCallback.onSuccess(codeBean);
                }
                else
                {
                    toastMgr.builder.display(codeBean.getMessage(),1);
                }
            }

        }
    }


}
