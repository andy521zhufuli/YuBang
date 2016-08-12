package com.sales.exception;

/**
 * 内部错误
 * @author zhoupeng
 *
 */
public class SalesInternalException extends SalesException {
	public SalesInternalException(int expCode, String errMsg)
	{
		super(expCode, errMsg);
	}
	

}
