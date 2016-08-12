package com.sales.vo;

import java.util.List;

import com.sales.vo.base.OrderCancelReason;
import com.sales.vo.base.OrderGoods;
import com.sales.vo.base.SalesLoginedReq;
import com.sales.vo.base.MSG_TYPES;

public class GetOrderProfitListReq extends SalesLoginedReq {

	public GetOrderProfitListReq() {
		super(MSG_TYPES.MSG_GET_ORDER_PROFIT_LIST);
		// TODO Auto-generated constructor stub
	}
	
	private int pid = 0;
	private String status = null;
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	

	
	
	
}
