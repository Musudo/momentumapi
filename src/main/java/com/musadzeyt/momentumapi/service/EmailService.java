package com.musadzeyt.momentumapi.service;

import com.musadzeyt.momentumapi.dto.EmailDto;
import com.musadzeyt.momentumapi.service.entityService.ErrorLogService;
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

/**
 * Service for composing and sending HTML-based email notifications using Thymeleaf templates.
 * <p>
 * Uses a {@link JavaMailSender} to dispatch messages and a {@link SpringTemplateEngine}
 * to process Thymeleaf templates. Errors during email preparation or sending can be
 * logged via {@link ErrorLogService} if needed.
 * </p>
 * <p>
 * Example usage:
 * <pre>{@code
 * EmailDto dto = new EmailDto(
 *     "John Doe",
 *     "john@example.com",
 *     "Your Activity Confirmation",
 *     "Morning Run",
 *     LocalDateTime.now().plusDays(1)
 * );
 * emailService.sendConfirmationEmail(dto);
 * }</pre>
 * </p>
 *
 * @see JavaMailSender
 * @see SpringTemplateEngine
 * @see ErrorLogService
 */
@Service
@RequiredArgsConstructor
public class EmailService {

    /**
     * Spring JavaMailSender for creating and sending MIME messages.
     */
    private final JavaMailSender javaMailSender;

    /**
     * Sender email address, injected from the property <code>spring.mail.username</code>.
     */
    @Value("${spring.mail.username}")
    private String sender;

    /**
     * Thymeleaf template engine for processing HTML templates.
     */
    private final SpringTemplateEngine templateEngine;

    /**
     * Service for logging errors that occur during email operations.
     */
    private final ErrorLogService errorLogService;

    /**
     * Sends a confirmation email based on the provided {@link EmailDto}.
     * <p>
     * This method:
     * <ol>
     *   <li>Creates a Thymeleaf context with variables from the DTO.</li>
     *   <li>Processes the <code>activity_confirmation_template</code> to generate HTML content.</li>
     *   <li>Builds a {@link MimeMessage} with UTF-8 encoding and sets headers.</li>
     *   <li>Sends the message via {@link JavaMailSender}.</li>
     * </ol>
     * </p>
     *
     * @param email the data transfer object containing recipient, subject, and template variables
     * @throws MessagingException if an error occurs while creating or sending the email
     */
    public void sendConfirmationEmail(EmailDto email) throws MessagingException {
        // 1. Create email context and process template
        Context context = new Context();
        context.setVariable("recipientName", email.getRecipientName());
        context.setVariable("activityName", email.getActivityName());
        context.setVariable("startTime", email.getStartTime());
        String htmlContent = templateEngine.process("activity_confirmation_template", context);

        // 2. Prepare MIME email with mixed-related multipart mode
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(
                message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name()
        );
        helper.setFrom(sender);
        helper.setReplyTo("noreply@momentum.com");
        helper.setTo(email.getRecipientEmail());
        helper.setSubject(email.getSubject());
        helper.setText(htmlContent, true);

        // 3. Send the email
        javaMailSender.send(message);
    }
}
