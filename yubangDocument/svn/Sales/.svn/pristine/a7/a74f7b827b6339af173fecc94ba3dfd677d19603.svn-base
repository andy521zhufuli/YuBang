package com.sales.web.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sales.service.ConfigService;
import com.sales.vo.ShareReq;
import com.sales.vo.base.MSG_TYPES;
import com.sales.vo.base.SalesReq;
import com.sales.vo.base.SalesResp;
import com.sales.web.base.SalesAction;
@Component("ShareAction")
@Scope("prototype")
public class ShareAction extends SalesAction{

	@Autowired
	private ConfigService configService;
	
	@Override
	protected SalesResp work(SalesReq req) {
		// TODO Auto-generated method stub
		ShareReq shareReq = (ShareReq) req;
		return configService.getShareConfig(shareReq.getUserid(), shareReq.getAccessToken());
	}

	@Override
	protected String getMsgType() {
		// TODO Auto-generated method stub
		return MSG_TYPES.MSG_SHARE;
	}


	@Override
	protected SalesReq setDefault(SalesReq req) {
		// TODO Auto-generated method stub
		return req;
	}

}
