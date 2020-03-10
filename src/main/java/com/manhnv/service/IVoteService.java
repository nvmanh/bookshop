package com.manhnv.service;

import org.springframework.data.domain.Page;

import com.manhnv.entity.Vote;
import com.manhnv.model.request.VoteRequest;

public interface IVoteService {
	/**
	 * 
	 * @param v
	 * @return
	 */
	Vote save(Vote v);

	/**
	 * 
	 * @param userId
	 * @param authorId
	 * @return
	 */
	Vote findVoteByUserIdAndAuthorId(Long userId, Long authorId);

	/**
	 * 
	 * @param request
	 * @return
	 */
	Page<Vote> findAllVote(VoteRequest request);
}
