package com.common.log;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.common.log.proxy.LoggerFactory;

/**
 * log4j宸ュ叿绫伙紝鐢ㄤ簬鑾峰彇{@link com.aspire.common.log.JLogger}绫荤殑瀹炰緥
 * @author xieyonggui
 * @since jdk1.6 log4j1.4
 * @version 1.0
 * @see com.aspire.common.log.JLogger
 */
public class LoggerUtils 
{ 
    public static JLogger getLogger(Class<?> clazz)
    {
        return LoggerFactory.getLogger(clazz);
    }
    
    /**
	 * 鏃ュ織鍒嗛殧绗�
	 * */
	public static final String SPACER = String.valueOf((char)(0x1F));
    
    public static JLogger getLogger(String classname)
    {
        return LoggerFactory.getLogger(classname);
    }
    
//    public static void writeDataCenterPps(Date date,String ip, String url)
//    {
//        Long l = new Date().getTime()-date.getTime();
//        StringBuffer sb = new StringBuffer();
//        sb.append(Util.formatStrByLen(format.format(date), 14));
//        sb.append(CHAR_31);
//        sb.append(Util.formatStr(String.valueOf(l)));
//        sb.append(CHAR_31);
//        sb.append(Util.formatStr(ip));
//        sb.append(CHAR_31);
//        sb.append(Util.formatStr(url));
//        logDataCenterPps.info(sb.toString());
//    }
    
    public static void writeExceptionLog(String doName,String msisdn, String pars, String redisKey)
    {
        StringBuffer sb = new StringBuffer();
        sb.append(Util.formatStrByLen(format.format(new Date()), 14));
        sb.append(CHAR_31);
        sb.append(Util.formatStr(doName));
        sb.append(CHAR_31);
        sb.append(Util.formatStr(msisdn));
        sb.append(CHAR_31);
        sb.append(Util.formatStr(pars));
        sb.append(CHAR_31);
        sb.append(Util.formatStr(redisKey));
        logException.info(sb.toString());
    }
    
    private static String CHAR_31 = "^";
    static JLogger logMmsearchPps = getLogger("biz.mmsearch.pps");
    static JLogger logException = getLogger("biz.mmsearch.exception");
    public static SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
}
