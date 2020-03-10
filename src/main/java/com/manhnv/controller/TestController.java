package com.manhnv.controller;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.manhnv.book.AuthorRepository;
import com.manhnv.book.BookRepository;
import com.manhnv.common.Const;
import com.manhnv.common.PathConsts;
import com.manhnv.entity.Author;
import com.manhnv.entity.Book;
import com.manhnv.entity.Privilege;
import com.manhnv.entity.Role;
import com.manhnv.entity.User;
import com.manhnv.entity.UserDetail;
import com.manhnv.entity.Vote;
import com.manhnv.model.Status;
import com.manhnv.model.response.JwtResponse;
import com.manhnv.service.VoteService;
import com.manhnv.user.IPrivilegeRepository;
import com.manhnv.user.IRoleRepository;
import com.manhnv.user.IUserDetailRepository;
import com.manhnv.user.IUserRepository;

@RestController
public class TestController {

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private IRoleRepository roleRepository;

	@Autowired
	private IPrivilegeRepository privilegeRepository;

	@Autowired
	private IUserDetailRepository userDetailRepository;

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private AuthorRepository authorRepository;

	@Autowired
	private VoteService voteService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	private boolean isNeedInitialData() {
		List<Privilege> privileges = privilegeRepository.findAll();
		return privileges == null || privileges.size() <= 0;
	}

	@PostMapping(path = PathConsts.v1.CREATE_SAMPLE)
	@Transactional
	@ResponseBody
	public JwtResponse<Object> create() {

		if (!isNeedInitialData()) {
			return new JwtResponse<Object>().onFail(HttpStatus.CONFLICT.value(), Const.CREATE_SAMPLE_SUCCESS,
					PathConsts.v1.CREATE_SAMPLE);
		}
		Privilege readPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE");
		Privilege writePrivilege = createPrivilegeIfNotFound("WRITE_PRIVILEGE");

		Set<Privilege> adminPrivileges = new HashSet<Privilege>();
		adminPrivileges.add(readPrivilege);
		adminPrivileges.add(writePrivilege);

		Set<Privilege> normalPrivileges = new HashSet<Privilege>();
		normalPrivileges.add(readPrivilege);

		createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
		createRoleIfNotFound("ROLE_USER", normalPrivileges);

		Role adminRole = roleRepository.findByName("ROLE_ADMIN");

		// add user
		User user = new User();
		user.setName("admin");
		user.setPassword(passwordEncoder.encode("Aa@123456"));
		userRepository.save(user);
		// add user profile
		UserDetail detail = new UserDetail();
		detail.setAddress1("147 Hoang Quoc Viet, Cau Giay, Ha Noi");
		detail.setAge(31);
		detail.setFullName("Nguyen Viet Manh");
		detail.setName("admin");
		detail.setPhone1("0944014295");
		detail.setUser(user);
		detail.addRole(adminRole);
		userDetailRepository.save(detail);
		// author
		Author jack = new Author("Jack London");
		jack.addFollowers(detail);
		authorRepository.save(jack);
		Author xq = new Author("Xuan Quynh");
		xq.addFollowers(detail);
		authorRepository.save(xq);
		Author suzunne = new Author("Suzanne Collins");
		authorRepository.save(suzunne);
		Author rowling = new Author("J.K. Rowling");
		authorRepository.save(rowling);
		Author meyer = new Author("Stephenie Meyer");
		authorRepository.save(meyer);
		Author tolken = new Author("J.R.R. Tolkien");
		authorRepository.save(tolken);
		Author lee = new Author("Harper Lee");
		authorRepository.save(lee);
		Author austeen = new Author("Jane Austen");
		authorRepository.save(austeen);
		Author zusak = new Author("Arkus Zusak");
		zusak.setRate(4.7);
		authorRepository.save(zusak);
		Author roth = new Author("Veronica Roth");
		roth.setRate(4.8);
		authorRepository.save(roth);

		// vote
		Vote v = new Vote(jack, detail, true);
		voteService.save(v);
		// book
		Book book1 = new Book("The call of wild", jack, new BigDecimal(100.23));
		bookRepository.save(book1);
		Book book2 = new Book("Song", xq, new BigDecimal(27.23));
		bookRepository.save(book2);
		bookRepository.save(new Book("The Hunger Games (The Hunger Games, #1)", suzunne, new BigDecimal(10003.5), Status.DELETED));
		bookRepository.save(new Book("Catching Fire (The Hunger Games, #2)", suzunne, new BigDecimal(10003.5), Status.OUTSTOCK));
		bookRepository
				.save(new Book("Harry Potter Series Box Set (Harry Potter, #1-7)", rowling, new BigDecimal(10003.5), Status.REMOVED));
		bookRepository.save(new Book("Mockingjay (The Hunger Games, #3)", suzunne, new BigDecimal(10003.5)));
		bookRepository.save(
				new Book("Harry Potter and the Sorcerer's Stone (Harry Potter, #1)", rowling, new BigDecimal(10003.5)));
		bookRepository.save(new Book("Twilight (Twilight, #1)", meyer, new BigDecimal(10003.5)));
		bookRepository
				.save(new Book("To Kill a Mockingbird (To Kill a Mockingbird, #1)", lee, new BigDecimal(10003.5)));
		bookRepository.save(new Book("Pride and Prejudice", austeen, new BigDecimal(10003.5)));
		bookRepository.save(new Book("The Hobbit, or There and Back Again", tolken, new BigDecimal(10003.5)));
		bookRepository.save(new Book("Breaking Dawn (Twilight, #4)", meyer, new BigDecimal(10003.5)));
		bookRepository.save(new Book("The Book Thief", zusak, new BigDecimal(10003.5)));
		bookRepository.save(new Book("Divergent (Divergent, #1)", roth, new BigDecimal(10003.5)));
		bookRepository.save(new Book("Eclipse (Twilight, #3)", meyer, new BigDecimal(10003.5)));
		bookRepository.save(new Book("New Moon (Twilight, #2)", meyer, new BigDecimal(10003.5)));
		JwtResponse<Object> jwtResponse = new JwtResponse<Object>().onSuccess(Const.CREATE_SAMPLE_SUCCESS, PathConsts.v1.CREATE_SAMPLE);
		jwtResponse.setMessage(Const.CREATE_SAMPLE_SUCCESS);
		return jwtResponse;
	}

	@Transactional
	private Privilege createPrivilegeIfNotFound(String name) {

		Privilege privilege = privilegeRepository.findByName(name);
		if (privilege == null) {
			privilege = new Privilege(name);
			privilegeRepository.save(privilege);
		}
		return privilege;
	}

	@Transactional
	private Role createRoleIfNotFound(String name, Set<Privilege> privileges) {

		Role role = roleRepository.findByName(name);
		if (role == null) {
			role = new Role(name);
			role.setPrivileges(privileges);
			roleRepository.save(role);
		}
		return role;
	}
}
