package com.sales.vo.base;

import com.sales.model.TGoods;

public class Goods {
	private String goodsid = null;
	private String imgurl = null;
	private String title = null;
	public String getGoodsid() {
		return goodsid;
	}
	public void setGoodsid(String goodsid) {
		this.goodsid = goodsid;
	}
	public String getImgurl() {
		return imgurl;
	}
	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public void transferFromTGoods(TGoods goods)
	{
		this.setGoodsid(String.valueOf(goods.getGoodsid()));
		this.setTitle(goods.getTitle());
		this.setImgurl(goods.getTitleimgurl());
	}
}
