package com.sales.web.base;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.common.log.JLogger;
import com.common.log.LoggerUtils;
import com.sales.common.until.ReturnCode;
import com.sales.exception.SalesInternalException;
import com.sales.exception.SalesLogicalException;
import com.sales.service.AccessTokenService;
import com.sales.vo.base.SalesLoginedReq;
import com.sales.vo.base.SalesReq;

public abstract class SalesAuthAction  extends SalesAction{
	final private static JLogger logger = LoggerUtils
			.getLogger(SalesAuthAction.class);
	
	@Autowired
	private AccessTokenService accessTokenService;
	
	protected AccessTokenService getAccessTokenService()
	{
		return accessTokenService;
	}
	
	  @Override
	  protected   void verifyReq(SalesReq req) throws Exception
	  {
		  super.verifyReq(req);
		  
		  SalesLoginedReq loginedReq = (SalesLoginedReq)req;
		  
		  String useridStr = loginedReq.getUserid();
		  String accesstoken = loginedReq.getAuthorizedtoken();
		  
			if(StringUtils.isBlank(useridStr)){
				logger.debug("Userid is null");
				throw new SalesInternalException(ReturnCode.RET_INTER_ARGS_ERROR, " Userid is null:" +req.getJson());
			}
			
			if(StringUtils.isBlank(accesstoken)){
				logger.debug("accesstoken is null");
				throw new SalesInternalException(ReturnCode.RET_INTER_ARGS_ERROR, " accesstoken is null:" +req.getJson());
			} 
			
			int userid = Integer.valueOf(useridStr);			
			// TODO 鉴权
			accessTokenService.authAccessToken(userid, accesstoken);
	  }

}
