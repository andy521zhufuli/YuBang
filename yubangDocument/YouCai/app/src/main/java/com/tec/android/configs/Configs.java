package com.tec.android.configs;

public class Configs
{
	public static final String CHARSET = "utf-8";



	public static int timeout = 5000;
	public static int SO_TIMEOUT = 10 * 1000;


	public static final int RESULT_STATUS_SUCCESS = 1;
	public static final int RESULT_STATUS_FAIL = 2;
	public static final int RESULT_STATUS_PASSWORD_ERROR = 3;



	//webview URL root 才哥
//	public static final String SERVER_IP_ADDRESS = "http://10.9.112.220:8080/Sales/";
	//鹏哥
	public static final String SERVER_IP_ADDRESS = "http://10.9.112.238:8080/Sales/";
	//public static final String SERVER_IP_ADDRESS = "http://10.21.23.181:8080/Sales/";
	//自己
	//public static final String SERVER_IP_ADDRESS = "http://192.168.1.18:8080/Sales/";

	//qqAPPID
	public static final String QQ_APP_ID = "1104725631";
	//微信 APPID
	public static final String WX_APP_ID = "";



	//SharedPreference 里面
	public static final String IS_USER_LOGINED = "is_user_logined";//用户是否登陆
	public static final String LOGINED = "YES";
	public static final String NOT_LOGINED = "NO";
	//当用户成功登陆的时候  会拿到后台发来的信息   这个信息保存在这里
	public static final String LOGIN_INFO = "login_info";

	public static final String SHOPPINGC_CAR_IN_SP = "shoppingcar";//保存在sp里面的购物车数据

	public static final String ADDRESS_INFO_LIST_IN_SP = "address";//保存在sp里面的个人地址信息


}
