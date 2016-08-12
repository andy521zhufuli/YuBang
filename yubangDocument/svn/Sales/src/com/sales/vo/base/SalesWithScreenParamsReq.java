package com.sales.vo.base;

public class SalesWithScreenParamsReq  extends SalesReq {
	public SalesWithScreenParamsReq(String msgtype) {
		super(msgtype);
		// TODO Auto-generated constructor stub
	}

	// ��Ļ���
	private int width = 0;
	
	// ��Ļ�߶�
	private int height = 0;

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}


}

