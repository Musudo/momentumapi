package com.musadzeyt.momentumapi.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for Cloud AMQP integration using Spring AMQP.
 * <p>
 * Binds properties prefixed with <code>messaging</code> from application
 * configuration to this class (e.g., the name of the email queue).
 * Defines beans for JSON message conversion, RabbitTemplate, and a durable queue.
 * </p>
 * <p>
 * Example properties:
 * <pre>{@code
 * messaging.emailQueue=email_notifications
 * spring.rabbitmq.host=...
 * spring.rabbitmq.username=...
 * spring.rabbitmq.password=...
 * }</pre>
 * </p>
 *
 * @see RabbitTemplate
 * @see Queue
 */
@Configuration
@ConfigurationProperties(prefix = "messaging")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CloudAmqpConfig {

    /**
     * Connection factory for establishing RabbitMQ connections.
     * Autowired by Spring based on RabbitMQ properties (spring.rabbitmq.*).
     */
    @Autowired
    private ConnectionFactory connectionFactory;

    /**
     * Name of the RabbitMQ queue for email messages, injected from the
     * <code>messaging.emailQueue</code> property.
     */
    private String emailQueue;

    /**
     * Configures a RabbitTemplate bean for sending messages. Uses
     * {@link Jackson2JsonMessageConverter} to serialize message payloads to JSON.
     *
     * @return a configured RabbitTemplate instance
     */
    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(new Jackson2JsonMessageConverter());
        return template;
    }

    /**
     * Declares a durable RabbitMQ queue with the configured name.
     * This bean ensures the queue exists on the broker at startup.
     *
     * @return a new durable Queue instance named by {@link #emailQueue}
     */
    @Bean
    public Queue myQueue() {
        return new Queue(emailQueue, true);
    }
}
