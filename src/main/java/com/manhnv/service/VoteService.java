package com.manhnv.service;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.manhnv.common.Const;
import com.manhnv.entity.Vote;
import com.manhnv.error.DataInvalidException;
import com.manhnv.error.ModelNotFoundException;
import com.manhnv.model.request.VoteRequest;

@Service
public class VoteService implements IVoteService {
	@Autowired
	private IVoteRepository voteRepository;

	@Override
	public Vote save(Vote v) {
		if (v == null)
			throw new DataInvalidException();
		if (!voteRepository.existsById(v.getId())) {
			return voteRepository.save(v);
		} else {
			Vote exist = voteRepository.findById(v.getId())
					.orElseThrow(() -> new ModelNotFoundException(Const.TABLE_ALIAS_VOTE, v.getId()));
			exist.setVoted(v.getVoted());
			return voteRepository.save(exist);
		}
	}

	@Override
	public Vote findVoteByUserIdAndAuthorId(Long userId, Long authorId) {
		if (userId == null || userId <= 0) {
			throw new ModelNotFoundException(Const.TABLE_ALIAS_USER, userId);
		}
		if (authorId == null || authorId <= 0) {
			throw new ModelNotFoundException(Const.TABLE_ALIAS_AUTHOR, authorId);
		}
		return voteRepository.findVoteByUserIdAndAuthorId(userId, authorId)
				.orElseThrow(() -> new DataInvalidException());
	}

	@Override
	public Page<Vote> findAllVote(VoteRequest request) {
		Pageable pageable = PageRequest.of(Const.DEFAULT_START_PAGE, Const.DEFAULT_PAGE_SIZE);
		if (request != null) {
			pageable = PageRequest.of(request.getPageNo(), request.getPageSize());
		}
		if (request == null || request.inValid()) {
			return voteRepository.findAll(pageable);
		}
		if (request.validUserAndAuthor()) {
			Vote v = voteRepository.findVoteByUserIdAndAuthorId(request.getUserId(), request.getAuthorId()).get();
			return new PageImpl<Vote>(Arrays.asList(v));
		} else if (request.getUserId() != null && request.getUserId() > 0 && request.getVoted() != null) {
			return voteRepository.findAllVoteByUserIdAndVoted(request.getUserId(), request.getVoted(), pageable);
		} else if (request.getUserId() != null && request.getUserId() > 0) {
			return voteRepository.findAllVoteByUserId(request.getUserId(), pageable);
		} else if (request.getAuthorId() != null && request.getAuthorId() > 0 && request.getVoted() != null) {
			return voteRepository.findAllVoteByAuthorIdAndVoted(request.getAuthorId(), request.getVoted(), pageable);
		} else if (request.getAuthorId() != null && request.getAuthorId() > 0) {
			return voteRepository.findAllVoteByAuthorId(request.getAuthorId(), pageable);
		}
		return new PageImpl<Vote>(new ArrayList<Vote>());
	}

}
