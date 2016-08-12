package com.sales.vo.base;

public class SalesLoginedReq  extends SalesWithScreenParamsReq {
	public SalesLoginedReq(String msgtype) {
		super(msgtype);
		// TODO Auto-generated constructor stub
	}
	
	// �û�ID
	private String userid = null;
	
	// ��������
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

