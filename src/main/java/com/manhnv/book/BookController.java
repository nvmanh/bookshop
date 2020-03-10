package com.manhnv.book;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.manhnv.common.PathConsts;
import com.manhnv.common.ResponseConst;
import com.manhnv.controller.BaseController;
import com.manhnv.model.request.BookCreateRequest;
import com.manhnv.model.request.BookRatePageRequest;
import com.manhnv.model.request.BookRateRequest;
import com.manhnv.model.request.BookRequest;
import com.manhnv.model.response.JwtResponse;

@RestController
@Validated
//@RequestMapping(value = PathConsts.BOOK)
@CacheConfig(cacheNames = { "book" })
public class BookController extends BaseController {

	@Autowired
	private BookRepository repository;

	@Autowired
	private BookService bookService;

	@GetMapping(value = PathConsts.v1.BOOK)
	@ResponseBody
	// @Cacheable
	JwtResponse<Object> findAll(@RequestBody(required = false) BookRequest req) {
//		try {
		return new JwtResponse<Object>().onSuccess(bookService.findAll(req), PathConsts.v1.BOOK);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return handleExeption(e, PathConsts.v1.BOOK);
//		}
	}

//	@GetMapping(path = PathConsts.v1.BOOK_WITH_AUTHOR)
//	@ResponseBody
//	@Cacheable
//	JwtResponse<Object> findAllBookWithAuthor(@RequestBody BookRequest req) {
//		try {
//			return new JwtResponse<Object>().onSuccess(bookService.findBookByAuthor(req), PathConsts.v1.BOOK);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return handleExeption(e, PathConsts.v1.BOOK);
//		}
//	}

	@PostMapping(path = PathConsts.v1.BOOK_RATE)
	@ResponseBody
	JwtResponse<Object> rateBook(@PathVariable("id") Long id, @RequestBody(required = false) BookRateRequest req) {
//		try {
		return new JwtResponse<Object>().onSuccess(bookService.rate(id, req), PathConsts.v1.BOOK_RATE);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return handleExeption(e, PathConsts.v1.BOOK_RATE);
//		}
	}

	// @GetMapping(path = PathConsts.v1.BOOK_ALL_RATE)
	@GetMapping(path = PathConsts.v1.BOOK_RATE)
	@ResponseBody
	@Cacheable
	JwtResponse<Object> viewAllRates(@PathVariable("id") Long id,
			@RequestBody(required = false) BookRatePageRequest request) throws Exception {
//		try {
		return new JwtResponse<Object>().onSuccess(bookService.getAllRates(id, request), PathConsts.v1.BOOK_RATE);
//		} catch (Exception e) {
//			// e.printStackTrace();
//			return handleExeption(e, PathConsts.v1.BOOK_RATE);
//		}
	}

	// Save
	@PostMapping(value = PathConsts.v1.BOOK)
	@ResponseStatus(HttpStatus.CREATED)
	JwtResponse<Object> newBook(@Valid @RequestBody BookCreateRequest newBook) {
		return new JwtResponse<Object>().onSuccess(bookService.newBook(newBook), PathConsts.v1.BOOK);
	}

	// Find
	@GetMapping(value = PathConsts.v1.BOOK_DETAIL)
	JwtResponse<Object> findOne(@PathVariable @Min(1) Long id) {
		return new JwtResponse<Object>().onSuccess(bookService.findOne(id), PathConsts.v1.BOOK_DETAIL);
	}

	// Save or update
	@PutMapping(value = PathConsts.v1.BOOK_DETAIL)
	JwtResponse<Object> saveOrUpdate(@PathVariable("id") Long id, @RequestBody BookCreateRequest newBook) {
		return new JwtResponse<Object>().onSuccess(bookService.saveOrUpdate(id, newBook), PathConsts.v1.BOOK_DETAIL);
	}

	@DeleteMapping(value = PathConsts.v1.BOOK_DETAIL)
	JwtResponse<Object> deleteBook(@PathVariable Long id) {
		repository.deleteById(id);
		return new JwtResponse<Object>().onSuccess(ResponseConst.DELETE_SUCCESSFUL, PathConsts.v1.BOOK_DETAIL);
	}
}
