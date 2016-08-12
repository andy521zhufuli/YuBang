package qtp.api.tester;

import qtp.api.HttpPoster;

import com.sales.common.until.ReturnCode;
import com.sales.vo.AppConfigReq;
import com.sales.vo.AppConfigResp;
import com.sales.vo.base.SalesBaseMsg;
import com.sales.vo.base.SalesMsgUtils;
import com.sales.vo.base.SalesReq;
import com.sales.vo.base.SalesResp;

public class AppConfigTester {
	
	public static void main(String[] args) throws Exception 
	{
		String URL = "http://119.29.82.148/Sales/app/AppConfig";
		
		AppConfigReq req = new AppConfigReq();
		req.setApptype("test");
		req.setAppversion("1.0");
		req.setDeviceid("d");
		req.setHeight(10);
		req.setWidth(10);
		req.setOs("test");
		req.setFormat("html");
		
		SalesBaseMsg resp = sendAndRecv(req);
		AppConfigResp appConfigResp = (AppConfigResp)resp;
		
		String bootingimgurl = appConfigResp.getBootimgurl();
		

		

	
		
		
	}
	
	public static SalesBaseMsg sendAndRecv(SalesReq req) throws Exception
	{
		String reqJson = SalesMsgUtils.toJson(req);
		HttpPoster poster = new HttpPoster();
		String url = "http://localhost:8080/" + req.getMsgtype();
		poster.post(url, reqJson);
		
		String respJson = poster.getRecBuffer();
		SalesBaseMsg resp = SalesMsgUtils.fromJson(respJson, req.getMsgtype(), false);
		
		SalesResp salesResp = (SalesResp)resp;
		int ret = salesResp.getReturncode();
		// 处理返回码
		
		return resp;
	}
	
	

}
