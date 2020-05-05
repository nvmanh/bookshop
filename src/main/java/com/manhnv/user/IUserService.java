package com.manhnv.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;

import com.manhnv.entity.Author;
import com.manhnv.entity.User;
import com.manhnv.entity.UserDetail;
import com.manhnv.model.dto.UserDTO;
import com.manhnv.model.request.BasePageRequest;
import com.manhnv.model.request.UserChangeRequest;
import com.manhnv.model.request.UserCreateRequest;

public interface IUserService {
	/**
	 * 
	 * @param request
	 * @return
	 */
	Page<UserDTO> findAll(BasePageRequest request);

	/**
	 * 
	 * @param user
	 */
	void saveUser(User user);

	/**
	 * 
	 * @param user
	 * @return
	 */
	UserDetail saveUser(UserCreateRequest user);

	/**
	 * 
	 * @param id
	 * @param req
	 * @return
	 */
	UserDetail updateInfo(Long id, UserChangeRequest req);

	/**
	 * 
	 * @param id
	 * @return
	 */
	List<Author> getFollowings(Long id);

	/**
	 * 
	 * @param id
	 */
	void deleteById(Long id);

	/**
	 * 
	 * @param id
	 * @return
	 */
	UserDetail getUserInfo(Long id);
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	UserDetail getUserInfo(HttpServletRequest request);
}
