package qtp.api.tester;

import qtp.api.HttpPoster;

import com.sales.vo.AppConfigReq;
import com.sales.vo.LoadGoodsDetailReq;
import com.sales.vo.LoadGoodsListReq;
import com.sales.vo.base.SalesMsgUtils;

public class LoadListPageTester {
	
	public static void main(String[] args) 
	{
		String URL = "http://119.29.82.148/Sales/goods/LoadListPage";
		
		LoadGoodsListReq req = new LoadGoodsListReq();
		req.setApptype("test");
		req.setAppversion("1.0");
		req.setDeviceid("d");
		req.setHeight(10);
		req.setWidth(10);
		req.setOs("test");
		req.setFormat("json");
		req.setMsgtype("LoadListPage");
		
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
