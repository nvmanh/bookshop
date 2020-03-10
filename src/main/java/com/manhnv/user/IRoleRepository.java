package com.manhnv.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.manhnv.entity.Role;

public interface IRoleRepository extends CrudRepository<Role, Integer> {
	@Query(value = "select * from tbl_role where name = :name", nativeQuery = true)
	Role findByName(@Param("name") String name);
}
