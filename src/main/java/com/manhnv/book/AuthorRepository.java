package com.manhnv.book;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.manhnv.entity.Author;

public interface AuthorRepository extends CrudRepository<Author, Long> {
	@Query("select a from tbl_author a left join fetch a.followers f where f.id = :id")
	public List<Author> findAuthorsByFollowers(@Param("id") Long followerId);

	@Query("select a from tbl_author a where a.name like %:name%")
	public Page<Author> findByName(@Param("name") String name, Pageable pageable);

	public Page<Author> findAll(Pageable pageable);
}
