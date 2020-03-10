package com.manhnv.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;

import com.manhnv.entity.Author;
import com.manhnv.entity.Book;
import com.manhnv.entity.Role;
import com.manhnv.entity.User;
import com.manhnv.entity.UserDetail;
import com.manhnv.entity.Vote;
import com.manhnv.model.RoleName;
import com.manhnv.model.dto.AuthorDTO;
import com.manhnv.model.dto.UserDTO;
import com.manhnv.model.dto.VoteDTO;
import com.manhnv.model.request.BookCreateRequest;
import com.manhnv.user.IUserDetailRepository;
import com.manhnv.user.IUserRepository;
import com.manhnv.user.UserSevice;

@SpringBootTest
@ContextConfiguration
@TestInstance(value = Lifecycle.PER_CLASS)
public class DTOConverterTest {

	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private IUserRepository userRepository;
	@MockBean
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	UserSevice userSevice;
	@Autowired
	private IUserDetailRepository userDetailRepository;

	@BeforeAll
	public void setup() throws Exception {
		User user = new User();
		user.setName("admin");
		user.setPassword(passwordEncoder.encode("Aa@123456"));
		userRepository.save(user);
		UserDetail detail = new UserDetail();
		detail.setAddress1("147 Hoang Quoc Viet, Cau Giay, Ha Noi");
		detail.setAge(31);
		detail.setFullName("Nguyen Viet Manh");
		detail.setName("admin");
		detail.setPhone1("0944014295");
		detail.setUser(user);
		userDetailRepository.save(detail);
	}

	@Test
	public void test_convertAuthorDTO() {
		String authorName = "manhnv";
		Author author = new Author(authorName);
		assertThat(modelMapper).isNotNull();
		AuthorDTO dto = DTOConverter.convertAuthorDTO(author, modelMapper);
		assertTrue(authorName == dto.getName(), "OK");
	}

	@Test
	public void test_convertUserDTO() {
		User user = new User();
		user.setName("admin");
		user.setPassword(passwordEncoder.encode("Aa@123456"));
		UserDTO userDTO = DTOConverter.convertUserDTO(user, modelMapper);
		assertThat(userDTO).isNotNull().matches(v -> v.getName() == "admin", "test_convertUserDTO");
	}

	@Test
	public void test_convertVoteDTO() {
		Vote vote = new Vote();
		Author author = new Author("author");
		UserDetail detail = new UserDetail();
		detail.setAddress1("147 Hoang Quoc Viet, Cau Giay, Ha Noi");
		detail.setAge(31);
		detail.setFullName("Nguyen Viet Manh");
		detail.setName("admin");
		detail.setPhone1("0944014295");
		detail.addRole(new Role(RoleName.ROLE_ADMIN.name()));
		vote.setAuthor(author);
		vote.setUser(detail);
		vote.setVoted(true);
		vote.setId(100L);
		VoteDTO voteDTO = DTOConverter.convertVoteDTO(vote, modelMapper);
		assertThat(voteDTO).isNotNull().matches(v -> v.getId() == 100L, "test_convertVoteDTO");
	}
	
	@Test
	public void test_convertBook() {
		BookCreateRequest request = new BookCreateRequest();
		request.setName("Test book");
		request.setPrice(120.0);
		request.setAuthorId(1L);
		request.setId(20L);
		
		Book book = DTOConverter.convertBook(request, modelMapper);
		assertThat(book).isNotNull().matches(b -> b.getId() == 20L, "test_convertBook");
	}
}
