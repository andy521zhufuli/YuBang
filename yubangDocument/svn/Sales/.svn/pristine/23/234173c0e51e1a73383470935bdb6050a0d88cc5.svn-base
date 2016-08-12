package com.sales.vo.base;

import com.sales.common.until.ReturnCode;

public class SalesResp extends SalesBaseMsg{
	
	public SalesResp(String msgtype) {
		super(msgtype);
		// TODO Auto-generated constructor stub
	}

	// ������
	private int returncode = ReturnCode.RET_SUCCESS;
	
	// ��ʾ��Ϣ
	private String errmsg = null;
	


	public int getReturncode() {
		return returncode;
	}

	public void setReturncode(int returncode) {
		this.returncode = returncode;
		errmsg = ReturnCode.getCodeMsg(returncode);
	}

	public String getErrmsg() {
		return errmsg;
	}

//	public void setErrmsg(String errmsg) {
//		this.errmsg = errmsg;
//	}
//	
	


	
	
	
	

	
}
