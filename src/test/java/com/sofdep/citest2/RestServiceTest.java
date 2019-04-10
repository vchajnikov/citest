package com.sofdep.citest2;

import com.sofdep.citest2.RestService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@WebMvcTest(RestService.class)
public class RestServiceTest {

  @Autowired
  private MockMvc mvc;

  @Test
  public void testHelloWorld() throws Exception {

    mvc.perform(
        MockMvcRequestBuilders
            .get("/hello/BABOON")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(
            MediaType.TEXT_PLAIN_VALUE + ";charset=UTF-8"))
        .andExpect(MockMvcResultMatchers.content().string("Hello BABOON"));
  }
}