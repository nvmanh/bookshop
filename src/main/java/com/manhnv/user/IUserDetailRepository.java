package com.manhnv.user;

import org.springframework.data.repository.CrudRepository;

import com.manhnv.entity.UserDetail;

public interface IUserDetailRepository extends CrudRepository<UserDetail, Long> {
//	@Query(value = "SELECT * from tbl_user_detail where id = :id", nativeQuery = true)
//	UserDetail findUserDetailById(@Param("id") Long id);
}
