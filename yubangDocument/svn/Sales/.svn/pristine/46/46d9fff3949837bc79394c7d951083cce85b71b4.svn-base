package com.sales.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TOrder entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_order", catalog = "sales")
public class TOrder implements java.io.Serializable {

	// Fields

	private Integer id;
	private Timestamp updatetime;
	private String status;
	private Integer userid;
	private String goodslist;
	private String addressname;
	private String addressphone;
	private String address;
	private Integer value;

	// Constructors

	/** default constructor */
	public TOrder() {
	}

	/** minimal constructor */
	public TOrder(Timestamp updatetime, String status, Integer userid,
			String goodslist, Integer value) {
		this.updatetime = updatetime;
		this.status = status;
		this.userid = userid;
		this.goodslist = goodslist;
		this.value = value;
	}

	/** full constructor */
	public TOrder(Timestamp updatetime, String status, Integer userid,
			String goodslist, String addressname, String addressphone,
			String address, Integer value) {
		this.updatetime = updatetime;
		this.status = status;
		this.userid = userid;
		this.goodslist = goodslist;
		this.addressname = addressname;
		this.addressphone = addressphone;
		this.address = address;
		this.value = value;
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

	@Column(name = "userid", nullable = false)
	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
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

	@Column(name = "value", nullable = false)
	public Integer getValue() {
		return this.value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

}