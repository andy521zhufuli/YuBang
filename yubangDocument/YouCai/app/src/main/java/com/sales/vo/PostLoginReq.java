package com.sales.vo;

import com.sales.vo.base.MSG_TYPES;
import com.sales.vo.base.SalesReq;

public class PostLoginReq extends SalesReq {

	public PostLoginReq() {
		super(MSG_TYPES.MSG_POST_LOGIN);
		// TODO Auto-generated constructor stub
	}
	
	//登录类型
	private String loginType;
	
	//ERR_OK = 0(用户同意)	ERR_AUTH_DENIED = -4（用户拒绝授权）	ERR_USER_CANCEL = -2（用户取消）
    private String ErrCode;
    
    //用户换取access_token的code，仅在ErrCode为0时有效
    private String code;
    
    //第三方程序发送时用来标识其请求的唯一性的标志，由第三方程序调用sendReq时传入，由微信终端回传，state字符串长度不能超过1K
    private String state;
    
    //微信客户端当前语言
    private String lang;
    
    //微信用户当前国家信息
    private String country;
    
    //qq登录授权
    private String accessToken;

    //qq的openId
    private String qqOpenId;
    
	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

	public String getErrCode() {
		return ErrCode;
	}

	public void setErrCode(String errCode) {
		ErrCode = errCode;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getQqOpenId() {
		return qqOpenId;
	}

	public void setQqOpenId(String qqOpenId) {
		this.qqOpenId = qqOpenId;
	}
    
    
    
}
