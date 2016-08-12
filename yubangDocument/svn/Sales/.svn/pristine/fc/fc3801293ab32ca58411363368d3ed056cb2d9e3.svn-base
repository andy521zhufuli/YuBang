package com.sales.common.until;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.common.log.JLogger;
import com.common.log.LoggerUtils;
import com.sales.model.TCashAccount;
import com.sales.model.TCashBank;
import com.sales.model.TGoods;
import com.sales.model.TGoodsImg;
import com.sales.model.TOrder;
import com.sales.model.TOrderGoods;
import com.sales.vo.base.BankInfo;
import com.sales.vo.base.BriefGoods;
import com.sales.vo.base.CashAccountInfo;
import com.sales.vo.base.DetailGoods;
import com.sales.vo.base.OrderGoods;

public class SalesUtil {
	
	final private static JLogger logger = LoggerUtils.getLogger(SalesUtil.class);
	
	


	
	/**
	 * 根据屏幕尺寸和页码计算商品列表的起始下标
	 * */
	public static int GetGoodsListStartIndex(int width, int height, int pid) {
		int sizeForFirstPage = ScreenAdapter.GetGoodsListSizeForFirstPage(width, height);
		
	    int start = 0;
	    if(pid <= 1)
	    {
	    	// 第一页总是从1号算起
	    	start = 0;
	    }
	    else
	    {
	    	int sizePerPage = ScreenAdapter.GetGoodsListSizePerPage(width, height);
	    	start =  sizeForFirstPage  + ( pid - 2 ) * sizePerPage;
	    }
		logger.debug("width : "+width+" height : "+height+" pid : "+pid+" start : "+start);
		return start;
	}
	
	/**
	 * 根据屏幕尺寸和页码计算上商品列表页的结束下标
	 * */
	public static int GetGoodsListEndIndex(int width, int height, int pid) {
		 int end = 0;
		 int sizePerPage = 0;
		 if(pid <= 1)
		 {
			 sizePerPage = ScreenAdapter.GetGoodsListSizeForFirstPage(width, height);
		 }
		 else
		 {
			 sizePerPage = ScreenAdapter.GetGoodsListSizePerPage(width, height);
		 }
		 end = GetGoodsListStartIndex(width, height, pid)  + sizePerPage;
		 
		 logger.debug("width : "+width+" height : "+height+" pid : "+pid+" end : "+end);
		 return end;
	}
	
	/**
	 * 根据屏幕尺寸和页码计算商品列表的起始下标
	 * */
	public static int GetOrderListStartIndex(int width, int height, int pid) {
		int sizeForFirstPage = ScreenAdapter.GetOrderListSizeForFirstPage(width, height);
		
	    int start = 0;
	    if(pid <= 1)
	    {
	    	// 第一页总是从1号算起
	    	start = 0;
	    }
	    else
	    {
	    	int sizePerPage = ScreenAdapter.GetOrderListSizePerPage(width, height);
	    	start =  sizeForFirstPage  + ( pid - 2 ) * sizePerPage;
	    }
		logger.debug("width : "+width+" height : "+height+" pid : "+pid+" start : "+start);
		return start;
	}
	
	/**
	 * 根据屏幕尺寸和页码计算上商品列表页的结束下标
	 * */
	public static int GetOrderListEndIndex(int width, int height, int pid) {
		 int end = 0;
		 int sizePerPage = 0;
		 if(pid <= 0)
		 {
			 sizePerPage = ScreenAdapter.GetOrderListSizeForFirstPage(width, height);
		 }
		 else
		 {
			 sizePerPage = ScreenAdapter.GetOrderListSizePerPage(width, height);
		 }
		 end = GetOrderListStartIndex(width, height, pid)  + sizePerPage;
		 
		 logger.debug("width : "+width+" height : "+height+" pid : "+pid+" end : "+end);
		 return end;
	}
	
	/**
	 * 根据屏幕尺寸和页码计算提现列表的起始下标
	 * */
	public static int GetCashListStartIndex(int width, int height, int pid) {
		int sizeForFirstPage = ScreenAdapter.GetCashListSizeForFirstPage(width, height);
		
	    int start = 0;
	    if(pid <= 1)
	    {
	    	// 第一页总是从1号算起
	    	start = 0;
	    }
	    else
	    {
	    	int sizePerPage = ScreenAdapter.GetCashListSizePerPage(width, height);
	    	start =  sizeForFirstPage  + ( pid - 2 ) * sizePerPage;
	    }
		logger.debug("width : "+width+" height : "+height+" pid : "+pid+" start : "+start);
		return start;
	}
	
	/**
	 * 根据屏幕尺寸和页码计算上提现列表页的结束下标
	 * */
	public static int GetCashListEndIndex(int width, int height, int pid) {
		 int end = 0;
		 int sizePerPage = 0;
		 if(pid <= 1)
		 {
			 sizePerPage = ScreenAdapter.GetCashListSizeForFirstPage(width, height);
		 }
		 else
		 {
			 sizePerPage = ScreenAdapter.GetCashListSizePerPage(width, height);
		 }
		 end = GetCashListStartIndex(width, height, pid)  + sizePerPage;
		 
		 logger.debug("width : "+width+" height : "+height+" pid : "+pid+" end : "+end);
		 return end;
	}
	
	/**
	 * ��TGoodsת��ΪBriefGoods
	 * @param goods
	 * @return
	 */
	public static BriefGoods transTgoodsToBriefGoods(TGoods goods){
		logger.debug(goods.getGoodsid()+" trans to briefGoods");
		BriefGoods briefGoods = new BriefGoods();
		briefGoods.setDiscountprice(goods.getDiscountprice());
		briefGoods.setGoodsid(String.valueOf(goods.getGoodsid()));
		briefGoods.setImgurl(goods.getListimgurl());
		briefGoods.setOriginalprice(goods.getOriginalprice());
		briefGoods.setSecondarytitle(goods.getSecondarytitle());
		briefGoods.setTitle(goods.getTitle());
		return briefGoods;
	}
	
	/**
	 * ��TGoodsת��ΪDetailGoods
	 * @param goods
	 * @return
	 */
//	public static DetailGoods transTgoodsToDetailGoods(TGoods goods){
//		logger.debug(goods.getGoodsid()+" trans to DetailGoods");
//		DetailGoods detailGoods = new DetailGoods();
//		detailGoods.setDiscountprice(goods.getDiscountprice());
//		detailGoods.setGoodsid(String.valueOf(goods.getGoodsid()));
//		List<String> imgUrls = new ArrayList<String>();
//		Set<TGoodsImg> goodsImgs = goods.getTGoodsImgs();
//		for (TGoodsImg tGoodsImg : goodsImgs) {
//			imgUrls.add(tGoodsImg.getUrl());
//		}
//		detailGoods.setImglist(imgUrls);
//		detailGoods.setImgurl(goods.getTitleimgurl());
//		detailGoods.setOriginalprice(goods.getOriginalprice());
//		detailGoods.setSecondarytitle(goods.getSecondarytitle());
//		detailGoods.setTitle(goods.getTitle());
//		return detailGoods;
//	}
	
//	/**
//	 * ��Set<TOrderGoods>ת��ΪList<OrderGoods>
//	 * @param goods
//	 * @return
//	 */
//	public static List<OrderGoods> transTOrderGoodsListToOrderGoodsList(Set<TOrderGoods> goods){
//		//��goodsidΪkey������OrderGoods����
//		Map<Integer, OrderGoods> orderGoodsMap = new HashMap<Integer, OrderGoods>();
//	
//		for (TOrderGoods tOrderGoods : goods) {
//	    TGoods tGoods = tOrderGoods.getTGoods();
//		logger.debug(tGoods.getGoodsid()+" trans to OrderGoods");
//		
//		OrderGoods orderGoods = null;
//		
//		if(orderGoodsMap.containsKey(tGoods.getGoodsid())){
//			orderGoods = orderGoodsMap.get(tGoods.getGoodsid());
//			orderGoods.setNum(orderGoods.getNum()+1);
//		}else{
//			orderGoods = new OrderGoods();
//			orderGoods.setDiscountprice(tGoods.getDiscountprice());
//			orderGoods.setGoodsid(String.valueOf(tGoods.getGoodsid()));
//			orderGoods.setImgurl(tGoods.getThumbimgurl());
//			orderGoods.setNum(1);
//			orderGoods.setOriginalprice(tGoods.getOriginalprice());
//			orderGoods.setSecondarytitle(tGoods.getSecondarytitle());
//			orderGoods.setTitle(tGoods.getTitle());
//			orderGoodsMap.put(tGoods.getGoodsid(), orderGoods);
//		}
//		
//		}
//		return new ArrayList<OrderGoods>(orderGoodsMap.values());
//	}
	
	
//	public static Set<TOrderGoods> transOrderGoodsListToTOrderGoodsList(List<OrderGoods> orderGoodsList,TOrder tOrder){
//		
//		Set<TOrderGoods> tOrderGoodsSet = new HashSet<TOrderGoods>();
//		
//		for (OrderGoods orderGoods : orderGoodsList) {
//			TOrderGoods tOrderGoods = new TOrderGoods();
//			TGoods tGoods = new TGoods();
//			tGoods.setGoodsid(Integer.parseInt(orderGoods.getGoodsid()));
//			tOrderGoods.setTGoods(tGoods);
//			tOrderGoods.setTOrder(tOrder);
//			tOrderGoodsSet.add(tOrderGoods);
//		}
//		
//		return tOrderGoodsSet;
//	}
	
	
	public static List<BankInfo> transBankListToBankInfoList(List<TCashBank> cashBanks){
		List<BankInfo> bankInfos = new ArrayList<BankInfo>();
		for (TCashBank tCashBank : cashBanks) {
			BankInfo bankInfo = new BankInfo();
			bankInfo.setBankid(tCashBank.getId());
			bankInfo.setImgurl(tCashBank.getImgurl());
			bankInfo.setName(tCashBank.getBankname());
			bankInfos.add(bankInfo);
		}
		return bankInfos;
	}
}
