package com.example.simplespringwebfluxapp.inventory.testutils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class FlywayTestDatabaseConfig {

    private @Value("${spring.flyway.url}") String url;
    private @Value("${spring.flyway.user}") String user;
    private @Value("${spring.flyway.password}") String password;


    /**
     * Necessary to run sql scripts with @Sql
     */
    @Bean
    public DataSource testDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        return dataSource;
    }
}

