package com.sales.web.goods;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.common.log.JLogger;
import com.common.log.LoggerUtils;
import com.sales.common.until.ReturnCode;
import com.sales.common.until.SalesUtil;
import com.sales.config.Config;
import com.sales.model.TGoods;
import com.sales.service.ConfigService;
import com.sales.service.GoodsService;
import com.sales.vo.AppConfigReq;
import com.sales.vo.AppConfigResp;
import com.sales.vo.LoadGoodsListReq;
import com.sales.vo.LoadGoodsListResp;
import com.sales.vo.base.BriefGoods;
import com.sales.vo.base.Goods;
import com.sales.vo.base.MSG_TYPES;
import com.sales.vo.base.SalesReq;
import com.sales.vo.base.SalesResp;
import com.sales.web.base.SalesAction;
import com.sales.web.base.SalesScreenAdapterAction;

@Component("LoadGoodsListAction")
@Scope("prototype")
public class LoadGoodsListAction extends SalesScreenAdapterAction{
	
	final private static JLogger logger = LoggerUtils.getLogger(LoadGoodsListAction.class);
	
	@Autowired
	private GoodsService goodsService;

	@Override
	protected SalesResp work(SalesReq req) throws Exception
	{
		LoadGoodsListResp loadGoodsListResp = new LoadGoodsListResp();
		LoadGoodsListReq loadGoodsListReq = (LoadGoodsListReq) req;
		
		try{
			// 计算起始下标和结束下标
			int begin = SalesUtil.GetGoodsListStartIndex(loadGoodsListReq.getWidth(), loadGoodsListReq.getHeight(), loadGoodsListReq.getPid());
			int end = SalesUtil.GetGoodsListEndIndex(loadGoodsListReq.getWidth(), loadGoodsListReq.getHeight(), loadGoodsListReq.getPid());
			
			// 查询商品列表
			List<BriefGoods> briefGoodsList  = goodsService.getGoods(begin, end);
			if (briefGoodsList != null)
			{
				// 设置返回列表
				loadGoodsListResp.setGoodslist(briefGoodsList);
			}
		}catch (Exception e) {			
			logger.error("internal error: LoadGoodsDetailAction", e);
			throw e;
		}
		return loadGoodsListResp;
	}

	@Override
	protected String getMsgType() {
		return MSG_TYPES.MSG_LOAD_GOODS_LIST;
	}

	@Override
	protected SalesReq setDefault(SalesReq req) {
		LoadGoodsListReq loadGoodsListReq = (LoadGoodsListReq) req;
		
		if(loadGoodsListReq.getPid()==0){
			loadGoodsListReq.setPid(1);
			logger.debug("set default pid 1");
		}
		
		if(StringUtils.isBlank(loadGoodsListReq.getFormat())){
			loadGoodsListReq.setFormat("html");
			logger.debug("set format html");
		}
		return req;
	}

}
