package com.car.yubangapk.configs;

import android.content.Context;

import com.car.yubangapk.json.bean.Json2LoginBean;
import com.car.yubangapk.utils.SPUtils;

/**
 * Created by andy on 16/2/22.
 */
public class Configs {




    //Mob SMSSdk
    public static String SMS_APPKEY = "1130ebf82262c";
    public static String SMS_APPSECRET = "7f3dedcb36d92deebcb373af921d635a";

    //网络请求地址
    //public static String IP_ADDRESS = "http://203.195.206.146/carService";
    public static String IP_ADDRESS = "http://192.168.1.7:8080/carService";

    //--------------------------网络请求action-------------------------------------
    //action
    public static String IP_ADDRESS_ACTION_GETDATA= "/getData";
    public static String IP_ADDRESS_ACTION_GETFILE= "/getFile";
    public static String IP_ADDRESS_ACTION_SYS_CONFIG= "/client/sys/getSysConfig";
    public static String IP_ADDRESS_ACTION_SEND_VERIFY_MSG= "/sendSms";
    public static String IP_ADDRESSS_ACTION_REGISTER = "/client/user/register";
    public static String IP_ADDRESSS_ACTION_LOGIN = "/client/user/login";
    public static String IP_ADDRESSS_ACTION_GET_CAR_COMPANY = "/client/user/login";
    public static String IP_ADDRESSS_ACTION_UP_LOAD_FILE = "/client/user/uploadFile";
    public static String IP_ADDRESSS_ACTION_UP_LOAD_CAR_TYPE = "/client/user/alterCuserCarType";
    public static String IP_ADDRESSS_ACTION_UP_LOAD_CAR_NUM = "/client/user/alterCarNum";
    public static String IP_ADDRESS_ACTION_GET_FIRST_PAGE_TAB= "/getData";
    public static String IP_ADDRESS_ACTION_GET_SHOP =  "/client/shop/getShop";
    public static String IP_ADDRESS_ACTION_GET_ADDRESS = "/client/address/address";
    public static String IP_ADDRESS_ACTION_GET_INSTALL_SHOP = "/client/shop/getServiceShop";
    public static String IP_ADDRESS_ACTION_GET_SERVICE_SHOP = "/client/shop/getServiceShop";//推荐合伙人
    public static String IP_ADDRESS_ACTION_GET_COUPON = "/client/order/getCoupon";
    public static String IP_ADDRESS_ACTION_GET_ORDER_PRICE = "/client/order/getOrderPrice";
    public static String IP_ADDRESS_ACTION_GET_RECOMMEDN_SHOP_CLICK = "/client/point/pointRecShop";
    public static String IP_ADDRESS_ACTION_GET_RECOMMEDN_SHOP_PUT_DETAIL_INFO = "/client/shop/recommendShop";
    public static String IP_ADDRESS_ACTION_GET_RECOMMEDN_SHOP_UPLOAD_SHOP_PHOTO = "/client/shop/uploadShopPhoto";
    public static String IP_ADDRESS_ACTION_GET_USER_ORDER = "/client/order/getUserOrder";
    public static String IP_ADDRESS_ACTION_GET_USER_INFO = "/client/user/getUserInfo";
    public static String IP_ADDRESS_ACTION_ALTER_USER_INFO = "/client/user/alterUserInfo";






    //--------------------------网络请求action-------------------------------------

    //Sputil
    public static final String carCompany = "companyId";//选择公司时候保存
    public static final String carBrand = "brandId";//选择品牌保存
    public static final String carSeries = "seriesId";//选择子品牌保存
    public static final String produceYear = "yearId";//选择年份保存
    public static final String carCapacity = "capacityId";//选择排量保存


    //--------------------------用户登录后的信息-------------------------------------
    /**
     * 用户登录后的信息
     */
    public static final String FILE_USER_INFO = "user_info";
    //--------------------------用户登录后的信息-------------------------------------


    //--------------------------用户登陆后返回的信息字段-------------------------------------
    /**
     * 用户id
     */
    public static final String userid       = "userid";
    public static final String isJson       = "isJson";
    public static final String isReturnStr  = "isReturnStr";
    public static final String returnCode   = "returnCode";//  0未审核   1 审核   合伙人状态 0.审核中 1.已通过 2.不通过 3.已忽略 4.已屏蔽 5.等待上传资质
    public static final String returneMsg   = "returneMsg";
    public static final String message      = "message";
    public static final String carType      = "carType";
    public static final String name         = "name";
    public static final String status       = "status";
    //--------------------------用户登陆后返回的信息字段-------------------------------------

    //--------------------------是否登录-------------------------------------
    /**
     * 用户是否登陆路
     */
    public static final String LoginOrNot = "login_or_not";
    /**
     * 没登陆
     */
    public static final String NOTLOGINED = "NOT_LOGINED";
    /**
     * 已经登录
     */
    public static final String LOGINED = "LOGINED";
    //--------------------------是否登录-------------------------------------


    //--------------------------获取产品包id需要的参数-------------------------------------
    public static final String mCarType = "mCarType";
    public static final String serviceId = "serviceId";
    public static final String categoryId = "categoryId";//更换产品的时候的分类id

    //程序一启动的配置信息
    public static String APP_SYS_CONFIG= "APP_SYS_CONFIG";

    //from
    public static String FROM = "from";
    public static String FROM_SHOPPINGMALL = "shoppingmall";//商城
    public static String FROM_SHOP         = "shop";//门店


    /**
     * 登陆之后的信息设置到sp里面
     * @param mContext
     * @param json2LoginBean
     */
    public static  void putLoginedInfo(Context mContext,Json2LoginBean json2LoginBean)
    {
        //{"userid":"","carType":"","name":"住福利","status":"5","isJson":true,"isReturnStr":false,"returnCode":0,"returneMsg":"SUCCESS","message":"登陆成功"}

        String      userid       = json2LoginBean.getUserid();
        boolean     isJson      = json2LoginBean.isJson();
        boolean     isReturnStr = json2LoginBean.isReturnStr();
        int         returnCode      = json2LoginBean.getReturnCode();
        String      returneMsg   = json2LoginBean.getReturneMsg();
        String      message     = json2LoginBean.getMessage();
        String      carType      = json2LoginBean.getCarType();
        String      name         = json2LoginBean.getName();
        String      status       = json2LoginBean.getStatus();

        SPUtils.putUserInfo(mContext,Configs.userid,        userid);
        SPUtils.putUserInfo(mContext,Configs.isJson,        isJson);
        SPUtils.putUserInfo(mContext,Configs.isReturnStr,   isReturnStr);
        SPUtils.putUserInfo(mContext,Configs.returnCode,    returnCode);
        SPUtils.putUserInfo(mContext,Configs.returneMsg,    returneMsg);
        SPUtils.putUserInfo(mContext,Configs.message,       message);
        SPUtils.putUserInfo(mContext,Configs.carType,       carType);
        SPUtils.putUserInfo(mContext,Configs.name,          name);
        SPUtils.putUserInfo(mContext,Configs.status,        status);
    }

    /**
     * 去SP里面拿登陆后保存的信息
     * @param mContext
     * @return
     */
    public static Json2LoginBean getLoginedInfo(Context mContext)
    {
        String      userid     = (String) SPUtils.getUserInfo(mContext, Configs.userid, "");
        boolean     isJson     = (boolean) SPUtils.getUserInfo(mContext, Configs.isJson, false);
        boolean     isReturnStr= (boolean) SPUtils.getUserInfo(mContext, Configs.isReturnStr, false);
        int         returnCode = (int) SPUtils.getUserInfo(mContext, Configs.returnCode, -100);
        String      returneMsg = (String) SPUtils.getUserInfo(mContext, Configs.returneMsg, "");
        String      message    = (String) SPUtils.getUserInfo(mContext, Configs.message, "");
        String      carType    = (String) SPUtils.getUserInfo(mContext, Configs.carType, "");
        String      name       = (String) SPUtils.getUserInfo(mContext, Configs.name, "");
        String      status     = (String) SPUtils.getUserInfo(mContext, Configs.status, "");
        Json2LoginBean json2LoginBean = new Json2LoginBean();
        json2LoginBean.setUserid(userid);
                json2LoginBean.setIsJson(isJson);
        json2LoginBean.setIsReturnStr(isReturnStr);
                json2LoginBean.setReturnCode(returnCode);
        json2LoginBean.setReturneMsg(returneMsg);
                json2LoginBean.setMessage(message);
        json2LoginBean.setCarType(carType);
                json2LoginBean.setName(name);
        json2LoginBean.setStatus(status);

        return json2LoginBean;
    }


}
