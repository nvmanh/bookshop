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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import com.manhnv.StartBookApplication;
import com.manhnv.common.PathConsts;
import com.manhnv.model.request.AuthorChangeRequest;
import com.manhnv.model.request.BasePageRequest;
import com.manhnv.model.request.VoteRequest;
import com.manhnv.utils.TextUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = StartBookApplication.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AuthorControllerTest {

	private String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTU4MzQxNDE3NSwiaWF0IjoxNTgzMzk2MTc1fQ.iw8yPmffyKs7WmDB6F7zfYv_ma_r0IXhrHwQVBbw47n_qv7glxGqcfJ7Uj_LZQUGGM_E7mkYMGgvj7Oy3ZZd_w";
	private long authorId = 1;
	private long userDetailId = 1;

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
		// default paging
		this.mockMvc
				.perform(MockMvcRequestBuilders.get(PathConsts.v1.AUTHOR).contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + token))
				.andDo(print()).andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
				.andExpect(jsonPath("$.data").isNotEmpty()).andReturn();

		BasePageRequest request = new BasePageRequest();
		request.setPageSize(1);
		// with paging
		this.mockMvc
				.perform(MockMvcRequestBuilders.get(PathConsts.v1.AUTHOR).content(TextUtils.asJsonString(request))
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
						.header("Authorization", "Bearer " + token))
				.andDo(print()).andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
				.andExpect(jsonPath("$.data").isNotEmpty()).andReturn();
	}

	@Test
	public void test_update() throws Exception {
		AuthorChangeRequest request = new AuthorChangeRequest();
		request.setName("ManhNV");

		this.mockMvc
				.perform(MockMvcRequestBuilders.post(PathConsts.v1.AUTHOR_DETAIL, authorId)
						.content(TextUtils.asJsonString(request)).contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + token))
				.andDo(print()).andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
				.andExpect(jsonPath("$.data").isNotEmpty()).andReturn();
	}

	@Test
	public void test_1_vote() throws Exception {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("user_id", String.valueOf(userDetailId));
		params.add("voted", String.valueOf(true));

		this.mockMvc
				.perform(MockMvcRequestBuilders.post(PathConsts.v1.AUTHOR_VOTE, authorId).queryParams(params)
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
						.header("Authorization", "Bearer " + token))
				.andDo(print()).andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
				.andExpect(jsonPath("$.data").isNotEmpty()).andReturn();
	}

	@Test
	public void test_2_viewVotes() throws Exception {
		VoteRequest request = new VoteRequest();
		request.setUserId(userDetailId);
		request.setAuthorId(authorId);
		request.setVoted(true);
		this.mockMvc
				.perform(MockMvcRequestBuilders.get(PathConsts.v1.VOTE).content(TextUtils.asJsonString(request))
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
						.header("Authorization", "Bearer " + token))
				.andDo(print()).andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
				.andExpect(jsonPath("$.data").isNotEmpty()).andReturn();
	}
}
