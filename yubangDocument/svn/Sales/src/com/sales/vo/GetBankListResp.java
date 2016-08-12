package com.sales.vo;

import java.util.List;


import com.sales.vo.base.BankInfo;
import com.sales.vo.base.CashAccountInfo;
import com.sales.vo.base.OrderGoods;
import com.sales.vo.base.MSG_TYPES;
import com.sales.vo.base.SalesResp;

public class GetBankListResp extends SalesResp {
	
	public GetBankListResp() {
		super(MSG_TYPES.MSG_GET_BANK_LIST);
		// TODO Auto-generated constructor stub
	}
	
	private List<BankInfo>  banklist = null;

	public List<BankInfo> getBanklist() {
		return banklist;
	}

	public void setBanklist(List<BankInfo> banklist) {
		this.banklist = banklist;
	}


	
   
	

}
