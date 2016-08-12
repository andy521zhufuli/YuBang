package com.sales.vo;

import com.sales.vo.base.MSG_TYPES;
import com.sales.vo.base.SalesResp;

public class AppVersionResp extends SalesResp {
	
	public AppVersionResp() {
		super(MSG_TYPES.MSG_APP_VERSION);
		// TODO Auto-generated constructor stub
	}

	// ������ӭͼƬ
	private String latestVersionUrl = null;

	public String getLatestVersionUrl() {
		return latestVersionUrl;
	}

	public void setLatestVersionUrl(String latestVersionUrl) {
		this.latestVersionUrl = latestVersionUrl;
	}

	
}
