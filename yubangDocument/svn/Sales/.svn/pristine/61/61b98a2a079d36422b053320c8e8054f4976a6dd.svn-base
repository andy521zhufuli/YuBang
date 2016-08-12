package com.sales.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TUsr entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_usr", catalog = "sales")
public class TUsr implements java.io.Serializable {

	// Fields

	private Integer userid;
	private String username;
	private String nickname;
	private String headimgurl;
	private String accesstoken;
	private String puserid;
	private String status;
	private Integer cashbalance;
	private String phone;
	private String type;
	private Timestamp updatetime;

	// Constructors

	/** default constructor */
	public TUsr() {
	}

	/** minimal constructor */
	public TUsr(String username, String type) {
		this.username = username;
		this.type = type;
	}

	/** full constructor */
	public TUsr(String username, String nickname, String headimgurl,
			String accesstoken, String puserid, String status,
			Integer cashbalance, String phone, String type, Timestamp updatetime) {
		this.username = username;
		this.nickname = nickname;
		this.headimgurl = headimgurl;
		this.accesstoken = accesstoken;
		this.puserid = puserid;
		this.status = status;
		this.cashbalance = cashbalance;
		this.phone = phone;
		this.type = type;
		this.updatetime = updatetime;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "userid", unique = true, nullable = false)
	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	@Column(name = "username", nullable = false, length = 256)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "nickname", length = 256)
	public String getNickname() {
		return this.nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	@Column(name = "headimgurl", length = 256)
	public String getHeadimgurl() {
		return this.headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	@Column(name = "accesstoken", length = 1024)
	public String getAccesstoken() {
		return this.accesstoken;
	}

	public void setAccesstoken(String accesstoken) {
		this.accesstoken = accesstoken;
	}

	@Column(name = "puserid", length = 45)
	public String getPuserid() {
		return this.puserid;
	}

	public void setPuserid(String puserid) {
		this.puserid = puserid;
	}

	@Column(name = "status", length = 45)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "cashbalance")
	public Integer getCashbalance() {
		return this.cashbalance;
	}

	public void setCashbalance(Integer cashbalance) {
		this.cashbalance = cashbalance;
	}

	@Column(name = "phone", length = 45)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "type", nullable = false, length = 45)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "updatetime", length = 19)
	public Timestamp getUpdatetime() {
		return this.updatetime;
	}

	public void setUpdatetime(Timestamp updatetime) {
		this.updatetime = updatetime;
	}

}