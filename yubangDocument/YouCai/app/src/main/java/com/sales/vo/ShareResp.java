package com.sales.vo;

import com.sales.vo.base.MSG_TYPES;
import com.sales.vo.base.SalesResp;

public class ShareResp extends SalesResp {
	
	public ShareResp() {
		super(MSG_TYPES.MSG_SHARE);
		// TODO Auto-generated constructor stub
	}
	
	private ShareGoodModel shareGoodModel;

	//用户名
	private String userid = null;

	//授权码
	private String accessToken = null;

	//网页
	private String webpageUrl;
	
	//标题
	private String title;
	
	//描述
	private String description;
	
	//图片
	private String imageUrl;

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getWebpageUrl() {
		return webpageUrl;
	}

	public void setWebpageUrl(String webpageUrl) {
		this.webpageUrl = webpageUrl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public ShareGoodModel getShareGoodModel() {
		return shareGoodModel;
	}

	public void setShareGoodModel(ShareGoodModel shareGoodModel) {
		this.shareGoodModel = shareGoodModel;
	}
	
	
	
}
