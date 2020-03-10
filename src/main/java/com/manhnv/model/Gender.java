package com.manhnv.model;

public enum Gender {
	MALE(1), FEMALE(2), UNKNOWN(3);

	private int mVal;

	private Gender(int val) {
		this.mVal = val;
	}

	public int value() {
		return mVal;
	}
}
