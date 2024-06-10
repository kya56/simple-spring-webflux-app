package com.example.simplespringwebfluxapp.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Set;

@RestController
@RequiredArgsConstructor
public class AuthController {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @GetMapping(value = "/auth/token")
    public Mono<String> getToken(@RequestParam(value = "admin", defaultValue = "false") boolean admin) {
        var expirationDate = Date.from(LocalDate.now().plusDays(1).atStartOfDay().toInstant(ZoneOffset.UTC));
        var token = Jwts.builder()
            .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()), Jwts.SIG.HS256)
            .header().add("typ", "JWT")
            .and()
            .issuedAt(new Date())
            .expiration(expirationDate)
            .claim("authorities", admin ? Set.of("ADMIN") : Set.of())
            .claim("user_name", "testuser")
            .compact();
        return Mono.just(token);
    }

}
