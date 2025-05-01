package com.musadzeyt.momentumapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musadzeyt.momentumapi.config.CloudAmqpConfig;
import com.musadzeyt.momentumapi.dto.EmailDto;
import com.musadzeyt.momentumapi.dto.entity.ErrorLogDto;
import com.musadzeyt.momentumapi.util.mapper.IErrorLogMapper;
import lombok.AllArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CloudAmqpService {
    private final RabbitTemplate rabbitTemplate;
    private final CloudAmqpConfig properties;
    private final EmailService emailService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ErrorLogService errorLogService;
    private final CustomUserDetailsService customUserDetailsService;

    public void sendMessageToEmailQueue(EmailDto emailDto) {
        try {
            rabbitTemplate.convertAndSend(properties.getEmailQueue(), emailDto);
        } catch (Exception e) {
            ErrorLogDto errorLogDto = new ErrorLogDto();
            errorLogDto.setMessage(e.getMessage());
            errorLogDto.setEntity(this.getClass().getName());
            errorLogDto.setUserId(customUserDetailsService.getCurrentUser().getId());

            errorLogService.create(IErrorLogMapper.INSTANCE.dtoToEntity(errorLogDto));
        }
    }

    @RabbitListener(queues = "${messaging.emailQueue}")
    public void receiveMessageToEmailQueue(Message message) {
        try {
            // Convert the message body to a Map object using Jackson JSON deserializer
            EmailDto emailDto = objectMapper.readValue(message.getBody(), EmailDto.class);
            emailService.sendConfirmationEmail(emailDto);
        } catch (Exception e) {
            ErrorLogDto errorLogDto = new ErrorLogDto();
            errorLogDto.setMessage(e.getMessage());
            errorLogDto.setEntity(this.getClass().getName());
            errorLogDto.setUserId(customUserDetailsService.getCurrentUser().getId());

            errorLogService.create(IErrorLogMapper.INSTANCE.dtoToEntity(errorLogDto));
        }
    }
}
