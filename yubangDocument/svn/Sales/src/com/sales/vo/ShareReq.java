package com.sales.vo;

import com.sales.vo.base.MSG_TYPES;
import com.sales.vo.base.SalesReq;

public class ShareReq extends SalesReq {

	public ShareReq() {
		super(MSG_TYPES.MSG_SHARE);
		// TODO Auto-generated constructor stub
	}
	
	//分享的类型，text，img，
	private String shareType;
	
    private String userid;
    
    private String accessToken;

	public String getShareType() {
		return shareType;
	}

	public void setShareType(String shareType) {
		this.shareType = shareType;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
    
    
}
