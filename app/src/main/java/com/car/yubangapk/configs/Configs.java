package com.car.yubangapk.configs;

/**
 * Created by andy on 16/2/22.
 */
public class Configs {
    public static final String IS_USER_LOGINED = "NOT_LOGINED";
    public static final String LOGINED = "LOGINED";



    //Mob SMSSdk
    public static String SMS_APPKEY = "1130ebf82262c";
    public static String SMS_APPSECRET = "7f3dedcb36d92deebcb373af921d635a";

    //网络请求地址

    public static String OUT_IP_ADDRESS = "http://203.195.206.146/carService";
    public static String IP_ADDRESS = "http://192.168.1.50:8080/carService";

    //action
    public static String IP_ADDRESS_ACTION_GETDATA= "/getData";
    public static String IP_ADDRESS_ACTION_GETFILE= "/getFile";
    public static String IP_ADDRESS_ACTION_SYS_CONFIG= "/client/sys/getSysConfig";
    public static String IP_ADDRESS_ACTION_SEND_VERIFY_MSG= "/sendSms";
    public static String IP_ADDRESSS_ACTION_REGISTER = "/client/user/register";
    public static String IP_ADDRESSS_ACTION_LOGIN = "/client/user/login";
    public static String IP_ADDRESSS_ACTION_GET_CAR_COMPANY = "/client/user/login";
    public static String IP_ADDRESSS_ACTION_UP_LOAD_FILE = "/client/user/uploadFile";








    //banner SkipType
    public static String SKIP_TYPE_WEB= "1";//网页
    public static String SKIP_TYPE_PRODUCT_PACKAGE= "1";//产品包
    public static String SKIP_TYPE_LOGIC_SERVICE= "1";//逻辑服务

    //程序一启动的配置信息
    public static String APP_SYS_CONFIG= "APP_SYS_CONFIG";
}
