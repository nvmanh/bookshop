package com.manhnv.model;

public enum UserStatus {
	DISABLE(0), ENABLE(1);

	private int mVal;

	private UserStatus(int val) {
		this.mVal = val;
	}

	public int value() {
		return mVal;
	}
}
