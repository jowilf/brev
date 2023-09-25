package com.brev.core.jwt;


import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public abstract class BaseJwtRequestFilter extends OncePerRequestFilter {

    private final JwtTokenService jwtTokenService;

    public BaseJwtRequestFilter(JwtTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest request,
                                    final HttpServletResponse response,
                                    final FilterChain chain) throws ServletException, IOException {
        // look for Bearer auth header
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null || !header.startsWith("Bearer ") || header.split(" ").length > 2) {
            chain.doFilter(request, response);
            return;
        }

        final String token = header.substring(7);
        final TokenInfo tokenInfo = jwtTokenService.validateToken(token);
        if (tokenInfo == null) {
            // validation failed or token expired
            chain.doFilter(request, response);
            return;
        }

        this.setAuthentication(tokenInfo);

        // continue with authenticated user
        chain.doFilter(request, response);
    }

    protected abstract void setAuthentication(@Nonnull TokenInfo tokenInfo);
}