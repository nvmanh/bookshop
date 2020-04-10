package com.manhnv.book;

import java.util.List;

import org.springframework.data.domain.Page;

import com.manhnv.entity.Author;
import com.manhnv.entity.Book;
import com.manhnv.entity.BookRate;
import com.manhnv.model.request.BookCreateRequest;
import com.manhnv.model.request.BookRatePageRequest;
import com.manhnv.model.request.BookRateRequest;
import com.manhnv.model.request.BookRequest;

public interface IBookService {
	/**
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param descending
	 * @param sortBy
	 * @return
	 * @throws Exception
	 */
	public Page<Book> findAll(int pageNo, int pageSize, boolean descending, String sortBy) throws Exception;

	/**
	 * 
	 * @param bookRequest
	 * @return
	 */
	public Page<Book> findAll(BookRequest bookRequest);

	/**
	 * 
	 * @param bookRequest
	 * @return
	 */
	public Page<Author> findBookByAuthor(BookRequest bookRequest);

	/**
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	public BookRate rate(Long id, BookRateRequest request);

	/**
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	public Page<BookRate> getAllRates(Long id, BookRatePageRequest request);

	/**
	 * 
	 * @param name
	 * @return
	 */
	public Book findBookByName(String name);

	/**
	 * 
	 * @param id
	 * @return
	 */
	public Book findBookById(Long id);

	/**
	 * 
	 * @param bookCreateRequest
	 * @return
	 */
	public Book newBook(BookCreateRequest bookCreateRequest);

	/**
	 * 
	 * @param id
	 * @return
	 */
	public Book findOne(Long id);

	/**
	 * 
	 * @param id
	 * @param bookCreateRequest
	 * @return
	 */
	public Book saveOrUpdate(Long id, BookCreateRequest bookCreateRequest);

	/**
	 * 
	 * @param id
	 */
	public void deleteBook(Long id);
	
	/**
	 * 
	 * @return
	 */
	public List<Book> getNewBooks();
	
	/**
	 * 
	 * @return
	 */
	public List<Book> getBestSellerBooks();
	
	/**
	 * 
	 * @return
	 */
	public List<Book> getMostPopularBooks();
}
