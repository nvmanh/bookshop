package com.manhnv.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.manhnv.StartBookApplication;
import com.manhnv.common.PathConsts;
import com.manhnv.common.ResponseConst;
import com.manhnv.model.Status;
import com.manhnv.model.request.BookCreateRequest;
import com.manhnv.model.request.BookRatePageRequest;
import com.manhnv.model.request.BookRateRequest;
import com.manhnv.model.request.BookRequest;
import com.manhnv.utils.TextUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = StartBookApplication.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BookControllerTest {
	private String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTU4MzMxOTkwMiwiaWF0IjoxNTgzMzAxOTAyfQ.RhR0rDON9wD-po05NzCPwemBbFJepKFaKg3XSeg5zcZMr9O_TdXPQCyh6LVRmflg2LUVDCvZWCVjDb-Z7ZBDyg";
	private long bookId = 1;
	private long bookIdDelete = 2;

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext wac;

	@Before
	public void setUp() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
		this.mockMvc.perform(MockMvcRequestBuilders.post(PathConsts.v1.CREATE_SAMPLE)).andReturn();
	}

	@Test
	public void test_findAll() throws Exception {
		// without body
		this.mockMvc
				.perform(MockMvcRequestBuilders.get(PathConsts.v1.BOOK).contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.data").isNotEmpty()).andReturn();

		// with body
		BookRequest bookRequest = new BookRequest();
		bookRequest.setSortBy("price");
		// sort by price
		this.mockMvc
				.perform(MockMvcRequestBuilders.get(PathConsts.v1.BOOK, bookRequest)
						.content(TextUtils.asJsonString(bookRequest)).contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.data").isNotEmpty()).andReturn();

		bookRequest.setAuthor("Jack");
		// find by author contain keyword
		this.mockMvc
				.perform(MockMvcRequestBuilders.get(PathConsts.v1.BOOK, bookRequest)
						.content(TextUtils.asJsonString(bookRequest)).contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.data").isNotEmpty()).andReturn();

		bookRequest.setStatus(Status.INSTOCK);
		this.mockMvc
				.perform(MockMvcRequestBuilders.get(PathConsts.v1.BOOK, bookRequest)
						.content(TextUtils.asJsonString(bookRequest)).contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.data").isNotEmpty()).andReturn();

		bookRequest.setStatus(Status.REMOVED);
		// find by status: REMOVED
		this.mockMvc
				.perform(MockMvcRequestBuilders.get(PathConsts.v1.BOOK, bookRequest)
						.content(TextUtils.asJsonString(bookRequest)).contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.data").isNotEmpty()).andReturn();

		bookRequest = new BookRequest();
		bookRequest.setStatus(Status.INSTOCK);
		// find by status: IN-STOCK
		this.mockMvc
				.perform(MockMvcRequestBuilders.get(PathConsts.v1.BOOK, bookRequest)
						.content(TextUtils.asJsonString(bookRequest)).contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.data").isNotEmpty()).andReturn();
	}

	@Test
	public void test_rateBook() throws Exception {
		BookRateRequest request = new BookRateRequest();
		request.setRate(4.1);
		request.setComment("from test");
		request.setUserId(1L);

		this.mockMvc
				.perform(MockMvcRequestBuilders.post(PathConsts.v1.BOOK_RATE, bookId)
						.content(TextUtils.asJsonString(request)).contentType(MediaType.APPLICATION_JSON)
						.header("Authorization", "Bearer " + token).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.data").isNotEmpty()).andReturn();
	}

	@Test
	public void test_viewAllRates() throws Exception {
		// without body data
		this.mockMvc
				.perform(MockMvcRequestBuilders.get(PathConsts.v1.BOOK_RATE, bookId)
						.contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + token)
						.accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.data").isNotEmpty()).andReturn();

		// with page body data
		BookRatePageRequest request = new BookRatePageRequest();
		this.mockMvc
				.perform(MockMvcRequestBuilders.get(PathConsts.v1.BOOK_RATE, bookId)
						.content(TextUtils.asJsonString(request)).contentType(MediaType.APPLICATION_JSON)
						.header("Authorization", "Bearer " + token).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.data").isNotEmpty()).andReturn();
	}

	@Test
	public void test_newBook() throws Exception {
		BookCreateRequest request = new BookCreateRequest();
		request.setName("Test book");
		request.setPrice(120.0);
		request.setAuthorId(1L);
		this.mockMvc
				.perform(MockMvcRequestBuilders.post(PathConsts.v1.BOOK).content(TextUtils.asJsonString(request))
						.contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + token)
						.accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
				.andExpect(jsonPath("$.data").isNotEmpty()).andReturn();
	}

	@Test
	public void test_findOne() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.get(PathConsts.v1.BOOK_DETAIL, bookId)
						.contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + token)
						.accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.data").isNotEmpty()).andReturn();
	}

	@Test
	public void test_saveOrUpdate() throws Exception {
		// create new book
		BookCreateRequest request = new BookCreateRequest();
		request.setName("Test book");
		request.setPrice(120.0);
		request.setAuthorId(1L);
		request.setId(20L);
		this.mockMvc
				.perform(MockMvcRequestBuilders.put(PathConsts.v1.BOOK_DETAIL, 20)
						.content(TextUtils.asJsonString(request)).contentType(MediaType.APPLICATION_JSON)
						.header("Authorization", "Bearer " + token).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
				.andExpect(jsonPath("$.data").isNotEmpty()).andReturn();
		// update exist book
		request.setId(bookId);
		this.mockMvc
				.perform(MockMvcRequestBuilders.put(PathConsts.v1.BOOK_DETAIL, bookId)
						.content(TextUtils.asJsonString(request)).contentType(MediaType.APPLICATION_JSON)
						.header("Authorization", "Bearer " + token).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
				.andExpect(jsonPath("$.data").isNotEmpty()).andReturn();
	}

	@Test
	public void test_deleteBook() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.delete(PathConsts.v1.BOOK_DETAIL, bookIdDelete)
						.contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + token)
						.accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
				.andExpect(jsonPath("$.data").value(ResponseConst.DELETE_SUCCESSFUL)).andReturn();
	}
}
