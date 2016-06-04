package com.car.yubangapk.network.myHttpReq;

import com.car.yubangapk.configs.Configs;
import com.car.yubangapk.configs.ErrorCodes;
import com.car.yubangapk.json.bean.Json2CityBean;
import com.car.yubangapk.json.bean.Json2ProvinceBean;
import com.car.yubangapk.json.formatJson.Json2City;
import com.car.yubangapk.json.formatJson.Json2Province;
import com.car.yubangapk.network.okhttp.OkHttpUtils;
import com.car.yubangapk.network.okhttp.callback.StringCallback;
import com.car.yubangapk.utils.L;
import com.car.yubangapk.utils.toastMgr;

import java.util.List;

import okhttp3.Call;

/**
 * Created by andy on 16/6/4.
 *
 * 获取省市区
 *
 */
public class HttpReqGetRegion
{
    private HttpReqCallback mCallback;
    private int TYPE = 0;
    public static int TYPE_PROVINCE = 1;
    public static int TYPE_CITY = 2;

    public HttpReqGetRegion()
    {}

    public void setCallback(HttpReqCallback callback)
    {
        this.mCallback = callback;
    }

    public void setCurrentType(int type)
    {
        this.TYPE = type;
    }


    public void getProvince()
    {
        OkHttpUtils.post()
                .url(Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GET_PROVINCE)
                .addParams("sqlName", "clientGetProvince")
                .addParams("dataReqModel.args.needTotal", "needTotal")
                .build()
                .execute(new GetRegionCallBack());

        L.i("HttpReqGetRegion", "获取省 url = " + Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GET_PROVINCE + "?"
                        + "sqlName" + "=" + "clientGetProvince"
                        + "&" + "dataReqModel.args.needTotal" + "=" + "needTotal"
        );
    }

    public void getCity(String patent_ID)
    {
        OkHttpUtils.post()
                .url(Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GET_CITY)
                .addParams("sqlName", "clientGetRegion")
                .addParams("dataReqModel.args.needTotal", "needTotal")
                .addParams("dataReqModel.args.PARENT_ID", patent_ID)
                .build()
                .execute(new GetRegionCallBack());
        L.i("HttpReqGetRegion", "获取市 url = " + Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GET_CITY + "?"
                        + "sqlName" + "=" + "clientGetRegion"
                        + "&" + "dataReqModel.args.needTotal" + "=" + "needTotal"
                        + "&" + "dataReqModel.args.PARENT_ID" + "=" + "patent_ID"
        );
    }

    class GetRegionCallBack extends StringCallback
    {

        @Override
        public void onError(Call call, Exception e) {
            mCallback.onFail(ErrorCodes.ERROR_CODE_NETWORK, ErrorCodes.NETWORK_ERROR);
        }

        @Override
        public void onResponse(String response) {
            L.i("HttpReqGetRegion", "获取省市区json = " + response);

            if (TYPE == TYPE_PROVINCE)
            {
                //省
                Json2Province json2Province = new Json2Province(response);
                List<Json2ProvinceBean> provinceBeans = json2Province.getProvince();
                if (provinceBeans == null)
                {
                    mCallback.onFail(ErrorCodes.ERROR_CODE_SERVER_ERROR, ErrorCodes.SERVER_ERROR);
                    toastMgr.builder.display(ErrorCodes.NO_DATA, 1);
                }
                else
                {
                    if (provinceBeans.get(0).isHasData() == false)
                    {
                        mCallback.onFail(ErrorCodes.ERROR_CODE_NO_DATA, ErrorCodes.NO_DATA);
                        toastMgr.builder.display(ErrorCodes.NO_DATA, 1);
                    }
                    else
                    {
                        mCallback.onSuccess(provinceBeans);
                    }
                }
            }
            else
            {
                Json2City json2City = new Json2City(response);
                List<Json2CityBean> cityBeans = json2City.getCity();
                if (cityBeans == null)
                {
                    mCallback.onFail(ErrorCodes.ERROR_CODE_SERVER_ERROR, ErrorCodes.SERVER_ERROR);
                    toastMgr.builder.display(ErrorCodes.NO_DATA, 1);
                }
                else
                {
                    if (cityBeans.get(0).isHasData() == false)
                    {
                        mCallback.onFail(ErrorCodes.ERROR_CODE_NO_DATA, ErrorCodes.NO_DATA);
                        toastMgr.builder.display(ErrorCodes.NO_DATA, 1);
                    }
                    else
                    {
                        mCallback.onSuccess(cityBeans);
                    }
                }
            }


        }
    }


}
