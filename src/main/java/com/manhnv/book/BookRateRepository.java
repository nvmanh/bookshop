package com.manhnv.book;

import javax.websocket.server.PathParam;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.manhnv.entity.BookRate;

public interface BookRateRepository extends CrudRepository<BookRate, Long> {
	@Query("select br from tbl_book_rate br left join br.book b where b.id = :bookId")
	public Page<BookRate> findAllByBookId(@PathParam("bookId") Long bookId, Pageable pageable);

	@Query("select br from tbl_book_rate br left join br.book b left join br.user u where b.id = :bookId and u.id = :userId")
	public Page<BookRate> findAllByBookIdAndUserId(@PathParam("bookId") Long bookId, @PathParam("userId") Long userId,
			Pageable pageable);
}
