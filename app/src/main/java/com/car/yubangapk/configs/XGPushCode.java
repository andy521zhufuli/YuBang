package com.car.yubangapk.configs;

/**
 * Created by andy on 16/5/11.
 */
public class XGPushCode {
    public static int CODE_SUCCESS              = 0;
    public static int CODE_PARAN_ERROR          = 2;
    public static int CODE_VERIFY_QUANXIAN      = 20;
    public static int CODE_START                = 10000;
    public static int CODE_CAOZUO               = 10001;
    public static int CODE_REGISTER_AGAIN       = 10002;
    public static int CODE_QUANXIAN             = 10003;
    public static int CODE_SO                   = 10004;
    public static int CODE_NET                  = 10100;
    public static int CODE_ROAD                 = 10101;
    public static int CODE_ROAD_CLOSE           = 10102;
    public static int CODE_ROAD_SERVER_CLOSE    = 10103;
    public static int CODE_CLIENT_ERROR         = 10104;
    public static int CODE_SEND_RECE_TIMEOUT    = 10105;
    public static int CODE_WAIT_SENT_TIMEOUT    = 10106;
    public static int CODE_WAIT_RECE_TIMEOUT    = 10107;
    public static int CODE_SERVER               = 10108;
    public static int CODE_UNKONWN              = 10109;
    public static int CODE_ROAD_HANDLER_NULL    = 10110;


    public static String CODE_STRING_SUCCESS            = "调用成功";
    public static String CODE_STRING_PARAN_ERROR        = "参数错误，例如绑定了单字符的别名，或是ios的token长度不对，应为64个字符";
    public static String CODE_STRING_VERIFY_QUANXIAN    = "鉴权错误";
    public static String CODE_STRING_START              = "起始错误";
    public static String CODE_STRING_CAOZUO             = "操作类型错误码，例如参数错误时将会发生该错误";
    public static String CODE_STRING_REGISTER_AGAIN     = "正在执行注册操作时，又有一个注册操作到来，则回调此错误码";
    public static String CODE_STRING_QUANXIAN           = "权限出错";
    public static String CODE_STRING_SO                 = "so出错";
    public static String CODE_STRING_NET                = "当前网络不可用";
    public static String CODE_STRING_ROAD               = "创建链路失败";
    public static String CODE_STRING_ROAD_CLOSE         = "请求处理过程中， 链路被主动关闭";
    public static String CODE_STRING_ROAD_SERVER_CLOSE  = "请求处理过程中，服务器关闭链接";
    public static String CODE_STRING_CLIENT_ERROR       = "请求处理过程中，客户端产生异常";
    public static String CODE_STRING_SEND_RECE_TIMEOUT  = "请求处理过程中，发送或接收报文超时";
    public static String CODE_STRING_WAIT_SENT_TIMEOUT  = "请求处理过程中， 等待发送请求超时";
    public static String CODE_STRING_WAIT_RECE_TIMEOUT  = "请求处理过程中， 等待接收请求超时";
    public static String CODE_STRING_SERVER             = "服务器返回异常报文";
    public static String CODE_STRING_UNKONWN            = "未知异常，请在QQ群中直接联系管理员，或前往论坛发帖留言";
    public static String CODE_STRING_ROAD_HANDLER_NULL  = "创建链路的handler为null";

}
