package com.manhnv.error;

public class ModelNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1321786441633571480L;

	public ModelNotFoundException(String name, Long id) {
		super(name + " id not found: " + id);
	}

	public ModelNotFoundException(String message) {
		super(message);
	}
}
