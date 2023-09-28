package com.brev.usersservice.config;

import com.brev.usersservice.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class InitDB implements CommandLineRunner {
    private final SuperAdmin superAdmin;
    private final PasswordEncoder encoder;

    private final UserRepository repository;

    public InitDB(SuperAdmin superAdmin, PasswordEncoder encoder, UserRepository repository) {
        this.superAdmin = superAdmin;
        this.encoder = encoder;
        this.repository = repository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (repository.findByUsername(superAdmin.getUsername()).isEmpty()) {
            repository.save(superAdmin.getUser(encoder));
        }
    }
}
