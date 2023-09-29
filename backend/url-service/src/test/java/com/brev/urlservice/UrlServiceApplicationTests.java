package com.brev.urlservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(ContainerManager.class)
class UrlServiceApplicationTests {

    @Test
    void contextLoads() {
    }

}
