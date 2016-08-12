package com.sales.model;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * VUsrOrderId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class VUsrOrderId implements java.io.Serializable {

	// Fields

	private Integer userid;
	private Long ordernum;
	private BigDecimal ordervalueofmonth;

	// Constructors

	/** default constructor */
	public VUsrOrderId() {
	}

	/** minimal constructor */
	public VUsrOrderId(Integer userid, Long ordernum) {
		this.userid = userid;
		this.ordernum = ordernum;
	}

	/** full constructor */
	public VUsrOrderId(Integer userid, Long ordernum,
			BigDecimal ordervalueofmonth) {
		this.userid = userid;
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
		if (!(other instanceof VUsrOrderId))
			return false;
		VUsrOrderId castOther = (VUsrOrderId) other;

		return ((this.getUserid() == castOther.getUserid()) || (this
				.getUserid() != null && castOther.getUserid() != null && this
				.getUserid().equals(castOther.getUserid())))
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
				+ (getOrdernum() == null ? 0 : this.getOrdernum().hashCode());
		result = 37
				* result
				+ (getOrdervalueofmonth() == null ? 0 : this
						.getOrdervalueofmonth().hashCode());
		return result;
	}

}