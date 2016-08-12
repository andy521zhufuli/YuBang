package com.sales.web.base;

import org.apache.commons.lang3.StringUtils;

import com.common.utils.TimeUtils;

public class SeqGenerator {

	private static final int SEQ_LEN = 4;
	private static final int MAX_SEQ = 9999;
	private static int seqnum = 0;
	private static String seqTimeStamp = null;
	
	
	private static synchronized int getSeqNum(String timeStamp)
	{
		// ʱ�����ͬ�� ���кż�1
		if (null != seqTimeStamp && seqTimeStamp.equals(timeStamp))
		{
			seqnum ++;
			if (seqnum > MAX_SEQ)
			{
				seqnum = 1;
			}
		}
		// ʱ�����ͬ�� ���кŹ�0
		else
			
		{
			seqTimeStamp = timeStamp;
			seqnum = 1;
					
		}
		return seqnum ;
	}

	private static String getSlaveIpPort() {
	/*	String slaveIP = System.getenv("LOCAL_IP").trim();
		String[] ipArry = slaveIP.split("\\.");
		String slavePort = System.getenv("LOCAL_PORT").trim();
		return StringUtils.leftPad(ipArry[3], 3, "0")
				+ StringUtils.leftPad(slavePort, 5, "0");*/
		return "";
	}
	
	public static String BuildSeq() {
		String timestamp = TimeUtils
				.getTimestampString(TimeUtils.DateFormat.tableName);
		int seq = getSeqNum(timestamp);
		String seqStr = String.valueOf(seq);
		seqStr = StringUtils.leftPad(seqStr, SEQ_LEN, "0");
		
		return getSlaveIpPort() + timestamp + seqStr;
	}
	
}
