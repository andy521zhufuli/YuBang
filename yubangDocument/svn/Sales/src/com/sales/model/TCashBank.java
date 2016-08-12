package com.sales.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TCashBank entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_cash_bank", catalog = "sales")
public class TCashBank implements java.io.Serializable {

	// Fields

	private Integer id;
	private String bankname;
	private String imgurl;
	private Timestamp updatetime;
	private Integer status;

	// Constructors

	/** default constructor */
	public TCashBank() {
	}

	/** minimal constructor */
	public TCashBank(String bankname, String imgurl, Integer status) {
		this.bankname = bankname;
		this.imgurl = imgurl;
		this.status = status;
	}

	/** full constructor */
	public TCashBank(String bankname, String imgurl, Timestamp updatetime,
			Integer status) {
		this.bankname = bankname;
		this.imgurl = imgurl;
		this.updatetime = updatetime;
		this.status = status;
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

	@Column(name = "bankname", nullable = false, length = 256)
	public String getBankname() {
		return this.bankname;
	}

	public void setBankname(String bankname) {
		this.bankname = bankname;
	}

	@Column(name = "imgurl", nullable = false, length = 256)
	public String getImgurl() {
		return this.imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	@Column(name = "updatetime", length = 19)
	public Timestamp getUpdatetime() {
		return this.updatetime;
	}

	public void setUpdatetime(Timestamp updatetime) {
		this.updatetime = updatetime;
	}

	@Column(name = "status", nullable = false)
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}