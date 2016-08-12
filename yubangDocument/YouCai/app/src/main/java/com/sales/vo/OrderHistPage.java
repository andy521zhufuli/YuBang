package com.sales.vo;

import java.sql.Timestamp;


public class OrderHistPage {
	
	private Timestamp timestamp;
	
	private String opertor;
	
	private String info;

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public String getOpertor() {
		return opertor;
	}

	public void setOpertor(String opertor) {
		this.opertor = opertor;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
	


}
