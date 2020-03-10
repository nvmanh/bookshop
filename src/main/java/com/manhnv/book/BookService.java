package com.manhnv.book;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.manhnv.common.Const;
import com.manhnv.entity.Author;
import com.manhnv.entity.Book;
import com.manhnv.entity.BookRate;
import com.manhnv.entity.UserDetail;
import com.manhnv.error.DataInvalidException;
import com.manhnv.error.ModelNotFoundException;
import com.manhnv.model.request.BookCreateRequest;
import com.manhnv.model.request.BookRatePageRequest;
import com.manhnv.model.request.BookRateRequest;
import com.manhnv.model.request.BookRequest;
import com.manhnv.user.IUserDetailRepository;
import com.manhnv.utils.DTOConverter;
import com.manhnv.utils.TextUtils;

@Service
//@CacheConfig(cacheNames = { "book" })
public class BookService implements IBookService {

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private AuthorRepository authorRepository;

	@Autowired
	private IUserDetailRepository userRepository;

	@Autowired
	private BookRateRepository bookRateRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	@Cacheable(value = "books", keyGenerator = "customKeyGenerator")
	public Page<Book> findAll(int pageNo, int pageSize, boolean descending, String sortBy) {
		if (pageSize <= 0)
			pageSize = 100;
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		if (!TextUtils.isEmpty(sortBy)) {
			if (descending) {
				pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
			} else {
				pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).ascending());
			}
		}
		return bookRepository.findAll(pageable);
	}

	@Override
	public Page<Book> findAll(BookRequest bookRequest) {
		Pageable pageable = PageRequest.of(Const.DEFAULT_START_PAGE, Const.DEFAULT_PAGE_SIZE);
		if (bookRequest != null) {
			if (TextUtils.isEmpty(bookRequest.getSortBy())) {
				pageable = PageRequest.of(bookRequest.getPageNo(), bookRequest.getPageSize());
			} else {
				if (bookRequest.isDescending()) {
					pageable = PageRequest.of(bookRequest.getPageNo(), bookRequest.getPageSize(),
							Sort.by(bookRequest.getSortBy()).descending());
				} else {
					pageable = PageRequest.of(bookRequest.getPageNo(), bookRequest.getPageSize(),
							Sort.by(bookRequest.getSortBy()).ascending());
				}
			}
			if (!TextUtils.isEmpty(bookRequest.getAuthor()) && bookRequest.isValidStatus()) {
				return bookRepository.findAllByAuthorAndStatus(bookRequest.getAuthor(), bookRequest.getStatus(),
						pageable);
			} else if (!TextUtils.isEmpty(bookRequest.getAuthor())) {
				return bookRepository.findAllByAuthor(bookRequest.getAuthor(), pageable);
			} else if (bookRequest.isValidStatus()) {
				return bookRepository.findAllByStatus(bookRequest.getStatus(), pageable);
			}
		}
		return bookRepository.findAll(pageable);
	}

	@Override
	public Page<Author> findBookByAuthor(BookRequest bookRequest) {
		Pageable pageable = PageRequest.of(Const.DEFAULT_START_PAGE, Const.DEFAULT_PAGE_SIZE);
		if (bookRequest != null) {
			if (TextUtils.isEmpty(bookRequest.getSortBy())) {
				pageable = PageRequest.of(bookRequest.getPageNo(), bookRequest.getPageSize());
			} else {
				if (bookRequest.isDescending()) {
					pageable = PageRequest.of(bookRequest.getPageNo(), bookRequest.getPageSize(),
							Sort.by(bookRequest.getSortBy()).descending());
				} else {
					pageable = PageRequest.of(bookRequest.getPageNo(), bookRequest.getPageSize(),
							Sort.by(bookRequest.getSortBy()).ascending());
				}
			}
			if (!TextUtils.isEmpty(bookRequest.getAuthor())) {
				return authorRepository.findByName(bookRequest.getAuthor(), pageable);
			}
		}
		return authorRepository.findAll(pageable);
	}

	@Override
	public BookRate rate(Long id, BookRateRequest request) {
		if (id == null || id <= 0 || request == null || !request.isValid()) {
			throw new DataInvalidException();
		}

		if (!bookRepository.existsById(id) || !userRepository.existsById(request.getUserId())) {
			throw new DataInvalidException();
		}

		Book book = bookRepository.findById(id).get();
		UserDetail user = userRepository.findById(request.getUserId()).get();
		BookRate bookRate = new BookRate(request);
		bookRate.setBook(book);
		bookRate.setUser(user);
		return bookRateRepository.save(bookRate);
	}

	@Override
	public Page<BookRate> getAllRates(Long id, BookRatePageRequest request) {
		if (id == null || id <= 0)
			throw new ModelNotFoundException(Const.TABLE_ALIAS_BOOK, id);
		Pageable pageable = PageRequest.of(Const.DEFAULT_START_PAGE, Const.DEFAULT_PAGE_SIZE);
		if (request != null) {
			pageable = PageRequest.of(request.getPageNo(), request.getPageSize());
			if (request.getUserId() != null && request.getUserId() > 0) {
				return bookRateRepository.findAllByBookIdAndUserId(id, request.getUserId(), pageable);
			}
		}
		return bookRateRepository.findAllByBookId(id, pageable);
	}

	@Override
	public Book findBookByName(String name) {
		if (TextUtils.isEmpty(name)) {
			throw new DataInvalidException(Const.BOOK_NOT_EXIST);
		}
		return bookRepository.findByName(name).orElseThrow(() -> new ModelNotFoundException(Const.BOOK_NOT_EXIST));
	}

	@Override
	public Book findBookById(Long id) {
		if (id == null || id <= 0 || !bookRepository.existsById(id)) {
			throw new ModelNotFoundException(Const.BOOK_NOT_EXIST, id);
		}
		return bookRepository.findById(id).orElseThrow(() -> new ModelNotFoundException(Const.BOOK_NOT_EXIST, id));
	}

	@Override
	public Book newBook(BookCreateRequest bookCreateRequest) {
		if (bookCreateRequest == null) {
			throw new DataInvalidException();
		}
		Book book = DTOConverter.convertBook(bookCreateRequest, modelMapper);
		if (bookCreateRequest.getAuthorId() != null && bookCreateRequest.getAuthorId() > 0
				&& authorRepository.existsById(bookCreateRequest.getAuthorId())) {
			book.setAuthor(authorRepository.findById(bookCreateRequest.getAuthorId()).orElseThrow(
					() -> new ModelNotFoundException(Const.TABLE_ALIAS_AUTHOR, bookCreateRequest.getAuthorId())));
		}
		return bookRepository.save(book);
	}

	@Override
	public Book findOne(Long id) {
		return bookRepository.findById(id).orElseThrow(() -> new ModelNotFoundException(Const.TABLE_ALIAS_BOOK, id));
	}

	@Override
	public Book saveOrUpdate(Long id, BookCreateRequest bookCreateRequest) {
		if (id == null || id <= 0) {
			throw new ModelNotFoundException(Const.TABLE_ALIAS_BOOK, id);
		}
		Book book = new Book(bookCreateRequest);
		if (bookCreateRequest.getAuthorId() != null && bookCreateRequest.getAuthorId() > 0
				&& authorRepository.existsById(bookCreateRequest.getAuthorId())) {
			book.setAuthor(authorRepository.findById(bookCreateRequest.getAuthorId()).orElseThrow(
					() -> new ModelNotFoundException(Const.TABLE_ALIAS_AUTHOR, bookCreateRequest.getAuthorId())));
		}
		return bookRepository.save(book);
	}

	@Override
	public void deleteBook(Long id) {
		bookRepository.deleteById(id);
	}
}
