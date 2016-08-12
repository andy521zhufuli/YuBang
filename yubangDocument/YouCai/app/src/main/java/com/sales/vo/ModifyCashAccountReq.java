package com.sales.vo;

import java.util.List;

import com.sales.vo.base.CashAccountInfo;
import com.sales.vo.base.OrderCancelReason;
import com.sales.vo.base.OrderGoods;
import com.sales.vo.base.SalesLoginedReq;
import com.sales.vo.base.MSG_TYPES;

public class ModifyCashAccountReq extends SalesLoginedReq {

	public ModifyCashAccountReq() {
		super(MSG_TYPES.MSG_MODIFY_CASH_ACCOUNT);
		// TODO Auto-generated constructor stub
	}
	
	private String action = null;
	
	private CashAccountInfo accountinfo = null;
	
	

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public CashAccountInfo getAccountinfo() {
		return accountinfo;
	}

	public void setAccountinfo(CashAccountInfo accountinfo) {
		this.accountinfo = accountinfo;
	}
}
