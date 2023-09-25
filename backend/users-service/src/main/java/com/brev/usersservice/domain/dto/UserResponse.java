package com.brev.usersservice.domain.dto;


import com.brev.core.domain.Role;
import com.brev.usersservice.domain.entity.User;

import java.time.OffsetDateTime;

public record UserResponse(int id, String username, String firstName, String lastName, Role role,
                           OffsetDateTime createdAt, OffsetDateTime lastLogin) {

    public static UserResponse fromUser(User user) {
        return new UserResponse(user.getId(), user.getUsername(), user.getFirstName(), user.getLastName(),
                user.getRole(), user.getCreatedAt(), user.getLastLogin());
    }
}
