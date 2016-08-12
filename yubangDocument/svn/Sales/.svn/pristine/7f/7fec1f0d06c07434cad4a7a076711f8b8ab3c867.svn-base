package com.sales.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TCash entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_cash", catalog = "sales")
public class TCash implements java.io.Serializable {

	// Fields

	private String id;
	private Integer userid;
	private Integer amount;
	private String status;
	private Timestamp updatetime;
	private String accounttype;
	private String accountname;
	private String bankname;
	private String accountid;

	// Constructors

	/** default constructor */
	public TCash() {
	}

	/** minimal constructor */
	public TCash(String id, Integer userid, Integer amount, String status,
			String accounttype, String accountname, String accountid) {
		this.id = id;
		this.userid = userid;
		this.amount = amount;
		this.status = status;
		this.accounttype = accounttype;
		this.accountname = accountname;
		this.accountid = accountid;
	}

	/** full constructor */
	public TCash(String id, Integer userid, Integer amount, String status,
			Timestamp updatetime, String accounttype, String accountname,
			String bankname, String accountid) {
		this.id = id;
		this.userid = userid;
		this.amount = amount;
		this.status = status;
		this.updatetime = updatetime;
		this.accounttype = accounttype;
		this.accountname = accountname;
		this.bankname = bankname;
		this.accountid = accountid;
	}

	// Property accessors
	@Id
	@Column(name = "id", unique = true, nullable = false, length = 20)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "userid", nullable = false)
	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	@Column(name = "amount", nullable = false)
	public Integer getAmount() {
		return this.amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	@Column(name = "status", nullable = false, length = 256)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "updatetime", length = 19)
	public Timestamp getUpdatetime() {
		return this.updatetime;
	}

	public void setUpdatetime(Timestamp updatetime) {
		this.updatetime = updatetime;
	}

	@Column(name = "accounttype", nullable = false, length = 45)
	public String getAccounttype() {
		return this.accounttype;
	}

	public void setAccounttype(String accounttype) {
		this.accounttype = accounttype;
	}

	@Column(name = "accountname", nullable = false, length = 256)
	public String getAccountname() {
		return this.accountname;
	}

	public void setAccountname(String accountname) {
		this.accountname = accountname;
	}

	@Column(name = "bankname", length = 256)
	public String getBankname() {
		return this.bankname;
	}

	public void setBankname(String bankname) {
		this.bankname = bankname;
	}

	@Column(name = "accountid", nullable = false, length = 128)
	public String getAccountid() {
		return this.accountid;
	}

	public void setAccountid(String accountid) {
		this.accountid = accountid;
	}

}