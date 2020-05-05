package com.manhnv.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Null;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.manhnv.common.ResponseConst;
import com.manhnv.config.JwtTokenUtil;
import com.sun.istack.NotNull;

import io.jsonwebtoken.ExpiredJwtException;

public class RequestUtils {
	private static final Logger mLogger = LogManager.getLogger(RequestUtils.class);

	public static String[] getUserNameFromRequestToken(@NotNull HttpServletRequest request,
			@Null HttpServletResponse response, JwtTokenUtil jwtTokenUtil) {
		final String requestTokenHeader = request.getHeader("Authorization");
		String username = null;
		String jwtToken = null;
		// JWT Token is in the form "Bearer token". Remove Bearer word and get
		// only the Token
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
			try {
				username = jwtTokenUtil.getUsernameFromToken(jwtToken);
			} catch (IllegalArgumentException e) {
				// System.out.println("Unable to get JWT Token");
				mLogger.warn("Unable to get JWT Token");
				if (response != null) {
					response.setStatus(ResponseConst.HTTP_1002);
				}
				// response.sendError(ResponseConst.HTTP_1002, "Unable to get JWT Token");
			} catch (ExpiredJwtException e) {
				// System.out.println("JWT Token has expired");
				mLogger.warn("JWT Token has expired");
				if (response != null) {
					response.setStatus(ResponseConst.HTTP_1003);
				}
				// response.sendError(ResponseConst.HTTP_1003, "JWT Token has expired");
			}
		} else {
			mLogger.warn("JWT Token does not begin with Bearer String");
			// response.setStatus(ResponseConst.HTTP_1004);
			// response.sendError(ResponseConst.HTTP_1004, "JWT Token does not begin with
			// Bearer String");
		}
		return new String[] { username, jwtToken };
	}
}
