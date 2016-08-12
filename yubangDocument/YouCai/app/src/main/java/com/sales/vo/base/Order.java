package com.sales.vo.base;

import java.util.List;

public class Order {
	
	private List<OrderGoods>  list = null;
	private String status = null;
	private String cnstatus = null;
	private String nextoper = null;
	private String orderid = null;
	private int  goodsnum = 0;
	private int totalprice = 0;
	public List<OrderGoods> getList() {
		return list;
	}
	public void setList(List<OrderGoods> list) {
		this.list = list;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getNextoper() {
		return nextoper;
	}
	public void setNextoper(String nextoper) {
		this.nextoper = nextoper;
	}
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public int getGoodsnum() {
		return goodsnum;
	}
	public void setGoodsnum(int goodsnum) {
		this.goodsnum = goodsnum;
	}
	public int getTotalprice() {
		return totalprice;
	}
	public void setTotalprice(int totalprice) {
		this.totalprice = totalprice;
	}
	public String getCnstatus() {
		return cnstatus;
	}
	public void setCnstatus(String cnstatus) {
		this.cnstatus = cnstatus;
	}
	
	
	

}
