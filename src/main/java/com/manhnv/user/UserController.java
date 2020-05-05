package com.manhnv.user;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.manhnv.common.PathConsts;
import com.manhnv.common.ResponseConst;
import com.manhnv.controller.BaseController;
import com.manhnv.entity.UserDetail;
import com.manhnv.model.request.BasePageRequest;
import com.manhnv.model.request.UserChangeRequest;
import com.manhnv.model.request.UserCreateRequest;
import com.manhnv.model.response.JwtResponse;

@RestController
@Validated
@CrossOrigin
//@RequestMapping(value = PathConsts.v1.USER)
public class UserController extends BaseController {

	@Autowired(required = true)
	private UserSevice userService;
	
	

	@GetMapping(value = PathConsts.v1.USER)
	@ResponseBody
	public JwtResponse<Object> users(@RequestBody(required = false) BasePageRequest request) {
		return new JwtResponse<Object>().onSuccess(userService.findAll(request), PathConsts.v1.USER);
	}

	@RequestMapping(value = PathConsts.v1.USER_REGIST, method = RequestMethod.POST)
	public @ResponseBody JwtResponse<Object> regist(@Valid @RequestBody(required = false) UserCreateRequest jwtUser)
			throws Exception {
		UserDetail u = userService.saveUser(jwtUser);
		JwtResponse<Object> response = new JwtResponse<Object>();
		if (u == null) {
			response.setMessage(ResponseConst.MESSAGE_404);
			response.setStatus(ResponseConst.HTTP_404);
		} else {
			response.setMessage(ResponseConst.MESSAGE_200);
			response.setStatus(ResponseConst.HTTP_200);
		}
		response.setData(u);
		response.setPath(PathConsts.v1.USER_REGIST);
		return response;
	}

	@GetMapping(value = PathConsts.v1.USER_UPDATE)
	public @ResponseBody JwtResponse<Object> getUserDetail(@PathVariable("id") Long id) {
		if (id <= 0) {
			return new JwtResponse<Object>().onFail(ResponseConst.HTTP_503, ResponseConst.MESSAGE_503,
					PathConsts.v1.USER);
		}
		UserDetail u = userService.getUserInfo(id);
		if (u == null) {
			return new JwtResponse<Object>().onFail(ResponseConst.HTTP_404, ResponseConst.MESSAGE_404_USER,
					PathConsts.v1.USER_UPDATE);
		}
		return new JwtResponse<Object>().onSuccess(u, PathConsts.v1.USER_UPDATE);
	}

	@RequestMapping(value = PathConsts.v1.USER_UPDATE, method = RequestMethod.PUT)
	public @ResponseBody JwtResponse<Object> updateUserInfo(@PathVariable("id") Long id,
			@Valid @RequestBody UserChangeRequest req) throws Exception {
		if (req == null || id <= 0) {
			return new JwtResponse<Object>().onFail(ResponseConst.HTTP_503, ResponseConst.MESSAGE_503,
					PathConsts.v1.USER);
		}
		UserDetail u = userService.updateInfo(id, req);
		if (u == null) {
			return new JwtResponse<Object>().onFail(ResponseConst.HTTP_404, ResponseConst.MESSAGE_404_USER,
					PathConsts.v1.USER_UPDATE);
		}
		return new JwtResponse<Object>().onSuccess(u, PathConsts.v1.USER);
	}

	@GetMapping(value = PathConsts.v1.USER_FOLLOWING)
	public @ResponseBody JwtResponse<Object> getFollowing(@PathVariable("id") Long id) throws Exception {
		if (id <= 0) {
			return new JwtResponse<Object>().onFail(ResponseConst.HTTP_503, ResponseConst.MESSAGE_503,
					PathConsts.v1.USER_FOLLOWING);
		}
		return new JwtResponse<Object>().onSuccess(userService.getFollowings(id), PathConsts.v1.USER_FOLLOWING);
	}
	
	@GetMapping(value = PathConsts.v1.USER_PROFILE)
	public @ResponseBody JwtResponse<Object> findUser(HttpServletRequest request){
		UserDetail u = userService.getUserInfo(request);
		if (u == null) {
			return new JwtResponse<Object>().onFail(ResponseConst.HTTP_404, ResponseConst.MESSAGE_404_USER,
					PathConsts.v1.USER_PROFILE);
		}
		return new JwtResponse<Object>().onSuccess(u, PathConsts.v1.USER_PROFILE);
	}
}
