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
import com.sales.service.UserService;
import com.sales.vo.PostLoginReq;
import com.sales.vo.PreLoginReq;
import com.sales.vo.base.MSG_TYPES;
import com.sales.vo.base.SalesReq;
import com.sales.vo.base.SalesResp;
import com.sales.web.base.SalesAction;

@Component("PostLoginAction")
@Scope("prototype")
public class PostLoginAction extends SalesAction{
	final private static JLogger logger = LoggerUtils.getLogger(PostLoginAction.class);
	
	@Autowired
	private UserService userService;

	@Override
	protected SalesResp work(SalesReq req) throws Exception {
		// TODO Auto-generated method stub
		PostLoginReq  loginReq = (PostLoginReq) req;
		
		//微信登陆
		if(loginReq.getLoginType().equals(CommonDefs.UST_TYPE_WEIXIN)){
			
			//用户同意登陆
			if(loginReq.getErrCode().equals(CommonDefs.ERR_OK)){
				return userService.loginWeiXin(loginReq);
			}
		}
		
		if(loginReq.getLoginType().equals(CommonDefs.UST_TYPE_QQ)){
			return userService.loginQQ(loginReq);
		}
		
		return null;
	}
	
	  @Override
	  protected   void verifyReq(SalesReq req) throws Exception
	  {
		  super.verifyReq(req);
		  
		  PostLoginReq postLoginedReq = (PostLoginReq)req;
		  
		  String code = postLoginedReq.getCode();
		  
		  if(StringUtils.isBlank(code)){
				logger.debug("code is null");
				throw new SalesInternalException(ReturnCode.RET_INTER_ARGS_ERROR, " code is null:" +req.getJson());
			}
	  }

	@Override
	protected String getMsgType() {
		// TODO Auto-generated method stub
		return MSG_TYPES.MSG_POST_LOGIN;
	}


	@Override
	protected SalesReq setDefault(SalesReq req) {
		// TODO Auto-generated method stub
		return req;
	}

}
