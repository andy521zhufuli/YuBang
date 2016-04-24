package com.car.yubangapk.network.myHttpReq;

import android.content.Context;

import com.car.yubangapk.configs.Configs;
import com.car.yubangapk.configs.ErrorCodes;
import com.car.yubangapk.json.bean.Json2RecommendPartnerUploadPhotosBean;
import com.car.yubangapk.json.formatJson.Json2RecommendPartnerUploadPhotos;
import com.car.yubangapk.network.okhttp.OkHttpUtils;
import com.car.yubangapk.network.okhttp.callback.StringCallback;
import com.car.yubangapk.utils.L;
import com.car.yubangapk.utils.SPUtils;
import com.car.yubangapk.utils.toastMgr;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by andy on 16/4/24.
 *
 * 新增合伙人  上传店铺照片, 营业执照 , 法人身份证
 *
 */
public class HttpReqRecommendPartnerUploadPhotos
{

    Context mContext;
    UploadShopPhotosCallback mCallback;
    String mFileCode;
    public HttpReqRecommendPartnerUploadPhotos(Context context)
    {
        this.mContext = context;
    }

    public void setCallback(UploadShopPhotosCallback callback)
    {
        this.mCallback = callback;
    }

    public void recommendPartnerUploadPhotos(String path, String fileCode, String shopid)
    {
        mFileCode = fileCode;
        File file = new File(path);

        if (!file.exists())
        {
            toastMgr.builder.display("文件不存在，请修改文件路径", 1);
            return;
        }
        Map<String, String> header = new HashMap<>();
        header.put("enctype", "multipart/form-data");
        String userid = Configs.getLoginedInfo(mContext).getUserid();
        if (userid.equals("") || userid == null)
        {
            toastMgr.builder.display("请重新登录",1);
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("shopReq.userid", userid);
        params.put("shopReq.photoFileName", file.getName());
        params.put("shopReq.fileCode", fileCode);
        params.put("shopReq.shopId", shopid);


        OkHttpUtils.post()//
                .url(Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GET_RECOMMEDN_SHOP_UPLOAD_SHOP_PHOTO)
                .addFile("shopReq.photo", file.getName(), file)//
                .params(params)
                .build()//
                .execute(new MyStringCallback());
    }

    /**
     * 上传照片
     */
    class MyStringCallback extends StringCallback {

        @Override
        public void onError(Call call, Exception e) {
            toastMgr.builder.display("上传失败,网络错误,请重新上传", 1);
            mCallback.onFail(ErrorCodes.ERROR_CODE_NETWORK, ErrorCodes.NETWORK_ERROR);
        }

        @Override
        public void onResponse(String response) {

            L.d("recommendPartnerUploadPhotos", response);

            Json2RecommendPartnerUploadPhotos uploadPhotos = new Json2RecommendPartnerUploadPhotos(response);
            Json2RecommendPartnerUploadPhotosBean bean = uploadPhotos.getResult();

            if (bean == null)
            {
                mCallback.onFail(ErrorCodes.ERROR_CODE_JSON_DECODE, ErrorCodes._JSON_DECODE_ERROR);
            }
            else
            {
                if (bean.getReturnCode() == 0)
                {
                    mCallback.onSuccess(bean, mFileCode);
                }
                else
                {
                    mCallback.onFail(bean.getReturnCode(), bean.getMessage());
                }
            }

        }
    }

    public interface UploadShopPhotosCallback
    {
        void onFail(int errorCode ,String message);
        void onSuccess(Json2RecommendPartnerUploadPhotosBean bean, String fileCode);
    }

}
