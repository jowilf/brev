package com.brev.usersservice.domain.dto;

import com.brev.usersservice.domain.entity.User;

public record UpdateUserRequest(String firstName, String lastName) {

    public void populateUser(User user) {
        user.setFirstName(firstName);
        user.setLastName(lastName);
    }
}
