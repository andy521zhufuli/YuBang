package com.sales.vo;

import com.sales.vo.base.MSG_TYPES;
import com.sales.vo.base.SalesResp;

public class AppConfigResp extends SalesResp {
	
	public AppConfigResp() {
		super(MSG_TYPES.MSG_APP_CONFIG);
		// TODO Auto-generated constructor stub
	}

	private String bootimgurl = null;

	public String getBootimgurl() {
		return bootimgurl;
	}

	public void setBootimgurl(String bootimgurl) {
		this.bootimgurl = bootimgurl;
	}
	
	

}
