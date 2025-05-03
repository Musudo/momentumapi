package com.musadzeyt.momentumapi.service;

import com.musadzeyt.momentumapi.dto.EmailDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String sender;
    private final SpringTemplateEngine templateEngine;
    private final ErrorLogService errorLogService;

    public void sendConfirmationEmail(EmailDto email) {
        try {
            // create email context and template
            Context context = new Context();
            context.setVariable("recipientName", email.getRecipientName());
            context.setVariable("activityName", email.getActivityName());
            context.setVariable("startTime", email.getStartTime());
            String template = templateEngine.process("activity_confirmation_template", context);

            // prepare email
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            messageHelper.setFrom(sender);
            messageHelper.setReplyTo("noreplay@momentum.com");
            messageHelper.setTo(email.getRecipientEmail());
            messageHelper.setSubject(email.getSubject());
            messageHelper.setText(template, true);

            // send email
            javaMailSender.send(message);
        } catch (MessagingException e) {
            errorLogService.createErrorLog(e.getMessage(), this.getClass().getSimpleName());
        }
    }
}
