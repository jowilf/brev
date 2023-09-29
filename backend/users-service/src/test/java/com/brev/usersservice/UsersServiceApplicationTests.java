package com.brev.usersservice;

import com.brev.usersservice.controller.AuthController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
class UsersServiceApplicationTests {

    @Autowired
    AuthController controller;

    @Test
    void contextLoads() {
        assertNotNull(controller);
    }

}
