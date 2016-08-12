package com.sales.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.common.log.JLogger;
import com.common.log.LoggerUtils;
import com.sales.common.until.CommonDefs;
import com.sales.common.until.ReturnCode;
import com.sales.common.until.SalesUtil;
import com.sales.dao.impl.TGoodsDAOImpl;
import com.sales.exception.SalesLogicalException;
import com.sales.model.TGoods;
import com.sales.model.TGoodsImg;
import com.sales.vo.base.BriefGoods;
import com.sales.vo.base.DetailGoods;
import com.sales.web.goods.LoadGoodsDetailAction;

@Service
@Transactional
public class GoodsService {
	final private static JLogger logger = LoggerUtils
			.getLogger(GoodsService.class);
	@Autowired
	private TGoodsDAOImpl tGoodsDAO;

	/**
	 * ��ȡ��ҳ����Ʒ�б�
	 * 
	 * @param begin
	 *            ��ʼ����
	 * @param end
	 *            �������
	 * @return ��ҳ����Ʒ�б�
	 * @throws SalesLogicalException 
	 */
	public List<BriefGoods> getGoods(int begin, int end) throws SalesLogicalException {
		List<TGoods> tGoodsList =  tGoodsDAO.getGoods(begin, end);
		
		if (null == tGoodsList)
		{
			logger.debug("no goods existed for begin:" + begin + ", end:" + end);
			throw new SalesLogicalException(ReturnCode.RET_LOGICAL_GOODS_ISNOTMORE, "no goods existed for begin:" + begin + ", end:" + end);
		}
		 List<BriefGoods> briefGoodsList = new ArrayList<BriefGoods>();
		for (TGoods tGoods : tGoodsList) {
			BriefGoods briefGoods = new BriefGoods();
			briefGoods.setDiscountprice(tGoods.getDiscountprice());
			briefGoods.setGoodsid(String.valueOf(tGoods.getGoodsid()));
			briefGoods.setImgurl(tGoods.getTitleimgurl());
			briefGoods.setOriginalprice(tGoods.getOriginalprice());
			briefGoods.setSecondarytitle(tGoods.getSecondarytitle());
			briefGoods.setTitle(tGoods.getTitle());
			briefGoodsList.add(briefGoods);
		}
		return briefGoodsList;
	}

	public DetailGoods getGoods(String goodsid) {
		TGoods tGoods = tGoodsDAO.findById(Integer.valueOf(goodsid));
		if (null == tGoods)
		{
			logger.debug("no goods existed for goodsid:" + goodsid);
			return null;
		}
		
		DetailGoods goods = new DetailGoods();
		
		goods.setDiscountprice(tGoods.getDiscountprice());
		goods.setGoodsid(String.valueOf(tGoods.getGoodsid()));
		goods.setImgurl(tGoods.getTitleimgurl());
		goods.setOriginalprice(tGoods.getOriginalprice());
		goods.setSecondarytitle(tGoods.getSecondarytitle());
		goods.setTitle(tGoods.getTitle());
		String listImgUrl = tGoods.getListimgurl();
		if (null != listImgUrl && listImgUrl.length() > 0)
		{
			String[] imgUrls = listImgUrl.split(CommonDefs.IMG_URL_SPACER);
			List<String> imgUrlsList = new ArrayList<String>();
			for(String imgUrl : imgUrls)
			{
				imgUrlsList.add(imgUrl);
			}
			goods.setImglist(imgUrlsList);
		}
		
		return goods;
	}
	
	
}
