package com.car.yubangapk.network.myHttpReq.alterUserInfo;

import android.widget.Toast;

import com.car.yubangapk.configs.Configs;
import com.car.yubangapk.configs.ErrorCodes;
import com.car.yubangapk.json.bean.GetVerifyCodeBean;
import com.car.yubangapk.json.bean.Json2LoginBean;
import com.car.yubangapk.json.formatJson.Json2Login;
import com.car.yubangapk.json.formatJson.VerifyCode;
import com.car.yubangapk.network.myHttpReq.HttpReqCallback;
import com.car.yubangapk.network.okhttp.OkHttpUtils;
import com.car.yubangapk.network.okhttp.callback.MyObjectStringCallback;
import com.car.yubangapk.network.okhttp.callback.StringCallback;
import com.car.yubangapk.utils.L;
import com.car.yubangapk.utils.SPUtils;
import com.car.yubangapk.utils.toastMgr;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by andy on 16/4/28.
 */
public class HttpReqUploadUserPic extends StringCallback {
    String USERID = "userReq.userid";           //用户id必填
    String FILE_CODE = "userReq.fileCode";       //这个值填6

    String PHOTO = "userReq.photo";       //修改电话号码时必填	新电话号码

    String FILE_NAME = "userReq.photoFileName";//图片名

    String PHOTO_CONTENT_TYPE = "userReq.photoContentType"; //图片类型



    HttpReqCallback mCallback;


    public void setCallback(HttpReqCallback callback)
    {
        this.mCallback = callback;
    }


    public void uploadPic(String userid, String photoPath)
    {
        File file = new File(photoPath);

        if (!file.exists())
        {
            toastMgr.builder.display("文件不存在，请修改文件路径", Toast.LENGTH_SHORT);
            mCallback.onFail(ErrorCodes.ERROR_CODE_FILE_NOT_EXIT, "文件不存在，请修改文件路径");
            return;
        }

        Map<String, String> header = new HashMap<>();
        header.put("enctype", "multipart/form-data");

        Map<String, String> params = new HashMap<>();
        params.put(USERID, userid);
        params.put(FILE_CODE, "6");
        OkHttpUtils.post()//
                .url(Configs.IP_ADDRESS + Configs.IP_ADDRESSS_ACTION_UP_LOAD_FILE)
                .addFile("userReq.photo", file.getName(), file)//
                .params(params)
                .build()//
                .execute(this);
    }


    @Override
    public void onError(Call call, Exception e) {
        mCallback.onFail(ErrorCodes.ERROR_CODE_NETWORK, ErrorCodes.NETWORK_ERROR);
    }

    @Override
    public void onResponse(String response) {
        L.d("修改个人信息, 上传照片 json = " + response);
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
}
