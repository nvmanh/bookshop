package com.manhnv.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.manhnv.dto.base.BaseCompareEntity;

@Entity
@Table(name = "tbl_user")
public class User extends BaseCompareEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -841251738925884441L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	private String password;

	@JsonManagedReference
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
	private UserDetail profile;

	public User() {
		super();
	}

	public User(String name, String password) {
		super();
		this.name = name;
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
