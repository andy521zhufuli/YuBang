package com.sales.vo.base;

public class BriefGoods  extends Goods{
	private String secondarytitle = null;
	private int originalprice = 0;
	private int discountprice = 0;
	public String getSecondarytitle() {
		return secondarytitle;
	}
	public void setSecondarytitle(String secondarytitle) {
		this.secondarytitle = secondarytitle;
	}
	public int getOriginalprice() {
		return originalprice;
	}
	public void setOriginalprice(int originalprice) {
		this.originalprice = originalprice;
	}
	public int getDiscountprice() {
		return discountprice;
	}
	public void setDiscountprice(int discountprice) {
		this.discountprice = discountprice;
	}
	
	
}
