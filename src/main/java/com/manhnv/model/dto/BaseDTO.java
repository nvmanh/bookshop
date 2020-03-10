package com.manhnv.model.dto;

import java.io.Serializable;

import com.google.gson.Gson;

public class BaseDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4016309799865982114L;

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
}
