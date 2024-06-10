package com.example.simplespringwebfluxapp.common;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JwtTokenUtils {

    private static final String USER_NAME_CLAIM = "user_name";
    private static final String AUTHORITIES_CLAIM = "authorities";

    public String getUsername(Jwt jwt) {
        return jwt.getClaimAsString(USER_NAME_CLAIM);
    }

    public List<String> getAuthorities(Jwt jwt) {
        return jwt.getClaimAsStringList(AUTHORITIES_CLAIM);
    }
}
