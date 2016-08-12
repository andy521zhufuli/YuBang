package com.tec.android.network;

import android.content.Context;

import com.sales.vo.ShareReq;
import com.sales.vo.UploadImgReq;
import com.tec.android.configs.Configs;
import com.tec.android.utils.L;

import java.io.File;

/**
 * Created by andy on 15/8/24.
 * 上传图片的网络请求
 */
public class UploadImgReqHttp
{
    private Context mContext;

    public UploadImgReqHttp(Context mContext) {
        this.mContext = mContext;
    }

    public void sendAndUploadImgReqHttpJson(final DoingSuccessCallback successCallback, final DoingFailedCallback failedCallback,
                                            File imageFile, String imageName)
    {
        UploadImgReq uploadImgReq = new UploadImgReq();
        //上传图片
        String reqJson = ReqSetProperties.setUploadImgReqProperties(uploadImgReq, "json", imageFile, imageName);
        L.i("UploadImgReqHttp sendAndUploadImgReqHttpJson reqJson = " + reqJson);
        L.i("UploadImgReqHttp sendAndUploadImgReqHttpJson ip address = " + Configs.SERVER_IP_ADDRESS + uploadImgReq.getMsgtype());
        new NetConnectionReq(Configs.SERVER_IP_ADDRESS + uploadImgReq.getMsgtype(),
                new NetConnectionReq.SuccessCallback() {
                    @Override
                    public void onSuccess(String result) {
                        if (successCallback != null)
                        {
                            successCallback.onSuccess(result);
                        }
                    }
                },
                new NetConnectionReq.FailCallback() {
                    @Override
                    public void onFail(String error) {
                        if (failedCallback != null)
                        {
                            //每次从这里回调的 error都是空的
                            failedCallback.onFail(error);
                        }
                    }
                },
                reqJson
        );
    }


    /**
     *执行成功的时候回调
     */
    public interface DoingSuccessCallback
    {
        /**
         *
         * @param result 成功的时候, 返回的应该是html
         */
        void onSuccess(String result);
    }

    /**
     * 执行失败时候的回调
     */
    public interface DoingFailedCallback
    {
        /**
         *
         * @param resultMsg 失败的信息, 或者是失败码
         */
        void onFail(String resultMsg);
    }


}
