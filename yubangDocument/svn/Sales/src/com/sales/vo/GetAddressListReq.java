package com.sales.vo;

import com.sales.vo.base.MSG_TYPES;
import com.sales.vo.base.SalesReq;

public class GetAddressListReq extends SalesReq {

	public GetAddressListReq() {
		super(MSG_TYPES.MSG_GET_ADDRESS_LIST);
		// TODO Auto-generated constructor stub
	}
	
	private String usrid = null;

	public String getUsrid() {
		return usrid;
	}

	public void setUsrid(String usrid) {
		this.usrid = usrid;
	}
	

}
