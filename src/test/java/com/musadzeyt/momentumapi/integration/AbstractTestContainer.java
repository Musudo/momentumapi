package com.musadzeyt.momentumapi.integration;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
@Import({TestUserProvider.class, TestMailConfig.class, TestCloudAmqpConfig.class})
@Tag("integration")
public abstract class AbstractTestContainer {

    static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:17")
//            .withDatabaseName(System.getenv("POSTGRES_DB"))
//            .withUsername(System.getenv("POSTGRES_USER"))
//            .withPassword(System.getenv("POSTGRES_PASSWORD"))
            .withDatabaseName("momentum_db")
            .withUsername("musa")
            .withPassword("userpass")
            .withLogConsumer(new Slf4jLogConsumer(LoggerFactory.getLogger("PostgresContainer")));

    @Autowired
    protected MockMvc mockMvc;

    @BeforeAll
    static void startContainer() {
        postgres.start();
    }

    @DynamicPropertySource
    static void overrideProps(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url" , postgres::getJdbcUrl);
        registry.add("spring.datasource.username" , postgres::getUsername);
        registry.add("spring.datasource.password" , postgres::getPassword);
        registry.add("spring.datasource.driver-class-name" , () -> "org.postgresql.Driver");
        registry.add("spring.flyway.enabled" , () -> true);
    }
}
