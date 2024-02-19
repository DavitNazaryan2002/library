package com.neetry.library;

import org.junit.ClassRule;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.Objects;

abstract class AbstractPostgresAwareIntegrationTest {
    @ClassRule
    public static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("test")
            .withUsername("test")
            .withPassword("test");


    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        PostgreSQLContainer<?> var10002 = postgresContainer;
        Objects.requireNonNull(var10002);
        registry.add("spring.datasource.url", var10002::getJdbcUrl);
        registry.add("spring.datasource.username", var10002::getUsername);
        registry.add("spring.datasource.password", var10002::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
    }

    @BeforeAll
    public static void startContainer() {
        postgresContainer.start();
    }

    @AfterAll
    public static void stopContainer() {
        postgresContainer.stop();
    }
}
