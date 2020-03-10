package com.manhnv.authen;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.manhnv.common.PathConsts;
import com.manhnv.common.ResponseConst;
import com.manhnv.config.JwtTokenUtil;
import com.manhnv.model.request.JwtRequest;
import com.manhnv.model.response.JwtResponse;

@RestController
@CrossOrigin
public class JwtAuthenticationController {
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private JwtUserDetailsService userDetailsService;

	/**
	 * 
	 * @param authenticationRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = PathConsts.v1.AUTH, method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
		try {
			authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		} catch (Exception e) {
			return ResponseEntity
					.ok(new JwtResponse<String>().onFail(ResponseConst.HTTP_403, e.getMessage(), PathConsts.v1.AUTH));
		}
		UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		if (userDetails == null) {
			return ResponseEntity.ok(new JwtResponse<String>().onFail(ResponseConst.HTTP_403, ResponseConst.MESSAGE_403,
					PathConsts.v1.AUTH));
		}
		return ResponseEntity
				.ok(new JwtResponse<String>().onSuccess(jwtTokenUtil.generateToken(userDetails), PathConsts.v1.AUTH));
	}

	/**
	 * 
	 * @param username
	 * @param password
	 * @throws Exception
	 */
	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = PathConsts.v1.LOGOUT)
	public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
		return ResponseEntity
				.ok(new JwtResponse<String>().onSuccess(ResponseConst.MESSAGE_LOGOUT_SUCESS, PathConsts.v1.LOGOUT));
	}
}