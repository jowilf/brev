package com.brev.urlservice.controller;

import com.brev.core.domain.Role;
import com.brev.core.jwt.JwtTokenService;
import com.brev.core.jwt.TokenInfo;
import com.brev.urlservice.ContainerManager;
import com.brev.urlservice.domain.document.UrlDocument;
import com.brev.urlservice.service.CacheService;
import jakarta.annotation.PostConstruct;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Import(ContainerManager.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ApiControllerTest {
    public static MockWebServer mockBackEnd;
    private static String accessToken;
    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    CacheService cacheService;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private JwtTokenService jwtTokenService;

    @BeforeAll
    static void setUp() throws IOException {
        mockBackEnd = new MockWebServer();
        mockBackEnd.start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockBackEnd.shutdown();
    }

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("app.kgs-base-url", () -> "http://localhost:%s".formatted(mockBackEnd.getPort()));
        registry.add("spring.kafka.bootstrap-servers", () -> ContainerManager.kafka.getBootstrapServers());
        registry.add("spring.data.mongodb.uri", () -> ContainerManager.mongo.getConnectionString());
    }

    @BeforeEach
    void setup() {
        accessToken = jwtTokenService.generateToken(new TokenInfo(1, "user@example.com", Role.USER));
    }


    @Test
    @Order(1)
    void shortenUrl() throws Exception {
        mockBackEnd.enqueue(new MockResponse().setBody("y1bctgl"));
        String jsonContent = "{\"originalUrl\": \"https://me.jowilf.com/\"}";
        mvc.perform(post("/api/v1/urls").header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON).content(jsonContent)).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.shortUrl").isNotEmpty())
                .andExpect(jsonPath("$.originalUrl").value("https://me.jowilf.com/"))
                .andExpect(jsonPath("$.path").value("y1bctgl"));
    }

    @Test
    @Order(2)
    void goToShortenUrl() throws Exception {
        mvc.perform(get("/y1bctgl"))
                .andExpect(status().is(302))
                .andExpect(header().string("location", "https://me.jowilf.com/"));
    }

    @Test
    @Order(3)
    void customShortUrl() throws Exception {
        String jsonContent = "{\"originalUrl\": \"https://me.jowilf.com/\",\"customShortUrl\": \"me\"}";
        mvc.perform(post("/api/v1/urls").header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON).content(jsonContent)).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.shortUrl").isNotEmpty())
                .andExpect(jsonPath("$.originalUrl").value("https://me.jowilf.com/"))
                .andExpect(jsonPath("$.path").value("me"));
    }

    @Test
    @Order(4)
    void goTocustomShortUrl() throws Exception {
        mvc.perform(get("/me"))
                .andExpect(status().is(302))
                .andExpect(header().string("location", "https://me.jowilf.com/"));
    }
}