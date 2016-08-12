package com.sales.vo;

import java.util.List;


import com.sales.vo.base.FriendInfo;
import com.sales.vo.base.OrderGoods;
import com.sales.vo.base.MSG_TYPES;
import com.sales.vo.base.SalesResp;

public class GetFriendListResp extends SalesResp {
	
	public GetFriendListResp() {
		super(MSG_TYPES.MSG_GET_FRIENT_LIST);
		// TODO Auto-generated constructor stub
	}
	
	private List<FriendInfo>  friendlist = null;
	
	private String fuserid = null;
	private String userid = null;

	public List<FriendInfo> getFriendlist() {
		return friendlist;
	}

	public void setFriendlist(List<FriendInfo> friendlist) {
		this.friendlist = friendlist;
	}

	public String getFuserid() {
		return fuserid;
	}

	public void setFuserid(String fuserid) {
		this.fuserid = fuserid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	
	

}
