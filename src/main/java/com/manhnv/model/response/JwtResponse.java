package com.manhnv.model.response;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.manhnv.common.ResponseConst;
import com.manhnv.config.Translator;
import com.manhnv.utils.HttpStatusUtils;

public class JwtResponse<T> implements Serializable {
	private static final long serialVersionUID = -8091879091924046844L;

	private Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	private int status;
	private String error;
	private List<String> errorData;
	private String message;
	private String path;
	private T data;

	public JwtResponse() {
		super();
	}

	public JwtResponse<T> onSuccess(T data, String path) {
		//this.message = ResponseConst.MESSAGE_200;
		this.message = Translator.toLocale("MESSAGE_200");
		this.status = ResponseConst.HTTP_200;
		this.path = path;
		this.data = data;
		return this;
	}

	public JwtResponse<T> onFail(int statusCode, String message, String path) {
		this.message = message;
		this.status = statusCode;
		this.error = HttpStatusUtils.getError(statusCode);
		this.path = path;
		return this;
	}

	public JwtResponse<T> onDuplicate(String path) {
		//this.message = ResponseConst.MESSAGE_2000_DUPLICATE;
		this.message = Translator.toLocale("MESSAGE_2000_DUPLICATE");
		this.status = ResponseConst.HTTP_2000;
		this.error = HttpStatusUtils.getError(HttpStatus.CONFLICT);
		//this.error = HttpStatusUtils.getError(HttpStatus.OK);
		this.path = path;
		return this;
	}

	public JwtResponse<T> onInternalServerError(String path) {
		//this.message = ResponseConst.MESSAGE_500;
		this.message = Translator.toLocale("MESSAGE_500");
		this.status = ResponseConst.HTTP_500;
		this.error = HttpStatusUtils.getError(HttpStatus.INTERNAL_SERVER_ERROR);
		this.path = path;
		return this;
	}

	public JwtResponse<T> onUnknownError(String path) {
		//this.message = ResponseConst.MESSAGE_1001;
		this.message = Translator.toLocale("MESSAGE_1001");
		this.status = ResponseConst.HTTP_1001;
		this.error = HttpStatusUtils.getError(HttpStatus.BAD_REQUEST);
		this.path = path;
		return this;
	}

	public JwtResponse<T> onBadRequest(String path) {
		this.message = Translator.toLocale("BAD_REQUEST");
		this.status = HttpStatus.BAD_REQUEST.value();
		this.path = path;
		return this;
	}

	public JwtResponse<T> onNotFound(String path) {
		this.message = Translator.toLocale("NOT_FOUND");
		this.status = HttpStatus.NOT_FOUND.value();
		this.path = path;
		return this;
	}

	public String getMessage() {
		return message;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public final List<String> getErrorData() {
		return errorData;
	}

	public final void setErrorData(List<String> errorData) {
		this.errorData = errorData;
	}

}
