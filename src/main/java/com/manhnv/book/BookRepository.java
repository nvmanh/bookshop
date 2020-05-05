package com.manhnv.book;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import com.manhnv.entity.Book;
import com.manhnv.model.Status;

@RestController
@Validated
public interface BookRepository extends JpaRepository<Book, Long> {
	Page<Book> findAll(Pageable pageable);

	@Query("select b from tbl_book b left join b.author a where a.name like %:author%")
	Page<Book> findAllByAuthor(@Param("author") String author, Pageable pageable);

	Page<Book> findBookByAuthorName(String authorName, Pageable pageable);

	Optional<Book> findByName(String name);

	@Query("select b from tbl_book b left join b.author a where a.name like %:author% and b.status = :status")
	Page<Book> findAllByAuthorAndStatus(@Param("author") String author, @Param("status") Status status,
			Pageable pageable);

	@Query("select b from tbl_book b where b.status = :status")
	Page<Book> findAllByStatus(@Param("status") Status status, Pageable pageable);
	
	@Query("select b from tbl_book b where b.isNew = 1")
	List<Book> findNewBooks();
	
	@Query("select b from tbl_book b where b.isMostPopular = 1")
	List<Book> findMostPopularBooks(); 
	
	@Query("select b from tbl_book b where b.isBestSeller = 1")
	List<Book> findBestSellerBooks();
}
