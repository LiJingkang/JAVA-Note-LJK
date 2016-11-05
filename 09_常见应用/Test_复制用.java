package com.newings.controller;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.newings.BaseControllerTest;
import com.newings.entity.model.Meeting;

/**
 * 
 * @author LJK
 *
 */
public class TestXXXXX extends BaseControllerTest {

  private static final String ACCEPT_JSON = "application/json;charset=utf-8";

  @Autowired
  private WebApplicationContext wac;
  private MockMvc mockMvc;

  @Before
  public void setUp() {
    mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
  }

  @Test
  public void test() throws Exception {
    String url = "XXXXXX";

    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.set("XXXXXX", "XXXXXXXX");
    params.set("XXXXXX", "XXXXXX");
    params.set("XXXXXX", "XXXXXX");

    RequestBuilder requestBuilder =
        MockMvcRequestBuilders.get(url).params(params).accept(ACCEPT_JSON);
    MvcResult result = mockMvc.perform(requestBuilder).andReturn();

    int status = result.getResponse().getStatus();
    if (status == HttpStatus.SC_OK) {
      String json = result.getResponse().getContentAsString();
      ObjectMapper objectMapper = new ObjectMapper();

      JavaType javaType =
          objectMapper.getTypeFactory().constructParametricType(ArrayList.class, XXXXXX.class);
      @SuppressWarnings("unchecked")
      List<XXXXXX> XXXXXX = (ArrayList<XXXXXX>) objectMapper.readValue(json, javaType);

      if (XXXXXX != null) {
        for (XXXXXX XXXXXX : XXXXXX) {
          System.out.println(XXXXXX.toString());
        }
      }
      if (XXXXXX.isEmpty()) {
        System.out.println("response is null");
        fail("response is null");
      }
    } else {
      System.out.println("response status is not 200");
      fail("response status is not 200");
    } 
  }
}


