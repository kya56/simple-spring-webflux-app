package com.example.simplespringwebfluxapp.common;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;


@Component
@RequiredArgsConstructor
public class SecurityUtil {

    public Mono<String> getUsername() {
        return ReactiveSecurityContextHolder
            .getContext()
            .map(SecurityContext::getAuthentication)
            .filter(auth -> auth.isAuthenticated() && auth instanceof JwtAuthenticationToken)
            .map(auth -> auth != null ? auth.getName() : "system")
            .defaultIfEmpty("system");
    }

}
