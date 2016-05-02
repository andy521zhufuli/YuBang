package com.car.yubangapk.network.myHttpReq;

import android.content.Context;
import android.view.View;

import com.car.yubangapk.configs.Configs;
import com.car.yubangapk.configs.ErrorCodes;
import com.car.yubangapk.json.bean.Json2LoginBean;
import com.car.yubangapk.json.formatJson.Json2Login;
import com.car.yubangapk.network.okhttp.OkHttpUtils;
import com.car.yubangapk.network.okhttp.callback.StringCallback;
import com.car.yubangapk.utils.L;
import com.car.yubangapk.utils.toastMgr;
import com.car.yubangapk.view.AlertDialog;

import okhttp3.Call;

/**
 * Created by andy on 16/5/2.
 */
public class HttpReqUploadCarType
{
    private Context mContext;
    private String TAG = HttpReqUploadCarType.class.getSimpleName();
    HttpReqUploadCarTypeCallback mCallback;

    public HttpReqUploadCarType(Context context)
    {
        this.mContext = context;
    }
    public void setCallback(HttpReqUploadCarTypeCallback callback){
        this.mCallback = callback;
    }

    /**
     * 上传carType
     * @param _carCompany
     * @param _carBrand
     * @param _carSeries
     * @param _produceYear
     * @param _carCapacity
     */
    public void upLoadCarType( String _carCompany,
                                String _carBrand,
                                String _carSeries,
                                String _produceYear,
                                String _carCapacity,
                                String _userid
    )
    {

        OkHttpUtils.post()
                .url(Configs.IP_ADDRESS + Configs.IP_ADDRESSS_ACTION_UP_LOAD_CAR_TYPE)
                .addParams("userReq.carCompany",  _carCompany)
                .addParams("userReq.carBrand",    _carBrand)
                .addParams("userReq.carSeries", _carSeries)
                .addParams("userReq.produceYear", _produceYear)
                .addParams("userReq.carCapacity", _carCapacity)
                .addParams("userReq.userid", _userid)
                .build()
                .execute(new UploadCarTypeCallback());
        L.i(TAG, "上传carType url = " + Configs.IP_ADDRESS + Configs.IP_ADDRESSS_ACTION_UP_LOAD_CAR_TYPE + "?"
                        + "userReq.carCompany=" + _carCompany
                        + "&userReq.carBrand=" + _carBrand
                        + "&userReq.carSeries=" + _carSeries
                        + "&userReq.produceYear=" + _produceYear
                        + "&userReq.carCapacity=" + _carCapacity
                        + "&userReq.userid=" + _userid
        );

    }

    class UploadCarTypeCallback extends StringCallback
    {

        @Override
        public void onError(Call call, Exception e) {
            toastMgr.builder.display("网络错误,请检查网络!,上传车型失败", 1);
            mCallback.onUploadCarTypeFail(ErrorCodes.ERROR_CODE_NETWORK, "网络连接出错");
        }

        @Override
        public void onResponse(String response) {
            L.d(TAG, "上传汽车信息 json -= " + response);
            Json2Login json2Login = new Json2Login(response);

            Json2LoginBean json2LoginBean = json2Login.getLoginObj();

            if (json2LoginBean == null)
            {
                mCallback.onUploadCarTypeFail(ErrorCodes.ERROR_CODE_SERVER_ERROR, "服务器错误");
            }
            else
            {
                if (json2LoginBean.getReturnCode() == 0)
                {
                    //登陆成功
                    //将最新的信息保存
                    Configs.putLoginedInfo(mContext, json2LoginBean);
                    L.d(TAG, "上传车辆信息成功, 保存车辆信息成功");
                    mCallback.onUploadCarTypeSuccess(json2LoginBean);
                    toastMgr.builder.display("上传车辆信息成功, 保存车辆信息成功",1);
                }
                else if (json2LoginBean.getReturnCode() == 100)
                {
                    //未登录
                    toastMgr.builder.display(json2LoginBean.getMessage(), 1);
                    mCallback.onUploadCarTypeFail(ErrorCodes.ERROR_CODE_NOT_LOGIN, ErrorCodes.NOT_LOGIN);
                }
                else if (json2LoginBean.getReturnCode() == -2)
                {
                    //查无此车
                    toastMgr.builder.display(json2LoginBean.getMessage(), 1);

                }
                else if(json2LoginBean.getReturnCode() == -1)
                {
                    toastMgr.builder.display(json2LoginBean.getMessage(), 1);
                }
            }
        }
    }


    public interface HttpReqUploadCarTypeCallback
    {
        void onUploadCarTypeFail(int errorCode, String message);
        void onUploadCarTypeSuccess(Object object);
    }
}
