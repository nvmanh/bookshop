package com.manhnv.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.manhnv.entity.User;

@Repository
public interface IUserRepository extends CrudRepository<User, Integer> {
	@Query(value = "SELECT * from tbl_user where name = :name", nativeQuery = true)
	User findByName(@Param("name") String name);

	@Query(value = "SELECT * from tbl_user where id LIKE :id", nativeQuery = true)
	User findUserById(@Param("id") Long id);

	@Query(value = "SELECT * FROM tbl_user t where t.name = :name AND t.password = :password", nativeQuery = true)
	public User findByNameAndPassword(@Param("name") String name, @Param("password") String password);

	Page<User> findAll(Pageable pageable);

	void deleteById(Long id);

	User findById(Long id);

	boolean existsByName(String name);

}
