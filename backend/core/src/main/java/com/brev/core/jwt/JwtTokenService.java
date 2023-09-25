package com.brev.core.jwt;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

@Service
public class JwtTokenService {

    private final Algorithm hmac512;
    private final JWTVerifier verifier;
    private final Duration tokenValidity;

    @Value("${jwt.issuer:issuer}")
    private final String issuer = null;

    public JwtTokenService(@Value("${jwt.secret:secreet-key}") final String secret,
                           @Value("${jwt.token.ttl:3600}") final int tokenDurationInSeconds) {
        this.hmac512 = Algorithm.HMAC512(secret);
        this.verifier = JWT.require(this.hmac512).build();
        this.tokenValidity = Duration.ofSeconds(tokenDurationInSeconds);
    }


    public String generateToken(final TokenInfo userInfo) {
        final Instant now = Instant.now();
        return JWT.create()
                .withSubject(userInfo.username())
                .withClaim(TokenInfo.USER_ID, userInfo.userId())
                .withClaim(TokenInfo.ROLE, userInfo.role().name())
                .withIssuer(this.issuer)
                .withIssuedAt(now)
                .withExpiresAt(now.plusMillis(tokenValidity.toMillis()))
                .sign(this.hmac512);
    }

    public TokenInfo validateToken(final String token) {
        try {
            DecodedJWT decodedJWT = verifier.verify(token);
            return TokenInfo.fromDecodedJWT(decodedJWT);
        } catch (final JWTVerificationException verificationEx) {
            return null;
        }
    }

}
