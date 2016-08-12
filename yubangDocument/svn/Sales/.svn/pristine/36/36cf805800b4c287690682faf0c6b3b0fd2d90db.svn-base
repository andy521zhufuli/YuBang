package com.common.utils;

import java.util.Random;

import org.apache.commons.lang3.StringUtils;


public class CommonUtils {
	
	public final static long MIN_NUM = 1000000000L;

	public final static long MAX_NUM = 9999999999L;
	
	/**
	 * 获取一定范围内的随机数
	 * 
	 * @return
	 */
	public static long getRandom() {

		long num = Math.abs(new Random().nextLong());
		while (num < MIN_NUM || num > MAX_NUM) {
			if (num > MAX_NUM) {
				num = num / MIN_NUM;
			} else {
				num += MIN_NUM;
			}
		}
		return num;
	}
	
	public static String buildSeq() {
		String timestamp = TimeUtils
				.getTimestampString(TimeUtils.DateFormat.tableName);
		String randomNum = CommonUtils.getRandom() + "";
		return getSlaveIpPort() + timestamp + randomNum;
	}
	
	private static String getSlaveIpPort() {
	/*	String slaveIP = System.getenv("LOCAL_IP").trim();
		String[] ipArry = slaveIP.split("\\.");
		String slavePort = System.getenv("LOCAL_PORT").trim();
		return StringUtils.leftPad(ipArry[3], 3, "0")
				+ StringUtils.leftPad(slavePort, 5, "0");*/
		return "";
	}

}
