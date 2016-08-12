package com.sales.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TConfig entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_config", catalog = "sales")
public class TConfig implements java.io.Serializable {

	// Fields

	private String key;
	private String value;
	private String operator;
	private Timestamp updatetime;

	// Constructors

	/** default constructor */
	public TConfig() {
	}

	/** minimal constructor */
	public TConfig(String key, String value, String operator) {
		this.key = key;
		this.value = value;
		this.operator = operator;
	}

	/** full constructor */
	public TConfig(String key, String value, String operator,
			Timestamp updatetime) {
		this.key = key;
		this.value = value;
		this.operator = operator;
		this.updatetime = updatetime;
	}

	// Property accessors
	@Id
	@Column(name = "key", unique = true, nullable = false)
	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Column(name = "value", nullable = false)
	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Column(name = "operator", nullable = false)
	public String getOperator() {
		return this.operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	@Column(name = "updatetime", length = 19)
	public Timestamp getUpdatetime() {
		return this.updatetime;
	}

	public void setUpdatetime(Timestamp updatetime) {
		this.updatetime = updatetime;
	}

}