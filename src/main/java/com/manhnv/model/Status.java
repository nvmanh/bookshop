package com.manhnv.model;

public enum Status {
	INSTOCK("IN-STOCK"), REMOVED("REMOVED"), OUTSTOCK("OUT-OF-STOCK"), DELETED("DELETED");

	private String mVal;

	private Status(String val) {
		this.mVal = val;
	}

	public String getValue() {
		return mVal;
	}
}
