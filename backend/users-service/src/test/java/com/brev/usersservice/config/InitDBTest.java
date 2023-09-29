package com.brev.usersservice.config;

import com.brev.usersservice.domain.entity.User;
import com.brev.usersservice.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
class InitDBTest {
    @Autowired
    UserRepository repository;

    @Autowired
    SuperAdmin superAdmin;

    @Autowired
    PasswordEncoder encoder;

    @Test
    void testSuperAdminUser() {
        Optional<User> superAdminUser = repository.findByUsername(superAdmin.getUsername());
        assertTrue(superAdminUser.isPresent());
        superAdminUser.ifPresent(user -> {
            assertEquals(user.getUsername(), "test@brev.com");
            assertTrue(encoder.matches("1234567", user.getPassword()));
        });
    }
}