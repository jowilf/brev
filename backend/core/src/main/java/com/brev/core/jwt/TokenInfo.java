package com.brev.core.jwt;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.brev.core.domain.Role;

public record TokenInfo(int userId, String username, Role role) {
    public static String USER_ID = "userId";
    public static String ROLE = "role";


    static TokenInfo fromDecodedJWT(DecodedJWT decodedJWT) {
        return new TokenInfo(decodedJWT.getClaim(USER_ID).asInt(), decodedJWT.getSubject(),
                Role.valueOf(decodedJWT.getClaim(ROLE).asString()));
    }

}
