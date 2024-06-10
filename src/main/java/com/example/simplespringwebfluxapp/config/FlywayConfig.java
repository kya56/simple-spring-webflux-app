package com.example.simplespringwebfluxapp.config;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.migration.JavaMigration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.Arrays;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class FlywayConfig {

    private final JavaMigration[] javaMigrations;

    private @Value("${spring.flyway.url}") String url;
    private @Value("${spring.flyway.user}") String user;
    private @Value("${spring.flyway.password}") String password;
    private @Value("${spring.flyway.locations}") String[] locations;

    @Bean
    @Primary
    public Flyway primaryFlyway() {
        var flyway = Flyway.configure()
                .locations(this.locations)
                .dataSource(this.url, this.user, this.password)
                .javaMigrations(javaMigrations)
                .load();

        var locations = Arrays.toString(flyway.getConfiguration().getLocations());
        log.info("Flyway migrations for {} started ...", locations);

        flyway.migrate();

        log.info("Flyway migrations for {} finished.", locations);
        return flyway;
    }

}
