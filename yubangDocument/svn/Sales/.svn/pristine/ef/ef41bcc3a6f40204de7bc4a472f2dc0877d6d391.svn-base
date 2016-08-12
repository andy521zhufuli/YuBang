package com.sales.model;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * VUsrCashId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class VUsrCashId implements java.io.Serializable {

	// Fields

	private Integer userid;
	private BigDecimal totalincome;

	// Constructors

	/** default constructor */
	public VUsrCashId() {
	}

	/** minimal constructor */
	public VUsrCashId(Integer userid) {
		this.userid = userid;
	}

	/** full constructor */
	public VUsrCashId(Integer userid, BigDecimal totalincome) {
		this.userid = userid;
		this.totalincome = totalincome;
	}

	// Property accessors

	@Column(name = "userid", nullable = false)
	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	@Column(name = "totalincome", precision = 32, scale = 0)
	public BigDecimal getTotalincome() {
		return this.totalincome;
	}

	public void setTotalincome(BigDecimal totalincome) {
		this.totalincome = totalincome;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof VUsrCashId))
			return false;
		VUsrCashId castOther = (VUsrCashId) other;

		return ((this.getUserid() == castOther.getUserid()) || (this
				.getUserid() != null && castOther.getUserid() != null && this
				.getUserid().equals(castOther.getUserid())))
				&& ((this.getTotalincome() == castOther.getTotalincome()) || (this
						.getTotalincome() != null
						&& castOther.getTotalincome() != null && this
						.getTotalincome().equals(castOther.getTotalincome())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getUserid() == null ? 0 : this.getUserid().hashCode());
		result = 37
				* result
				+ (getTotalincome() == null ? 0 : this.getTotalincome()
						.hashCode());
		return result;
	}

}