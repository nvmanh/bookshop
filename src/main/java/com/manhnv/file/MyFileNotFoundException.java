package com.manhnv.file;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MyFileNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2203232511631149225L;

	public MyFileNotFoundException(String message) {
		super(message);
	}

	public MyFileNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
