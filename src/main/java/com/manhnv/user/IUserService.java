package com.manhnv.user;

import java.util.List;

import org.springframework.data.domain.Page;

import com.manhnv.entity.Author;
import com.manhnv.entity.User;
import com.manhnv.entity.UserDetail;
import com.manhnv.model.dto.UserDTO;
import com.manhnv.model.request.BasePageRequest;
import com.manhnv.model.request.UserChangeRequest;
import com.manhnv.model.request.UserCreateRequest;

public interface IUserService {
	Page<UserDTO> findAll(BasePageRequest request);

	void saveUser(User user) throws Exception;

	UserDetail saveUser(UserCreateRequest user) throws Exception;

	UserDetail updateInfo(Long id, UserChangeRequest req) throws Exception;

	List<Author> getFollowings(Long id);
	
	void deleteById(Long id);
}
