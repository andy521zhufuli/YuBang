package com.sales.web.base;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.common.log.JLogger;
import com.common.log.LoggerUtils;
import com.common.web.struts.BaseAction;
import com.sales.common.until.ReturnCode;
import com.sales.exception.SalesInternalException;
import com.sales.exception.SalesLogicalException;
import com.sales.vo.base.SalesMsgUtils;
import com.sales.vo.base.SalesReq;
import com.sales.vo.base.SalesResp;

public abstract class SalesAction  extends BaseAction{
	
	final private static JLogger logger = LoggerUtils
			.getLogger(SalesAction.class);
	  //
	  protected abstract SalesResp work(SalesReq req)  throws Exception;
	  
	  protected abstract String getMsgType();
	  
	  protected   void verifyReq(SalesReq req) throws Exception
	  {
			if(StringUtils.isBlank(req.getSeq())){
				logger.debug("seq is null");
				throw new SalesInternalException(ReturnCode.RET_INTER_ARGS_ERROR, " seq is null:" +req.getJson());
			}
			if(StringUtils.isBlank(req.getAppversion())){
				logger.debug("Appversion is null");
				throw new SalesInternalException(ReturnCode.RET_INTER_ARGS_ERROR, " Appversion is null:" +req.getJson());
			}
			if(StringUtils.isBlank(req.getOs())){
				logger.debug("os is null");
				throw new SalesInternalException(ReturnCode.RET_INTER_ARGS_ERROR, " os is null:" +req.getJson());
			}
			if(StringUtils.isBlank(req.getDeviceid())){
				logger.debug("Deviceid is error");
				throw new SalesInternalException(ReturnCode.RET_INTER_ARGS_ERROR, " Deviceid is error:" +req.getJson());
			}
	  }
	  
	  protected abstract SalesReq setDefault(SalesReq req);

	  
       public String doAction() throws Exception
       {
    	   // 获取消息类型
    	   String msgType  = getMsgType();    	   
    	   
    	   // 从请求消息里获取请求报文
    	   HttpServletRequest request = getRequest();
    	   String reqJson = getRequestPostMsg(request);    	   
    	  
    	   // 将请求报文反序列化到请求对象
    	   SalesReq req = (SalesReq)(SalesMsgUtils.fromJson(reqJson, msgType, true));
    	   if (null == req)
    	   {
    		   // 请求为空， 需要抛出异常
    		   return ReturnCode.RET_NULL+"";
    	   }
          
    	   // 如果序列号为空， 则产生新的序列号
    	   if (req != null &&( req.getSeq() == null || req.getSeq().length() <= 0 ))
    	   {
    		   String seq = SeqGenerator.BuildSeq();
    		   req.setSeq(seq);
    	   }
    	   
    	   // 验证各项请求参数
     	   SalesResp resp = null;
    	   try
    	   {
               verifyReq(req);
               resp = work(req);
               resp.setReturncode(ReturnCode.RET_SUCCESS);
    	   }
    	   catch (SalesLogicalException e)
    	   {
    		   if (resp == null)
    		   {
    			   resp = new SalesResp(req.getMsgtype());
    		   }
    		   logger.error("logical error, code:" + e.getExpCode() + ", errorMsg:"  + e.getErrMsg() + ", req json: "+ req.getJson());
    		   resp.setReturncode(e.getExpCode());
    	   }
    	   catch (SalesInternalException e)
    	   {
    		   if (resp == null)
    		   {
    			   resp = new SalesResp(req.getMsgtype());
    		   }
    		   logger.error("Internal error, code:" + e.getExpCode() + ", errorMsg:"  + e.getErrMsg()+ ", req json: "+ req.getJson());
    		   resp.setReturncode(e.getExpCode());
    	   }    	   
    	   catch (Exception e)
    	   {
    		   if (resp == null)
    		   {
    			   resp = new SalesResp(req.getMsgtype());
    		   }
    		   System.out.println(e);
    		   resp.setReturncode(ReturnCode.RET_ERROR);
    	   }

  
		  resp.setSeq(req.getSeq());
		  
		  if (req.isHtml())
		  {
			 // HttpServletResponse response = getResponse();
			//  response.addHeader("returncode",String.valueOf(resp.getReturncode()));
			  request.setAttribute("obj", resp);
			  return  resp.getReturncode()+"";
		  }
		  else
		  {
			  
			  String respJson = SalesMsgUtils.toJson(resp);
			  getRequest().setAttribute("json", respJson);
			  
			  PrintWriter pw= null;
			  try{
			  pw=getPrintWriter();
			  pw.write(respJson);
			  }
			  catch(Exception e)
			  {
				  
			  }
			  finally
			  {
                  pw.flush();
                  pw.close(); 
			  }
			  
			  return null;
			  
		  }
		  
		  //return resp.getReturncode()+"";
		  
		   
       }

}
