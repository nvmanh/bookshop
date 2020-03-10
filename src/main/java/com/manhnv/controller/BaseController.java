package com.manhnv.controller;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;

import com.manhnv.model.response.JwtResponse;

public class BaseController {

	public <T> JwtResponse<T> handleExeption(Exception e, String path) {

		getLogger().error(e.getMessage());

		if (e instanceof NoSuchElementException) {
			return new JwtResponse<T>().onNotFound(path);
		} else if (e instanceof SQLException) {
			return new JwtResponse<T>().onBadRequest(path);
		}
		return new JwtResponse<T>().onFail(HttpStatus.BAD_REQUEST.value(), e.getMessage(), path);
	}

	public Logger getLogger() {
		return LogManager.getLogger(getClass());
	}
}
