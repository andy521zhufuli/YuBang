package com.sales.common.until;

import java.util.ArrayList;
import java.util.HashMap;

public class ReturnCode {
	public static String getCodeMsg(int code)
	{
		String msg = CODE_MSG_MAP.get(code);
		if (null == msg)
		{
			msg = GET_UNKOWN_ERROR(code);
		}
		
		return msg;
	}
	
	private static final String GET_UNKOWN_ERROR(int code)
	{
		return  "未知错误" + code;
	}
   private static final HashMap<Integer , String> CODE_MSG_MAP = new  HashMap<Integer , String>();
   
   public  final static String VERFITYFALSE ="验证失败";
 
   public final static int MIN_INTERAL_ERROR_CODE = 1;
   public final static int MAX_INTERAL_ERROR_CODE = 2000;
   public final static String INTERAL_ERROR_PROMT = "对不起，系统内部错误， 请联系客户，谢谢！";
   public final static int MIM_LOGICAL_ERROR_CODE = 2001;
   public final static int MAX_LOGICAL_ERROR_CODE = 3000;
   
	public static final int RET_SUCCESS = 0;
    public static final int RET_ERROR = 1;
	public static final int RET_JSON = 2;
	public static final int RET_NULL = 3;
	
	/***********************************************
	 * 以下是内部错误返回码
	 * ***********************************************/
	public static final int RET_INTER_ARGS_ERROR = 1001; // 参数错误
	public static final int RET_INTER_JSON_ERROR = 1002; // json报文错误
	public static final int RET_INTER_USR_NOT_EXISTED = 1003; // 用户不存在
	public static final int RET_CASH_ACCOUNT_CAN_NOT_BE_MODIFIED = 1004; // 提现账户不能修改
	public static final int RET_SMS_ERROR = 1005;//短信网关错误
	public static final int RET_SQL_ERROR = 1006;//数据库语句错误
	public static final int RET_SQL_QQERROR = 1007;//QQ登录错误
	/***********************************************
	 * 以下是逻辑错误返回码
	 * ***********************************************/
	public static final int RET_LOGICAL_GOODS_NOT_EXIST = 2001;  // 商品不存在
	public static final int RET_LOGICAL_ORDER_NOT_EXIST  = 2002;  // 订单不存在
	public static final int RET_LOGICAL_NOT_LOGINED          = 2003;  // 尚未登录
	public static final int RET_LOGICAL_LOGINED_EXPIRED   = 2004;  // 登录已过期
	public static final int RET_LOGICAL_SMS_OVER   = 2005;  // 超过当天发送短信限制
	public static final int RET_LOGICAL_SMS_SUBTIME   = 2006;  //短信验证码未过期，请稍候再试
	public static final int RET_LOGICAL_SMS_NOSENDSMS   = 2007;  //短信未发出，请重试
	public static final int RET_LOGICAL_SMS_HASBEENVERFITY   = 2008;  //短信已经验证过
	public static final int RET_LOGICAL_SMS_OUTOFTIME   = 2009;  //短信已经超时
	public static final int RET_LOGICAL_SMS_VERFITYERROR   = 2010;  //短信验证码错误
	public static final int RET_LOGICAL_GOODS_ISNOTMORE   = 2011;  //无更多商品
	public static final int RET_LOGICAL_ORDER_CANELREASONNULL   = 2012;  //取消原因不能为空
	public static final int RET_LOGICAL_LOGIN_USERISNULL   = 2013;  //用户id为空
	public static final int RET_LOGICAL_LOGIN_USERISTWO   = 2014;  //存在两个相同的用户id
	public static final int RET_LOGICAL_LOGIN_USERISNOTEXIT   = 2015;  //用户名不存在
	
	static{
		CODE_MSG_MAP.put(RET_SUCCESS, "成功");
		CODE_MSG_MAP.put(RET_ERROR, "失败");
		CODE_MSG_MAP.put(RET_JSON, "JSON报文");
		CODE_MSG_MAP.put(RET_NULL, "请求报文为空");
		
		/***********************************************
		 * 以下是内部错误返回码对应的错误信息
		 * ***********************************************/
		CODE_MSG_MAP.put(RET_INTER_ARGS_ERROR, "请求参数错误");
		CODE_MSG_MAP.put(RET_INTER_JSON_ERROR, "请求报文校验错误");
		CODE_MSG_MAP.put(RET_INTER_USR_NOT_EXISTED, "用户不存在");
		CODE_MSG_MAP.put(RET_SMS_ERROR, "短信网关错误");
		CODE_MSG_MAP.put(RET_SQL_ERROR, "SQL语句错误");
		CODE_MSG_MAP.put(RET_SQL_QQERROR, "QQ登录错误");
		
		
		/***********************************************
		 * 以下是逻辑错误返回码对应的错误信息
		 * ***********************************************/
		CODE_MSG_MAP.put(RET_LOGICAL_GOODS_NOT_EXIST, "对不起， 您购买的商品已下架，要不逛逛其他商品？");
		CODE_MSG_MAP.put(RET_LOGICAL_ORDER_NOT_EXIST, "对不起， 您的订单不存在，请联系客户！");
		CODE_MSG_MAP.put(RET_LOGICAL_NOT_LOGINED, "对不起， 您尚未登录，请登录！");
		CODE_MSG_MAP.put(RET_LOGICAL_LOGINED_EXPIRED, "对不起， 您的登录已过期，请重新登录！");
		CODE_MSG_MAP.put(RET_LOGICAL_SMS_OVER, "超过当天发送短信限制");
		CODE_MSG_MAP.put(RET_LOGICAL_SMS_SUBTIME, "短信验证码未过期，请稍候再试");
		CODE_MSG_MAP.put(RET_LOGICAL_SMS_NOSENDSMS, "短信未发出，请重试");
		CODE_MSG_MAP.put(RET_LOGICAL_SMS_HASBEENVERFITY, "短信已经验证过");
		CODE_MSG_MAP.put(RET_LOGICAL_SMS_OUTOFTIME, "短信已经超时");
		CODE_MSG_MAP.put(RET_LOGICAL_SMS_VERFITYERROR, "短信验证码错误");
		CODE_MSG_MAP.put(RET_LOGICAL_GOODS_ISNOTMORE, "无更多商品");
		CODE_MSG_MAP.put(RET_LOGICAL_LOGIN_USERISNULL, "取消原因不能为空");
		CODE_MSG_MAP.put(RET_LOGICAL_LOGIN_USERISTWO, "存在两个相同的用户id");
		CODE_MSG_MAP.put(RET_LOGICAL_LOGIN_USERISNOTEXIT, "用户名不存在");
	}

}
