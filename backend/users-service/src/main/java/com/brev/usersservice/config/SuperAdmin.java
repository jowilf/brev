package com.brev.usersservice.config;

import com.brev.core.domain.Role;
import com.brev.usersservice.domain.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;

@Configuration
@ConfigurationProperties("app.super-admin")
@Data
@Validated
public class SuperAdmin {

    @NotBlank
    @Email
    @Length(min = 3, max = 100)
    private String username;
    @NotBlank
    @Length(min = 6, max = 50)
    private String password;

    public User getUser(PasswordEncoder encoder) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(encoder.encode(password));
        user.setRole(Role.SUPER_ADMIN);
        return user;
    }
}
