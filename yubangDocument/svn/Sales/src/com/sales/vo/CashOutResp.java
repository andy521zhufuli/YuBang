package com.sales.vo;

import com.sales.vo.base.CashInfo;
import com.sales.vo.base.MSG_TYPES;
import com.sales.vo.base.SalesResp;

public class CashOutResp extends SalesResp{

	public CashOutResp() {
		super(MSG_TYPES.MSG_CASH_OUT);
		// TODO Auto-generated constructor stub
	}
	
	private CashInfo cashInfo;
	
	public CashInfo getCashInfo() {
		return cashInfo;
	}

	public void setCashInfo(CashInfo cashInfo) {
		this.cashInfo = cashInfo;
	}
	
	

}
