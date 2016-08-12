package com.sales.vo;

import java.util.List;

import com.sales.vo.base.CashInfo;
import com.sales.vo.base.OrderCancelReason;
import com.sales.vo.base.OrderGoods;
import com.sales.vo.base.SalesLoginedReq;
import com.sales.vo.base.MSG_TYPES;

public class CommitCashReq extends SalesLoginedReq {

	public CommitCashReq() {
		super(MSG_TYPES.MSG_COMMIT_CASH);
		// TODO Auto-generated constructor stub
	}

	private CashInfo cashInfo = null;
	private String verifycode = null;
	public CashInfo getCashInfo() {
		return cashInfo;
	}
	public void setCashInfo(CashInfo cashInfo) {
		this.cashInfo = cashInfo;
	}
	public String getVerifycode() {
		return verifycode;
	}
	public void setVerifycode(String verifycode) {
		this.verifycode = verifycode;
	}
	
	
	
	
	
}
