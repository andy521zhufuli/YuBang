package com.sales.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TOrderGoods entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_order_goods", catalog = "sales")
public class TOrderGoods implements java.io.Serializable {

	// Fields

	private Integer id;
	private String version;
	private Integer goodsid;
	private Integer orderid;

	// Constructors

	/** default constructor */
	public TOrderGoods() {
	}

	/** full constructor */
	public TOrderGoods(String version, Integer goodsid, Integer orderid) {
		this.version = version;
		this.goodsid = goodsid;
		this.orderid = orderid;
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

	@Column(name = "version", nullable = false)
	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Column(name = "goodsid", nullable = false)
	public Integer getGoodsid() {
		return this.goodsid;
	}

	public void setGoodsid(Integer goodsid) {
		this.goodsid = goodsid;
	}

	@Column(name = "orderid", nullable = false)
	public Integer getOrderid() {
		return this.orderid;
	}

	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}

}