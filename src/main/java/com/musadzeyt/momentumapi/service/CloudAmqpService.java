package com.musadzeyt.momentumapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.musadzeyt.momentumapi.config.CloudAmqpConfig;
import com.musadzeyt.momentumapi.dto.EmailDto;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class CloudAmqpService {
    private final RabbitTemplate rabbitTemplate;
    private final CloudAmqpConfig properties;
    private final EmailService emailService;
    private final ObjectMapper objectMapper;

    public void sendMessageToEmailQueue(EmailDto message) {
        rabbitTemplate.convertAndSend(properties.getEmailQueue(), message);
    }

    @RabbitListener(queues = "${messaging.emailQueue}")
    public void receiveMessageToEmailQueue(Message message) throws JsonProcessingException, MessagingException {
        String messageBody = new String(message.getBody());
        // Convert the message body to a Map object using Jackson JSON deserializer
        EmailDto emailDto = objectMapper.readValue(messageBody, EmailDto.class);

        emailService.sendConfirmationEmail(emailDto);
    }
}
