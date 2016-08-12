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
import com.sales.exception.SalesLogicalException;
import com.sales.model.TOrder;
import com.sales.model.TOrderGoods;
import com.sales.service.CashService;
import com.sales.service.OrderService;
import com.sales.vo.GetOrderReq;
import com.sales.vo.GetOrderResp;
import com.sales.vo.ModifyCashAccountReq;
import com.sales.vo.ModifyCashAccountResp;
import com.sales.vo.ModifyOrderReq;
import com.sales.vo.ModifyOrderResp;
import com.sales.vo.base.CashAccountInfo;
import com.sales.vo.base.MSG_TYPES;
import com.sales.vo.base.OrderGoods;
import com.sales.vo.base.SalesReq;
import com.sales.vo.base.SalesResp;
import com.sales.web.base.SalesAction;
import com.sales.web.base.SalesAuthAction;
import com.sales.web.goods.LoadGoodsDetailAction;

@Component("ModifyCashAccountAction")
@Scope("prototype")
public class ModifyCashAccountAction extends SalesAuthAction {

	final private static JLogger logger = LoggerUtils
			.getLogger(ModifyCashAccountAction.class);

	@Autowired
	private CashService cashService;

	@Override
	protected SalesResp work(SalesReq req) throws Exception {
		// TODO Auto-generated method stub

		
		ModifyCashAccountReq modifyCashAccountReq = (ModifyCashAccountReq) req;
		ModifyCashAccountResp modifyCashAccountResp = new ModifyCashAccountResp();
		
		String userid = modifyCashAccountReq.getUserid();
		String act = modifyCashAccountReq.getAction();
		CashAccountInfo info = modifyCashAccountReq.getAccountinfo();

		try {
			if(act.equals(CommonDefs.ORDER_ACT_ADD)){
				cashService.saveCashAccount(userid, info);
			}
			if(act.equals(CommonDefs.ORDER_ACT_MODIFY)){
				cashService.modifyCashAccount(userid, info);
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			throw e;
		}

		return modifyCashAccountResp;
	}

	@Override
	protected String getMsgType() {
		// TODO Auto-generated method stub
		return MSG_TYPES.MSG_MODIFY_CASH_ACCOUNT;
	}

	@Override
	protected void verifyReq(SalesReq req) throws Exception {
		// TODO Auto-generated method stub
		// ת��Ϊ����
		ModifyCashAccountReq modifyCashAccountReq = (ModifyCashAccountReq) req;
		String act = modifyCashAccountReq.getAction();
		if (null == act  || (!(act.equals(CommonDefs.CASH_ACCOUNT_ACT_ADD)) && (!(act.equals(CommonDefs.CASH_ACCOUNT_ACT_MODIFY))  )))
		{
			logger.debug(" action is invalid:" +modifyCashAccountReq.getJson() );
			throw new SalesInternalException(ReturnCode.RET_INTER_ARGS_ERROR, " action is invalid:" +modifyCashAccountReq.getJson());
		}
		CashAccountInfo info = modifyCashAccountReq.getAccountinfo();
		if (null == info )
		{
			logger.debug(" CashAccountInfo can not be null:" +modifyCashAccountReq.getJson() );
			throw new SalesInternalException(ReturnCode.RET_INTER_ARGS_ERROR, " CashAccountInfo id can not be null:" +modifyCashAccountReq.getJson());
		}
		if (act.equals(CommonDefs.CASH_ACCOUNT_ACT_MODIFY))
		{
			if (null == info.getId() || info.getId().length() <= 0)
			{
				logger.debug(" CashAccount id can not be null:" +modifyCashAccountReq.getJson() );
				throw new SalesInternalException(ReturnCode.RET_INTER_ARGS_ERROR, " CashAccount id can not be null:" +modifyCashAccountReq.getJson());
			}
		}
	}

	@Override
	protected SalesReq setDefault(SalesReq req) {
		// TODO Auto-generated method stub
		//ת��Ϊ����
		GetOrderReq getOrderReq = (GetOrderReq) req;
		if (StringUtils.isBlank(getOrderReq.getFormat())) {
			getOrderReq.setFormat("html");
			logger.debug("set format html");
		}
		return req;
	}

}
