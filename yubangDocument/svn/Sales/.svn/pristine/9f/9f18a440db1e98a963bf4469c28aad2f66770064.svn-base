package com.sales.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TGoods entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_goods", catalog = "sales")
public class TGoods implements java.io.Serializable {

	// Fields

	private Integer goodsid;
	private String title;
	private String secondarytitle;
	private Integer originalprice;
	private Integer discountprice;
	private String titleimgurl;
	private String thumbimgurl;
	private Integer order;
	private Timestamp updatetime;
	private String version;
	private String listimgurl;

	// Constructors

	/** default constructor */
	public TGoods() {
	}

	/** minimal constructor */
	public TGoods(String title, String secondarytitle, Integer originalprice,
			Integer discountprice, String titleimgurl, String thumbimgurl,
			String version) {
		this.title = title;
		this.secondarytitle = secondarytitle;
		this.originalprice = originalprice;
		this.discountprice = discountprice;
		this.titleimgurl = titleimgurl;
		this.thumbimgurl = thumbimgurl;
		this.version = version;
	}

	/** full constructor */
	public TGoods(String title, String secondarytitle, Integer originalprice,
			Integer discountprice, String titleimgurl, String thumbimgurl,
			Integer order, Timestamp updatetime, String version,
			String listimgurl) {
		this.title = title;
		this.secondarytitle = secondarytitle;
		this.originalprice = originalprice;
		this.discountprice = discountprice;
		this.titleimgurl = titleimgurl;
		this.thumbimgurl = thumbimgurl;
		this.order = order;
		this.updatetime = updatetime;
		this.version = version;
		this.listimgurl = listimgurl;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "goodsid", unique = true, nullable = false)
	public Integer getGoodsid() {
		return this.goodsid;
	}

	public void setGoodsid(Integer goodsid) {
		this.goodsid = goodsid;
	}

	@Column(name = "title", nullable = false, length = 1024)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "secondarytitle", nullable = false, length = 1024)
	public String getSecondarytitle() {
		return this.secondarytitle;
	}

	public void setSecondarytitle(String secondarytitle) {
		this.secondarytitle = secondarytitle;
	}

	@Column(name = "originalprice", nullable = false)
	public Integer getOriginalprice() {
		return this.originalprice;
	}

	public void setOriginalprice(Integer originalprice) {
		this.originalprice = originalprice;
	}

	@Column(name = "discountprice", nullable = false)
	public Integer getDiscountprice() {
		return this.discountprice;
	}

	public void setDiscountprice(Integer discountprice) {
		this.discountprice = discountprice;
	}

	@Column(name = "titleimgurl", nullable = false, length = 256)
	public String getTitleimgurl() {
		return this.titleimgurl;
	}

	public void setTitleimgurl(String titleimgurl) {
		this.titleimgurl = titleimgurl;
	}

	@Column(name = "thumbimgurl", nullable = false, length = 256)
	public String getThumbimgurl() {
		return this.thumbimgurl;
	}

	public void setThumbimgurl(String thumbimgurl) {
		this.thumbimgurl = thumbimgurl;
	}

	@Column(name = "order")
	public Integer getOrder() {
		return this.order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	@Column(name = "updatetime", length = 19)
	public Timestamp getUpdatetime() {
		return this.updatetime;
	}

	public void setUpdatetime(Timestamp updatetime) {
		this.updatetime = updatetime;
	}

	@Column(name = "version", nullable = false, length = 256)
	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Column(name = "listimgurl", length = 256)
	public String getListimgurl() {
		return this.listimgurl;
	}

	public void setListimgurl(String listimgurl) {
		this.listimgurl = listimgurl;
	}

}