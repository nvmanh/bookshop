package com.manhnv.controller;
import static org.hamcrest.CoreMatchers.equalTo;
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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = StartBookApplication.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CustomErrorControllerTest {
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext wac;

	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}
	
	@Test
	public void test_onError() throws Exception{
		this.mockMvc.perform(MockMvcRequestBuilders.get(PathConsts.v1.ERROR).accept(MediaType.APPLICATION_JSON))
		.andDo(print()).andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(jsonPath("$.message", equalTo(ResponseConst.MESSAGE_OK))).andReturn();
	}
	
	@Test
	public void test_onApiError() throws Exception{
		this.mockMvc.perform(MockMvcRequestBuilders.get(PathConsts.v1.API_ERROR).accept(MediaType.APPLICATION_JSON))
		.andDo(print()).andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(jsonPath("$.message", equalTo(ResponseConst.MESSAGE_500))).andReturn();
	}
}
