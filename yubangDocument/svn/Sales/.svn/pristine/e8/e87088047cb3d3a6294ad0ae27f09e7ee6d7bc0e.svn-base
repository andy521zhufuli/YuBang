package com.sales.model;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * VUsrAccountinfoId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class VUsrAccountinfoId implements java.io.Serializable {

	// Fields

	private Integer userid;
	private String nickname;
	private String headimgurl;
	private Integer cashbalance;
	private BigDecimal totalincome;
	private Long friendnum;
	private Long ordernum;
	private BigDecimal ordervalueofmonth;

	// Constructors

	/** default constructor */
	public VUsrAccountinfoId() {
	}

	/** minimal constructor */
	public VUsrAccountinfoId(Integer userid, Long friendnum, Long ordernum) {
		this.userid = userid;
		this.friendnum = friendnum;
		this.ordernum = ordernum;
	}

	/** full constructor */
	public VUsrAccountinfoId(Integer userid, String nickname,
			String headimgurl, Integer cashbalance, BigDecimal totalincome,
			Long friendnum, Long ordernum, BigDecimal ordervalueofmonth) {
		this.userid = userid;
		this.nickname = nickname;
		this.headimgurl = headimgurl;
		this.cashbalance = cashbalance;
		this.totalincome = totalincome;
		this.friendnum = friendnum;
		this.ordernum = ordernum;
		this.ordervalueofmonth = ordervalueofmonth;
	}

	// Property accessors

	@Column(name = "userid", nullable = false)
	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
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

	@Column(name = "cashbalance")
	public Integer getCashbalance() {
		return this.cashbalance;
	}

	public void setCashbalance(Integer cashbalance) {
		this.cashbalance = cashbalance;
	}

	@Column(name = "totalincome", precision = 32, scale = 0)
	public BigDecimal getTotalincome() {
		return this.totalincome;
	}

	public void setTotalincome(BigDecimal totalincome) {
		this.totalincome = totalincome;
	}

	@Column(name = "friendnum", nullable = false)
	public Long getFriendnum() {
		return this.friendnum;
	}

	public void setFriendnum(Long friendnum) {
		this.friendnum = friendnum;
	}

	@Column(name = "ordernum", nullable = false)
	public Long getOrdernum() {
		return this.ordernum;
	}

	public void setOrdernum(Long ordernum) {
		this.ordernum = ordernum;
	}

	@Column(name = "ordervalueofmonth", precision = 32, scale = 0)
	public BigDecimal getOrdervalueofmonth() {
		return this.ordervalueofmonth;
	}

	public void setOrdervalueofmonth(BigDecimal ordervalueofmonth) {
		this.ordervalueofmonth = ordervalueofmonth;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof VUsrAccountinfoId))
			return false;
		VUsrAccountinfoId castOther = (VUsrAccountinfoId) other;

		return ((this.getUserid() == castOther.getUserid()) || (this
				.getUserid() != null && castOther.getUserid() != null && this
				.getUserid().equals(castOther.getUserid())))
				&& ((this.getNickname() == castOther.getNickname()) || (this
						.getNickname() != null
						&& castOther.getNickname() != null && this
						.getNickname().equals(castOther.getNickname())))
				&& ((this.getHeadimgurl() == castOther.getHeadimgurl()) || (this
						.getHeadimgurl() != null
						&& castOther.getHeadimgurl() != null && this
						.getHeadimgurl().equals(castOther.getHeadimgurl())))
				&& ((this.getCashbalance() == castOther.getCashbalance()) || (this
						.getCashbalance() != null
						&& castOther.getCashbalance() != null && this
						.getCashbalance().equals(castOther.getCashbalance())))
				&& ((this.getTotalincome() == castOther.getTotalincome()) || (this
						.getTotalincome() != null
						&& castOther.getTotalincome() != null && this
						.getTotalincome().equals(castOther.getTotalincome())))
				&& ((this.getFriendnum() == castOther.getFriendnum()) || (this
						.getFriendnum() != null
						&& castOther.getFriendnum() != null && this
						.getFriendnum().equals(castOther.getFriendnum())))
				&& ((this.getOrdernum() == castOther.getOrdernum()) || (this
						.getOrdernum() != null
						&& castOther.getOrdernum() != null && this
						.getOrdernum().equals(castOther.getOrdernum())))
				&& ((this.getOrdervalueofmonth() == castOther
						.getOrdervalueofmonth()) || (this
						.getOrdervalueofmonth() != null
						&& castOther.getOrdervalueofmonth() != null && this
						.getOrdervalueofmonth().equals(
								castOther.getOrdervalueofmonth())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getUserid() == null ? 0 : this.getUserid().hashCode());
		result = 37 * result
				+ (getNickname() == null ? 0 : this.getNickname().hashCode());
		result = 37
				* result
				+ (getHeadimgurl() == null ? 0 : this.getHeadimgurl()
						.hashCode());
		result = 37
				* result
				+ (getCashbalance() == null ? 0 : this.getCashbalance()
						.hashCode());
		result = 37
				* result
				+ (getTotalincome() == null ? 0 : this.getTotalincome()
						.hashCode());
		result = 37 * result
				+ (getFriendnum() == null ? 0 : this.getFriendnum().hashCode());
		result = 37 * result
				+ (getOrdernum() == null ? 0 : this.getOrdernum().hashCode());
		result = 37
				* result
				+ (getOrdervalueofmonth() == null ? 0 : this
						.getOrdervalueofmonth().hashCode());
		return result;
	}

}