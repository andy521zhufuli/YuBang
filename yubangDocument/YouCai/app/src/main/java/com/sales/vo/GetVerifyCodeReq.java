package com.sales.vo;

import com.sales.vo.base.SalesLoginedReq;
import com.sales.vo.base.MSG_TYPES;
import com.sales.vo.base.SalesReq;

public class GetVerifyCodeReq extends SalesLoginedReq {

	public GetVerifyCodeReq() {
		super(MSG_TYPES.MSG_GET_VERIFY_CODE);
		// TODO Auto-generated constructor stub
	}
	
	private String trigger = null;

	public String getTrigger() {
		return trigger;
	}

	public void setTrigger(String trigger) {
		this.trigger = trigger;
	}
	
	
}
