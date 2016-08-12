package com.sales.vo;

import java.util.List;


import com.sales.vo.base.CashInfo;
import com.sales.vo.base.OrderGoods;
import com.sales.vo.base.MSG_TYPES;
import com.sales.vo.base.OrderProfitInfo;
import com.sales.vo.base.SalesResp;

public class GetOrderProfitListResp extends SalesResp {
	
	public GetOrderProfitListResp() {
		super(MSG_TYPES.MSG_GET_ORDER_PROFIT_LIST);
		// TODO Auto-generated constructor stub
	}

	private List<OrderProfitInfo> profitlist = null;
}
