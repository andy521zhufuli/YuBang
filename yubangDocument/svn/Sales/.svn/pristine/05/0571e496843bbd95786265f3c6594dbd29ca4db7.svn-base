package com.common.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
/**
 * 处理和转换时间日期对象的工具�?
 * @author xieyonggui
 * @since jdk1.6
 * @version 1.0
 */
public class TimeUtils
{
    /**
     * SimpleDateFormat对象
     */
    private final static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * SimpleDateFormat对象
     */
    private final static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
   
    /**
     * SimpleDateFormat对象
     */
    public final static SimpleDateFormat sdf3 = new SimpleDateFormat("yyyyMMddHHmmss");

    /**
     * 时间字符串格式每�?
     * @author xieyonggui
     *
     */
    public static enum DateFormat{
        /**
         * �?�?分隔
         */
        separated,
        /**
         * 连接
         */
        join,
        /**
         * table后缀
         */
        tableName
    };
    /**
     * 将时间日期字符串转换为java.util.Date对象(默认时间格式为yyyy-MM-dd HH:mm:ss)
     * @param dateStr 时间日期字符�?
     * @return
     * @throws ParseException
     */
    public static Date parseToDate(String dateStr)
    {
        return parseToDate(dateStr, DateFormat.separated);
    }
    /**
     * 将时间日期字符串转换为java.util.Date对象
     * @param dateStr 时间日期字符�?
     * @param format 时间格式
     * @return
     * @throws ParseException
     */
    public static Date parseToDate(String dateString,DateFormat format)
    {
        SimpleDateFormat sdf = getSDF(format);
        try
        {
            return sdf.parse(dateString);
        }
        catch (ParseException e)
        {
            return null;
        }
    }
    /**
     * 将时间日期字符串转换为时间戳(默认时间格式为yyyy-MM-dd HH:mm:ss)
     * @param dateStr
     * @return
     * @throws ParseException
     */
    public static Timestamp parseToTimestamp(String dateStr)
    {
        return parseToTimestamp(dateStr, DateFormat.separated);
    }
    /**
     * 将时间日期字符串转换为时间戳
     * @param dateStr
     * @param format 时间格式
     * @return 
     * @throws ParseException
     */
    public static Timestamp parseToTimestamp(String dateStr,DateFormat format)
    {
        try
        {
            return new Timestamp(parseToDate(dateStr,format).getTime());
        }
        catch (RuntimeException e)
        {
            return null;
        }
    }
    /**
     * 将java.util.Date对象转换为格式化的时间日期字符串(默认时间格式为yyyy-MM-dd HH:mm:ss)
     * @param date 被转换的java.util.Date对象
     * @return
     */
    public static String format(Date date)
    {
        return format(date,DateFormat.separated);
    }
    /**
     * 将java.util.Date对象转换为格式化的时间日期字符串
     * @param date 被转换的java.util.Date对象
     * @param format 时间格式
     * @return
     */
    public static String format(Date date,DateFormat format)
    {
        SimpleDateFormat sdf = getSDF(format);
        try
        {
            return sdf.format(date);
        }
        catch (RuntimeException e)
        {
            return null;
        }
    }
    /**
     * 将时间戳转换为格式化的时间日期字符串(默认时间格式为yyyy-MM-dd HH:mm:ss)
     * @param timestamp  被转换的时间�?
     * @return
     */
    public static String format(Timestamp timestamp)
    {
        return format(timestamp, DateFormat.separated);
    }
    /**
     * 将时间戳转换为格式化的时间日期字符串
     * @param timestamp  被转换的时间�?
     * @param format 时间格式
     * @return
     */
    public static String format(Timestamp timestamp,DateFormat format)
    {
        SimpleDateFormat sdf = getSDF(format);
        try
        {
            Date date = new Date(timestamp.getTime());
            return sdf.format(date);
        }
        catch (RuntimeException e)
        {
            return null;
        }
    }
    /**
     * 获取当前时间对应的java.util.Date对象
     * @return
     */
    public static Date getDate()
    {
        return new Date(System.currentTimeMillis());
    }
    /**
     * 获取当前时间对应的时间戳
     * @return
     */
    public static Timestamp getTimestamp()
    {
        return new Timestamp(System.currentTimeMillis());
    }
    /**
     * 获取当前时间的时间戳的字符串(默认时间格式为yyyy-MM-dd HH:mm:ss)
     * @return
     */
    public static String getTimestampString()
    {
        return format(new Date());
    }
    /**
     * 获取当前时间的时间戳的字符串 
     * @param format 时间字符串格�?
     * @return
     */
    public static String getTimestampString(DateFormat format)
    {
        return format(new Date(),format);
    }
    
    /**
     * 将合乎格式的日期字串转换成Timestamp对象(默认时间格式为yyyy-MM-dd HH:mm:ss)
     * 转换失败则返回null
     * @param dateString 时间字符�?
     * @return
     */
    public static Timestamp getTimestamp(String dateString){
        return getTimestamp(dateString, DateFormat.separated);
    }
    /**
     * 将合乎格式的日期字串转换成Timestamp对象
     * 转换失败则返回null
     * @param dateString 时间字符�?
     * @param format 时间格式
     * @return
     */
    public static Timestamp getTimestamp(String dateString,DateFormat format)
    {
        Date date = parseToDate(dateString, format);
        if (date==null)
        {
            return null;
        }
        return new Timestamp(date.getTime());
    }
    /**
     * 获取指定毫秒数对应的时间�?
     * @param timeMillis
     * @return
     */
    public static Timestamp getTimestamp(long timeMillis)
    {
        return new Timestamp(timeMillis);
    }
    
    /**
     * 获取两个java.util.Date之间的相差的毫秒�?
     * @param lower
     * @param upper
     * @return
     */
    public static long timeDifference(Date lower,Date upper)
    {
        return upper.getTime()-lower.getTime();
    }
    /**
     * 获取java.util.Date中年、月、日等�?
     * @param date
     * @param type Calendar.YEAR �?Calendar.MONTH�?Calendar.DATE日等
     * @return
     */
    public static int get(Date date,int type)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(type);
    }
    /**
     * 获取时间戳中年�?月�?日等�?
     * @param date
     * @param type Calendar.YEAR �?Calendar.MONTH�?Calendar.DATE日等
     * @return
     */
    public static int get(Timestamp timestamp,int type)
    {
        Date date = new Date(timestamp.getTime());
        return get(date, type);
    }
    /**
     * 获取指定的SimpleDateFormat对象
     * @param format
     * @return
     */
    private static SimpleDateFormat getSDF(DateFormat format)
    {
        switch (format)
        {
            case separated:
                return sdf1;
            case join:
                return sdf2;
            case tableName:
                return sdf3;
            default:
                return sdf1;
        }
    }
    
    
    /**
     * 得到当前日期字符
     * @return String，当前日期字�?
     */
    public static String getCurDateTime ()
    {
        Calendar now = Calendar.getInstance(TimeZone.getDefault()) ;
        String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss" ;
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
            DATE_FORMAT) ;
        sdf.setTimeZone(TimeZone.getDefault()) ;
        return sdf.format(now.getTime()) ;
    }

    /**
     * 得到当前日期
     *
     * @param dateFormate String，格式化字符�?
     * @return String，当前日�?
     */
    public static String getCurDateTime (String dateFormate)
    {
        try
        {
            Calendar now = Calendar.getInstance(TimeZone.getDefault()) ;
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
                dateFormate) ;
            sdf.setTimeZone(TimeZone.getDefault()) ;
            return sdf.format(now.getTime()) ;
        }
        catch (Exception e)
        {
            return getCurDateTime() ; //如果无法转化，则返回默认格式的时间�?
        }
    }

    /**
     * 格式化日期为字符�?
     * @param date，日�?
     * @param dateFormate，格式化字符�?
     * @return String，格式化后的日期字符�?
     */
    public static String getDateString (java.util.Date date, String dateFormate)
    {
        if (date == null)
        {
            return "" ;
        }
        try
        {

            SimpleDateFormat sdf = new SimpleDateFormat(dateFormate) ;
            //Stringsdf.format(date);
            return sdf.format(date) ;
        }
        catch (Exception e)
        {
            //如果无法转化，则返回默认格式的时间�?
            return getCurDateTime() ; 
        }
    }
    
    /**
     * 获取相隔几天的日期日�?
     * @param date
     * @param dayCount 相隔天数
     * @return
     */
    public static Date getDay(Date date,int dayCount) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, dayCount);
		date = calendar.getTime();
		return date;
	}
    
    /**
     * 将时间日期字符串转换为java.util.Date对象
     * @param dateStr 时间日期字符�?
     * @param format 时间格式
     * @return
     * @throws ParseException
     */
    public static Date parseToDate(String dateString,String format)
    {
    	SimpleDateFormat sdf = new SimpleDateFormat(format);
        try
        {
            return sdf.parse(dateString);
        }
        catch (ParseException e)
        {
            return null;
        }
    }
    
    /**
     * 相隔几天的日�?
     * @param dateString
     * @param format
     * @param day
     * @return
     */
    public static String addDay(String dateString,String format, int day)
    {
    
        try
        {
        	SimpleDateFormat df=new SimpleDateFormat(format);
    		Date  d = df.parse(dateString);      
    		Calendar cal=Calendar.getInstance();
    		cal.setTime(d);
    		cal.add(Calendar.DATE, day); 
    		
            return df.format(cal.getTime());
        }
        catch (ParseException e)
        {
            return null;
        }
    }
    
    public static Date convertToDate(String dateStr, String format) throws Exception
    {
    	SimpleDateFormat fmt = new SimpleDateFormat(format);
		return fmt.parse(dateStr);
    }
    
    public static String convertToDateStr(Date date, String format) throws Exception
    {
    	SimpleDateFormat fmt = new SimpleDateFormat(format);
    	return fmt.format(date);
    }
    
    public static Date convertCSTToDate(String dateStr) throws Exception
    {
    	String[] strs = dateStr.split(" ");
    	if (strs.length != 6)
    		throw new Exception("Invalid CST date format:" + dateStr);
    	String[] tms = strs[3].split(":");
    	if (tms.length != 3)
    		throw new Exception("Invalid CST date format:" + dateStr);
    	String year = strs[5];
    	
    	String month = convertMonthStr(strs[1], true);
    	String day = strs[2];
    	String hour = tms[0];
    	String min = tms[1];
    	String second = tms[2];

        StringBuilder sb = new StringBuilder();
        sb.append(year).append(month).append(day).append(hour).append(min).append(second);
        return convertToDate(sb.toString(), "yyyyMMddHHmmss");
    }
    
    public static String convertMonthStr(String monthStr,boolean isWithZero){
 	    String monthResult="";
 	    if("Jan".equals(monthStr))
 	       monthResult="01";
 	    else if("Feb".equals(monthStr))
 	       monthResult="02";
 	    else if("Mar".equals(monthStr))
 	       monthResult="03";
 	    else if("Apr".equals(monthStr))
 	       monthResult="04";
 	    else if("May".equals(monthStr))
 	       monthResult="05";
 	    else if("Jun".equals(monthStr))
 	       monthResult="06";
 	    else if("Jul".equals(monthStr))
 	       monthResult="07";
 	    else if("Aug".equals(monthStr))
 	       monthResult="08";
 	    else if("Sep".equals(monthStr))
 	       monthResult="09";
 	    else if("Oct".equals(monthStr))
 	       monthResult="10";
 	    else if("Nov".equals(monthStr))
 	       monthResult="11";
 	    else if("Dec".equals(monthStr))
 	       monthResult="12";
 	   
 	    if(!isWithZero){
 	       if(monthResult.startsWith("0"))
 	    	    monthResult=monthResult.substring(1,2);
 	    }
 	    return monthResult;
 	}
    
	/**
	 * 获取上一周某�?��的日�?
	 * dayIndex 为某�?���?1为星期一�?2为星期二，一次类推， 7为星期天
	 * */
	public static String getLastWeekDayStr(int dayIndex, String format)
	{
		String dayStr = null;
		Calendar cal =Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat(format); 
        if (dayIndex == 7)
        {
        	cal.set(Calendar.DAY_OF_WEEK, 1);
        	dayStr = df.format(cal.getTime());
        }
        else if (dayIndex >= 1 && dayIndex <= 6)
        {	
            cal.add(Calendar.WEEK_OF_MONTH, -1);
            cal.set(Calendar.DAY_OF_WEEK, dayIndex + 1);
            dayStr = df.format(cal.getTime());
        }
        return  dayStr;
	}
	


}
