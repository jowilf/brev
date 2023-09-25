package com.brev.usersservice.controller;


import com.brev.usersservice.domain.dto.UpdateUserRequest;
import com.brev.usersservice.domain.dto.UserResponse;
import com.brev.usersservice.domain.entity.User;
import com.brev.usersservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public UserResponse getProfile(Authentication authentication) {
        User user = ((User) authentication.getPrincipal());
        return UserResponse.fromUser(user);
    }

    @PutMapping("/me")
    public UserResponse updateProfile(@RequestBody UpdateUserRequest updateUserRequest, Authentication authentication) {
        User user = ((User) authentication.getPrincipal());
        updateUserRequest.populateUser(user);
        return UserResponse.fromUser(userService.save(user));
    }

}
