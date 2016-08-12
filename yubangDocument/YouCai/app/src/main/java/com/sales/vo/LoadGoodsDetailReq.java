package com.sales.vo;

import com.sales.vo.base.MSG_TYPES;
import com.sales.vo.base.SalesWithScreenParamsReq;

public class LoadGoodsDetailReq extends SalesWithScreenParamsReq {

	public LoadGoodsDetailReq() {
		super(MSG_TYPES.MSG_LOAD_GOODS_DETAIL);
		// TODO Auto-generated constructor stub
	}
	

	private String goodsid = null;

	public String getGoodsid() {
		return goodsid;
	}

	public void setGoodsid(String goodsid) {
		this.goodsid = goodsid;
	}
	

	
}
