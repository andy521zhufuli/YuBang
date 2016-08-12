package com.sales.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TAddress entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_address", catalog = "sales")
public class TAddress implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer userid;
	private String name;
	private String address;
	private String phone;
	private Timestamp updatetime;

	// Constructors

	/** default constructor */
	public TAddress() {
	}

	/** minimal constructor */
	public TAddress(Integer userid, String name, String address, String phone) {
		this.userid = userid;
		this.name = name;
		this.address = address;
		this.phone = phone;
	}

	/** full constructor */
	public TAddress(Integer userid, String name, String address, String phone,
			Timestamp updatetime) {
		this.userid = userid;
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.updatetime = updatetime;
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

	@Column(name = "userid", nullable = false)
	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	@Column(name = "name", nullable = false, length = 128)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "address", nullable = false, length = 1024)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "phone", nullable = false, length = 45)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "updatetime", length = 19)
	public Timestamp getUpdatetime() {
		return this.updatetime;
	}

	public void setUpdatetime(Timestamp updatetime) {
		this.updatetime = updatetime;
	}

}