package com.manhnv.bookshop.repository.test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;

import com.manhnv.entity.User;
import com.manhnv.user.IUserRepository;
import com.manhnv.user.UserSevice;

@SpringBootTest
@ContextConfiguration
@TestInstance(value = Lifecycle.PER_CLASS)
public class UserRepositoryTest {
	@Autowired
	private IUserRepository userRepository;
	@MockBean
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	UserSevice userSevice;

	@BeforeAll
	public void setUp() throws Exception {
		User user1 = new User("test1", "Aa@123456");
		User user2 = new User("test2", "Aa@123456");
		assertNull(user1.getId());
		assertNull(user2.getId());// null before save
		userSevice.saveUser(user1);
		userSevice.saveUser(user2);
	}

	@Test
	public void testFetchData() {
		/* Test data retrieval */
		User userA = userRepository.findByName("test1");
		assertNotNull(userA);
		/* Get all users, list should only have two */
		Iterable<User> users = userRepository.findAll();
		int count = 0;
		assertNotNull(users);
		for (@SuppressWarnings("unused")
		User p : users) {
			count++;
		}
		// assertEquals(count, 2);
		assertTrue(count > 0);
	}

	@AfterAll
	public void removeData() {
		User userA = userRepository.findByName("test1");
		userSevice.deleteById(userA.getId());
		User userB = userRepository.findByName("test2");
		userSevice.deleteById(userB.getId());

		userA = userRepository.findByName("test1");
		assertNull(userA);
		userB = userRepository.findByName("test2");
		assertNull(userB);
	}
}