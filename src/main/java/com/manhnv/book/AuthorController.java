package com.manhnv.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.manhnv.common.PathConsts;
import com.manhnv.controller.BaseController;
import com.manhnv.model.request.AuthorChangeRequest;
import com.manhnv.model.request.BasePageRequest;
import com.manhnv.model.request.VoteRequest;
import com.manhnv.model.response.JwtResponse;

@RestController
//@RequestMapping(path = PathConsts.v1.AUTH)
public class AuthorController extends BaseController {

	@Autowired
	AuthorService authorService;

	@GetMapping(path = PathConsts.v1.AUTHOR)
	@ResponseBody
	JwtResponse<Object> findAll(@RequestBody(required = false) BasePageRequest request) {
		return new JwtResponse<Object>().onSuccess(authorService.findAll(request), PathConsts.v1.AUTHOR);
	}

	@PostMapping(path = PathConsts.v1.AUTHOR_DETAIL)
	@ResponseBody
	JwtResponse<Object> update(@PathVariable("id") Long id, @RequestBody(required = false) AuthorChangeRequest request) {
		return new JwtResponse<Object>().onSuccess(authorService.update(id, request), PathConsts.v1.AUTHOR_DETAIL);
	}

	@PostMapping(path = PathConsts.v1.AUTHOR_VOTE)
	@ResponseBody
	JwtResponse<Object> vote(@PathVariable("id") Long id, @RequestParam("user_id") Long userId,
			@RequestParam("voted") boolean voted) {
		return new JwtResponse<Object>().onSuccess(authorService.vote(id, userId, voted), PathConsts.v1.AUTHOR_VOTE);
	}

	@GetMapping(path = PathConsts.v1.VOTE)
	@ResponseBody
	JwtResponse<Object> viewVotes(@RequestBody(required = false) VoteRequest request) {
		return new JwtResponse<Object>().onSuccess(authorService.viewVotes(request), PathConsts.v1.VOTE);
	}
}
