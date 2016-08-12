package com.sales.vo.base;

import com.sales.common.until.CommonDefs;

public class AddressInfo {
	
	
	private String addressid = null;
	private String addressname = null;
	private String address = null;
	private String addressphone = null;
	private int selected = CommonDefs.ADDRESS_NOT_SELECTED;
	public String getAddressid() {
		return addressid;
	}
	public void setAddressid(String addressid) {
		this.addressid = addressid;
	}
	public String getAddressname() {
		return addressname;
	}
	public void setAddressname(String addressname) {
		this.addressname = addressname;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getAddressphone() {
		return addressphone;
	}
	public void setAddressphone(String addressphone) {
		this.addressphone = addressphone;
	}
	public int getSelected() {
		return selected;
	}
	public void setSelected(int selected) {
		this.selected = selected;
	} 
	
	

}
