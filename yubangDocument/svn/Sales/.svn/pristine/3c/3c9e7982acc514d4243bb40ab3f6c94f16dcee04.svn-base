package com.sales.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TGoodsImg entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_goods_img", catalog = "sales")
public class TGoodsImg implements java.io.Serializable {

	// Fields

	private Integer id;
	private String url;
	private Integer goodsid;
	private Integer order;

	// Constructors

	/** default constructor */
	public TGoodsImg() {
	}

	/** full constructor */
	public TGoodsImg(String url, Integer goodsid, Integer order) {
		this.url = url;
		this.goodsid = goodsid;
		this.order = order;
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

	@Column(name = "url", nullable = false, length = 256)
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "goodsid", nullable = false)
	public Integer getGoodsid() {
		return this.goodsid;
	}

	public void setGoodsid(Integer goodsid) {
		this.goodsid = goodsid;
	}

	@Column(name = "order", nullable = false)
	public Integer getOrder() {
		return this.order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

}