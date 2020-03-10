package com.manhnv.model;

public enum RoleName {

	ROLE_ADMIN("ROLE_ADMIN"), ROLE_USER("ROLE_USER");

	private String mValue;

	private RoleName(String value) {
		this.mValue = value;
	}

	public String value() {
		return mValue;
	}
}
