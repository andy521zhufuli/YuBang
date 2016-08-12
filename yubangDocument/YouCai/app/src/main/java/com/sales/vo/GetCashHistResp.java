package com.sales.vo;

import java.util.List;


import com.sales.vo.base.CashInfo;
import com.sales.vo.base.OrderGoods;
import com.sales.vo.base.MSG_TYPES;
import com.sales.vo.base.SalesResp;

public class GetCashHistResp extends SalesResp {
	
	public GetCashHistResp() {
		super(MSG_TYPES.MSG_GET_CASH_HIST_LIST);
		// TODO Auto-generated constructor stub
	}

	private List<CashInfo> cashinfolist = null;
	
	private String status = null;

	public List<CashInfo> getCashinfolist() {
		return cashinfolist;
	}

	public void setCashinfolist(List<CashInfo> cashinfolist) {
		this.cashinfolist = cashinfolist;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	

}
