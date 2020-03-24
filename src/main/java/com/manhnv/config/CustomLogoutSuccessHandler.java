package com.manhnv.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import com.manhnv.common.PathConsts;

public class CustomLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler implements LogoutSuccessHandler {

	public CustomLogoutSuccessHandler() {
		super();
	}

	// API
	@Override
	public void onLogoutSuccess(final HttpServletRequest request, final HttpServletResponse response,
			final Authentication authentication) throws IOException, ServletException {
		//final String refererUrl = request.getHeader("Referer");
		//System.out.println(refererUrl);
		response.setStatus(HttpStatus.OK.value());
		response.sendRedirect(PathConsts.v1.LOGOUT);
	}

}
