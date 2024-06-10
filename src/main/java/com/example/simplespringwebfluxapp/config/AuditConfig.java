package com.example.simplespringwebfluxapp.config;

import com.example.simplespringwebfluxapp.common.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.ReactiveAuditorAware;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;

@Configuration
@Slf4j
@EnableR2dbcAuditing(auditorAwareRef = "auditorAware")
public class AuditConfig {

    @Bean
    public ReactiveAuditorAware<String> auditorAware(SecurityUtil securityUtil) {
        return securityUtil::getUsername;
    }
}
