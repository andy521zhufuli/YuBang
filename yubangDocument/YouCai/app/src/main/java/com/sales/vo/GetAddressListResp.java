package com.sales.vo;

import java.util.List;

import com.sales.vo.base.AddressInfo;
import com.sales.vo.base.MSG_TYPES;
import com.sales.vo.base.SalesResp;

public class GetAddressListResp extends SalesResp {
	
	public GetAddressListResp() {
		super(MSG_TYPES.MSG_GET_ADDRESS_LIST);
		// TODO Auto-generated constructor stub
	}
	
	private List<AddressInfo> addresslist = null;

	public List<AddressInfo> getAddresslist() {
		return addresslist;
	}

	public void setAddresslist(List<AddressInfo> addresslist) {
		this.addresslist = addresslist;
	}
	
	
	
	

}
