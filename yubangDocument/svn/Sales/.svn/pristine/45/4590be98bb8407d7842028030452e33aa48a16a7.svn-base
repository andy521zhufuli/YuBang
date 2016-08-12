package com.sales.common.until;

import org.apache.commons.lang3.StringUtils;

import com.common.utils.TimeUtils;

public class IdGenerator {
	public static final String ID_TYPE_CASH = "C";
	public static final String ID_TYPE_ORDER = "T";
	public static final String ID_TYPE_USER = "U";
	
	private static final int SEQ_LEN = 4;
	private static final int MAX_SEQ = 9999;
	private static int seqnum = 0;
	private static String seqTimeStamp = null;
	
	
	private static synchronized int getSeqNum(String timeStamp)
	{
		if (null != seqTimeStamp && seqTimeStamp.equals(timeStamp))
		{
			seqnum ++;
			if (seqnum > MAX_SEQ)
			{
				seqnum = 1;
			}
		}
		else
			
		{
			seqTimeStamp = timeStamp;
			seqnum = 1;
					
		}
		return seqnum ;
	}
	
	public static String GenID(String idType) {
		String timestamp = TimeUtils
				.getTimestampString(TimeUtils.DateFormat.tableName);
		int seq = getSeqNum(timestamp);
		String seqStr = String.valueOf(seq);
		seqStr = StringUtils.leftPad(seqStr, SEQ_LEN, "0");
		
		return idType + timestamp + seqStr;
	}

}
