package com.sales.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TOrderHist entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_order_hist", catalog = "sales")
public class TOrderHist implements java.io.Serializable {

	// Fields

	private Integer id;
	private Timestamp updatetime;
	private String status;
	private Integer operator;
	private String operation;
	private Integer orderid;
	private String goodslist;
	private String addressname;
	private String addressphone;
	private String address;

	// Constructors

	/** default constructor */
	public TOrderHist() {
	}

	/** minimal constructor */
	public TOrderHist(Timestamp updatetime, String status, Integer operator,
			String operation, Integer orderid, String goodslist) {
		this.updatetime = updatetime;
		this.status = status;
		this.operator = operator;
		this.operation = operation;
		this.orderid = orderid;
		this.goodslist = goodslist;
	}

	/** full constructor */
	public TOrderHist(Timestamp updatetime, String status, Integer operator,
			String operation, Integer orderid, String goodslist,
			String addressname, String addressphone, String address) {
		this.updatetime = updatetime;
		this.status = status;
		this.operator = operator;
		this.operation = operation;
		this.orderid = orderid;
		this.goodslist = goodslist;
		this.addressname = addressname;
		this.addressphone = addressphone;
		this.address = address;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "updatetime", nullable = false, length = 19)
	public Timestamp getUpdatetime() {
		return this.updatetime;
	}

	public void setUpdatetime(Timestamp updatetime) {
		this.updatetime = updatetime;
	}

	@Column(name = "status", nullable = false, length = 45)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "operator", nullable = false)
	public Integer getOperator() {
		return this.operator;
	}

	public void setOperator(Integer operator) {
		this.operator = operator;
	}

	@Column(name = "operation", nullable = false, length = 1024)
	public String getOperation() {
		return this.operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	@Column(name = "orderid", nullable = false)
	public Integer getOrderid() {
		return this.orderid;
	}

	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}

	@Column(name = "goodslist", nullable = false, length = 1024)
	public String getGoodslist() {
		return this.goodslist;
	}

	public void setGoodslist(String goodslist) {
		this.goodslist = goodslist;
	}

	@Column(name = "addressname", length = 45)
	public String getAddressname() {
		return this.addressname;
	}

	public void setAddressname(String addressname) {
		this.addressname = addressname;
	}

	@Column(name = "addressphone", length = 45)
	public String getAddressphone() {
		return this.addressphone;
	}

	public void setAddressphone(String addressphone) {
		this.addressphone = addressphone;
	}

	@Column(name = "address", length = 1024)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}