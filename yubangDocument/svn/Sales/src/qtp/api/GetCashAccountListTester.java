package qtp.api;

import com.sales.vo.AppConfigReq;
import com.sales.vo.GetCashAccountListReq;
import com.sales.vo.GetOrderReq;
import com.sales.vo.LoadGoodsDetailReq;
import com.sales.vo.LoadGoodsListReq;
import com.sales.vo.base.SalesMsgUtils;

public class GetCashAccountListTester {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		String URL = "http://localhost:8080/Sales/user/GetCashAccountList";
		
		GetCashAccountListReq req = new GetCashAccountListReq();
		req.setApptype("1111");
		req.setAppversion("1111");
		req.setAuthorizedtoken("1111");
		req.setDeviceid("1111");
		req.setHeight(1);
		req.setMsgtype("GetCashAccountList");
		req.setOs("111");
		req.setSeq("1111");
		req.setUserid("11111");
		req.setWidth(111);
		req.setUserid("1111");
		
		String reqJson = SalesMsgUtils.toJson(req);
		
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
