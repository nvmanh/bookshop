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
	/**
	 * 
	 * @param request
	 * @return
	 */
	public Page<AuthorDTO> findAll(BasePageRequest request);

	/**
	 * 
	 * @param request
	 * @return
	 */
	public Page<Author> findByName(AuthorRequest request);

	/**
	 * 
	 * @param id
	 * @return
	 */
	public Author findById(Long id);

	/**
	 * 
	 * @param authorID
	 * @param request
	 * @return
	 */
	public AuthorRate rate(Long authorID, AuthorRateRequest request);

	/**
	 * 
	 * @param authorID
	 * @param userId
	 * @param follow
	 * @return
	 */
	public Author follow(Long authorID, Long userId, boolean follow);

	/**
	 * 
	 * @param authorID
	 * @param userId
	 * @return
	 */
	public Author upVote(Long authorID, Long userId);

	/**
	 * 
	 * @param authorID
	 * @param userId
	 * @return
	 */
	public Author downVote(Long authorID, Long userId);

	/**
	 * 
	 * @param authorId
	 * @param userId
	 * @param voted
	 * @return
	 */
	public Author vote(Long authorId, Long userId, boolean voted);

	/**
	 * 
	 * @param request
	 * @return
	 */
	public Page<VoteDTO> viewVotes(VoteRequest request);

	/**
	 * 
	 * @param id
	 * @param req
	 * @return
	 */
	public Author update(Long id, AuthorChangeRequest req);
}
