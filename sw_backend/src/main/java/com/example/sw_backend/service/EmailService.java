package com.example.sw_backend.service;

import com.example.sw_backend.entity.Contact;
import com.example.sw_backend.entity.Internship;
import jakarta.mail.internet.MimeMessage;
import org.springframework.core.io.ByteArrayResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;

    @Value("${app.admin.email}")
    private String adminEmail;

    private void sendEmail(String subject, String textContent, MultipartFile attachment) {
        try {
            boolean hasAttachment = attachment != null && !attachment.isEmpty();
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, hasAttachment, StandardCharsets.UTF_8.name());
            helper.setFrom(senderEmail);
            helper.setTo(adminEmail);
            helper.setSubject(subject);
            helper.setText(textContent, false);

            if (hasAttachment) {
                try {
                    String filename = attachment.getOriginalFilename() != null ? attachment.getOriginalFilename() : "attachment";
                    helper.addAttachment(filename, new ByteArrayResource(attachment.getBytes()));
                } catch (Exception ex) {
                    logger.warn("Failed to attach file to email: {}", ex.getMessage());
                }
            }

            mailSender.send(mimeMessage);
            logger.info("Email sent successfully to {}", adminEmail);
        } catch (Exception e) {
            logger.error("Failed to send email via SMTP", e);
        }
    }

    @Async
    public void sendEmailToAdmin(Internship saveIntern, MultipartFile file) {
        logger.info("Sending internship notification email to {}", adminEmail);
        String content = "A new Intern has registered.\n\n"
            + "Name: " + saveIntern.getName() + "\n"
            + "Email: " + saveIntern.getEmail() + "\n"
            + "Number: " + saveIntern.getNumber() + "\n"
            + "Course: " + saveIntern.getCourse() + "\n"
            + "Education: " + saveIntern.getEducation_background();
        sendEmail("New Intern Registration", content, file);
    }

    @Async
    public void sendContactEmail(Contact savedContact) {
        logger.info("Sending contact notification email for {}", savedContact.getEmail());
        String content = "A new contact form has been submitted.\n\n"
                + "Name: " + savedContact.getName() + "\n"
                + "Email: " + savedContact.getEmail() + "\n"
                + "Number: " + savedContact.getNumber() + "\n"
                + "Course: " + savedContact.getCourse() + "\n"
                + "Education: " + savedContact.getEducation_background();
        sendEmail("New Contact Form Submission", content,null);
    }
}
