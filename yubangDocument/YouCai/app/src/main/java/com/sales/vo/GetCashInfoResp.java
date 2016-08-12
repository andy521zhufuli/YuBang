package com.sales.vo;

import java.util.List;


import com.sales.model.OrderDealHis;
import com.sales.vo.base.CashInfo;
import com.sales.vo.base.OrderGoods;
import com.sales.vo.base.MSG_TYPES;
import com.sales.vo.base.SalesResp;

public class GetCashInfoResp extends SalesResp {
	
	public GetCashInfoResp() {
		super(MSG_TYPES.MSG_GET_CASH_INFO);
		// TODO Auto-generated constructor stub
	}
	
  private CashInfo cashInfo = null;
	
	//处理记录
	private List<OrderDealHis> orderDealHisList;


	public List<OrderDealHis> getOrderDealHisList() {
		return orderDealHisList;
	}

	public void setOrderDealHisList(List<OrderDealHis> orderDealHisList) {
		this.orderDealHisList = orderDealHisList;
	}

	public CashInfo getCashInfo() {
		return cashInfo;
	}

	public void setCashInfo(CashInfo cashInfo) {
		this.cashInfo = cashInfo;
	}
	

	

}
