package com.manhnv.service;

import org.springframework.data.domain.Page;

import com.manhnv.entity.Vote;
import com.manhnv.model.request.VoteRequest;

public interface IVoteService {
	Vote save(Vote v);

	Vote findVoteByUserIdAndAuthorId(Long userId, Long authorId);

	Page<Vote> findAllVote(VoteRequest request);
}
