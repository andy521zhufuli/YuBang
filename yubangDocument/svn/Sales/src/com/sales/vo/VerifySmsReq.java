package com.sales.vo;

import com.sales.vo.base.MSG_TYPES;
import com.sales.vo.base.SalesLoginedReq;
import com.sales.vo.base.SalesReq;

public class VerifySmsReq extends SalesLoginedReq{

	public VerifySmsReq() {
		super(MSG_TYPES.MSG_VERFITYSMS);
		// TODO Auto-generated constructor stub
	}
	
	private String captcha;

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}
	
	

}
