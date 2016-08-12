package com.sales.model;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * VUsrOrder entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "v_usr_order", catalog = "sales")
public class VUsrOrder implements java.io.Serializable {

	// Fields

	private VUsrOrderId id;

	// Constructors

	/** default constructor */
	public VUsrOrder() {
	}

	/** full constructor */
	public VUsrOrder(VUsrOrderId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "userid", column = @Column(name = "userid", nullable = false)),
			@AttributeOverride(name = "ordernum", column = @Column(name = "ordernum", nullable = false)),
			@AttributeOverride(name = "ordervalueofmonth", column = @Column(name = "ordervalueofmonth", precision = 32, scale = 0)) })
	public VUsrOrderId getId() {
		return this.id;
	}

	public void setId(VUsrOrderId id) {
		this.id = id;
	}

}