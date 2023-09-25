package com.brev.usersservice.service;


import com.brev.usersservice.domain.dto.RegisterRequest;
import com.brev.core.domain.Role;
import com.brev.usersservice.domain.dto.LoginRequest;
import com.brev.usersservice.domain.dto.TokenResponse;
import com.brev.usersservice.domain.entity.User;
import org.springframework.scheduling.annotation.Async;

import java.util.Optional;

public interface UserService {

    User save(User user);

    @Async
    void saveAsync(User user);

    Optional<User> findByUsername(String username);

    User createUser(RegisterRequest userRegisterRequest, Role role);

    TokenResponse login(LoginRequest loginRequest);
}
