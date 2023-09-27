package com.brev.kgsservice;

import com.brev.kgsservice.config.ZookeeperTestConfiguration;
import com.brev.kgsservice.controller.ApiController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@Import(ZookeeperTestConfiguration.class)
@ActiveProfiles("test")
class KGSApplicationTests {

    @Autowired
    ApiController apiController;

    @Test
    void contextLoads() {
        Assertions.assertNotNull(apiController);
    }

}
