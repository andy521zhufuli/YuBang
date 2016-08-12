package com.sales.vo;

import java.util.List;

import com.sales.vo.base.OrderCancelReason;
import com.sales.vo.base.OrderGoods;
import com.sales.vo.base.SalesLoginedReq;
import com.sales.vo.base.MSG_TYPES;

public class GetCashInfoReq extends SalesLoginedReq {

	public GetCashInfoReq() {
		super(MSG_TYPES.MSG_GET_CASH_INFO);
		// TODO Auto-generated constructor stub
	}

	private String cashid = null;

	public String getCashid() {
		return cashid;
	}

	public void setCashid(String cashid) {
		this.cashid = cashid;
	}
	
	
	
	
}
