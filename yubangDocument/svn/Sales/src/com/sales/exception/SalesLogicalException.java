package com.sales.exception;

/**
 * 逻辑错粗
 * @author zhoupeng
 *
 */
public class SalesLogicalException extends SalesException {
	public SalesLogicalException(int expCode, String errMsg)
	{
		super(expCode, errMsg);
	}

}
