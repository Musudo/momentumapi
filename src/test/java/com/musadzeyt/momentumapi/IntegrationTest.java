package com.musadzeyt.momentumapi;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
@Tag("integration")
class IntegrationTest {

    @Container
    @SuppressWarnings("resource")
    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0")
            .withDatabaseName("momentum_db")
            .withUsername("musa")
            .withPassword("userpass");

    @DynamicPropertySource
    static void overrideProps(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);

        registry.add("spring.rabbitmq.username", () -> "fake");
        registry.add("spring.rabbitmq.password", () -> "fake");
        registry.add("spring.rabbitmq.host", () -> "localhost");
        registry.add("spring.rabbitmq.port", () -> "12345");
        registry.add("spring.mail.username", () -> "test@example.com");
        registry.add("jwt.secret", () -> "superSecretTestKeyThatIsDefinitelyFake");
        registry.add("jwt.expirationTime", () -> "99999999");
    }

    @Test
    void appContextLoadsAndConnectsToRealDb() {
        assertThat(mysql.isRunning()).isTrue();
    }
}
