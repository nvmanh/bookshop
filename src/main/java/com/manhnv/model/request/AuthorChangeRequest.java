package com.manhnv.model.request;

import java.sql.Date;

public class AuthorChangeRequest {
	private String name;
	private String shortDescription;
	private String description;
	private Boolean hideProfile;
	private Date birthday;

	public AuthorChangeRequest() {
		super();
	}

	public AuthorChangeRequest(String name, String shortDescription, String description, boolean hideProfile,
			Date birthday) {
		super();
		this.name = name;
		this.shortDescription = shortDescription;
		this.description = description;
		this.hideProfile = hideProfile;
		this.birthday = birthday;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean isHideProfile() {
		return hideProfile;
	}

	public void setHideProfile(Boolean hideProfile) {
		this.hideProfile = hideProfile;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

}
