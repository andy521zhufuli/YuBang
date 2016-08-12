package com.sales.vo;

import java.util.List;

import com.sales.vo.base.AddressInfo;
import com.sales.vo.base.OrderCancelReason;
import com.sales.vo.base.OrderGoods;
import com.sales.vo.base.SalesLoginedReq;
import com.sales.vo.base.MSG_TYPES;

public class ModifyAddressInfoReq extends SalesLoginedReq {

	public ModifyAddressInfoReq() {
		super(MSG_TYPES.MSG_MODIFY_ADDRESS_INFO);
		// TODO Auto-generated constructor stub
	}
	
	private String action = null;
	private AddressInfo addressinfo = null;
		
	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public AddressInfo getAddressinfo() {
		return addressinfo;
	}

	public void setAddressinfo(AddressInfo addressinfo) {
		this.addressinfo = addressinfo;
	}
	
	
	
	
}
