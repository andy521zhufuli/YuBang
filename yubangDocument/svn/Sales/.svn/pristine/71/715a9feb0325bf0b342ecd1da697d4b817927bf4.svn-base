package com.sales.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TCancelOrder entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_cancel_order", catalog = "sales")
public class TCancelOrder implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer orderid;
	private String briefreason;
	private String detailreason;
	private String imglist;
	private Timestamp updatetime;

	// Constructors

	/** default constructor */
	public TCancelOrder() {
	}

	/** minimal constructor */
	public TCancelOrder(Integer id, Integer orderid, String briefreason,
			Timestamp updatetime) {
		this.id = id;
		this.orderid = orderid;
		this.briefreason = briefreason;
		this.updatetime = updatetime;
	}

	/** full constructor */
	public TCancelOrder(Integer id, Integer orderid, String briefreason,
			String detailreason, String imglist, Timestamp updatetime) {
		this.id = id;
		this.orderid = orderid;
		this.briefreason = briefreason;
		this.detailreason = detailreason;
		this.imglist = imglist;
		this.updatetime = updatetime;
	}

	// Property accessors
	@Id
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "orderid", nullable = false)
	public Integer getOrderid() {
		return this.orderid;
	}

	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}

	@Column(name = "briefreason", nullable = false, length = 256)
	public String getBriefreason() {
		return this.briefreason;
	}

	public void setBriefreason(String briefreason) {
		this.briefreason = briefreason;
	}

	@Column(name = "detailreason", length = 1024)
	public String getDetailreason() {
		return this.detailreason;
	}

	public void setDetailreason(String detailreason) {
		this.detailreason = detailreason;
	}

	@Column(name = "imglist", length = 1024)
	public String getImglist() {
		return this.imglist;
	}

	public void setImglist(String imglist) {
		this.imglist = imglist;
	}

	@Column(name = "updatetime", nullable = false, length = 19)
	public Timestamp getUpdatetime() {
		return this.updatetime;
	}

	public void setUpdatetime(Timestamp updatetime) {
		this.updatetime = updatetime;
	}

}