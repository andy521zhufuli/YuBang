package com.sales.vo;

import com.sales.vo.base.CashInfo;
import com.sales.vo.base.MSG_TYPES;
import com.sales.vo.base.SalesLoginedReq;
import com.sales.vo.base.SalesResp;

public class CashOutReq extends SalesLoginedReq {

	public CashOutReq() {
		super(MSG_TYPES.MSG_CASH_OUT);
	}

	private CashInfo cashInfo;


	public CashInfo getCashInfo() {
		return cashInfo;
	}

	public void setCashInfo(CashInfo cashInfo) {
		this.cashInfo = cashInfo;
	}
	
	

}
