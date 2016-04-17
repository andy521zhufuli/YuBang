package com.car.yubangapk.network.myHttp;

import com.car.yubangapk.configs.Configs;
import com.car.yubangapk.configs.ErrorCodes;
import com.car.yubangapk.json.bean.Json2AddressBean;
import com.car.yubangapk.json.formatJson.Json2Address;
import com.car.yubangapk.network.okhttp.OkHttpUtils;
import com.car.yubangapk.network.okhttp.callback.StringCallback;
import com.car.yubangapk.utils.L;

import okhttp3.Call;

/**
 * Created by andy on 16/4/16.
 */
public class HttpReqAddress {


    private String TAG = HttpReqAddress.class.getSimpleName();

    private String mUserId;
    private String mReqMethod;
    private String mName;
    private String mPhone;
    private String mAddressId;

    private httpReqInterface callback;


    private static final String ARGS_USER_NAME = "addressReqModel.userName";//0 1 必填
    private static final String ARGS_PHONE_NUM = "addressReqModel.phoneNum";//0 1 必填
    private static final String ARGS_FUNCTION = "addressReqModel.function";//0添加，1修改，2删除，3查询，4修改默认地址
    private static final String ARGS_ADDRESS_ID = "addressReqModel.addressId";//2，4必填
    private static final String ARGS_USER_ID = "addressReqModel.userId";


    /**
     * 构造函数
     * @param userid
     * @param requestMethod
     * @param name
     * @param phone
     * @param addressid
     */
    public HttpReqAddress(String userid, String requestMethod, String name, String phone, String addressid)
    {
        this.mUserId = userid;
        this.mReqMethod = requestMethod;
        this.mName = name;
        this.mPhone = phone;
        this.mAddressId = addressid;
    }

    /**
     * 设置回调
     * @param callback
     */
    public void setCallback(httpReqInterface callback)
    {
        this.callback = callback;
    }
    public void getAddressPeopleInfo()
    {


        OkHttpUtils.post()
                .url(Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GET_ADDRESS)
                .addParams(ARGS_FUNCTION,mReqMethod)
                .addParams(ARGS_USER_ID, mUserId)

                .build()
                .execute(new GetAddressCallBack());

        L.i(TAG, "获取address url = " + Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GET_ADDRESS + "?"
                + ARGS_FUNCTION + "=" + mReqMethod
                + "&" + ARGS_USER_ID + "=" + mUserId
        );
    }


    class GetAddressCallBack extends StringCallback
    {

        @Override
        public void onError(Call call, Exception e) {
            L.d(TAG, "网络错误");
            callback.onGetAddressFail(ErrorCodes.ERROR_CODE_NETWORK);
        }

        @Override
        public void onResponse(String response) {
            //{"defaultAddress":{},"addresses":[],"isJson":true,"isReturnStr":false,"returnCode":-100,"returneMsg":"SUCCESS","message":"服务器错误"}
            // {"defaultAddress":{},"addresses":[],"isJson":true,"isReturnStr":false,"returnCode":1,"returneMsg":"SUCCESS","message":"该用户无收货地址"}
            //address json = {"defaultAddress":{"id":"0","CUserid":"bf8eb70b-c29f-41c8-a9bc-7e7dd56880bb","name":"朱福利","phone":"123456789","isDefaule":1},"addresses":[{"id":"0","CUserid":"bf8eb70b-c29f-41c8-a9bc-7e7dd56880bb","name":"朱福利","phone":"123456789","isDefaule":1}],"isJson":true,"isReturnStr":false,"returnCode":0,"returneMsg":"SUCCESS"}
            L.d(TAG, "address json = " + response);

            Json2Address address = new Json2Address(response);
            Json2AddressBean addressBean = address.getAddress();
            if (addressBean == null)
            {
                callback.onGetAddressFail(ErrorCodes.ERROR_CODE_LOW_VERSION);
            }
            else
            {
                if (addressBean.getReturnCode() == ErrorCodes.ERROR_CODE_NOT_LOGIN)
                {
                    callback.onGetAddressFail(ErrorCodes.ERROR_CODE_NOT_LOGIN);
                }
                else if (addressBean.getAddresses().size() == 0)
                {
                    callback.onGetAddressFail(ErrorCodes.ERROR_CODE_NO_ADDRESS);
                }
                else
                {
                    callback.onGetAddressSucces(addressBean);
                }
            }
        }
    }

    /**
     * 新增地址
     */
    public void addAddress()
    {
        OkHttpUtils.post()
                .url(Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GET_ADDRESS)
                .addParams(ARGS_FUNCTION,mReqMethod)//为 0
                .addParams(ARGS_USER_NAME, mName)//必填
                .addParams(ARGS_PHONE_NUM, mPhone)//必填
                .addParams(ARGS_USER_ID, mUserId)
                .build()
                .execute(new GetAddressCallBack());

        L.i(TAG, "a增加address url = " + Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GET_ADDRESS + "?"
                + ARGS_FUNCTION + "=" + mReqMethod
                + "&" + ARGS_USER_NAME + "=" + mName
                + "&" + ARGS_PHONE_NUM + "=" + mPhone
                + "&" + ARGS_USER_ID + "=" + mUserId
        );
    }

    /**
     * 修改地址
     */
    public void modifyAddress()
    {
        OkHttpUtils.post()
                .url(Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GET_ADDRESS)
                .addParams(ARGS_FUNCTION,mReqMethod)//为 1 修改
                .addParams(ARGS_USER_NAME, mName)//必填
                .addParams(ARGS_PHONE_NUM, mPhone)//必填
                .addParams(ARGS_ADDRESS_ID, mAddressId)//必填
                .addParams(ARGS_USER_ID, mUserId)
                .build()
                .execute(new GetAddressCallBack());
        L.i(TAG, "修改address url = " + Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GET_ADDRESS + "?"
                + ARGS_FUNCTION + "=" + mReqMethod
                + "&" + ARGS_USER_NAME + "=" + mName
                + "&" + ARGS_PHONE_NUM + "=" + mPhone
                + "&" + ARGS_ADDRESS_ID + "=" + mAddressId
                + "&" + ARGS_USER_ID + "=" + mUserId
        );
    }

    public void deleteAddress()
    {
        OkHttpUtils.post()
                .url(Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GET_ADDRESS)
                .addParams(ARGS_FUNCTION,mReqMethod)//为 2
                .addParams(ARGS_USER_NAME, mName)//必填
                .addParams(ARGS_PHONE_NUM, mPhone)//必填
                .addParams(ARGS_ADDRESS_ID, mAddressId)//必填
                .addParams(ARGS_USER_ID, mUserId)
                .build()
                .execute(new GetAddressCallBack());

        L.i(TAG, "删除address url = " + Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GET_ADDRESS + "?"
                + ARGS_FUNCTION + "=" + mReqMethod
                + "&" + ARGS_USER_NAME + "=" + mName
                + "&" + ARGS_PHONE_NUM + "=" + mPhone
                + "&" + ARGS_ADDRESS_ID + "=" + mAddressId
                + "&" + ARGS_USER_ID + "=" + mUserId
        );
    }
}
