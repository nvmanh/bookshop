package com.manhnv.model.dto;

import java.sql.Date;

public class AuthorDTO extends BaseDTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1226672765252494729L;
	private Long id;
	private String name;
	private String pseudonym;
	private Double rate;
	private String shortDescription;
	private String description;
	private boolean hideProfile;
	private Date birthday;

	public AuthorDTO() {
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

	public final String getPseudonym() {
		return pseudonym;
	}

	public final void setPseudonym(String pseudonym) {
		this.pseudonym = pseudonym;
	}

	public final Double getRate() {
		return rate;
	}

	public final void setRate(Double rate) {
		this.rate = rate;
	}

	public final String getShortDescription() {
		return shortDescription;
	}

	public final void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public final String getDescription() {
		return description;
	}

	public final void setDescription(String description) {
		this.description = description;
	}

	public final boolean isHideProfile() {
		return hideProfile;
	}

	public final void setHideProfile(boolean hideProfile) {
		this.hideProfile = hideProfile;
	}

	public final Date getBirthday() {
		return birthday;
	}

	public final void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

}
