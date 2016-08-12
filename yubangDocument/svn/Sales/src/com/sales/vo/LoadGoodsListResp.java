package com.sales.vo;

import java.util.List;

import com.sales.vo.base.BriefGoods;
import com.sales.vo.base.MSG_TYPES;
import com.sales.vo.base.SalesResp;

public class LoadGoodsListResp extends SalesResp {
	
	public LoadGoodsListResp() {
		super(MSG_TYPES.MSG_LOAD_GOODS_LIST);
		// TODO Auto-generated constructor stub
	}

	private List<BriefGoods> goodslist = null;

	public List<BriefGoods> getGoodslist() {
		return goodslist;
	}

	public void setGoodslist(List<BriefGoods> goodslist) {
		this.goodslist = goodslist;
	}

	

}
