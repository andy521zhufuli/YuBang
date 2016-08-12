package com.sales.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TCashAccount entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_cash_account", catalog = "sales")
public class TCashAccount implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer userid;
	private Timestamp updatetime;
	private String type;
	private String accountid;
	private String accountname;
	private String bankname;
	private Integer selected;

	// Constructors

	/** default constructor */
	public TCashAccount() {
	}

	/** minimal constructor */
	public TCashAccount(Integer userid, String type, String accountid,
			String accountname, Integer selected) {
		this.userid = userid;
		this.type = type;
		this.accountid = accountid;
		this.accountname = accountname;
		this.selected = selected;
	}

	/** full constructor */
	public TCashAccount(Integer userid, Timestamp updatetime, String type,
			String accountid, String accountname, String bankname,
			Integer selected) {
		this.userid = userid;
		this.updatetime = updatetime;
		this.type = type;
		this.accountid = accountid;
		this.accountname = accountname;
		this.bankname = bankname;
		this.selected = selected;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "userid", nullable = false)
	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	@Column(name = "updatetime", length = 19)
	public Timestamp getUpdatetime() {
		return this.updatetime;
	}

	public void setUpdatetime(Timestamp updatetime) {
		this.updatetime = updatetime;
	}

	@Column(name = "type", nullable = false, length = 45)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "accountid", nullable = false, length = 256)
	public String getAccountid() {
		return this.accountid;
	}

	public void setAccountid(String accountid) {
		this.accountid = accountid;
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

	@Column(name = "selected", nullable = false)
	public Integer getSelected() {
		return this.selected;
	}

	public void setSelected(Integer selected) {
		this.selected = selected;
	}

}