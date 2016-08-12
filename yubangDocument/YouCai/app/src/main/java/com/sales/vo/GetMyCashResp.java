package com.sales.vo;

import java.math.BigDecimal;
import java.util.List;

import com.sales.vo.base.CashInfo;
import com.sales.vo.base.OrderGoods;
import com.sales.vo.base.MSG_TYPES;
import com.sales.vo.base.SalesResp;

public class GetMyCashResp extends SalesResp {

	public GetMyCashResp() {
		super(MSG_TYPES.MSG_GET_MY_CASH);
		// TODO Auto-generated constructor stub
	}

	private String userid;
	private String cashbalance;
	private String incash;
	private String finishedCash;
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getCashbalance() {
		return cashbalance;
	}
	public void setCashbalance(String cashbalance) {
		this.cashbalance = cashbalance;
	}
	public String getIncash() {
		return incash;
	}
	public void setIncash(String incash) {
		this.incash = incash;
	}
	public String getFinishedCash() {
		return finishedCash;
	}
	public void setFinishedCash(String finishedCash) {
		this.finishedCash = finishedCash;
	}
	
	
	

}
