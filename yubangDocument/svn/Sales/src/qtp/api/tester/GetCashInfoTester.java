package qtp.api.tester;

import qtp.api.HttpPoster;

import com.sales.common.until.CommonDefs;
import com.sales.vo.AppConfigReq;
import com.sales.vo.CashOutReq;
import com.sales.vo.GetCashInfoReq;
import com.sales.vo.base.CashInfo;
import com.sales.vo.base.MSG_TYPES;
import com.sales.vo.base.SalesMsgUtils;

public class GetCashInfoTester {
	
	public static void main(String[] args) 
	{
		String URL = "http://119.29.82.148/Sales/app/AppConfig";
		
		GetCashInfoReq req = new GetCashInfoReq();
		req.setApptype("test");
		req.setAppversion("1.0");
		req.setDeviceid("d");
		req.setHeight(10);
		req.setWidth(10);
		req.setOs("test");
		req.setFormat("html");
		
		req.setUserid("12123456");
		req.setAuthorizedtoken("fdasfdsafdsafd");
		req.setCashid("C201507291419370001");
		
	
		
		
		String reqJson = SalesMsgUtils.toJson(req);
		System.out.println("send:"+reqJson);
		
		HttpPoster poster = new HttpPoster();
		try {
			poster.post(URL, reqJson);
			String respJson = poster.getRecBuffer();
			System.out.println("received:"+respJson);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
