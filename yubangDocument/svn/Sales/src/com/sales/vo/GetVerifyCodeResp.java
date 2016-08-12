package com.sales.vo;

import java.util.List;


import com.sales.vo.base.OrderGoods;
import com.sales.vo.base.MSG_TYPES;
import com.sales.vo.base.SalesResp;

public class GetVerifyCodeResp extends SalesResp {
	
	public GetVerifyCodeResp() {
		super(MSG_TYPES.MSG_GET_VERIFY_CODE);
		// TODO Auto-generated constructor stub
	}

	private int retryinterval = 60;

	public int getRetryinterval() {
		return retryinterval;
	}

	public void setRetryinterval(int retryinterval) {
		this.retryinterval = retryinterval;
	}
}
