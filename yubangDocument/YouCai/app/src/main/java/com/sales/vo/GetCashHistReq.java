package com.sales.vo;

import java.util.List;

import com.sales.vo.base.OrderCancelReason;
import com.sales.vo.base.OrderGoods;
import com.sales.vo.base.SalesLoginedReq;
import com.sales.vo.base.MSG_TYPES;

public class GetCashHistReq extends SalesLoginedReq {

	public GetCashHistReq() {
		super(MSG_TYPES.MSG_GET_CASH_HIST_LIST);
		// TODO Auto-generated constructor stub
	}

	// 默认第一页
	private int pid = 1;
	private String status = null;
	public int getPid() 
	{
		return pid;
	}
	public void setPid(int pid) 
	{
		this.pid = pid;
	}
	public String getStatus() 
	{
		return status;
	}
	public void setStatus(String status)
	{
		this.status = status;
	}
	
	
	
	
}
