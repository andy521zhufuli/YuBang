package com.sales.vo.base;

public class OrderProfitInfo {
	
	private String orderid = null; // 订单号
	private String ordervalue = null; // 订单金额
	private String userid = null; // 受益者用户Id(本人)
	private String rate = null; //返点率
	private String value = null;// 返点金额， 以分为单位
    private String friendnickname = null; // 好友昵称（贡献者昵称）
    private String updatetime  = null; //结算时间戳
    private String status = null;//结算状态
    
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public String getOrdervalue() {
		return ordervalue;
	}
	public void setOrdervalue(String ordervalue) {
		this.ordervalue = ordervalue;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getFriendnickname() {
		return friendnickname;
	}
	public void setFriendnickname(String friendnickname) {
		this.friendnickname = friendnickname;
	}
	public String getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	

}
