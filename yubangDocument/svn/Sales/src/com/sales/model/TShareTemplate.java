package com.sales.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TShareTemplate entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_share_template", catalog = "sales")
public class TShareTemplate implements java.io.Serializable {

	// Fields

	private Integer id;
	private String content;
	private Integer type;

	// Constructors

	/** default constructor */
	public TShareTemplate() {
	}

	/** full constructor */
	public TShareTemplate(String content, Integer type) {
		this.content = content;
		this.type = type;
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

	@Column(name = "content", nullable = false, length = 1024)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "type", nullable = false)
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}