package com.sales.vo;

import java.util.List;


import com.sales.vo.base.OrderGoods;
import com.sales.vo.base.MSG_TYPES;
import com.sales.vo.base.SalesResp;

public class UploadImgResp extends SalesResp {
	
	public UploadImgResp() {
		super(MSG_TYPES.MSG_UPLOAD_IMG);
		// TODO Auto-generated constructor stub
	}
	
	private String imgurl = null;

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}
	
	


}
