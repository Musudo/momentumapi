package com.musadzeyt.momentumapi.service;

import com.musadzeyt.momentumapi.dto.EmailDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;

@Slf4j
@Service
//@ConfigurationProperties(prefix = "spring.mail")
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String sender;
    private final SpringTemplateEngine templateEngine;

    public void sendConfirmationEmail(EmailDto email) {
        try {
            // create email context and template
            Context context = new Context();
            context.setVariable("recipientName", email.getRecipientName());
            context.setVariable("activityName", email.getActivityName());
            context.setVariable("activityStartTime", email.getActivityStartTime());
            context.setVariable("reservationNumber", email.getReservationNumber());
            String template;
            if (email.getSubject().equals("Reservatie aangemaakt")) {
                template = templateEngine.process("reservation_confirmation_template", context);
            } else {
                template = templateEngine.process("payment_confirmation_template", context);
            }

            // prepare email
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            messageHelper.setFrom(sender);
            messageHelper.setTo(email.getRecipientEmail());
            messageHelper.setSubject(email.getSubject());
            messageHelper.setText(template, true);

            // send email
            javaMailSender.send(message);
        } catch (MessagingException e) {
            log.error("Failed to send email");
        }
    }
}
