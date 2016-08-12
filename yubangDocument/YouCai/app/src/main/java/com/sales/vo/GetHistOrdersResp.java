package com.sales.vo;

import java.util.List;


import com.sales.vo.base.MyOrder;
import com.sales.vo.base.Order;
import com.sales.vo.base.OrderGood;
import com.sales.vo.base.OrderGoods;
import com.sales.vo.base.MSG_TYPES;
import com.sales.vo.base.SalesResp;

public class GetHistOrdersResp extends SalesResp {
	
	public GetHistOrdersResp() {
		super(MSG_TYPES.MSG_GET_HIST_ORDERS);
		// TODO Auto-generated constructor stub
	}
	
	private String status = null;
	private List<Order> orderList ;

	public List<Order> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<Order> orderList) {
		this.orderList = orderList;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	


	
	
	

}
