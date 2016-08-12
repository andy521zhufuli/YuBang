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
import com.sales.vo.base.CashInfo;
import com.sales.vo.base.MSG_TYPES;
import com.sales.vo.base.SalesReq;
import com.sales.vo.base.SalesResp;
import com.sales.web.base.SalesAuthAction;

@Component("CashOutAction")
@Scope("prototype")
public class CashOutAction extends SalesAuthAction {

	final private static JLogger logger = LoggerUtils
			.getLogger(CashOutAction.class);

	@Autowired
	private CashService cashService;

	@Override
	protected SalesResp work(SalesReq req) throws Exception {
		// TODO Auto-generated method stub

	
		CashOutReq cashOutReq = (CashOutReq)req;

		CashOutResp cashOutResp = new CashOutResp();
		try{
			CashInfo cashInfo = cashOutReq.getCashInfo();
			// 提交提现信息
			cashService.cashOut(Integer.valueOf(cashOutReq.getUserid()), cashInfo);
		}catch (Exception e) {
			// TODO: handle exception
			logger.error(e);
			throw e;
		}
		
		
		return cashOutResp;
	}

	@Override
	protected String getMsgType() {
		// TODO Auto-generated method stub
		return MSG_TYPES.MSG_CASH_OUT;
	}


	@Override
	protected SalesReq setDefault(SalesReq req) {
		// TODO Auto-generated method stub
		return req;
	}

}
