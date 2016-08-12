package com.sales.vo;

import java.util.List;


import com.sales.vo.base.CashAccountInfo;
import com.sales.vo.base.OrderGoods;
import com.sales.vo.base.MSG_TYPES;
import com.sales.vo.base.SalesResp;

public class GetCashAccountListResp extends SalesResp {
	
	public GetCashAccountListResp() {
		super(MSG_TYPES.MSG_GET_CASH_ACCOUNT_LIST);
		// TODO Auto-generated constructor stub
	}
	
	private List<CashAccountInfo>  cashaccountlist = null;

	public List<CashAccountInfo> getCashaccountlist() {
		return cashaccountlist;
	}

	public void setCashaccountlist(List<CashAccountInfo> cashaccountlist) {
		this.cashaccountlist = cashaccountlist;
	}
	
   
	

}
