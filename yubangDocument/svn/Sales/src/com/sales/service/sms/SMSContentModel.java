package com.sales.service.sms;

public class SMSContentModel {
	
	//公司名称前面的文字
	String companyPre;
	
	//公司名称
	String company;
	
	//验证码前面的文字
	String captchaPre;
	
	//验证码
	String captcha;
	
	//验证码结束文字
	String captchaEnd;

	public String getCompanyPre() {
		return companyPre;
	}

	public void setCompanyPre(String companyPre) {
		this.companyPre = companyPre;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getCaptchaPre() {
		return captchaPre;
	}

	public void setCaptchaPre(String captchaPre) {
		this.captchaPre = captchaPre;
	}

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public String getCaptchaEnd() {
		return captchaEnd;
	}

	public void setCaptchaEnd(String captchaEnd) {
		this.captchaEnd = captchaEnd;
	}

	public String toString(){
		return companyPre+company+captchaPre+captcha+captchaEnd;
	}
}
