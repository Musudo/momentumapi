package com.musadzeyt.momentumapi.integration;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import static org.mockito.Mockito.mock;

@TestConfiguration
public class TestCloudAmqpConfig {
    @Bean
    public ConnectionFactory connectionFactory() {
        return mock(ConnectionFactory.class);
    }
}
