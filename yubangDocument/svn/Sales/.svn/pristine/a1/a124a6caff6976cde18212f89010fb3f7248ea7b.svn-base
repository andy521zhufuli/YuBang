package com.common.utils;

import java.io.Reader;
import java.sql.Clob;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * 字符串操作工具类，封装了�?��诸如字符串非空转换�?非空判断、时间日期和字符串之间转换等方法
 * 
 * @author xieyonggui
 * @since jdk1.6
 * @version 1.0
 */
public final class StringUtils {
//	private final static JLogger logger = LoggerUtils
//			.getLogger(StringUtils.class);

	/**
	 * 默认的日期格式转换对�?SimpleDateFormat多线程时存在线程安全
	 */
	// private static final SimpleDateFormat DEFAULT_DATE_FORMAT = new
	// SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 读取Clob字段
	 * 
	 * @param clob
	 * @return
	 * @throws Exception
	 */
	public static String clobToString(Clob clob) throws Exception {

		StringBuffer buffer = null;
		if (clob != null) {
			buffer = new StringBuffer();
			Reader reader = null;
			try {
				reader = clob.getCharacterStream();
				char[] bytes = new char[1024];
				int i = 0;
				while ((i = reader.read(bytes)) != -1) {
					buffer.append(bytes, 0, i);
				}
			} finally {
				if (reader != null) {
					reader.close();
				}
			}
		}
		return buffer == null ? null : buffer.toString();
	}

	/**
	 * �?��字符串是否为�?
	 * 
	 * @param source
	 *            字符�?
	 * @param trim
	 *            是否�?��去除首尾空格
	 * @return
	 */
	public static boolean hasLength(String source, boolean trim) {

		boolean flag = Boolean.FALSE;
		if (source != null) {
			if (trim) {
				source = source.trim();
			}
			flag = source.length() > 0;
		}
		return flag;
	}

	/**
	 * �?��字符串是否为�?
	 * 
	 * @param source
	 *            字符�?
	 * @return
	 */
	public static boolean hasLength(String source) {

		return hasLength(source, Boolean.TRUE);
	}

	/**
	 * 将Date转换为字符串
	 * 
	 * @param date
	 *            �?��转换的Date对象
	 * @param formatString
	 *            时间展示格式
	 * @return 时间日期字符�?
	 * @throws Exception
	 */
	public static String dateToString(Date date, String formatString)
			throws Exception {
		SimpleDateFormat format = null;
		if (!hasLength(formatString)) {
			format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			;
		} else {
			format = new SimpleDateFormat(formatString);
		}
		try {
			return format.format(date);
		} catch (Exception e) {
			//logger.error("error date getTime:" + date.getTime());
			throw new Exception(e);
		}

	}
	
	

	/**
	 * 将Date转换为字符串(默认使用“yyyy-MM-dd HH:mm:ss”的格式展示时间)
	 * 
	 * @param date
	 *            �?��换的日期
	 * @return 转换后的时间日期字符�?
	 * @throws Exception
	 */
	public static String dateToString(Date date) throws Exception {

		return dateToString(date, null);
	}

	/**
	 * 将Timestamp对象转换成字符串
	 * 
	 * @param timestamp
	 *            �?imestamp对象
	 * @param formatString
	 *            时间展示格式
	 * @return 转换后的时间日期字符�?
	 * @throws Exception
	 */
	public static String timestampToString(Timestamp timestamp,
			String formatString) throws Exception {

		Date date = new Date(timestamp.getTime());
		return dateToString(date, formatString);
	}

	/**
	 * 将Timestamp对象转换成字符串(默认使用“yyyy-MM-dd HH:mm:ss”的格式展示时间)
	 * 
	 * @param timestamp
	 * @return 转换后的时间日期字符�?
	 * @throws Exception
	 */
	public static String timestampToString(Timestamp timestamp)
			throws Exception {

		return timestampToString(timestamp, null);
	}

	/**
	 * 如果字符串为空则将其转换为指定�?
	 * 
	 * @param str
	 *            �?��转换的字符串
	 * @param replacement
	 *            如果字符串为空则返回replacement指定的�?
	 * @return 转换后的字符�?
	 */
	public static String parseNull(String str, String replacement) {

		if (!hasLength(str, Boolean.TRUE)) {
			str = replacement;
		}
		return str;
	}

	/**
	 * 如果字符串为空则将其转换为空�?
	 * 
	 * @param str
	 *            �?��转换的字符串
	 * @return 转换后的字符�?
	 */
	public static String parseNull(String str) {

		return parseNull(str, "");
	}

	public static String fillterNullStr(String str) {
		if (str == null) {
			str = "";
		}


		return str;
	}

	public static List<String> oderStringListByLength(List<String> inputList) {
		if (inputList == null || inputList.size() <= 0)
			return inputList;

		Collections.sort(inputList, new Comparator<String>() {
			public int compare(String str1, String str2) {
				return (str1.length() > str2.length()) ? 0 : 1;
			}
		});

		return inputList;
	}


}