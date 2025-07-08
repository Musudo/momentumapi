package com.musadzeyt.momentumapi.service;

import com.musadzeyt.momentumapi.api.v1.dto.EmailDto;
import com.musadzeyt.momentumapi.service.entityService.ErrorLogService;
import com.musadzeyt.momentumapi.util.CalendarUtil;
import com.musadzeyt.momentumapi.util.DateAndTimeUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * <p>
 * EmailService is responsible for composing and sending HTML-based email notifications using Thymeleaf templates,
 * and attaching calendar event (.ics) files. It relies on {@link JavaMailSender} for sending emails,
 * and uses {@link SpringTemplateEngine} for rendering dynamic HTML content.
 * </p>
 *
 * <p>
 * <b>Features:</b>
 * <ul>
 *   <li>Generates personalized confirmation emails from templates</li>
 *   <li>Attaches iCalendar (.ics) files with event details to emails</li>
 *   <li>Supports UTF-8 encoding and mixed multipart emails</li>
 *   <li>Error logging capability via {@link ErrorLogService} (if integrated)</li>
 * </ul>
 * </p>
 *
 * <p>
 * <b>Usage Example:</b>
 * <pre>{@code
 * EmailDto dto = new EmailDto(
 *     "John Doe",
 *     "john@example.com",
 *     "Your Activity Confirmation",
 *     "Morning Run",
 *     "2025-07-07T14:42"
 * );
 * emailService.sendConfirmationEmail(dto);
 * }</pre>
 * </p>
 *
 * @author Musa Tashtamirov
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class EmailService {

    /**
     * Injected JavaMailSender for sending MIME emails.
     */
    private final JavaMailSender javaMailSender;

    /**
     * Sender's email address, provided from application properties (<code>mail.sender</code>).
     */
    @Value("${mail.sender}")
    private String sender;

    /**
     * Thymeleaf template engine for HTML email content rendering.
     */
    private final SpringTemplateEngine templateEngine;

    /**
     * Error logger for capturing email-related failures.
     */
    private final ErrorLogService errorLogService;

    /**
     * Sends an activity confirmation email with HTML content and an attached iCalendar event.
     * <p>
     * This method performs the following:
     * <ol>
     *   <li>Builds a Thymeleaf context from the email DTO.</li>
     *   <li>Renders the specified HTML template.</li>
     *   <li>Creates a MIME message with headers and UTF-8 encoding.</li>
     *   <li>Generates an iCalendar (.ics) file representing the activity and attaches it.</li>
     *   <li>Sends the complete email using the configured mail sender.</li>
     * </ol>
     * </p>
     *
     * @param email DTO containing recipient, subject, activity details, and times
     * @throws MessagingException if an error occurs while creating or sending the email
     * @throws IOException        if there is an error generating the calendar attachment
     */
    public void sendConfirmationEmail(EmailDto email) throws MessagingException, IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime startTime = LocalDateTime.parse(email.getStartTime(), formatter);
        LocalDateTime endTime = LocalDateTime.parse(email.getEndTime(), formatter);
        String startTimeStr = DateAndTimeUtil.formatDateIsoString(email.getStartTime(), "full");

        // 1. Prepare variables for the Thymeleaf template
        Context context = new Context();
        context.setVariable("recipientName", email.getRecipientName());
        context.setVariable("activityName", email.getActivityName());
        context.setVariable("startTime", startTimeStr);
        String htmlContent = templateEngine.process("activity_confirmation_template", context);

        // 2. Create MIME message and set properties
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(
                message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name()
        );
        helper.setFrom(sender); // Consider hiding or configuring neutral display name in production
        helper.setReplyTo(sender);
        helper.setTo(email.getRecipientEmail());
        helper.setSubject(email.getSubject());
        helper.setText(htmlContent, true);

        // 3. Generate and attach .ics calendar event
        Calendar calendar = CalendarUtil.createActivityCalendar(
                email.getSubject(),
                "This is test activity.",
                startTime,
                endTime
        );
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        CalendarOutputter outputter = new CalendarOutputter();
        outputter.output(calendar, outputStream);
        ByteArrayResource calendarAttachment = new ByteArrayResource(outputStream.toByteArray());
        helper.addAttachment(
                "activity_" + email.getSubject() + ".ics",
                calendarAttachment,
                "text/calendar; charset=UTF-8"
        );

        // 4. Send the composed email
        javaMailSender.send(message);
    }
}
