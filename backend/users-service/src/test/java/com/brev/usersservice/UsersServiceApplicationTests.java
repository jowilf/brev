package com.brev.usersservice;

import com.brev.usersservice.controller.AuthController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class UsersServiceApplicationTests {

    @Autowired
    AuthController controller;

    @Test
    void contextLoads() {
        assertNotNull(controller);
    }

}
