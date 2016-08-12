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
import com.sales.common.until.CommonDefs;
import com.sales.common.until.ReturnCode;
import com.sales.common.until.SalesUtil;
import com.sales.exception.SalesInternalException;
import com.sales.model.TOrder;
import com.sales.model.TOrderGoods;
import com.sales.service.CashService;
import com.sales.service.OrderService;
import com.sales.service.UserService;
import com.sales.vo.ExitLoginReq;
import com.sales.vo.ExitLoginResp;
import com.sales.vo.GetAccountInfoReq;
import com.sales.vo.GetAccountInfoResp;
import com.sales.vo.GetCashHistReq;
import com.sales.vo.GetCashHistResp;
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
import com.sales.vo.base.SalesLoginedReq;
import com.sales.vo.base.SalesReq;
import com.sales.vo.base.SalesResp;
import com.sales.web.base.SalesAction;
import com.sales.web.base.SalesAuthAction;
import com.sales.web.base.SalesScreenAdapterAction;
import com.sales.web.goods.LoadGoodsDetailAction;

@Component("GetCashHistAction")
@Scope("prototype")
public class GetCashHistAction extends SalesAuthAction {

	final private static JLogger logger = LoggerUtils
			.getLogger(GetCashHistAction.class);

	@Autowired
	private CashService cashService;

	@Override
	protected SalesResp work(SalesReq req) throws Exception {
		// TODO Auto-generated method stub

	
		GetCashHistReq getCashHistReq = (GetCashHistReq) req;
		GetCashHistResp getCashHistResp = new GetCashHistResp();
		int width = getCashHistReq.getWidth();
		int height =  getCashHistReq.getHeight();
		int pid = getCashHistReq.getPid();
		String userid = getCashHistReq.getUserid();
		String status = getCashHistReq.getStatus();
		if(status != null && status.equals(CommonDefs.CASH_STATUS_ALL))
		{
			status = null;
		}
		try{
			int start = SalesUtil.GetCashListStartIndex(width, height, pid);
			int end =  SalesUtil.GetCashListEndIndex(width, height, pid);
			List<CashInfo> cashInfoList = cashService.getCashList(userid,status, start, end);
			getCashHistResp.setCashinfolist(cashInfoList);
		}
		catch(Exception e)
		{
			logger.error(e);
			throw e;
		}
		

		return getCashHistResp;
	}

	@Override
	protected String getMsgType() {
		// TODO Auto-generated method stub
		return MSG_TYPES.MSG_GET_CASH_HIST_LIST;
	}

	@Override
	protected SalesReq setDefault(SalesReq req) {
		// TODO Auto-generated method stub
		return req;
	}

}
