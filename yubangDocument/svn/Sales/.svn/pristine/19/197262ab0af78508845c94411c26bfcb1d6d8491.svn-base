package com.sales.web.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.common.log.JLogger;
import com.common.log.LoggerUtils;
import com.sales.common.until.ReturnCode;
import com.sales.service.AccessTokenService;
import com.sales.service.UserService;
import com.sales.vo.ExitLoginReq;
import com.sales.vo.ExitLoginResp;
import com.sales.vo.base.MSG_TYPES;
import com.sales.vo.base.SalesReq;
import com.sales.vo.base.SalesResp;
import com.sales.web.base.SalesAuthAction;

@Component("ExitLoginAction")
@Scope("prototype")
public class ExitLoginAction extends SalesAuthAction {

	final private static JLogger logger = LoggerUtils
			.getLogger(ExitLoginAction.class);

	@Override
	protected SalesResp work(SalesReq req) throws Exception{
		ExitLoginReq exitLoginReq = (ExitLoginReq) req;
		ExitLoginResp exitLoginResp = new ExitLoginResp();

		AccessTokenService accessTokenService = getAccessTokenService();
		if (accessTokenService != null)
		{
			String useridStr = exitLoginReq.getUserid();
			int usrid = Integer.valueOf(useridStr);
			accessTokenService.loginOut(usrid);
		}


		return exitLoginResp;
	}

	@Override
	protected String getMsgType() {
		// TODO Auto-generated method stub
		return MSG_TYPES.MSG_ExitLogin;
	}

	@Override
	protected SalesReq setDefault(SalesReq req) {
		// TODO Auto-generated method stub
		return req;
	}

}
