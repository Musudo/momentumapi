package com.musadzeyt.momentumapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.musadzeyt.momentumapi.config.CloudAmqpConfig;
import com.musadzeyt.momentumapi.api.v1.dto.EmailDto;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

/**
 * Service for producing and consuming messages to/from a RabbitMQ email queue.
 * <p>
 * Uses {@link RabbitTemplate} to send {@link EmailDto} messages and
 * listens for incoming messages to trigger email delivery.
 * Messages are serialized/deserialized via Jackson's {@link ObjectMapper}.
 * </p>
 * <p>
 * Configuration for queue names is provided by {@link CloudAmqpConfig}.
 * </p>
 *
 * @see RabbitTemplate
 * @see RabbitListener
 */
@Slf4j
@Service
@AllArgsConstructor
public class CloudAmqpService {

    /**
     * RabbitTemplate for sending messages to RabbitMQ exchange/queue.
     */
    private final RabbitTemplate rabbitTemplate;

    /**
     * Configuration properties for RabbitMQ (queue names, etc.).
     */
    private final CloudAmqpConfig properties;

    /**
     * Service responsible for sending emails based on received DTOs.
     */
    private final EmailService emailService;

    /**
     * Jackson ObjectMapper for JSON (de)serialization of message bodies.
     */
    private final ObjectMapper objectMapper;

    /**
     * Sends an EmailDto message to the configured RabbitMQ email queue.
     *
     * @param message the email data transfer object to send
     */
    public void sendMessageToEmailQueue(EmailDto message) {
        rabbitTemplate.convertAndSend(properties.getEmailQueue(), message);
        log.debug("Sent message to queue {}: {}", properties.getEmailQueue(), message);
    }

    /**
     * Listens for messages on the configured email queue, deserializes the JSON body to an EmailDto,
     * and invokes the EmailService to send a confirmation email.
     *
     * @param message the raw AMQP message containing the JSON payload
     * @throws JsonProcessingException if message body cannot be deserialized to EmailDto
     * @throws MessagingException if sending the email fails
     */
    @RabbitListener(queues = "${messaging.emailQueue}")
    public void receiveMessageToEmailQueue(Message message) throws JsonProcessingException, MessagingException {
        String messageBody = new String(message.getBody());
        EmailDto emailDto = objectMapper.readValue(messageBody, EmailDto.class);
        log.debug("Received message from queue {}: {}", properties.getEmailQueue(), emailDto);
        emailService.sendConfirmationEmail(emailDto);
    }
}
