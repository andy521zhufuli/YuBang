//package com.car.yubangapk.network.myHttp;
//
//import com.car.yubangapk.configs.Configs;
//import com.car.yubangapk.configs.ErrorCodes;
//import com.car.yubangapk.json.bean.Json2AddressBean;
//import com.car.yubangapk.json.bean.Json2ProductPackageBean;
//import com.car.yubangapk.json.formatJson.Json2Address;
//import com.car.yubangapk.network.okhttp.OkHttpUtils;
//import com.car.yubangapk.network.okhttp.callback.StringCallback;
//import com.car.yubangapk.utils.L;
//import com.car.yubangapk.utils.SPUtils;
//
//import java.util.List;
//
//import okhttp3.Call;
//
///**
// *
// * 确认订单界面  去拿地址信息 联系人信息
// *
// * Created by andy on 16/4/16.
// */
//public class HttpReqGetAddressConformOrder {
//
//
//    private String TAG = HttpReqGetAddressConformOrder.class.getSimpleName();
//
//    private String mUserId;
//    private String mReqMethod;
//    private String mName;
//    private String mPhone;
//    private String mAddressId;
//
//    private GetAddressCallback callback;
//
//
//    private static final String ARGS_USER_NAME = "addressReqModel.userName";//0 1 必填
//    private static final String ARGS_PHONE_NUM = "addressReqModel.phoneNum";//0 1 必填
//    private static final String ARGS_FUNCTION = "addressReqModel.function";//0添加，1修改，2删除，3查询，4修改默认地址
//    private static final String ARGS_ADDRESS_ID = "addressReqModel.addressId";//2，4必填
//    private static final String ARGS_USER_ID = "addressReqModel.userId";
//
//
//    public HttpReqGetAddressConformOrder(String userid, String requestMethod, String name, String phone, String addressid)
//    {
//        this.mUserId = userid;
//        this.mReqMethod = requestMethod;
//        this.mName = name;
//        this.mPhone = phone;
//        this.mAddressId = addressid;
//    }
//
//    public void setCallback(GetAddressCallback callback)
//    {
//        this.callback = callback;
//    }
//
//
//    public void getAddressPeopleInfo()
//    {
//
//
//        OkHttpUtils.post()
//                .url(Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GET_ADDRESS)
//                .addParams(ARGS_FUNCTION,mReqMethod)
//                .addParams(ARGS_USER_ID, mUserId)
//                .build()
//                .execute(new GetAddressCallBack());
//
//        L.i(TAG, "获取address url = " + Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GET_ADDRESS + "?"
//                + ARGS_FUNCTION + "=" + mReqMethod
//                + "&" + ARGS_USER_ID + "=" + mUserId
//        );
//    }
//
//
//    class GetAddressCallBack extends StringCallback
//    {
//
//        @Override
//        public void onError(Call call, Exception e) {
//            L.d(TAG, "网络错误");
//            callback.onGetAddressFail(ErrorCodes.ERROR_CODE_NETWORK);
//        }
//
//        @Override
//        public void onResponse(String response) {
//            //{"defaultAddress":{},"addresses":[],"isJson":true,"isReturnStr":false,"returnCode":-100,"returneMsg":"SUCCESS","message":"服务器错误"}
//            // {"defaultAddress":{},"addresses":[],"isJson":true,"isReturnStr":false,"returnCode":1,"returneMsg":"SUCCESS","message":"该用户无收货地址"}
//            //address json = {"defaultAddress":{"id":"0","CUserid":"bf8eb70b-c29f-41c8-a9bc-7e7dd56880bb","name":"朱福利","phone":"123456789","isDefaule":1},"addresses":[{"id":"0","CUserid":"bf8eb70b-c29f-41c8-a9bc-7e7dd56880bb","name":"朱福利","phone":"123456789","isDefaule":1}],"isJson":true,"isReturnStr":false,"returnCode":0,"returneMsg":"SUCCESS"}
//            L.d(TAG, "address json = " + response);
//
//            Json2Address address = new Json2Address(response);
//            Json2AddressBean addressBean = address.getAddress();
//            if (addressBean == null)
//            {
//                callback.onGetAddressFail(ErrorCodes.ERROR_CODE_LOW_VERSION);
//            }
//            else
//            {
//                if (addressBean.getReturnCode() == ErrorCodes.ERROR_CODE_NOT_LOGIN)
//                {
//                    callback.onGetAddressFail(ErrorCodes.ERROR_CODE_NOT_LOGIN);
//                }
//                else if (addressBean.getAddresses().size() == 0)
//                {
//                    callback.onGetAddressFail(ErrorCodes.ERROR_CODE_NO_ADDRESS);
//                }
//                else
//                {
//                    callback.onGetAddressSucces(addressBean);
//                }
//            }
//
//
//
//        }
//    }
//
//
//
//
//
//    /**
//     * 调用成功或者失败的接口  用来回调
//     */
//    public interface GetAddressCallback
//    {
//        void onGetAddressSucces(Json2AddressBean addressBean);//得到的产品包列表
//        void onGetAddressFail(int errorCode);//失败的理由
//    }
//}
