package com.sales.vo.base;

import java.util.ArrayList;
import java.util.List;
import com.sales.common.until.CommonDefs;

public class OrderGoods  extends BriefGoods{
	

	
	private int num = 0;
	private int actualprice = 0;

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}
	
	public int getActualprice() {
		return actualprice;
	}

	public void setActualprice(int actualprice) {
		this.actualprice = actualprice;
	}

	/**
	 * 将商品列表拼装成字符串
	 * */
	public static String transferFromList(List<OrderGoods> goodsList)
	{
		if (goodsList == null || goodsList.size() <= 0)
		{
			return null;
		}
		
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (OrderGoods goods:goodsList)
		{
			if (!first)
			{
				sb.append(CommonDefs.GOODS_NUM_SPACER);
			}
			else
			{
				first = false;
			}
			sb.append(goods.getGoodsid());
			sb.append(CommonDefs.GOODS_ID_SPACER);
			sb.append(goods.getNum());
		}
		
		return sb.toString();
	}
	
	/**
	 * 将商品里列表字符串反序列化为list
	 * */
	public static List<OrderGoods> transferToList( String goodsListStr)
	{
		if (null == goodsListStr || goodsListStr.length() <= 0)
		{
		    return null;
		}
		
		List<OrderGoods> list = new ArrayList<OrderGoods>();
		String[] goodsArray = goodsListStr.split(CommonDefs.GOODS_NUM_SPACER);
		for (String goodsStr : goodsArray)
		{
			int spacerIndex = goodsStr.indexOf(CommonDefs.GOODS_ID_SPACER);
			if (spacerIndex <= 0)
			{
				
			}
			String goodsIdStr = goodsStr.substring(0, spacerIndex);
			String goodsNumStr  = goodsStr.substring(spacerIndex + 1);
			int goodsNum = Integer.valueOf(goodsNumStr);
			OrderGoods goods = new OrderGoods();
			goods.setGoodsid(goodsIdStr);
			goods.setNum(goodsNum);
			list.add(goods);			
		}		
		return list;			
	}


	
	
}
