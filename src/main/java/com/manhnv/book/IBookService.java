package com.manhnv.book;

import org.springframework.data.domain.Page;

import com.manhnv.entity.Author;
import com.manhnv.entity.Book;
import com.manhnv.entity.BookRate;
import com.manhnv.model.request.BookCreateRequest;
import com.manhnv.model.request.BookRatePageRequest;
import com.manhnv.model.request.BookRateRequest;
import com.manhnv.model.request.BookRequest;

public interface IBookService {
	public Page<Book> findAll(int pageNo, int pageSize, boolean descending, String sortBy) throws Exception;
	
	public Page<Book> findAll(BookRequest bookRequest);
	
	public Page<Author> findBookByAuthor(BookRequest bookRequest);
	
	public BookRate rate(Long id, BookRateRequest request);
	
	public Page<BookRate> getAllRates(Long id, BookRatePageRequest request);
	
	public Book findBookByName(String name);
	
	public Book findBookById(Long id);
	
	public Book newBook(BookCreateRequest bookCreateRequest);
	
	public Book findOne(Long id);
	
	public Book saveOrUpdate(Long id, BookCreateRequest bookCreateRequest);
	
	public void deleteBook(Long id);
}
