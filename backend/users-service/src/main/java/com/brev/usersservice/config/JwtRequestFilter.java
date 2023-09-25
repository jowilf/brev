package com.brev.usersservice.config;

import com.brev.core.jwt.BaseJwtRequestFilter;
import com.brev.core.jwt.JwtTokenService;
import com.brev.core.jwt.TokenInfo;
import com.brev.usersservice.repository.UserRepository;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class JwtRequestFilter extends BaseJwtRequestFilter {

    private final UserRepository userRepository;

    @Autowired
    public JwtRequestFilter(JwtTokenService jwtTokenService, UserRepository userRepository) {
        super(jwtTokenService);
        this.userRepository = userRepository;
    }

    @Override
    protected void setAuthentication(@Nonnull TokenInfo tokenInfo) {
        userRepository.findByUsername(tokenInfo.username()).ifPresent(user -> {
                    final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            user, null, user.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
        );
    }
}