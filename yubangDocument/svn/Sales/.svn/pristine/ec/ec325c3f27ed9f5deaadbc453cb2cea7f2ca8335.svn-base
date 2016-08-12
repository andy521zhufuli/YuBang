package com.sales.web.goods;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.common.log.JLogger;
import com.common.log.LoggerUtils;
import com.sales.common.until.ReturnCode;
import com.sales.common.until.SalesUtil;
import com.sales.exception.SalesInternalException;
import com.sales.model.TGoods;
import com.sales.service.GoodsService;
import com.sales.vo.AppConfigReq;
import com.sales.vo.AppConfigResp;
import com.sales.vo.LoadGoodsDetailReq;
import com.sales.vo.LoadGoodsDetailResp;
import com.sales.vo.base.DetailGoods;
import com.sales.vo.base.MSG_TYPES;
import com.sales.vo.base.SalesReq;
import com.sales.vo.base.SalesResp;
import com.sales.web.base.SalesAction;
import com.sales.web.base.SalesScreenAdapterAction;

@Component("LoadGoodsDetailAction")
@Scope("prototype")
public class LoadGoodsDetailAction extends SalesScreenAdapterAction {

	final private static JLogger logger = LoggerUtils
			.getLogger(LoadGoodsDetailAction.class);
	
	public final static String GOODS_NOT_EXIT = "商品不存在";

	@Autowired
	private GoodsService goodsService;

	@Override
	protected SalesResp work(SalesReq req)  throws Exception{
		// TODO Auto-generated method stub

		LoadGoodsDetailReq loadGoodsDetailReq = (LoadGoodsDetailReq) req;

		LoadGoodsDetailResp loadGoodsDetailResp = new LoadGoodsDetailResp();

		try {
			String goodsid = loadGoodsDetailReq.getGoodsid();
			DetailGoods detailGoods = goodsService.getGoods(goodsid);
			if(detailGoods == null){
				logger.error("goods not existed: " + req.getJson() );
				throw new SalesInternalException(ReturnCode.RET_LOGICAL_GOODS_NOT_EXIST, "goods not existed:" +req.getJson());
			}
			else
			{
				loadGoodsDetailResp.setDetailGoods(detailGoods);
			}
		} catch (Exception e) {
			logger.error("internal error: LoadGoodsDetailAction", e);		
			throw e;
		}

		return loadGoodsDetailResp;
	}

	@Override
	protected String getMsgType() {
		// TODO Auto-generated method stub
		return MSG_TYPES.MSG_LOAD_GOODS_DETAIL;
	}
	
	@Override
	protected SalesReq setDefault(SalesReq req) {
		// TODO Auto-generated method stub
		return req;
	}

}
