package com.sales.vo.base;

import com.google.gson.Gson;

public class SalesBaseMsg  {
	
	public SalesBaseMsg(String msgtype)
	{
		this.msgtype = msgtype;
	}
	
	private String msgtype = null;
	

	private String seq=null;

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}
		
	public String getMsgtype() {
		return msgtype;
	}

	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}
	
	public String toLog()
	{
		return null;
	}


	
}
