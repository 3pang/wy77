package com.lenovo.wy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.lenovo.wy.controller.IndexController;

@SpringBootTest
class Wy77t1ApplicationTests {

	@Autowired
	MockMvc mockMvc;
//	@Test
//	void contextLoads() {
//	}
    @Test
    public void IndexControllerTest() throws Exception {
    	mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.GET, "/inn"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Daniel Say Hello Spring Boot!"));
    }
}
