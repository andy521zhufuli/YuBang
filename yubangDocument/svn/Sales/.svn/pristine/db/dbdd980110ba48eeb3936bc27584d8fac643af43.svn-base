package com.sales.web.login;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.common.log.JLogger;
import com.common.log.LoggerUtils;
import com.sales.common.until.CommonDefs;
import com.sales.common.until.ReturnCode;
import com.sales.exception.SalesInternalException;
import com.sales.service.ConfigService;
import com.sales.vo.PreLoginReq;
import com.sales.vo.PreLoginResp;
import com.sales.vo.base.MSG_TYPES;
import com.sales.vo.base.SalesLoginedReq;
import com.sales.vo.base.SalesReq;
import com.sales.vo.base.SalesResp;
import com.sales.web.base.SalesAction;
import com.sun.org.apache.bcel.internal.generic.RETURN;

@Component("PreLoginAction")
@Scope("prototype")
public class PreLoginAction extends SalesAction{
	
   final private static JLogger logger = LoggerUtils.getLogger(PreLoginAction.class);
	
	@Autowired
	private ConfigService configService;

	@Override
	protected SalesResp work(SalesReq req) {
		// TODO Auto-generated method stub 
		PreLoginReq preLoginConfigReq = (PreLoginReq) req;
		PreLoginResp preLoginConfigResp = new PreLoginResp();
		preLoginConfigResp.setAppid(configService.getSystemConfig(CommonDefs.APPID));
		preLoginConfigResp.setScope(configService.getSystemConfig(CommonDefs.SCOPE));
		preLoginConfigResp.setState(configService.getSystemConfig(CommonDefs.STATE));
		preLoginConfigResp.setQqScope(configService.getSystemConfig(CommonDefs.QQSCOPE));
		preLoginConfigResp.setQqAppID(configService.getSystemConfig(CommonDefs.QQAPPID));
		preLoginConfigResp.setRedirect_uri(configService.getSystemConfig(CommonDefs.REDIRECT_URI));
		return preLoginConfigResp;
	}

	@Override
	protected String getMsgType() {
		// TODO Auto-generated method stub
		return MSG_TYPES.MSG_PRE_LOGIN;
	}
	
	  @Override
	  protected   void verifyReq(SalesReq req) throws Exception
	  {
		  super.verifyReq(req);
		  
		  PreLoginReq preLoginedReq = (PreLoginReq)req;
		  
		  String method = preLoginedReq.getMethod();

			if((method == null)||(!(method.equals(CommonDefs.UST_TYPE_QQ) || method.equals(CommonDefs.UST_TYPE_WEIXIN))))
			{
				logger.debug("invalid method:" + req.getJson());
				throw new SalesInternalException(ReturnCode.RET_INTER_ARGS_ERROR, " accesstoken is null:" +req.getJson());
			} 
	  }


	@Override
	protected SalesReq setDefault(SalesReq req) {
		// TODO Auto-generated method stub
		return req;
	}

}
