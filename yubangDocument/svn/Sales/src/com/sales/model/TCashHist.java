package com.sales.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TCashHist entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_cash_hist", catalog = "sales")
public class TCashHist implements java.io.Serializable {

	// Fields

	private Integer id;
	private String cashid;
	private String status;
	private Timestamp updatetime;
	private Integer operator;
	private String operation;
	private String notes;
	private Integer amount;
	private String accounttype;
	private String accountname;
	private String accountid;
	private String bankname;
	private Integer userid;

	// Constructors

	/** default constructor */
	public TCashHist() {
	}

	/** minimal constructor */
	public TCashHist(String cashid, String status, Integer operator,
			String operation, Integer amount, String accounttype,
			String accountname, String accountid, Integer userid) {
		this.cashid = cashid;
		this.status = status;
		this.operator = operator;
		this.operation = operation;
		this.amount = amount;
		this.accounttype = accounttype;
		this.accountname = accountname;
		this.accountid = accountid;
		this.userid = userid;
	}

	/** full constructor */
	public TCashHist(String cashid, String status, Timestamp updatetime,
			Integer operator, String operation, String notes, Integer amount,
			String accounttype, String accountname, String accountid,
			String bankname, Integer userid) {
		this.cashid = cashid;
		this.status = status;
		this.updatetime = updatetime;
		this.operator = operator;
		this.operation = operation;
		this.notes = notes;
		this.amount = amount;
		this.accounttype = accounttype;
		this.accountname = accountname;
		this.accountid = accountid;
		this.bankname = bankname;
		this.userid = userid;
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

	@Column(name = "cashid", nullable = false, length = 20)
	public String getCashid() {
		return this.cashid;
	}

	public void setCashid(String cashid) {
		this.cashid = cashid;
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

	@Column(name = "notes", length = 1024)
	public String getNotes() {
		return this.notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	@Column(name = "amount", nullable = false)
	public Integer getAmount() {
		return this.amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
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

	@Column(name = "accountid", nullable = false, length = 128)
	public String getAccountid() {
		return this.accountid;
	}

	public void setAccountid(String accountid) {
		this.accountid = accountid;
	}

	@Column(name = "bankname", length = 256)
	public String getBankname() {
		return this.bankname;
	}

	public void setBankname(String bankname) {
		this.bankname = bankname;
	}

	@Column(name = "userid", nullable = false)
	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

}