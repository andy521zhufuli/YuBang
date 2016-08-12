package com.sales.web.base;


import com.common.log.JLogger;
import com.common.log.LoggerUtils;
import com.sales.common.until.ReturnCode;
import com.sales.exception.SalesInternalException;
import com.sales.vo.base.SalesReq;
import com.sales.vo.base.SalesWithScreenParamsReq;

public abstract class SalesScreenAdapterAction  extends SalesAction{
	
	final private static JLogger logger = LoggerUtils
			.getLogger(SalesScreenAdapterAction.class);

	  @Override
	  protected   void verifyReq(SalesReq req) throws Exception
	  {
		  super.verifyReq(req);
		  
		  SalesWithScreenParamsReq screenParamsReq = (SalesWithScreenParamsReq)req;
			if(screenParamsReq.getWidth()==0){
				logger.debug("width is null");
				throw new SalesInternalException(ReturnCode.RET_INTER_ARGS_ERROR, " width is null:" +req.getJson());
			}
			if(screenParamsReq.getHeight()==0){
				logger.debug("height is null");
				throw new SalesInternalException(ReturnCode.RET_INTER_ARGS_ERROR, " height is null:" +req.getJson());
			}
	}
}
