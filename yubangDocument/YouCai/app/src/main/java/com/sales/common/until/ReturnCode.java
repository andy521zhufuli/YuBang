package com.sales.common.until;

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
	
	/***********************************************
	 * 以下是逻辑错误返回码
	 * ***********************************************/
	public static final int RET_LOGICAL_GOODS_NOT_EXIST = 2001;  // 商品不存在
	public static final int RET_LOGICAL_ORDER_NOT_EXIST  = 2002;  // 订单不存在
	public static final int RET_LOGICAL_NOT_LOGINED          = 2003;  // 尚未登录
	public static final int RET_LOGICAL_LOGINED_EXPIRED   = 2004;  // 登录已过期
	
	static{
		CODE_MSG_MAP.put(RET_SUCCESS, "成功");
		CODE_MSG_MAP.put(RET_ERROR, "失败");
		CODE_MSG_MAP.put(RET_JSON, "JSON报文");
		CODE_MSG_MAP.put(RET_NULL, "请求报文为空");
		
		/***********************************************
		 * 以下是内部错误返回码对应的错误信息
		 * ***********************************************/
		CODE_MSG_MAP.put(RET_INTER_ARGS_ERROR, "请求报文校验错误");
		CODE_MSG_MAP.put(RET_INTER_JSON_ERROR, "请求报文校验错误");
		
		
		
		
		/***********************************************
		 * 以下是逻辑错误返回码对应的错误信息
		 * ***********************************************/
		CODE_MSG_MAP.put(RET_LOGICAL_GOODS_NOT_EXIST, "对不起， 您购买的商品已下架，要不逛逛其他商品？");
		CODE_MSG_MAP.put(RET_LOGICAL_ORDER_NOT_EXIST, "对不起， 您的订单不存在，请联系客户！");
		CODE_MSG_MAP.put(RET_LOGICAL_NOT_LOGINED, "对不起， 您尚未登录，请登录！");
		CODE_MSG_MAP.put(RET_LOGICAL_LOGINED_EXPIRED, "对不起， 您的登录已过期，请重新登录！");
	}

}
