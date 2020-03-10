package com.manhnv.error;

import com.manhnv.utils.TextUtils;

public class DataInvalidException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7741810721039986382L;
	private static final String ERROR_MESSAGE = "Please provide neccessary information";

	public DataInvalidException(String message) {
		super(TextUtils.isEmpty(message) ? ERROR_MESSAGE : message);
	}

	public DataInvalidException() {
		super(ERROR_MESSAGE);
	}
}
