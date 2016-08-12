package com.sales.web.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.common.log.JLogger;
import com.common.log.LoggerUtils;
import com.sales.common.until.ReturnCode;
import com.sales.common.until.SalesUtil;
import com.sales.model.TOrder;
import com.sales.model.TOrderGoods;
import com.sales.service.OrderService;
import com.sales.service.UserService;
import com.sales.vo.ExitLoginReq;
import com.sales.vo.ExitLoginResp;
import com.sales.vo.GetAccountInfoReq;
import com.sales.vo.GetAccountInfoResp;
import com.sales.vo.GetOrderReq;
import com.sales.vo.GetOrderResp;
import com.sales.vo.base.MSG_TYPES;
import com.sales.vo.base.OrderGoods;
import com.sales.vo.base.SalesReq;
import com.sales.vo.base.SalesResp;
import com.sales.web.base.SalesAction;
import com.sales.web.base.SalesScreenAdapterAction;
import com.sales.web.goods.LoadGoodsDetailAction;

@Component("GetAccountInfoAction")
@Scope("prototype")
public class GetAccountInfoAction extends SalesScreenAdapterAction {

	final private static JLogger logger = LoggerUtils
			.getLogger(GetAccountInfoAction.class);

	@Autowired
	private UserService userService;

	@Override
	protected SalesResp work(SalesReq req) throws Exception {
		// TODO Auto-generated method stub

	
		GetAccountInfoReq getAccountInfoReq = (GetAccountInfoReq) req;

	
		GetAccountInfoResp getAccountInfoResp = null;

		try {
			// 查询用户的账户信息
			getAccountInfoResp = userService.getAccountInfo(getAccountInfoReq);
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e);
			throw e;
		}

		return getAccountInfoResp;
	}

	@Override
	protected String getMsgType() {
		// TODO Auto-generated method stub
		return MSG_TYPES.MSG_GET_ACCOUNT_INFO;
	}


	@Override
	protected SalesReq setDefault(SalesReq req) {
		// TODO Auto-generated method stub
		return req;
	}

}
