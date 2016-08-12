package com.sales.vo;

import com.sales.vo.base.MSG_TYPES;
import com.sales.vo.base.SalesReq;

public class PreLoginReq extends SalesReq {

	public PreLoginReq() {
		super(MSG_TYPES.MSG_PRE_LOGIN);
		// TODO Auto-generated constructor stub
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	private String method = null;	
	

	
	

}
