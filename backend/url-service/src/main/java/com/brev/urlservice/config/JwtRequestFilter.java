package com.brev.urlservice.config;

import com.brev.core.jwt.BaseJwtRequestFilter;
import com.brev.core.jwt.JwtTokenService;
import com.brev.core.jwt.TokenInfo;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JwtRequestFilter extends BaseJwtRequestFilter {


    @Autowired
    public JwtRequestFilter(JwtTokenService jwtTokenService) {
        super(jwtTokenService);
    }

    @Override
    protected void setAuthentication(@Nonnull TokenInfo tokenInfo) {
        final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                tokenInfo, null, List.of(new SimpleGrantedAuthority(tokenInfo.role().name())));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}