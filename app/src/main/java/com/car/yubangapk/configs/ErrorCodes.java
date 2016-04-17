package com.car.yubangapk.configs;

/**
 * Created by andy on 16/4/16.
 */
public class ErrorCodes {

    public static final String LOW_VERSION_TO_UPGRADE_APP = "您当前版本太低,请升级版本.";
    public static final String NETWORK_ERROR = "网络错误";
    public static final String SERVER_ERROR = "服务器错误";
    public static final String NO_PROCUDT_PACKAGE = "对不起, 没有相关产品包";

    public static final int ERROR_CODE_LOW_VERSION = 0;
    public static final int ERROR_CODE_NETWORK = 1;
    public static final int ERROR_CODE_SERVER  = 2;
    public static final int ERROR_CODE_NO_PRODUCT_PKG = 3;
    public static final int ERROR_CODE_NO_ADDRESS = 4;
    public static final int ERROR_CODE_NOT_LOGIN = 100;
    public static final int ERROR_CODE_SERVER_ERROR = -100;
}