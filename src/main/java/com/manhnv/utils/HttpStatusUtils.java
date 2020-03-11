package com.manhnv.utils;

import org.springframework.http.HttpStatus;

import com.manhnv.config.Translator;

public class HttpStatusUtils {
	public static String getError(int statusCode) {
		HttpStatus status;
		try {
			status = HttpStatus.valueOf(statusCode);
		} catch (Exception e) {
			status = HttpStatus.BAD_REQUEST;
		}
		return Translator.toLocale(status.name());
	}

	public static String getError(HttpStatus status) {
		return Translator.toLocale(status.name());
	}
}
