package com.manhnv.error;

import java.util.List;
import java.util.Set;

import com.google.gson.Gson;

public class ModelUnSupportedFiledPatchException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9044101031518593672L;

	public ModelUnSupportedFiledPatchException(Set<String> keys) {
		super("Field " + keys.toString() + " update is not allow.");
	}

	public ModelUnSupportedFiledPatchException(String... keys) {
		super("Field " + new Gson().toJson(keys) + " update is not allow.");
	}

	public ModelUnSupportedFiledPatchException(List<String> keys) {
		super("Field " + new Gson().toJson(keys) + " update is not allow.");
	}
}
