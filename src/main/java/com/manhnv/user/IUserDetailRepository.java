package com.manhnv.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.manhnv.entity.UserDetail;

public interface IUserDetailRepository extends CrudRepository<UserDetail, Long> {
//	@Query(value = "SELECT * from tbl_user_detail where id = :id", nativeQuery = true)
//	UserDetail findUserDetailById(@Param("id") Long id);

	@Query(value = "select * from tbl_user_detail where user_id = :id", nativeQuery = true)
	UserDetail findUserDetailByUserId(@Param("id") Long id);
}
