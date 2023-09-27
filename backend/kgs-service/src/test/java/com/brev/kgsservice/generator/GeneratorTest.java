package com.brev.kgsservice.generator;

import com.brev.kgsservice.config.AppConfig;
import com.brev.kgsservice.config.ZookeeperTestConfiguration;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.shared.SharedCount;
import org.apache.curator.retry.RetryOneTime;
import org.apache.curator.test.TestingServer;
import org.apache.curator.test.Timing;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
@Import(ZookeeperTestConfiguration.class)
@ActiveProfiles("test")
class GeneratorTest {

    @Autowired
    private SharedCount sharedCount;

    @Autowired
    private Generator generator;

    @Autowired
    private AppConfig appConfig;

    @BeforeEach
    void setUp() {
    }

    @Test
    void queueIsFilledAfterInitialization() {
        assertEquals(sharedCount.getCount(), 1);
        for (int i = 0; i < appConfig.getRangeOffset(); i++)
            // Empty the queue
            generator.nextKey();
        assertEquals(sharedCount.getCount(), 1);
    }

    @Test
    void nextKey_WithEmptyQueue_ShouldRequestNewRangeAndReturnNextKey() {
        for (int i = 0; i < appConfig.getRangeOffset(); i++)
            // Empty the queue
            generator.nextKey();
        assertNotNull(generator.nextKey());
        assertEquals(sharedCount.getCount(), 2);
    }


}