package com.example.simplespringwebfluxapp.config;

import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authorization.ExceptionTranslationWebFilter;
import org.springframework.web.reactive.config.EnableWebFlux;
import reactor.core.publisher.Mono;
import com.example.simplespringwebfluxapp.common.JwtTokenUtils;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


@Configuration
@EnableWebFlux
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtTokenUtils jwtTokenUtils;

    @Bean
    SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http, ReactiveJwtDecoder decoder) {
        http
            .authorizeExchange(authorizeExchangeSpec ->
                authorizeExchangeSpec
                    .pathMatchers("/auth/token").permitAll()
                    .anyExchange().authenticated()
            )
            .csrf(ServerHttpSecurity.CsrfSpec::disable)
            .addFilterAt(new ExceptionTranslationWebFilter(), SecurityWebFiltersOrder.AUTHORIZATION)
            .oauth2ResourceServer(oauth2ResourceServer ->
                oauth2ResourceServer
                    .jwt(jwt ->
                        jwt
                            .jwtDecoder(decoder)
                            .jwtAuthenticationConverter(jwtAuthenticationConverter())
                    ));
        return http.build();
    }

    @Bean("jwtDecoder")
    public ReactiveJwtDecoder jwtDecoder(@Value("${jwt.secret}") String secret) {
        SecretKey secretKey = new SecretKeySpec(secret.getBytes(), Jwts.SIG.HS256.toString());
        return NimbusReactiveJwtDecoder.withSecretKey(secretKey).build();
    }

    @Bean
    GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults("");
    }

    public Converter<Jwt, Mono<AbstractAuthenticationToken>> jwtAuthenticationConverter() {
        return jwt -> {
            var userName = jwtTokenUtils.getUsername(jwt);
            var authorities = jwtTokenUtils.getAuthorities(jwt);

            JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(
                jwt,
                authorities.stream().map(SimpleGrantedAuthority::new).toList(),
                userName
            );
            return Mono.just(jwtAuthenticationToken);
        };
    }
}
