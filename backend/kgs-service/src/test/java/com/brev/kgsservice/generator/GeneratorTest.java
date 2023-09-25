package com.brev.kgsservice.generator;

import org.apache.curator.framework.recipes.shared.SharedCount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest(
        {"spring.cloud.service-registry.auto-registration.enabled=false", "spring.cloud.zookeeper.enabled=false",
                "spring.cloud.discovery.enabled=false", "app.range-offset=5",
        })
@Import(GeneratorTest.Configuration.class)
class GeneratorTest {

    private static int RANGE_OFFSET = 5;

    @Autowired
    private SharedCount sharedCount;

    @Autowired
    private Generator generator;

    @BeforeEach
    void setUp() {
    }

    @Test
    void queueIsFilledAfterInitialization() {
        when(sharedCount.getCount()).thenThrow(new IllegalAccessError());
        for (int i = 0; i < RANGE_OFFSET; i++) System.out.println(generator.nextKey());
    }

    @Test
    void nextKey_WithAvailableKeysInQueue_ShouldReturnNextKey() {
        when(sharedCount.getCount()).thenReturn(1);
        for (int i = 0; i < RANGE_OFFSET; i++)
            // Empty the queue
            generator.nextKey();
        assertNotNull(generator.nextKey());
    }

    @TestConfiguration
    static class Configuration {
        @Bean
        public SharedCount sharedCount() {
            SharedCount sharedCount = Mockito.mock(SharedCount.class);
            when(sharedCount.getCount()).thenReturn(5);
            return sharedCount;
        }

    }


}