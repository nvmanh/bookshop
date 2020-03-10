package com.manhnv.book;

import org.springframework.data.domain.Page;

import com.manhnv.entity.Author;
import com.manhnv.entity.AuthorRate;
import com.manhnv.model.dto.AuthorDTO;
import com.manhnv.model.dto.VoteDTO;
import com.manhnv.model.request.AuthorChangeRequest;
import com.manhnv.model.request.AuthorRateRequest;
import com.manhnv.model.request.AuthorRequest;
import com.manhnv.model.request.BasePageRequest;
import com.manhnv.model.request.VoteRequest;

public interface IAuthorService {
	public Page<AuthorDTO> findAll(BasePageRequest request);

	public Page<Author> findByName(AuthorRequest request);

	public Author findById(Long id);

	public AuthorRate rate(Long authorID, AuthorRateRequest request);

	public Author follow(Long authorID, Long userId, boolean follow);

	public Author upVote(Long authorID, Long userId);

	public Author downVote(Long authorID, Long userId);

	public Author vote(Long authorId, Long userId, boolean voted);

	public Page<VoteDTO> viewVotes(VoteRequest request);

	public Author update(Long id, AuthorChangeRequest req);
}
