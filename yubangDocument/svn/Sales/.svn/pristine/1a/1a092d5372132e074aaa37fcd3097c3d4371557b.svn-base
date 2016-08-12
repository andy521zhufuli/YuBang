package com.sales.vo;

import com.sales.vo.base.MSG_TYPES;
import com.sales.vo.base.SalesResp;

public class PreLoginResp extends SalesResp {
	
	public PreLoginResp() {
		super(MSG_TYPES.MSG_PRE_LOGIN);
		// TODO Auto-generated constructor stub
	}
	
	//应用唯一标识，在微信开放平台提交应用审核通过后获得
	private String appid;
	
	//应用授权作用域，如获取用户个人信息则填写snsapi_userinfo
	private String scope;
	
	//用于保持请求和回调的状态，授权请求后原样带回给第三方。该参数可用于防止csrf攻击（跨站请求伪造攻击），建议第三方带上该参数，可设置为简单的随机数加session进行校验
	private String state;

	//qq应用授权作用域
	private String qqScope;
	
	//qq应用ID
	private String qqAppID;
	
	//回调url
	private String redirect_uri;
	
	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getQqScope() {
		return qqScope;
	}

	public void setQqScope(String qqScope) {
		this.qqScope = qqScope;
	}

	public String getQqAppID() {
		return qqAppID;
	}

	public void setQqAppID(String qqAppID) {
		this.qqAppID = qqAppID;
	}

	public String getRedirect_uri() {
		return redirect_uri;
	}

	public void setRedirect_uri(String redirect_uri) {
		this.redirect_uri = redirect_uri;
	}
	
	
	
}