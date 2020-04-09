package com.manhnv.controller;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.manhnv.book.AuthorRepository;
import com.manhnv.book.BookRepository;
import com.manhnv.common.Const;
import com.manhnv.common.PathConsts;
import com.manhnv.config.Translator;
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
		// add user profile
		UserDetail detail = new UserDetail();
		detail.setAddress1("147 Hoang Quoc Viet, Cau Giay, Ha Noi");
		detail.setAge(31);
		detail.setFullName("Nguyen Viet Manh");
		detail.setName("admin");
		detail.setPhone1("0944014295");
		detail.addRole(adminRole);
		detail.setUser(user);
		user.setProfile(detail);
		userRepository.save(user);
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
		book1.setCover("https://images-na.ssl-images-amazon.com/images/I/41ixwox%2BUDL._SX331_BO1,204,203,200_.jpg");
		book1.setThump("https://images-na.ssl-images-amazon.com/images/I/51GHpI9S5%2BL._SX305_BO1,204,203,200_.jpg");
		bookRepository.save(book1);
		Book book2 = new Book("Song", xq, new BigDecimal(27.23));
		book2.setThump("https://a.wattpad.com/cover/76090402-288-k676860.jpg");
		book2.setCover(
				"https://lh3.googleusercontent.com/proxy/w3pf8I23Tptrj0cNqy7YXIHFCU_xxnpUFcrh6umJxVFJvkOKsBP1r8B6fgupWR2aoPXcoqwtwLFfJ80lnPe4gpOwvHpv4bWmLcwttRhRCkFnsRd4IDg-MVZPoiMG1S_gQjMQn_w_9McE7LBkdmaHw4gFvQ");
		bookRepository.save(book2);
		Book book = new Book("The Hunger Games (The Hunger Games, #1)", suzunne, new BigDecimal(10003.5),
				Status.DELETED);
		book.setCover("https://i.pinimg.com/originals/97/88/22/978822885b33114fd721626c6396a6c4.png");
		bookRepository.save(book);
		book = new Book("Catching Fire (The Hunger Games, #2)", suzunne, new BigDecimal(10003.5), Status.OUTSTOCK);
		book.setCover("https://image.phimmoi.net/film/2805/poster.medium.jpg");
		bookRepository.save(book);
		book = new Book("Harry Potter Series Box Set (Harry Potter, #1-7)", rowling, new BigDecimal(10003.5),
				Status.REMOVED);
		book.setCover("https://images-na.ssl-images-amazon.com/images/I/51HSkTKlauL._SX346_BO1,204,203,200_.jpg");
		bookRepository.save(book);
		book = new Book("Mockingjay (The Hunger Games, #3)", suzunne, new BigDecimal(10003.5));
		book.setCover(
				"https://kbimages1-a.akamaihd.net/0b5c9521-159f-4500-b937-c9688bbcbe3d/1200/1200/False/mockingjay-the-final-book-of-the-hunger-games.jpg");
		bookRepository.save(book);
		book = new Book("Harry Potter and the Sorcerer's Stone (Harry Potter, #1)", rowling, new BigDecimal(10003.5));
		book.setCover("https://m.media-amazon.com/images/I/41lnLrvBnML.jpg");
		bookRepository.save(book);
		book = new Book("Twilight (Twilight, #1)", meyer, new BigDecimal(10003.5));
		book.setCover("https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1361039443l/41865.jpg");
		bookRepository.save(book);
		book = new Book("To Kill a Mockingbird (To Kill a Mockingbird, #1)", lee, new BigDecimal(10003.5));
		book.setCover(
				"https://www.kingstongrand.ca/sites/kingstongrand.ca/files/styles/large/public/img/event/hero/DominoKillAMockingBird.jpg?itok=XmsXANi0");
		bookRepository.save(book);
		book = new Book("Pride and Prejudice", austeen, new BigDecimal(10003.5));
		book.setCover("https://m.media-amazon.com/images/M/MV5BMTA1NDQ3NTcyOTNeQTJeQWpwZ15BbWU3MDA0MzA4MzE@._V1_.jpg");
		bookRepository.save(book);
		book = new Book("The Hobbit, or There and Back Again", tolken, new BigDecimal(10003.5));
		book.setCover("https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1546071216l/5907.jpg");
		bookRepository.save(book);
		book = new Book("Breaking Dawn (Twilight, #4)", meyer, new BigDecimal(10003.5));
		book.setCover("https://images-na.ssl-images-amazon.com/images/I/51DUJ9xNG0L._SX307_BO1,204,203,200_.jpg");
		bookRepository.save(book);
		book = new Book("The Book Thief", zusak, new BigDecimal(10003.5));
		book.setCover("https://images-na.ssl-images-amazon.com/images/I/9123eop9gIL.jpg");
		bookRepository.save(book);
		book = new Book("Divergent (Divergent, #1)", roth, new BigDecimal(10003.5));
		book.setCover("https://images-na.ssl-images-amazon.com/images/I/51x%2Bv3WfFNL.jpg");
		bookRepository.save(book);
		book = new Book("Eclipse (Twilight, #3)", meyer, new BigDecimal(10003.5));
		book.setCover("https://3.bp.blogspot.com/_O5GTW8d1sx0/TEgBkjMqOuI/AAAAAAAABLE/vgyTN6ui0_M/s1600/Twilight-Eclipse.jpg");
		bookRepository.save(book);
		book = new Book("New Moon (Twilight, #2)", meyer, new BigDecimal(10003.5));
		book.setCover("https://phimonl.tv/wp-content/uploads/2018/03/Chang-Vang-2-Tr-96516.jpg");
		bookRepository.save(book);
		JwtResponse<Object> jwtResponse = new JwtResponse<Object>().onSuccess(Const.CREATE_SAMPLE_SUCCESS,
				PathConsts.v1.CREATE_SAMPLE);
		jwtResponse.setMessage(Const.CREATE_SAMPLE_SUCCESS);
		return jwtResponse;
	}

	@GetMapping(path = PathConsts.v1.CURRENT_LANGUAGE)
	@ResponseBody
	public JwtResponse<Object> checkLanguage() {
		return new JwtResponse<Object>().onSuccess(Translator.toLocale("welcome"), PathConsts.v1.CURRENT_LANGUAGE);
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
