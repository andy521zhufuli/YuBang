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
import com.sales.vo.GetAddressListReq;
import com.sales.vo.GetAddressListResp;
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
import com.sales.vo.base.AddressInfo;
import com.sales.vo.base.CashInfo;
import com.sales.vo.base.MSG_TYPES;
import com.sales.vo.base.OrderGoods;
import com.sales.vo.base.SalesReq;
import com.sales.vo.base.SalesResp;
import com.sales.web.base.SalesAction;
import com.sales.web.base.SalesScreenAdapterAction;
import com.sales.web.goods.LoadGoodsDetailAction;

@Component("GetAddressListAction")
@Scope("prototype")
public class GetAddressListAction extends SalesScreenAdapterAction {

	final private static JLogger logger = LoggerUtils
			.getLogger(GetAddressListAction.class);
	
	@Autowired
	private UserService userService;

	@Override
	protected SalesResp work(SalesReq req) throws Exception {
		// TODO Auto-generated method stub

	
		GetAddressListReq getCashAccountListReq = (GetAddressListReq) req;

		GetAddressListResp getAddressListResp = new GetAddressListResp();
		
		try{
			//查询用户地址
			String useridStr = getCashAccountListReq.getUsrid();
			int userid = Integer.valueOf(useridStr);
			 List<AddressInfo> addressList = userService.getAddressList(userid);
			 getAddressListResp.setAddresslist(addressList);
		
		}catch (Exception e) {
			// TODO: handle exception
			logger.error(e);
			throw e;
		}
		
		
		return getAddressListResp;
	}

	@Override
	protected String getMsgType() {
		// TODO Auto-generated method stub
		return MSG_TYPES.MSG_GET_ADDRESS_LIST;
	}

	@Override
	protected SalesReq setDefault(SalesReq req) {
		// TODO Auto-generated method stub
		return req;
	}

}
