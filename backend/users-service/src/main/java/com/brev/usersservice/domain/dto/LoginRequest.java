package com.brev.usersservice.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record LoginRequest(@NotBlank @Email @Length(min = 3, max = 100) String username,
                           @NotBlank @Length(min = 6, max = 50) String password) {
}
