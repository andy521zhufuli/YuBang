package com.sales.vo;

import com.sales.vo.base.MSG_TYPES;
import com.sales.vo.base.SalesLoginedReq;
import com.sales.vo.base.SalesReq;

public class SendSmsReq extends SalesLoginedReq{

	public SendSmsReq() {
		super(MSG_TYPES.MSG_SENDSMS);
		// TODO Auto-generated constructor stub
	}
	
	private String phone;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	

}
