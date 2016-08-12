package com.sales.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TShare entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_share", catalog = "sales")
public class TShare implements java.io.Serializable {

	// Fields

	private Integer id;
	private String url;
	private Integer tid;
	private String additioncontent;
	private Integer userid;
	private Timestamp updatetime;

	// Constructors

	/** default constructor */
	public TShare() {
	}

	/** minimal constructor */
	public TShare(String url, Integer tid, Integer userid, Timestamp updatetime) {
		this.url = url;
		this.tid = tid;
		this.userid = userid;
		this.updatetime = updatetime;
	}

	/** full constructor */
	public TShare(String url, Integer tid, String additioncontent,
			Integer userid, Timestamp updatetime) {
		this.url = url;
		this.tid = tid;
		this.additioncontent = additioncontent;
		this.userid = userid;
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

	@Column(name = "url", nullable = false, length = 1024)
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "tid", nullable = false)
	public Integer getTid() {
		return this.tid;
	}

	public void setTid(Integer tid) {
		this.tid = tid;
	}

	@Column(name = "additioncontent", length = 1024)
	public String getAdditioncontent() {
		return this.additioncontent;
	}

	public void setAdditioncontent(String additioncontent) {
		this.additioncontent = additioncontent;
	}

	@Column(name = "userid", nullable = false)
	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	@Column(name = "updatetime", nullable = false, length = 19)
	public Timestamp getUpdatetime() {
		return this.updatetime;
	}

	public void setUpdatetime(Timestamp updatetime) {
		this.updatetime = updatetime;
	}

}