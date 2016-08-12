package com.sales.model;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * VUsrCash entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "v_usr_cash", catalog = "sales")
public class VUsrCash implements java.io.Serializable {

	// Fields

	private VUsrCashId id;

	// Constructors

	/** default constructor */
	public VUsrCash() {
	}

	/** full constructor */
	public VUsrCash(VUsrCashId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "userid", column = @Column(name = "userid", nullable = false)),
			@AttributeOverride(name = "totalincome", column = @Column(name = "totalincome", precision = 32, scale = 0)) })
	public VUsrCashId getId() {
		return this.id;
	}

	public void setId(VUsrCashId id) {
		this.id = id;
	}

}