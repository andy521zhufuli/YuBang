package com.sales.common.until;

import java.util.HashMap;
import java.util.Map;

/**
 * 常量定义区
 * */
public class CommonDefs {
	
	//分享商品
	public final static String SHAREGOOD="SHAREGOOD";
	// 应用唯一标识，在微信开放平台提交应用审核通过后获得
	public final static String APPID = "APPID";
	// 应用授权作用域，如获取用户个人信息则填写snsapi_userinfo
	public final static String SCOPE = "SCOPE";
	// 用于保持请求和回调的状态，授权请求后原样带回给第三方。该参数可用于防止csrf攻击（跨站请求伪造攻击），建议第三方带上该参数，可设置为简单的随机数加session进行校验
	public final static String STATE = "STATE";
	// 应用密钥AppSecret，在微信开放平台提交应用审核通过后获得
	public final static String SECRET = "SECRET";
	// qq授权作用域
	public final static String QQSCOPE = "QQSCOPE";
	// qqAppid
	public final static String QQAPPID = "QQAPPID";
	// qqAppid
	public final static String QQAPPKEY = "QQAPPKEY";
	
	//分享配置
	public final static String SHARECONFIG = "SHARECONFIG";
	// 回调url
	public final static String REDIRECT_URI = "REDIRECT_URI";
	public final static String AUTHORIZATION_CODE = "authorization_code";

	// 用户同意
	public final static String ERR_OK = "0";
	// 用户拒绝授权
	public final static String ERR_AUTH_DENIED = "-4";
	// 用户取消
	public final static String ERR_USER_CANCEL = "-2";

	// 微信url
	public final static String WEIXIN_URL = "https://api.weixin.qq.com";
	// QQurl
	public final static String QQ_URL = "https://graph.qq.com";

	// SMSurl
	public final static String SMS_URL = "http://106.ihuyi.cn/webservice/sms.php?method=Submit";

	// SMSACCOUNT
	public final static String SMSACCOUNT = "account";
	// SMSACCOUNT
	public final static String SMSACCOUNTCONFIG = "SMSACCOUNT";
	// SMSPASSWORLD
	public final static String SMSPASSWORLD = "password";
	// SMSPASSWORLD
	public final static String SMSPASSWORLDCONFIG = "SMSPASSWORD";
	// SMSMOBILE
	public final static String SMSMOBILE = "mobile";
	// SMSPASSWORLD
	public final static String SMSCONTENT = "content";
	// SMSPASSWORLD
	public final static String SMSCONTENTCONFIG= "SMSCONTENT";
	
	//SMSSuccesscode
	public final static String SUCCESSCODE ="2";
	public final static String SMSVERFITYCODE ="1";
	public final static String SMSNOVERFITYCODE ="0";
	
	public final static String UST_TYPE_QQ = "qq";
	public final static String UST_TYPE_WEIXIN = "weixin";

	public final static String CASH_METHOD_ALIPAY = "alipay";
	public final static String CASH_METHOD_WEIXIN = "weixin";
	public final static String CASH_METHOD_BANK = "bank";

	public final static String CASH_METHOD_NAME_ALIPAY = "支付宝";
	public final static String CASH_METHOD_NAME_WEIXIN = "微信";

	public final static String CASH_ACT_COMMIT = "commit"; // 提交
	public final static String CASH_ACT_APPROVE = "approve"; // 审核
	public final static String CASH_ACT_TRANSFER = "transfer"; // 转账

	public final static String CASH_STATUS_TO_APPROVE = "ToApprove"; // 待审核
	public final static String CASH_STATUS_TO_TRANSFER = "ToTransfer"; // 待 转账
	public final static String CASH_STATUS_TO_FINISHED = "ToFinshed"; // 结束
	public final static String CASH_STATUS_ALL = "All"; //全部
	
	public final static String CASH_ACCOUNT_ACT_ADD = "add"; // 添加账户
	public final static String CASH_ACCOUNT_ACT_MODIFY = "modify"; // 添加账户

	/**
	 * 以下是订单操作
	 * */
	public final static String ORDER_ACT_ADD = "add";
	public final static String ORDER_ACT_MODIFY = "modify";
	public final static String ORDER_ACT_COMMIT = "commit";
	public final static String ORDER_ACT_CANCEL = "cancel";
	public final static String ORDER_ACT_RECEIVE = "receive";

	public static Map<String, String> ORDER_CN_MAP = new HashMap<String, String>();
	static{
		ORDER_CN_MAP.put(ORDER_ACT_ADD, "新增订单");
		ORDER_CN_MAP.put(ORDER_ACT_MODIFY, "修改订单");
		ORDER_CN_MAP.put(ORDER_ACT_COMMIT, "提交订单");
		ORDER_CN_MAP.put(ORDER_ACT_CANCEL, "取消订单");
		ORDER_CN_MAP.put(ORDER_ACT_RECEIVE, "已收货");
	}
	/**
	 * 以下是订单状态
	 * */
	public final static String ORDER_STATUS_TO_COMMIT = "ToCommit"; // 待提交(购物车临时订单)
	public final static String ORDER_STATUS_TO_CHARGE = "ToCharge"; // 待支付
	public final static String ORDER_STATUS_TO_DELIVER = "ToDeliver"; // 待发货
	public final static String ORDER_STATUS_TO_RECEIVE = "ToReceive"; // 待签收
	public final static String ORDER_STATUS_TO_CANCEL = "ToCancel"; // 待退货审批
	public final static String ORDER_STATUS_CANCELLING = "Cancelling"; // 退货中
	public final static String ORDER_STATUS_FINISHED = "Finished"; // 退货中
	public final static String ORDER_STATUS_ALL = "all"; // 所有状态
	
	public final static int ADDRESS_NOT_SELECTED = 0; // 地址未被选中
	public final static int ADDRESS_SELECTED = 1; // 地址被选中
	
	public final static String ADDRESS_ACT_ADD = "add"; //添加
	public final static String ADDRESS_ACT_MODIFY = "modify"; //添加
	public final static String ADDRESS_ACT_DELETE = "delete"; //添加

	public final static int FIRST_GOODS_LIST_DEFAULT_SIZE = 3; // 商品列表首页的默认商品个数
	public final static int GOODS_LIST_DEFAULT_SIZE = 2; // 商品列表非首页的默认商品个数

	public final static int FIRST_ORDER_LIST_DEFAULT_SIZE = 3; // 商品列表首页的默认商品个数
	public final static int ORDER_LIST_DEFAULT_SIZE = 2; // 商品列表非首页的默认商品个数

	public final static int FIRST_CASH_LIST_DEFAULT_SIZE  = 3;  // 提现列表首页的默认商品个数	
	public final static int CASH_LIST_DEFAULT_SIZE = 2; // 提现列表非首页的默认商品个数
	
	public final static int FIRST_ORDER_PROFIT_LIST_DEFAULT_SIZE  = 3;  // 提现列表首页的默认商品个数	
	public final static int ORDER_PROFIT_LIST_DEFAULT_SIZE = 2; // 提现列表非首页的默认商品个数
	
	public final static int CASH_ACCOUNT_SELECTED = 1; //提现账户被选中
	public final static int CASH_ACCOUNT_NOT_SELECTED = 0; //提现账户未被选中
	
	public static Map<String, String> ORDER_STATUS_CN_MAP = new HashMap<String, String>();

	static {
		ORDER_STATUS_CN_MAP.put(ORDER_STATUS_TO_COMMIT, "待提交");
		ORDER_STATUS_CN_MAP.put(ORDER_STATUS_TO_CHARGE, "待支付");
		ORDER_STATUS_CN_MAP.put(ORDER_STATUS_TO_DELIVER, "待发货");
		ORDER_STATUS_CN_MAP.put(ORDER_STATUS_TO_RECEIVE, "待签收");
		ORDER_STATUS_CN_MAP.put(ORDER_STATUS_TO_CANCEL, "待退货审批");
		ORDER_STATUS_CN_MAP.put(ORDER_STATUS_CANCELLING, "退货中");
		ORDER_STATUS_CN_MAP.put(ORDER_STATUS_FINISHED, "完成");
	}

	public static Map<String, String> ORDER_NEXT_STATUS_CN_MAP = new HashMap<String, String>();

	public final static String FIELD_USER_ID = "userid";
	public final static String TIME_STAMP_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	public static final String GOODS_NUM_SPACER = ";";
	public static final String GOODS_ID_SPACER = "|";
	public static final String IMG_URL_SPACER = ";";
	

}
