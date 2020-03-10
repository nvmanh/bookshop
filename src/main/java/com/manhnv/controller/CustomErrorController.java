package com.manhnv.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.manhnv.common.PathConsts;
import com.manhnv.model.response.JwtResponse;

@RestController
@RequestMapping
public class CustomErrorController implements ErrorController {

	@Override
	public String getErrorPath() {
		return PathConsts.v1.ERROR;
	}

	@RequestMapping(path = PathConsts.v1.ERROR)
	@ResponseBody
	JwtResponse<Object> onError(HttpServletRequest request, HttpServletResponse response) {
		try {
			HttpStatus status = HttpStatus.valueOf(response.getStatus());
			return new JwtResponse<Object>().onFail(status.value(), status.getReasonPhrase(), request.getContextPath());
		} catch (Exception e) {
			return new JwtResponse<Object>().onUnknownError(request.getContextPath());
		}
	}

	@RequestMapping(path = PathConsts.v1.API_ERROR)
	@ResponseBody
	JwtResponse<Object> onApiError() {
		return new JwtResponse<Object>().onInternalServerError(PathConsts.v1.API_ERROR);
	}

}
