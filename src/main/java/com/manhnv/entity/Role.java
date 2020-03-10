package com.manhnv.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

@Entity
@Table(name = "tbl_role")
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String name;

	@ManyToMany(mappedBy = "roles")
	@JsonUnwrapped
	@JsonBackReference
	private Set<UserDetail> users;

	@JsonManagedReference
	@ManyToMany
	@JoinTable(name = "tbl_roles_privileges", joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "privilege_id", referencedColumnName = "id"))
	private Set<Privilege> privileges;

	public Role() {
		super();
	}

	public Role(String name) {
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

	public Set<UserDetail> getUsers() {
		return users;
	}

	public void setUsers(Set<UserDetail> users) {
		this.users = users;
	}

	public Set<Privilege> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(Set<Privilege> privileges) {
		this.privileges = privileges;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Role)) {
			return false;
		}
		return ((Role) obj).getId() == getId();
	}

	@Override
	public int hashCode() {
		return getId() == null ? super.hashCode() : Math.toIntExact(getId());
	}
}
