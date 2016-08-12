package com.sales.common.until;

import java.io.File;

import com.common.utils.TimeUtils;

public class ImgUtils {
	
	/**
	 * 以下是图片用途
	 * */
	public static final String IMG_TYPE_HEAD_IMG= "headimg"; // 用户头像
	public static final String IMG_TYPE_GOODS_TITILE_IMG = "goodstitle"; // 商品图标
	public static final String IMG_TYPE_GOODS_DETAIL_IMG = "goodsdetail"; // 商品详情图片
	
	private static final int SEQ_LEN = 6;
	private static final int MAX_SEQ = 999999;
	private static int seqnum = 0;
	private static String seqTimeStamp = null;
	
	
	private static synchronized int getSeqNum(String timeStamp)
	{
		if (null != seqTimeStamp && seqTimeStamp.equals(timeStamp))
		{
			seqnum ++;
		}
		else
			
		{
			seqTimeStamp = timeStamp;
			seqnum = 1;
					
		}
		return seqnum ;
	}
	
	/**
	 * 根据相关参数生成图片的路径
	 * 
	 * @param imgtype  图片用途，通过常量IMG_TYPE_HEAD_IMG, IMG_TYPE_GOODS_TITILE_IMG,IMG_TYPE_GOODS_DETAIL_IMG定义
	 * @param suffix 文件后缀， 如png， jpg等
	 * */
	public static String getImgPath(String imgtype,  String suffix) 
	{
		StringBuilder sb = new StringBuilder();
		sb.append(imgtype);
		sb.append(File.separator);
		String timestamp = TimeUtils
				.getTimestampString(TimeUtils.DateFormat.tableName);
		sb.append(timestamp);
		sb.append(File.separator);
		sb.append(getSeqNum(timestamp));
		sb.append(".");
		sb.append(suffix);		
		return sb.toString();
	}
	
	
	public static void main(String[] args) 
	{
		String path = ImgUtils.getImgPath(ImgUtils.IMG_TYPE_GOODS_DETAIL_IMG,"jpg" );
		System.out.println(path);
	}

}
