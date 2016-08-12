package qtp.api;

import com.sales.vo.AppConfigReq;
import com.sales.vo.ExitLoginReq;
import com.sales.vo.GetBankListReq;
import com.sales.vo.GetOrderReq;
import com.sales.vo.LoadGoodsDetailReq;
import com.sales.vo.LoadGoodsListReq;
import com.sales.vo.base.SalesMsgUtils;

public class GetBankListTester {
	
	public static void main(String[] args) 
	{
		String URL = "http://localhost:8080/Sales/user/GetBankList";
		
		GetBankListReq req = new GetBankListReq();
		
		req.setApptype("111");
		req.setAppversion("111");
		req.setDeviceid("1111");
		//req.setHeight(1111);
		req.setMsgtype("GetBankList");
		req.setOs("111");
		req.setSeq("111");
		//req.setWidth(1111);
		
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
