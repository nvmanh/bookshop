package com.manhnv.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.manhnv.entity.Vote;

public interface IVoteRepository extends CrudRepository<Vote, Long> {
	Page<Vote> findAllVoteByUserId(Long userId, Pageable pageable);

	Page<Vote> findAllVoteByAuthorId(Long authorId, Pageable pageable);

	Page<Vote> findAllVoteByAuthorIdAndVoted(Long authorId, Boolean voted, Pageable pageable);

	Page<Vote> findAllVoteByUserIdAndVoted(Long userId, Boolean voted, Pageable pageable);

	//@Query("select v from tbl_vote v where v.user_id = :user_id and v.author_id = :author_id")
	Optional<Vote> findVoteByUserIdAndAuthorId(Long userId, Long authorId);
	
	Page<Vote> findAll(Pageable pageable);

}
