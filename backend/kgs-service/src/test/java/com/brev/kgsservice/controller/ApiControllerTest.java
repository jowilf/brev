package com.brev.kgsservice.controller;

import com.brev.kgsservice.config.ZookeeperTestConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.oneOf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Import(ZookeeperTestConfiguration.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
class ApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void nextKey() throws Exception {
        mockMvc.perform(get("/api/v1/kgs/next")).andExpect(status().isOk())
                .andExpect(content().string(oneOf("yyyyyyd","yyyyyyG","yyyyyyB","yyyyyyH","yyyyyyZ")));
    }
}