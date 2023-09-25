package com.brev.usersservice.service;

import com.brev.usersservice.domain.dto.RegisterRequest;
import com.brev.usersservice.exception.LoginException;
import com.brev.usersservice.exception.UsernameAlreadyExistException;
import com.brev.usersservice.repository.UserRepository;
import com.brev.core.domain.Role;
import com.brev.core.jwt.JwtTokenService;
import com.brev.usersservice.domain.dto.LoginRequest;
import com.brev.usersservice.domain.dto.TokenResponse;
import com.brev.usersservice.domain.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenService jwtTokenService;

    @Autowired
    public UserServiceImpl(UserRepository repository, PasswordEncoder passwordEncoder,
                           JwtTokenService jwtTokenService) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    public User save(User user) {
        return repository.save(user);
    }

    @Async
    @Override
    public void saveAsync(User user) {
        repository.save(user);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public User createUser(RegisterRequest userRegisterRequest, Role role) {
        // Check if a user with the same username already exists
        if (this.findByUsername(userRegisterRequest.username()).isPresent())
            throw new UsernameAlreadyExistException();
        User user = RegisterRequest.toUser(userRegisterRequest);
        user.setPassword(passwordEncoder.encode(userRegisterRequest.password()));
        user.setRole(role);
        return this.save(user);
    }

    @Override
    public TokenResponse login(LoginRequest loginRequest) {
        // Attempt to find a user by their username
        User user = this.findByUsername(loginRequest.username())
                .orElseThrow(LoginException::new);

        // Check if the provided password matches the stored hashed password
        if (!passwordEncoder.matches(loginRequest.password(), user.getPassword()))
            throw new LoginException();

        // Generate a JWT token for the authenticated user
        String token = jwtTokenService.generateToken(user.toTokenInfo());

        user.setLastLogin(OffsetDateTime.now());
        saveAsync(user);

        return new TokenResponse(token);
    }
}
