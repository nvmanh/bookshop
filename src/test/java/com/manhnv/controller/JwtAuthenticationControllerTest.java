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
import com.manhnv.model.request.JwtRequest;
import com.manhnv.utils.TextUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = StartBookApplication.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JwtAuthenticationControllerTest {
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext wac;

	@Before
	public void setUp() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
		this.mockMvc.perform(MockMvcRequestBuilders.post(PathConsts.v1.CREATE_SAMPLE)).andReturn();
	}

	@Test
	public void test_createAuthenticationToken() throws Exception {
		JwtRequest request = new JwtRequest();
		request.setUsername("admin");
		request.setPassword("Aa@123456");
		this.mockMvc
				.perform(MockMvcRequestBuilders.post(PathConsts.v1.AUTH).content(TextUtils.asJsonString(request))
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.data").isNotEmpty()).andReturn();
		request.setPassword(null);
		this.mockMvc
		.perform(MockMvcRequestBuilders.post(PathConsts.v1.AUTH).content(TextUtils.asJsonString(request))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print()).andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(jsonPath("$.data").isEmpty()).andReturn();
	}

	@Test
	public void test_logout() throws Exception {
		String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTU4MzMyNDQ2OCwiaWF0IjoxNTgzMzA2NDY4fQ.iCwMPD3cII4Cq21YJtrtlZha0J_Ec5kAorQDeQCAhAI2THK7wj1hifGLJzt1Gj8CIxCd3OXtuFZPY8zh5uF8mw";
		this.mockMvc
				.perform(MockMvcRequestBuilders.post(PathConsts.v1.LOGOUT).header("Authorization", "Bearer " + token)
						.accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.data").isNotEmpty()).andReturn();
	}
}
