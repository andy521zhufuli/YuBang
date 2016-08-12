package com.sales.model;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * VUsrAccountinfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "v_usr_accountinfo", catalog = "sales")
public class VUsrAccountinfo implements java.io.Serializable {

	// Fields

	private VUsrAccountinfoId id;

	// Constructors

	/** default constructor */
	public VUsrAccountinfo() {
	}

	/** full constructor */
	public VUsrAccountinfo(VUsrAccountinfoId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "userid", column = @Column(name = "userid", nullable = false)),
			@AttributeOverride(name = "nickname", column = @Column(name = "nickname", length = 256)),
			@AttributeOverride(name = "headimgurl", column = @Column(name = "headimgurl", length = 256)),
			@AttributeOverride(name = "cashbalance", column = @Column(name = "cashbalance")),
			@AttributeOverride(name = "totalincome", column = @Column(name = "totalincome", precision = 32, scale = 0)),
			@AttributeOverride(name = "friendnum", column = @Column(name = "friendnum", nullable = false)),
			@AttributeOverride(name = "ordernum", column = @Column(name = "ordernum", nullable = false)),
			@AttributeOverride(name = "ordervalueofmonth", column = @Column(name = "ordervalueofmonth", precision = 32, scale = 0)) })
	public VUsrAccountinfoId getId() {
		return this.id;
	}

	public void setId(VUsrAccountinfoId id) {
		this.id = id;
	}

}