
package com.sales.web.app;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.common.log.JLogger;
import com.common.log.LoggerUtils;
import com.sales.common.until.ReturnCode;
import com.sales.config.Config;
import com.sales.service.ConfigService;
import com.sales.vo.AppConfigReq;
import com.sales.vo.AppConfigResp;
import com.sales.vo.base.MSG_TYPES;
import com.sales.vo.base.SalesReq;
import com.sales.vo.base.SalesResp;
import com.sales.web.base.SalesAction;
import com.sales.web.base.SalesScreenAdapterAction;
import com.sales.web.goods.LoadGoodsDetailAction;

@Component("AppConfigAction")
@Scope("prototype")
public class AppConfigAction extends SalesScreenAdapterAction{
	
	final private static JLogger logger = LoggerUtils.getLogger(AppConfigAction.class);
	
	@Autowired
	private ConfigService configService;

	@Override
	protected SalesResp work(SalesReq req)   throws Exception{
		// TODO Auto-generated method stub
		
		
		AppConfigReq appConfigReq = (AppConfigReq) req;
		
		
		AppConfigResp  appConfigResp = new AppConfigResp();

		try{
			String bootimgurl = configService.getSystemConfig(Config.bootimgurl);
			appConfigResp.setBootimgurl(bootimgurl);
		}catch (Exception e) {
			// TODO: handle exception
			logger.error("internal error:AppConfigAction" , e);
			throw e;
		}
		return appConfigResp;
	}

	@Override
	protected String getMsgType() {
		// TODO Auto-generated method stub
		return MSG_TYPES.MSG_APP_CONFIG;
	}


	@Override
	protected SalesReq setDefault(SalesReq req) {
		// TODO Auto-generated method stub
		return req;
	}

}
