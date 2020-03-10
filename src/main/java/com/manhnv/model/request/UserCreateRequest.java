package com.manhnv.model.request;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import com.manhnv.error.validator.Email;
import com.manhnv.error.validator.Phone;
import com.manhnv.model.Gender;
import com.manhnv.model.UserStatus;

public class UserCreateRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 222347033404556649L;
	@NotEmpty(message = "Username is required")
	private String username;
	@NotEmpty(message = "Password is required")
	private String password;
	private int age;
	private String address1;
	private String address2;
	private String fullName;
	private Gender gender;
	@Phone
	private String phone1;
	private String phone2;
	@Email
	private String email1;
	private String email2;
	private UserStatus status = UserStatus.ENABLE;

	public UserCreateRequest() {
		super();
	}

	public UserCreateRequest(String username, String password) {
		this.password = password;
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getPhone1() {
		return phone1;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	public String getPhone2() {
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	public String getEmail1() {
		return email1;
	}

	public void setEmail1(String email1) {
		this.email1 = email1;
	}

	public String getEmail2() {
		return email2;
	}

	public void setEmail2(String email2) {
		this.email2 = email2;
	}

	public UserStatus getStatus() {
		return status;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}

}
