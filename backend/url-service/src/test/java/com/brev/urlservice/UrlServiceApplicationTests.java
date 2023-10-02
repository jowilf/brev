package com.brev.urlservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@SpringBootTest
@Import(ContainerManager.class)
@ActiveProfiles("test")
class UrlServiceApplicationTests {
    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", ContainerManager.kafka::getBootstrapServers);
        registry.add("spring.data.mongodb.uri", () -> ContainerManager.mongo.getConnectionString());
    }

    @Test
    void contextLoads() {
    }

}
