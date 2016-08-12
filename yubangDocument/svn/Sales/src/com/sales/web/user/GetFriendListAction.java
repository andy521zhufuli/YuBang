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
import com.sales.vo.GetFriendListReq;
import com.sales.vo.GetFriendListResp;
import com.sales.vo.GetOrderReq;
import com.sales.vo.GetOrderResp;
import com.sales.vo.base.FriendInfo;
import com.sales.vo.base.MSG_TYPES;
import com.sales.vo.base.OrderGoods;
import com.sales.vo.base.SalesReq;
import com.sales.vo.base.SalesResp;
import com.sales.web.base.SalesAction;
import com.sales.web.base.SalesAuthAction;
import com.sales.web.base.SalesScreenAdapterAction;
import com.sales.web.goods.LoadGoodsDetailAction;

@Component("GetFriendListAction")
@Scope("prototype")
public class GetFriendListAction extends SalesAuthAction {

	final private static JLogger logger = LoggerUtils
			.getLogger(GetFriendListAction.class);

	@Autowired
	private UserService userService;

	@Override
	protected SalesResp work(SalesReq req) throws Exception {
		// TODO Auto-generated method stub

		
		GetFriendListReq getFriendListReq = (GetFriendListReq) req;
		GetFriendListResp getFriendListResp = new GetFriendListResp();
		
		try
		{
			int fuserid = Integer.valueOf(getFriendListReq.getFuserid());
			List<FriendInfo>  friendList = userService.getFriendList(fuserid);
			getFriendListResp.setFriendlist(friendList);
		}
		catch(Exception e)
		{
			logger.error(e);
			throw e;
		}
		
	

	

		return getFriendListResp;
	}

	@Override
	protected String getMsgType() {
		// TODO Auto-generated method stub
		return MSG_TYPES.MSG_GET_FRIENT_LIST;
	}


	@Override
	protected SalesReq setDefault(SalesReq req) {
		// TODO Auto-generated method stub
		return req;
	}

}
