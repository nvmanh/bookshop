package com.manhnv.user;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.manhnv.entity.Privilege;

public interface IPrivilegeRepository extends CrudRepository<Privilege, Integer> {
	@Query(value = "select * from tbl_privilege where name = :name", nativeQuery = true)
	Privilege findByName(@Param("name") String name);
	
	@Override
    List<Privilege> findAll();
}
