package com.brev.usersservice.controller;

import com.brev.usersservice.domain.dto.RegisterRequest;
import com.brev.usersservice.domain.dto.UserResponse;
import com.brev.core.domain.Role;
import com.brev.usersservice.domain.dto.LoginRequest;
import com.brev.usersservice.domain.dto.TokenResponse;
import com.brev.usersservice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final UserService userService;


    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/login")
    public TokenResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        return userService.login(loginRequest);
    }

    @PostMapping("/register")
    public UserResponse registerSimpleUser(@Valid @RequestBody RegisterRequest userRegisterRequest) {
        return UserResponse.fromUser(userService.createUser(userRegisterRequest, Role.USER));
    }

    @PostMapping("/register/admin")
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    public UserResponse registerAdminUser(@RequestBody RegisterRequest userRegisterRequest) {
        return UserResponse.fromUser(userService.createUser(userRegisterRequest, Role.ADMIN));
    }
}
