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
import com.sales.model.TCashAccount;
import com.sales.model.TOrder;
import com.sales.model.TOrderGoods;
import com.sales.service.CashService;
import com.sales.service.OrderService;
import com.sales.service.UserService;
import com.sales.vo.CashOutReq;
import com.sales.vo.CashOutResp;
import com.sales.vo.ExitLoginReq;
import com.sales.vo.ExitLoginResp;
import com.sales.vo.GetAccountInfoReq;
import com.sales.vo.GetAccountInfoResp;
import com.sales.vo.GetCancelOrderPromptReq;
import com.sales.vo.GetCancelOrderPromptResp;
import com.sales.vo.GetCashAccountListReq;
import com.sales.vo.GetCashAccountListResp;
import com.sales.vo.GetCashHistReq;
import com.sales.vo.GetCashInfoReq;
import com.sales.vo.GetCashInfoResp;
import com.sales.vo.GetFriendListReq;
import com.sales.vo.GetFriendListResp;
import com.sales.vo.GetHistOrdersReq;
import com.sales.vo.GetHistOrdersResp;
import com.sales.vo.GetOrderReq;
import com.sales.vo.GetOrderResp;
import com.sales.vo.base.CashInfo;
import com.sales.vo.base.MSG_TYPES;
import com.sales.vo.base.OrderGoods;
import com.sales.vo.base.SalesReq;
import com.sales.vo.base.SalesResp;
import com.sales.web.base.SalesAction;
import com.sales.web.goods.LoadGoodsDetailAction;

@Component("GetCancelOrderPromptAction")
@Scope("prototype")
public class GetCancelOrderPromptAction extends SalesAction {

	final private static JLogger logger = LoggerUtils
			.getLogger(GetCancelOrderPromptAction.class);

	@Override
	protected SalesResp work(SalesReq req) throws Exception {
		// TODO Auto-generated method stub
		
		//TODO 本页面直接返回一个静态页面

	
		GetCancelOrderPromptReq getCashAccountListReq = (GetCancelOrderPromptReq) req;

		GetCancelOrderPromptResp getCancelOrderPromptResp = new GetCancelOrderPromptResp();
		
		try{
		
			getCancelOrderPromptResp.setReturncode(ReturnCode.RET_SUCCESS);
		
		}catch (Exception e) {
			// TODO: handle exception
			logger.error(e);
			throw e;
		}
		
		
		return getCancelOrderPromptResp;
	}

	@Override
	protected String getMsgType() {
		// TODO Auto-generated method stub
		return MSG_TYPES.MSG_GET_CANCEL_ORDER_PROMPT;
	}

	@Override
	protected SalesReq setDefault(SalesReq req) {
		// TODO Auto-generated method stub
		return req;
	}

}
