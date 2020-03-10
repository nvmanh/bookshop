package com.manhnv.error;

import java.nio.file.AccessDeniedException;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.manhnv.file.MyFileNotFoundException;
import com.manhnv.model.response.JwtResponse;

@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE + 1)
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {
	private static final Logger logger = LogManager.getLogger(CustomGlobalExceptionHandler.class);

	// Let Spring BasicErrorController handle the exception, we just override the
	// status code
	@ExceptionHandler(value = { MyFileNotFoundException.class, ModelNotFoundException.class })
	public void springHandleNotFound(HttpServletResponse response) throws Exception {
		response.sendError(HttpStatus.NOT_FOUND.value());
	}

	@ExceptionHandler(value = { ModelUnSupportedFiledPatchException.class })
	public void springUnSupportedFieldPatch(HttpServletResponse response) throws Exception {
		response.sendError(HttpStatus.METHOD_NOT_ALLOWED.value());
	}

	// @Validate For Validating Path Variables and Request Parameters
	@ExceptionHandler(value = { ConstraintViolationException.class, RequestRejectedException.class })
	public void constraintViolationException(HttpServletResponse response) throws Exception {
		response.sendError(HttpStatus.BAD_REQUEST.value());
	}

	@ExceptionHandler(value = { DataInvalidException.class, TransactionSystemException.class })
	public void dataRequestRejectedException(HttpServletResponse response) throws Exception {
		response.sendError(HttpStatus.NOT_ACCEPTABLE.value());
	}

	@ExceptionHandler(AccessDeniedException.class)
	public void handleAccessDeniedException(HttpServletResponse response) throws Exception {
		response.sendError(HttpStatus.FORBIDDEN.value());
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		JwtResponse<Object> response = new JwtResponse<Object>();
		response.setTimestamp(new Timestamp(System.currentTimeMillis()));
		response.setPath(request.getContextPath());
		response.setStatus(status.value());
		response.setError(ex.getMessage());
		// response.setMessage(ex.getMessage());
		return new ResponseEntity<Object>(response, headers, status);
	}

	// error handle for @Valid
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		// Map<String, Object> body = new LinkedHashMap<>();
		// body.put("timestamp", new Date());
		// body.put("status", status.value());

		// Get all errors
		List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(x -> x.getDefaultMessage())
				.collect(Collectors.toList());

		// body.put("errors", errors);
		logger.error(errors.stream().map(Object::toString).collect(Collectors.joining(", ")));

		JwtResponse<Object> response = new JwtResponse<Object>();
		response.setPath(request.getContextPath());
		response.setStatus(status.value());
		//response.setError(ex.getMessage());
		response.setErrorData(errors);
		return new ResponseEntity<>(response, headers, status);
	}

}
