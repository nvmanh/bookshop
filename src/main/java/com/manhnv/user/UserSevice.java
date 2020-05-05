package com.manhnv.user;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.manhnv.book.AuthorRepository;
import com.manhnv.common.Const;
import com.manhnv.config.JwtTokenUtil;
import com.manhnv.entity.Author;
import com.manhnv.entity.Privilege;
import com.manhnv.entity.Role;
import com.manhnv.entity.User;
import com.manhnv.entity.UserDetail;
import com.manhnv.error.ModelNotFoundException;
import com.manhnv.model.RoleName;
import com.manhnv.model.dto.UserDTO;
import com.manhnv.model.request.BasePageRequest;
import com.manhnv.model.request.UserChangeRequest;
import com.manhnv.model.request.UserCreateRequest;
import com.manhnv.utils.DTOConverter;
import com.manhnv.utils.RequestUtils;
import com.manhnv.utils.TextUtils;

@Service
//@CacheConfig(cacheNames = { "user" })
@Transactional
public class UserSevice implements IUserService {

	@Autowired(required = true)
	private IUserRepository userRepository;

	@Autowired
	private IRoleRepository iRoleRepository;

	@Autowired
	private IPrivilegeRepository privilegeRepository;

	@Autowired
	private IUserDetailRepository userDetailRepository;

	@Autowired
	private AuthorRepository authorRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private BCryptPasswordEncoder encoder;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Override
	// @CacheEvict("users")
	public Page<UserDTO> findAll(BasePageRequest request) {
		Pageable pageable = PageRequest.of(Const.DEFAULT_START_PAGE, Const.DEFAULT_PAGE_SIZE);
		if (request != null) {
			pageable = PageRequest.of(request.getPageNo(), request.getPageSize());
		}
		return userRepository.findAll(pageable).map(user -> DTOConverter.convertUserDTO(user, modelMapper));
	}

	@Override
	public void saveUser(User user) {
		user.setPassword(encoder.encode(user.getPassword()));
		userRepository.save(user);
	}

	@Override
	public UserDetail saveUser(UserCreateRequest user) {
		User mUser = new User();
		mUser.setName(user.getUsername());
		mUser.setPassword(encoder.encode(user.getPassword()));

		Privilege read = privilegeRepository.findByName("READ_PRIVILEGE");
		if (read == null)
			return null;
		Role role = iRoleRepository.findByName(RoleName.ROLE_USER.value());
		if (role == null) {
			return null;
		}
		Set<Privilege> normalPrivileges = new HashSet<Privilege>();
		normalPrivileges.add(read);
		role.setPrivileges(normalPrivileges);

		UserDetail profile = new UserDetail(user);
		profile.addRole(role);
		profile.setUser(mUser);
		mUser.setProfile(profile);
		userRepository.save(mUser);
		return profile;
	}

	@Override
	public UserDetail updateInfo(Long id, UserChangeRequest req) {
		if (req == null || id == null || id <= 0)
			return null;
		UserDetail u = userDetailRepository.findById(id)
				.orElseThrow(() -> new ModelNotFoundException(Const.TABLE_ALIAS_USER, id));
		if (req.getStatus() != null) {
			u.setStatus(req.getStatus());
		}
		if (req.getAddress1() != null) {
			u.setAddress1(req.getAddress1());
		}
		if (!TextUtils.isEmpty(req.getAddress2())) {
			u.setAddress2(req.getAddress2());
		}
		if (!TextUtils.isEmpty(req.getFullName())) {
			u.setFullName(req.getFullName());
		}
		if (!TextUtils.isEmpty(req.getPhone1())) {
			u.setPhone1(req.getPhone1());
		}
		if (!TextUtils.isEmpty(req.getPhone2())) {
			u.setPhone2(req.getPhone2());
		}
		if (req.getAge() > 0) {
			u.setAge(req.getAge());
		}
		if (req.getGender() != null) {
			u.setGender(req.getGender());
		}
		if (!TextUtils.isEmpty(req.getEmail1())) {
			u.setEmail1(req.getEmail1());
		}
		if (!TextUtils.isEmpty(req.getEmail2())) {
			u.setEmail2(req.getEmail2());
		}
		return userDetailRepository.save(u);
	}

	@Override
	// @CacheEvict("followings")
	public List<Author> getFollowings(Long id) {
		List<Author> authors = authorRepository.findAuthorsByFollowers(id);
		if (authors != null) {
			authors.stream().forEach(author -> author.hideFollowers());
		}
		return authors;
	}

	@Override
	public void deleteById(Long id) {
		userRepository.deleteById(id);
	}

	@Override
	public UserDetail getUserInfo(Long id) {
		if (id == null || id <= 0 || !userRepository.existsById(id))
			return null;
		return userDetailRepository.findUserDetailByUserId(id);
	}

	@Override
	public UserDetail getUserInfo(HttpServletRequest request) {
		String[] data = RequestUtils.getUserNameFromRequestToken(request, null, jwtTokenUtil);
		String username = data[0];
		if (TextUtils.isEmpty(username) || !userRepository.existsByName(username))
			return null;
		User user = userRepository.findByName(username);
		return userDetailRepository.findUserDetailByUserId(user.getId());
	}
}
