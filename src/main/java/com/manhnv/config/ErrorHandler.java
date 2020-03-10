package com.manhnv.config;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.manhnv.model.response.JwtResponse;

@ControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {
	@ExceptionHandler(Exception.class)
	public JwtResponse<Object> handle(Exception ex, HttpServletRequest request, HttpServletResponse response) {
		if (ex instanceof NullPointerException) {
			return new JwtResponse<Object>().onBadRequest(request.getPathInfo());
		}
		return new JwtResponse<Object>().onInternalServerError(request.getPathInfo());
	}

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(SQLException.class)
	public JwtResponse<Object> handleSQLException(HttpServletRequest request, Exception ex) {
		return new JwtResponse<Object>().onBadRequest(request.getPathInfo());
	}

	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ExceptionHandler(NotFoundException.class)
	public JwtResponse<Object> handleNotFoundException(HttpServletRequest request, Exception ex) {
		return new JwtResponse<Object>().onBadRequest(request.getPathInfo());
	}
}
