package com.sales.web.order;

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
import com.sales.vo.GetOrderReq;
import com.sales.vo.GetOrderResp;
import com.sales.vo.base.AddressInfo;
import com.sales.vo.base.MSG_TYPES;
import com.sales.vo.base.OrderGoods;
import com.sales.vo.base.SalesReq;
import com.sales.vo.base.SalesResp;
import com.sales.web.base.SalesAction;
import com.sales.web.base.SalesAuthAction;
import com.sales.web.goods.LoadGoodsDetailAction;

@Component("GetOrderAction")
@Scope("prototype")
public class GetOrderAction extends SalesAuthAction {

	final private static JLogger logger = LoggerUtils
			.getLogger(GetOrderAction.class);
	
	public final static String ORDERISNOTEXIT = "订单不存在";

	@Autowired
	private OrderService orderService;

	@Override
	protected SalesResp work(SalesReq req) throws Exception {
		GetOrderReq getOrderReq = (GetOrderReq) req;

		GetOrderResp getOrderResp = new GetOrderResp();

		try {
			TOrder order = orderService.getOrder(getOrderReq.getOrderid());
			if(order ==null){
			    logger.error("order not existed:" + getOrderReq.getOrderid());
				getOrderResp.setReturncode(ReturnCode.RET_LOGICAL_ORDER_NOT_EXIST);	
			}else{
				// 查询商品信息
				List<OrderGoods> goodsList = orderService.getGoodsList(order);
				getOrderResp.setGoodlist(goodsList);
				int totalPrice = 0;
				for(OrderGoods goods : goodsList)
				{
					totalPrice = totalPrice + goods.getActualprice();
				}
				getOrderResp.setTotalprice(totalPrice);
				
				// TODO 优惠额和运费，有待完善
				getOrderResp.setShippirce(0);
				getOrderResp.setReduceprice(0);
				getOrderResp.setActualprice(getOrderResp.getTotalprice() 
						- getOrderResp.getReduceprice() 
						- getOrderResp.getShippirce() );
				
				// 查询地址信息
				AddressInfo addressinfo  = new AddressInfo();
				addressinfo.setAddress(order.getAddress());
				addressinfo.setAddressname(order.getAddressname());
				addressinfo.setAddressphone(order.getAddressphone());			    
				getOrderResp.setAddressinfo(addressinfo);
				
				getOrderResp.setStatus(order.getStatus());
			}
		} catch (Exception e) {
			logger.error(e);
			throw e;
		}

		return getOrderResp;
	}

	@Override
	protected String getMsgType() {
		return MSG_TYPES.MSG_GET_ORDER;
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
