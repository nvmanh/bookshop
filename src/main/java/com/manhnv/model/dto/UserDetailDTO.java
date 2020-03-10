package com.manhnv.model.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.manhnv.model.Gender;
import com.manhnv.model.UserStatus;

public class UserDetailDTO extends BaseDTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3956374479122942483L;
	private Long id;
	private String name;
	private String fullName;
	private String address1;
	private String address2;
	private int age;
	@Enumerated(EnumType.STRING)
	private Gender gender;
	private String phone1;
	private String phone2;
	private String email1;
	private String email2;
	private UserStatus status = UserStatus.ENABLE;

	public UserDetailDTO() {
		super();
	}

	public final Long getId() {
		return id;
	}

	public final void setId(Long id) {
		this.id = id;
	}

	public final String getName() {
		return name;
	}

	public final void setName(String name) {
		this.name = name;
	}

	public final String getFullName() {
		return fullName;
	}

	public final void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public final String getAddress1() {
		return address1;
	}

	public final void setAddress1(String address1) {
		this.address1 = address1;
	}

	public final String getAddress2() {
		return address2;
	}

	public final void setAddress2(String address2) {
		this.address2 = address2;
	}

	public final int getAge() {
		return age;
	}

	public final void setAge(int age) {
		this.age = age;
	}

	public final Gender getGender() {
		return gender;
	}

	public final void setGender(Gender gender) {
		this.gender = gender;
	}

	public final String getPhone1() {
		return phone1;
	}

	public final void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	public final String getPhone2() {
		return phone2;
	}

	public final void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	public final String getEmail1() {
		return email1;
	}

	public final void setEmail1(String email1) {
		this.email1 = email1;
	}

	public final String getEmail2() {
		return email2;
	}

	public final void setEmail2(String email2) {
		this.email2 = email2;
	}

	public final UserStatus getStatus() {
		return status;
	}

	public final void setStatus(UserStatus status) {
		this.status = status;
	}

}
