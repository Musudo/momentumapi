package com.musadzeyt.momentumapi.service;

//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.musadzeyt.momentumapi.config.CloudAmqpConfig;
//import com.musadzeyt.momentumapi.dto.EmailDto;
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.core.Message;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.stereotype.Service;

//@Slf4j
//@Service
//@AllArgsConstructor
public class CloudAmqpService {
//    private final RabbitTemplate rabbitTemplate;
//    private final CloudAmqpConfig properties;
////    private final EmailService emailService;
//    private final ObjectMapper objectMapper = new ObjectMapper();
//
//    public void sendEmailMessage(EmailDto message) {
//        try {
//            rabbitTemplate.convertAndSend(properties.getQueueName(), message);
//        } catch (Exception e) {
//            log.error("Failed to send message");
//        }
//    }
//
//    @RabbitListener(queues = "${messaging.queueName}")
//    public void receiveEmailMessage(Message message) {
//        try {
//            log.info("TEST -> message received" + message);
//
//            // Convert the message body to a Map object using Jackson JSON deserializer
//            EmailDto email = objectMapper.readValue(message.getBody(), EmailDto.class);
////            emailService.sendConfirmationEmail(email);
//
//        } catch (Exception e) {
//            log.error("Failed to receive message: {}", e.getMessage());
//        }
//    }
}
