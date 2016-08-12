package qtp.api.tester;

import qtp.api.HttpPoster;

import com.sales.common.until.CommonDefs;
import com.sales.vo.AppConfigReq;
import com.sales.vo.CashOutReq;
import com.sales.vo.GetCashHistReq;
import com.sales.vo.GetCashInfoReq;
import com.sales.vo.base.CashInfo;
import com.sales.vo.base.MSG_TYPES;
import com.sales.vo.base.SalesMsgUtils;

public class GetCashHistTester {
	
	public static void main(String[] args) 
	{
		String URL = "http://119.29.82.148/Sales/app/AppConfig";
		
		GetCashHistReq req = new GetCashHistReq();
		req.setApptype("test");
		req.setAppversion("1.0");
		req.setDeviceid("d");
		req.setHeight(10);
		req.setWidth(10);
		req.setOs("test");
		req.setFormat("html");
		req.setPid(1);		
		req.setUserid("12123456");
		req.setAuthorizedtoken("fdasfdsafdsafd");
		
	
		
		
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
