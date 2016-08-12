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
import com.sales.vo.ExitLoginReq;
import com.sales.vo.ExitLoginResp;
import com.sales.vo.GetAccountInfoReq;
import com.sales.vo.GetAccountInfoResp;
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
import com.sales.vo.base.CashAccountInfo;
import com.sales.vo.base.MSG_TYPES;
import com.sales.vo.base.OrderGoods;
import com.sales.vo.base.SalesReq;
import com.sales.vo.base.SalesResp;
import com.sales.web.base.SalesAction;
import com.sales.web.base.SalesScreenAdapterAction;
import com.sales.web.goods.LoadGoodsDetailAction;

@Component("GetCashAccountListAction")
@Scope("prototype")
public class GetCashAccountListAction extends SalesScreenAdapterAction {

	final private static JLogger logger = LoggerUtils
			.getLogger(GetCashAccountListAction.class);

	@Autowired
	private CashService cashService;

	@Override
	protected SalesResp work(SalesReq req) throws Exception {
		// TODO Auto-generated method stub

		// ת��Ϊ����
		GetCashAccountListReq getCashAccountListReq = (GetCashAccountListReq) req;

		GetCashAccountListResp getCashAccountListResp = new GetCashAccountListResp();
		try{
		List<CashAccountInfo> cashAccountInfoList = cashService.getCashAccount(getCashAccountListReq.getUserid());
		getCashAccountListResp.setCashaccountlist(cashAccountInfoList);
		
		}catch (Exception e) {
			// TODO: handle exception
			logger.error(e);
			throw e;
		}
		
		
		return getCashAccountListResp;
	}

	@Override
	protected String getMsgType() {
		// TODO Auto-generated method stub
		return MSG_TYPES.MSG_GET_CASH_ACCOUNT_LIST;
	}

	@Override
	protected SalesReq setDefault(SalesReq req) {
		// TODO Auto-generated method stub
		return req;
	}

}
