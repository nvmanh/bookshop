package com.manhnv.entity;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "tbl_privilege")
public class Privilege {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@NotEmpty(message = "Privilege name is not empty")
	private String name;

	private int status = 1;

	@ManyToMany(mappedBy = "privileges")
	@JsonBackReference
	private Collection<Role> roles;

	public Privilege() {
		super();
	}

	public Privilege(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}