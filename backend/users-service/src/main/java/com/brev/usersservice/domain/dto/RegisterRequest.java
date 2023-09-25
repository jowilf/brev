package com.brev.usersservice.domain.dto;


import com.brev.usersservice.domain.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record RegisterRequest(@NotBlank @Email @Length(min = 3, max = 100) String username,
                              @NotBlank @Length(min = 3, max = 255) String firstName,
                              @NotBlank @Length(min = 3, max = 255) String lastName,
                              @NotBlank @Length(min = 6, max = 50) String password) {

    public static User toUser(RegisterRequest dto) {
        User user = new User();
        user.setUsername(dto.username());
        user.setFirstName(dto.firstName());
        user.setLastName(dto.lastName());
        return user;
    }
}