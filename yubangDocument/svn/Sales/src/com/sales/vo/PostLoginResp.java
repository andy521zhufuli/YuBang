package com.sales.vo;

import com.sales.vo.base.MSG_TYPES;
import com.sales.vo.base.SalesResp;

public class PostLoginResp extends SalesResp {
	
	public PostLoginResp() {
		super(MSG_TYPES.MSG_POST_LOGIN);
		// TODO Auto-generated constructor stub
	}

	//用户名
	private String userid = null;

	//授权码
	private String authorizedtoken = null;

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getAuthorizedtoken() {
		return authorizedtoken;
	}

	public void setAuthorizedtoken(String authorizedtoken) {
		this.authorizedtoken = authorizedtoken;
	}
	

}
