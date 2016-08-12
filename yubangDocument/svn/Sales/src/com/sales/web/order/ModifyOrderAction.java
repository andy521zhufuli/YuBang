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
import com.sales.common.until.CommonDefs;
import com.sales.common.until.ReturnCode;
import com.sales.common.until.SalesUtil;
import com.sales.exception.SalesInternalException;
import com.sales.exception.SalesLogicalException;
import com.sales.model.TOrder;
import com.sales.model.TOrderGoods;
import com.sales.service.OrderService;
import com.sales.vo.GetOrderReq;
import com.sales.vo.GetOrderResp;
import com.sales.vo.ModifyOrderReq;
import com.sales.vo.ModifyOrderResp;
import com.sales.vo.base.MSG_TYPES;
import com.sales.vo.base.OrderGoods;
import com.sales.vo.base.SalesReq;
import com.sales.vo.base.SalesResp;
import com.sales.web.base.SalesAction;
import com.sales.web.base.SalesAuthAction;
import com.sales.web.goods.LoadGoodsDetailAction;

@Component("ModifyOrderAction")
@Scope("prototype")
public class ModifyOrderAction extends SalesAuthAction {

	final private static JLogger logger = LoggerUtils
			.getLogger(ModifyOrderAction.class);

	@Autowired
	private OrderService orderService;

	@Override
	protected SalesResp work(SalesReq req) throws Exception {
		// TODO Auto-generated method stub

		
		ModifyOrderReq modifyOrderReq = (ModifyOrderReq) req;

		
		ModifyOrderResp modifyOrderResp = new ModifyOrderResp();

		try {
			if(modifyOrderReq.getAction().equals(CommonDefs.ORDER_ACT_ADD)){
				modifyOrderResp.setOrderId( orderService.addOrder(modifyOrderReq));
			}
			if(modifyOrderReq.getAction().equals(CommonDefs.ORDER_ACT_MODIFY)){
				modifyOrderResp.setOrderId(orderService.modifyOrder(modifyOrderReq));
			}
			if(modifyOrderReq.getAction().equals(CommonDefs.ORDER_ACT_COMMIT)){
				modifyOrderResp.setOrderId(orderService.commitOrder(modifyOrderReq));
			}
			if(modifyOrderReq.getAction().equals(CommonDefs.ORDER_ACT_CANCEL)){
				modifyOrderResp.setOrderId(orderService.cancelOrder(modifyOrderReq));
			}
		} catch (Exception e) {
			logger.error(e);
			throw e;
		}

		return modifyOrderResp;
	}

	@Override
	protected String getMsgType() {
		// TODO Auto-generated method stub
		return MSG_TYPES.MSG_MODIFY_ORDER;
	}

	@Override
	protected void verifyReq(SalesReq req) throws Exception {
		// TODO Auto-generated method stub
		// ת��Ϊ����
		ModifyOrderReq modifyOrderReq = (ModifyOrderReq) req;

		if (!modifyOrderReq.getAction().equals(CommonDefs.ORDER_ACT_ADD)
				&&StringUtils.isBlank(modifyOrderReq.getOrderid())) {
			logger.debug(" order id can not be null:" +modifyOrderReq.getJson() );
			throw new SalesInternalException(ReturnCode.RET_INTER_ARGS_ERROR, " order id can not be null:" +modifyOrderReq.getJson());
		}
		if ((modifyOrderReq.getAction().equals(CommonDefs.ORDER_ACT_ADD)
				||modifyOrderReq.getAction().equals(CommonDefs.ORDER_ACT_MODIFY))
				&&(modifyOrderReq.getGoodlist()==null
				||modifyOrderReq.getGoodlist().size()==0)) {
			logger.debug(" goodsList can not be null");
			throw new SalesInternalException(ReturnCode.RET_INTER_ARGS_ERROR, " goodsList can not be null:" +modifyOrderReq.getJson());
		}
		if(modifyOrderReq.getAction().equals(CommonDefs.ORDER_ACT_CANCEL)
				&&StringUtils.isBlank(modifyOrderReq.getCommittoken())){
			logger.debug(" commitTocken can not be null");
			throw new SalesInternalException(ReturnCode.RET_INTER_ARGS_ERROR, " commitTocken can not be null:" +modifyOrderReq.getJson());
		}
		if(modifyOrderReq.getAction().equals(CommonDefs.ORDER_ACT_RECEIVE)
				&&modifyOrderReq.getOrdercancelreason()==null){
			logger.debug(" cancelReason can not be null");
			throw new SalesInternalException(ReturnCode.RET_INTER_ARGS_ERROR, " cancelReason id can not be null:" +modifyOrderReq.getJson());
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
