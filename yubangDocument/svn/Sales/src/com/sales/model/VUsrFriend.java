package com.sales.model;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * VUsrFriend entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "v_usr_friend", catalog = "sales")
public class VUsrFriend implements java.io.Serializable {

	// Fields

	private VUsrFriendId id;

	// Constructors

	/** default constructor */
	public VUsrFriend() {
	}

	/** full constructor */
	public VUsrFriend(VUsrFriendId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "userid", column = @Column(name = "userid", nullable = false)),
			@AttributeOverride(name = "friendnum", column = @Column(name = "friendnum", nullable = false)) })
	public VUsrFriendId getId() {
		return this.id;
	}

	public void setId(VUsrFriendId id) {
		this.id = id;
	}

}