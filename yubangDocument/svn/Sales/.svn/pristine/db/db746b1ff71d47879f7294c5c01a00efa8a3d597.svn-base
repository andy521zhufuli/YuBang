package com.sales.vo.base;

import java.util.ArrayList;
import java.util.List;

public class OrderCancelReason {
	private static final String  IMG_LIST_SPACER = ";";

	private String briefreason = null;
	private String detailreason = null;
	private List<String> imglist = null;
	public String getBriefreason() {
		return briefreason;
	}
	public void setBriefreason(String briefreason) {
		this.briefreason = briefreason;
	}
	public String getDetailreason() {
		return detailreason;
	}
	public void setDetailreason(String detailreason) {
		this.detailreason = detailreason;
	}
	public List<String> getImglist() {
		return imglist;
	}
	public void setImglist(List<String> imglist) {
		this.imglist = imglist;
	}
	
	public static String convertImglistToString(List<String> imglist)
	{
		StringBuilder sb = new StringBuilder();
	    for(String str : imglist)
	    {
	    	sb.append(str);
	    }
	    
	    return sb.toString();
	}
	
	public static List<String> convertImglistFromString(String imgListStr)
	{
		String[] imgStrs = imgListStr.split(IMG_LIST_SPACER);
		List<String> list = new ArrayList<String>();
		for (String str : imgStrs)
		{
			list.add(str);
		}
		return list;
	}
	
	
}
