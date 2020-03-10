package com.manhnv.file;

public class FileStorageException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8665741141427751797L;

	public FileStorageException(String message) {
		super(message);
	}

	public FileStorageException(String message, Throwable cause) {
		super(message, cause);
	}
}
