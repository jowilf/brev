package com.brev.kgsservice.generator;

import com.brev.kgsservice.config.AppConfig;
import com.brev.kgsservice.config.ZookeeperTestConfiguration;
import org.apache.curator.framework.recipes.shared.SharedCount;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Import(ZookeeperTestConfiguration.class)
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GeneratorTest {

    @Autowired
    private SharedCount sharedCount;

    @Autowired
    private Generator generator;

    @Autowired
    private AppConfig appConfig;

    @Test
    @Order(1)
    void queueIsFilledAfterInitialization() {
        int countBefore = sharedCount.getCount();
        for (int i = 0; i < appConfig.getRangeSize(); i++)
            // Empty the queue
            System.out.println(generator.nextKey());
        assertEquals(sharedCount.getCount(), countBefore);
    }

    @Test
    @Order(2)
    void nextKey_WithEmptyQueue_ShouldRequestNewRangeAndReturnNextKey() {
        // Empty the queue
        generator.clear();
        int countBefore = sharedCount.getCount();
        assertNotNull(generator.nextKey());
        assertEquals(sharedCount.getCount(), countBefore + 1);
    }


}