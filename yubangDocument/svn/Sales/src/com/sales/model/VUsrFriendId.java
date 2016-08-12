package com.sales.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * VUsrFriendId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class VUsrFriendId implements java.io.Serializable {

	// Fields

	private Integer userid;
	private Long friendnum;

	// Constructors

	/** default constructor */
	public VUsrFriendId() {
	}

	/** full constructor */
	public VUsrFriendId(Integer userid, Long friendnum) {
		this.userid = userid;
		this.friendnum = friendnum;
	}

	// Property accessors

	@Column(name = "userid", nullable = false)
	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	@Column(name = "friendnum", nullable = false)
	public Long getFriendnum() {
		return this.friendnum;
	}

	public void setFriendnum(Long friendnum) {
		this.friendnum = friendnum;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof VUsrFriendId))
			return false;
		VUsrFriendId castOther = (VUsrFriendId) other;

		return ((this.getUserid() == castOther.getUserid()) || (this
				.getUserid() != null && castOther.getUserid() != null && this
				.getUserid().equals(castOther.getUserid())))
				&& ((this.getFriendnum() == castOther.getFriendnum()) || (this
						.getFriendnum() != null
						&& castOther.getFriendnum() != null && this
						.getFriendnum().equals(castOther.getFriendnum())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getUserid() == null ? 0 : this.getUserid().hashCode());
		result = 37 * result
				+ (getFriendnum() == null ? 0 : this.getFriendnum().hashCode());
		return result;
	}

}