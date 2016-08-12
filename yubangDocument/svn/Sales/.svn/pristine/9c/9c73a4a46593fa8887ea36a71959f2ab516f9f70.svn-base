package com.sales.web.sms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.sales.service.SmsService;
import com.sales.vo.SendSmsReq;
import com.sales.vo.base.MSG_TYPES;
import com.sales.vo.base.SalesReq;
import com.sales.vo.base.SalesResp;
import com.sales.web.base.SalesAction;
import com.sales.web.base.SalesAuthAction;

@Component("SendSmsAction")
@Scope("prototype")
public class SendSmsAction extends SalesAuthAction{

	@Autowired
   private	SmsService smsService;

	@Override
	protected SalesResp work(SalesReq req) throws Exception {
		// TODO Auto-generated method stub
		SendSmsReq sendSmsReq = (SendSmsReq) req;
		
		return smsService.getCaptcha(sendSmsReq.getUserid(), sendSmsReq.getPhone());
	}

	@Override
	protected String getMsgType() {
		// TODO Auto-generated method stub
		return MSG_TYPES.MSG_SENDSMS;
	}

	@Override
	protected SalesReq setDefault(SalesReq req) {
		// TODO Auto-generated method stub
		return req;
	}

}
