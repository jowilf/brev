package com.brev.usersservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
class AuthenticationFlowTest {

    private static String accessToken;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Order(1)
    void registerSimpleUser() throws Exception {
        String jsonContent = """
                {
                    "firstName": "John",
                    "lastName": "Doe",
                    "username": "user@example.com",
                    "password": "password"
                }
                """;
        mvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.username").value("user@example.com"))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.password").doesNotExist())
                .andExpect(jsonPath("$.role").value("USER"));
    }

    @Test
    @Order(2)
    void login() throws Exception {
        String jsonContent = """
                {
                    "username": "user@example.com",
                    "password": "password"
                }
                """;
        String response = mvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.accessToken").isNotEmpty())
                .andReturn()
                .getResponse().getContentAsString();
        accessToken = objectMapper
                .readTree(response).get("accessToken").asText();
    }


    @Test
    @Order(3)
    void getProfile() throws Exception {
        mvc.perform(get("/api/v1/users/me")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + accessToken)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.username").value("user@example.com"))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.password").doesNotExist())
                .andExpect(jsonPath("$.role").value("USER"));
    }

    @Test
    @Order(4)
    void updateProfile() throws Exception {
        String jsonContent = """
                {
                    "firstName": "John edit",
                    "lastName": "Doe edit"
                }
                """;
        mvc.perform(put("/api/v1/users/me")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.username").value("user@example.com"))
                .andExpect(jsonPath("$.firstName").value("John edit"))
                .andExpect(jsonPath("$.lastName").value("Doe edit"))
                .andExpect(jsonPath("$.password").doesNotExist())
                .andExpect(jsonPath("$.role").value("USER"));
    }

    @Test
    @Order(5)
    void testDuplicateUser() throws Exception {
        String jsonContent = """
                {
                    "firstName": "John",
                    "lastName": "Doe",
                    "username": "user@example.com",
                    "password": "password"
                }
                """;
        mvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().is(409))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("This username already exist"));
    }

    @ParameterizedTest
    @Order(6)
    @ValueSource(strings = {"{ \"username\":\"user@invalid.com\",\"password\":\"password\"}",
            "{ \"username\":\"user@example.com\",\"password\":\"invalidpassword\"}"})
    void invalidLoginCredentials(String content) throws Exception {
        mvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
                )
                .andExpect(status().is(404))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Invalid username or password"));
    }
}
