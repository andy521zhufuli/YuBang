package com.sales.vo;

import java.util.List;

import com.sales.vo.base.OrderCancelReason;
import com.sales.vo.base.OrderGoods;
import com.sales.vo.base.SalesLoginedReq;
import com.sales.vo.base.MSG_TYPES;

public class GetAccountInfoReq extends SalesLoginedReq {

	public GetAccountInfoReq() {
		super(MSG_TYPES.MSG_GET_ACCOUNT_INFO);
		// TODO Auto-generated constructor stub
	}
	
	private String fuserid = null;

	public String getFuserid() {
		return fuserid;
	}

	public void setFuserid(String fuserid) {
		this.fuserid = fuserid;
	}

	
	
	
	
}
