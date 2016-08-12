package com.sales.exception;

public class SalesException extends Exception {
	public SalesException(int expCode, String errMsg)
	{
		super();
		
		this.expCode = expCode;
	}
	
	// 异常返回码
	private int expCode = 0;
	private String errMsg = null;

	public int getExpCode() {
		return expCode;
	}

	public String getErrMsg() {
		return errMsg;
	}


}
