package com.sales.web.user;

import java.io.PrintWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.common.log.JLogger;
import com.common.log.LoggerUtils;
import com.common.web.struts.BaseAction;
import com.sales.common.until.ReturnCode;
import com.sales.service.UserService;
import com.sales.vo.UploadImgReq;
import com.sales.vo.UploadImgResp;
import com.sales.vo.base.SalesMsgUtils;
import com.sales.web.app.AppConfigAction;

@Component("UploadAction")
@Scope("prototype")
public class UploadAction extends BaseAction{

	final private static JLogger logger = LoggerUtils.getLogger(UploadAction.class);
	
	@Autowired
	private UserService userService;
	
	private UploadImgReq uploadImgReq;
	
	public void upload(){
		UploadImgResp uploadImgResp = new UploadImgResp();
		try{
		uploadImgResp.setImgurl(userService.uploadImage(uploadImgReq));
		uploadImgResp.setReturncode(ReturnCode.RET_SUCCESS);
		}catch (Exception e) {
			// TODO: handle exception
			uploadImgResp.setReturncode(ReturnCode.RET_ERROR);
		}
		  String respJson = SalesMsgUtils.toJson(uploadImgResp);
		  getRequest().setAttribute("json", respJson);
		  
		  PrintWriter pw= null;
		  try{
		  pw=getPrintWriter();
		  pw.write(respJson);
		  }
		  catch(Exception e)
		  {
			  logger.error(e);
		  }
		  finally
		  {
              pw.flush();
              pw.close(); 
		  }
	}

}
