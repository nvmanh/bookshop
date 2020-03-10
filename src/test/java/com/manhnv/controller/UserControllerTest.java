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
import com.manhnv.model.request.BasePageRequest;
import com.manhnv.model.request.UserChangeRequest;
import com.manhnv.utils.TextUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = StartBookApplication.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserControllerTest {
	private long userId = 1;
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext wac;

	@Before
	public void setUp() throws Exception{
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
		this.mockMvc.perform(MockMvcRequestBuilders.post(PathConsts.v1.CREATE_SAMPLE)).andReturn();
	}
	
	@Test
	public void test_users() throws Exception{
		this.mockMvc
		.perform(MockMvcRequestBuilders.get(PathConsts.v1.USER).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andDo(print()).andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(jsonPath("$.data").isNotEmpty()).andReturn();
		
		BasePageRequest request = new BasePageRequest();
		request.setPageSize(1);
		this.mockMvc
		.perform(MockMvcRequestBuilders.get(PathConsts.v1.USER).contentType(MediaType.APPLICATION_JSON)
				.content(TextUtils.asJsonString(request))
				.accept(MediaType.APPLICATION_JSON))
		.andDo(print()).andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(jsonPath("$.data").isNotEmpty()).andReturn();
	}
	
	@Test
	public void test_regist() throws Exception {
		UserChangeRequest request = new UserChangeRequest();
		request.setUsername("manhnv3");
		request.setAddress1("147 Hoang Quoc Viet, Nam Tu Liem, Ha Noi");
		request.setAddress2("Test");
		request.setAge(30);
		request.setPhone1("0944014295");
		request.setEmail1("abcds@gmail.com");
		request.setPassword("Aa@123456");
		this.mockMvc
		.perform(MockMvcRequestBuilders.post(PathConsts.v1.USER_REGIST).contentType(MediaType.APPLICATION_JSON)
				.content(TextUtils.asJsonString(request))
				.accept(MediaType.APPLICATION_JSON))
		.andDo(print()).andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
		.andExpect(jsonPath("$.data").isNotEmpty()).andReturn();
	}
	
	@Test
	public void test_updateUserInfo() throws Exception{
		UserChangeRequest request = new UserChangeRequest();
		request.setAddress1("147 Hoang Quoc Viet, Nam Tu Liem, Ha Noi");
		request.setAddress2("Test");
		request.setPhone1("0944014295");
		this.mockMvc
		.perform(MockMvcRequestBuilders.put(PathConsts.v1.USER_UPDATE, userId).contentType(MediaType.APPLICATION_JSON)
				.content(TextUtils.asJsonString(request))
				.accept(MediaType.APPLICATION_JSON))
		.andDo(print()).andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
	}
	
	@Test
	public void test_getFollowing() throws Exception{
		this.mockMvc
		.perform(MockMvcRequestBuilders.get(PathConsts.v1.USER_FOLLOWING, userId).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andDo(print()).andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(jsonPath("$.data").isNotEmpty()).andReturn();
	}
}
