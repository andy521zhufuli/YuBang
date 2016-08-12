package com.sales.vo;

import java.util.List;


import com.sales.vo.base.OrderGoods;
import com.sales.vo.base.MSG_TYPES;
import com.sales.vo.base.SalesResp;

public class GetAccountInfoResp extends SalesResp {
	
	public GetAccountInfoResp() {
		super(MSG_TYPES.MSG_GET_ACCOUNT_INFO);
		// TODO Auto-generated constructor stub
	}
	
	private String userid = null;  // 用户ID
	private String fuserid = null; // 上级用户的ID
	private String nickname = null; // 用户昵称
	private String headimgurl = null; // 用户头像URL
	private long friendnum = 0; // 好友个数
	private long ordernum = 0;  // 本月订单数
	private long cashbalance = 0;  // 可提现余额
	private long totalincome = 0;   // 累积输入
	private long ordervalueofmonth = 0; // 本月交易额
	private String logined = null; // 是否登录成功
	public long getFriendnum() {
		return friendnum;
	}
	public void setFriendnum(long friendnum) {
		this.friendnum = friendnum;
	}
	public long getOrdernum() {
		return ordernum;
	}
	public void setOrdernum(long ordernum) {
		this.ordernum = ordernum;
	}
	public long getCashbalance() {
		return cashbalance;
	}
	public void setCashbalance(long cashbalance) {
		this.cashbalance = cashbalance;
	}
	public long getTotalincome() {
		return totalincome;
	}
	public void setTotalincome(long totalincome) {
		this.totalincome = totalincome;
	}
	public long getOrdervalueofmonth() {
		return ordervalueofmonth;
	}
	public void setOrdervalueofmonth(long ordervalueofmonth) {
		this.ordervalueofmonth = ordervalueofmonth;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getHeadimgurl() {
		return headimgurl;
	}
	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}
	public String getLogined() {
		return logined;
	}
	public void setLogined(String logined) {
		this.logined = logined;
	}
	public String getFuserid() {
		return fuserid;
	}
	public void setFuserid(String fuserid) {
		this.fuserid = fuserid;
	}
	
	
	
	

	

}
