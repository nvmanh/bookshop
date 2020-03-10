package com.manhnv.book;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.manhnv.common.Const;
import com.manhnv.entity.Author;
import com.manhnv.entity.AuthorRate;
import com.manhnv.entity.UserDetail;
import com.manhnv.entity.Vote;
import com.manhnv.error.DataInvalidException;
import com.manhnv.error.ModelNotFoundException;
import com.manhnv.model.dto.AuthorDTO;
import com.manhnv.model.dto.VoteDTO;
import com.manhnv.model.request.AuthorChangeRequest;
import com.manhnv.model.request.AuthorRateRequest;
import com.manhnv.model.request.AuthorRequest;
import com.manhnv.model.request.BasePageRequest;
import com.manhnv.model.request.VoteRequest;
import com.manhnv.service.VoteService;
import com.manhnv.user.IUserDetailRepository;
import com.manhnv.utils.DTOConverter;
import com.manhnv.utils.TextUtils;

@Service
@CacheConfig(cacheNames = { "author" })
public class AuthorService implements IAuthorService {

	@Autowired
	private AuthorRepository authorRepository;

	@Autowired
	private IUserDetailRepository userDetailRepository;

	@Autowired
	private AuthorRateRepository authorRateRepository;

	@Autowired
	private VoteService voteService;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	@CacheEvict(value = "authors", allEntries = true)
	public Page<AuthorDTO> findAll(BasePageRequest request) {
		Pageable pageable = PageRequest.of(Const.DEFAULT_START_PAGE, Const.DEFAULT_PAGE_SIZE);
		if (request != null) {
			pageable = PageRequest.of(request.getPageNo(), request.getPageSize());
		}
		return authorRepository.findAll(pageable).map(author -> DTOConverter.convertAuthorDTO(author, modelMapper));
	}

	@Override
	@CacheEvict(value = "authorByName", allEntries = true)
	public Page<Author> findByName(AuthorRequest request) {
		Pageable pageable = PageRequest.of(Const.DEFAULT_START_PAGE, Const.DEFAULT_PAGE_SIZE);
		if (request != null) {
			pageable = PageRequest.of(request.getPageNo(), request.getPageSize());
		}
		if (!TextUtils.isEmpty(request.getName())) {
			return authorRepository.findByName(request.getName(), pageable);
		}
		return authorRepository.findAll(pageable);
	}

	@Override
	@CachePut("author")
	public Author findById(Long id) {
		if (id == null || id <= 0)
			throw new ModelNotFoundException(Const.TABLE_ALIAS_AUTHOR, id);
		return authorRepository.findById(id)
				.orElseThrow(() -> new ModelNotFoundException(Const.TABLE_ALIAS_AUTHOR, id));
	}

	@Override
	public AuthorRate rate(Long id, AuthorRateRequest request) {
		if (id == null || id <= 0)
			throw new ModelNotFoundException(Const.TABLE_ALIAS_AUTHORRATE, id);
		if (request == null)
			throw new DataInvalidException();
		if (!request.isValid())
			throw new ModelNotFoundException(Const.TABLE_ALIAS_USER, request.getUserId());
		Author author = authorRepository.findById(id)
				.orElseThrow(() -> new ModelNotFoundException(Const.TABLE_ALIAS_AUTHOR, request.getUserId()));
		AuthorRate rate = new AuthorRate(request);
		UserDetail user = userDetailRepository.findById(request.getUserId())
				.orElseThrow(() -> new ModelNotFoundException(Const.TABLE_ALIAS_USER, request.getUserId()));
		rate.setUser(user);
		rate.setAuthor(author);
		return authorRateRepository.save(rate);
	}

	@Override
	public Author follow(Long id, Long userId, boolean follow) {
		if (id == null || id <= 0 || !authorRepository.existsById(id))
			throw new ModelNotFoundException(Const.TABLE_ALIAS_AUTHOR, id);
		if (userId == null || userId <= 0 || userDetailRepository.existsById(userId))
			throw new ModelNotFoundException(Const.TABLE_ALIAS_USER, id);
		Author author = authorRepository.findById(id)
				.orElseThrow(() -> new ModelNotFoundException(Const.TABLE_ALIAS_AUTHOR, id));
		UserDetail user = userDetailRepository.findById(userId)
				.orElseThrow(() -> new ModelNotFoundException(Const.TABLE_ALIAS_USER, userId));
		author.addFollowers(user);
		return authorRepository.save(author);
	}

	@Override
	public Author upVote(Long id, Long userId) {
		return vote(id, userId, true);
	}

	@Override
	public Author downVote(Long id, Long userId) {
		return vote(id, userId, false);
	}

	@Override
	public Author vote(Long id, Long userId, boolean voted) {
		if (id == null || id <= 0 || !authorRepository.existsById(id))
			throw new ModelNotFoundException(Const.TABLE_ALIAS_AUTHOR, id);
		if (userId == null || userId <= 0 || !userDetailRepository.existsById(userId))
			throw new ModelNotFoundException(Const.TABLE_ALIAS_USER, id);
		Author author = authorRepository.findById(id)
				.orElseThrow(() -> new ModelNotFoundException(Const.TABLE_ALIAS_AUTHOR, id));
		UserDetail user = userDetailRepository.findById(userId)
				.orElseThrow(() -> new ModelNotFoundException(Const.TABLE_ALIAS_USER, userId));
		Vote exist = voteService.findVoteByUserIdAndAuthorId(userId, id);
		if (exist != null) {
			Vote v = new Vote(author, user, voted);
			v.setId(exist.getId());
			voteService.save(v);
		} else {
			voteService.save(new Vote(author, user, voted));
		}
		return authorRepository.findById(id)
				.orElseThrow(() -> new ModelNotFoundException(Const.TABLE_ALIAS_AUTHOR, id));
	}

	@Override
	public Page<VoteDTO> viewVotes(VoteRequest request) {
		return voteService.findAllVote(request).map(vote -> DTOConverter.convertVoteDTO(vote, modelMapper));
	}

	@Override
	public Author update(Long id, AuthorChangeRequest req) {
		if (id == null || id <= 0)
			throw new ModelNotFoundException(Const.TABLE_ALIAS_AUTHORRATE, id);
		Author author = authorRepository.findById(id)
				.orElseThrow(() -> new ModelNotFoundException(Const.TABLE_ALIAS_AUTHORRATE, id)).update(req);
		return authorRepository.save(author);
	}

}
