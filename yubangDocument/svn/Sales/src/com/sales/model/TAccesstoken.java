package com.sales.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TAccesstoken entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_accesstoken", catalog = "sales")
public class TAccesstoken implements java.io.Serializable {

	// Fields

	private Integer usrid;
	private String accesstoken;
	private Timestamp updatetime;
	private Integer triggers;
	private Timestamp createtime;

	// Constructors

	/** default constructor */
	public TAccesstoken() {
	}

	/** minimal constructor */
	public TAccesstoken(Integer usrid, String accesstoken, Integer triggers) {
		this.usrid = usrid;
		this.accesstoken = accesstoken;
		this.triggers = triggers;
	}

	/** full constructor */
	public TAccesstoken(Integer usrid, String accesstoken,
			Timestamp updatetime, Integer triggers, Timestamp createtime) {
		this.usrid = usrid;
		this.accesstoken = accesstoken;
		this.updatetime = updatetime;
		this.triggers = triggers;
		this.createtime = createtime;
	}

	// Property accessors
	@Id
	@Column(name = "usrid", unique = true, nullable = false)
	public Integer getUsrid() {
		return this.usrid;
	}

	public void setUsrid(Integer usrid) {
		this.usrid = usrid;
	}

	@Column(name = "accesstoken", nullable = false, length = 1024)
	public String getAccesstoken() {
		return this.accesstoken;
	}

	public void setAccesstoken(String accesstoken) {
		this.accesstoken = accesstoken;
	}

	@Column(name = "updatetime", length = 19)
	public Timestamp getUpdatetime() {
		return this.updatetime;
	}

	public void setUpdatetime(Timestamp updatetime) {
		this.updatetime = updatetime;
	}

	@Column(name = "triggers", nullable = false)
	public Integer getTriggers() {
		return this.triggers;
	}

	public void setTriggers(Integer triggers) {
		this.triggers = triggers;
	}

	@Column(name = "createtime", length = 19)
	public Timestamp getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

}