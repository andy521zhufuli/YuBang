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
import com.sales.vo.GetHistOrdersReq;
import com.sales.vo.GetHistOrdersResp;
import com.sales.vo.GetOrderReq;
import com.sales.vo.GetOrderResp;
import com.sales.vo.base.MSG_TYPES;
import com.sales.vo.base.Order;
import com.sales.vo.base.OrderGoods;
import com.sales.vo.base.SalesReq;
import com.sales.vo.base.SalesResp;
import com.sales.web.base.SalesAction;
import com.sales.web.base.SalesAuthAction;
import com.sales.web.goods.LoadGoodsDetailAction;
import com.sun.org.apache.bcel.internal.generic.RETURN;

/**
 * 查询历史订单列
 * @author zhoupeng
 *
 */
@Component("GetHistOrdersAction")
@Scope("prototype")
public class GetHistOrdersAction extends SalesAuthAction {

	final private static JLogger logger = LoggerUtils
			.getLogger(GetHistOrdersAction.class);

	@Autowired
	private OrderService orderService;

	@Override
	protected SalesResp work(SalesReq req) throws Exception {
		// TODO Auto-generated method stub

	  GetHistOrdersResp getHistOrdersResp = null;
	   try{
		GetHistOrdersReq getHistOrdersReq = (GetHistOrdersReq) req;
		
		int width = getHistOrdersReq.getWidth();
		int height =  getHistOrdersReq.getHeight();
		int pid = getHistOrdersReq.getPid();
		String userid = getHistOrdersReq.getUserid();
		String status = getHistOrdersReq.getStatus();

		// 计算起始下标和结束下标
		int begin = SalesUtil.GetGoodsListStartIndex(width, height, pid);
	    int end = SalesUtil.GetGoodsListEndIndex(width, height, pid);
	    List<Order> orderList = orderService.getHisOrder(userid, begin, end, status);
	    
	    getHistOrdersResp = new GetHistOrdersResp();
	    getHistOrdersResp.setOrderList(orderList);
	    
	   }catch (Exception e) {
		// TODO: handle exception
		   logger.error(e);
		   throw e;
	}
		return getHistOrdersResp;
	}

	@Override
	protected String getMsgType() {
		// TODO Auto-generated method stub
		return MSG_TYPES.MSG_GET_HIST_ORDERS;
	}


	@Override
	protected SalesReq setDefault(SalesReq req) {
		// TODO Auto-generated method stub
		return req;
	}

}
