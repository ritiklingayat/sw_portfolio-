package com.example.sw_backend.service;

import com.example.sw_backend.entity.Contact;
import com.example.sw_backend.entity.Internship;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String mailFrom;

    @Value("${app.admin.email}")
    private String adminEmail;

    @Async
    public void sendEmailToAdmin(Internship saveIntern, MultipartFile file) {
        logger.info("Sending internship notification email from {} to {}", mailFrom, adminEmail);
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(mailFrom);
            helper.setTo(adminEmail);
            helper.setSubject("New Intern Registration");
            helper.setText(
                    "A new Intern has registered.\n\n"
                            + "Name : " + saveIntern.getName() + "\n"
                            + "Email : " + saveIntern.getEmail() + "\n"
                            + "Number : " + saveIntern.getNumber() + "\n"
                            + "Course : " + saveIntern.getCourse() + "\n"
                            + "Education: " + saveIntern.getEducation_background());

            if (file != null && !file.isEmpty()) {
                helper.addAttachment(
                        file.getOriginalFilename(),
                        new ByteArrayResource(file.getBytes())
                );
            }
            mailSender.send(message);
            logger.info("Internship email sent successfully to {}", adminEmail);
        } catch (Exception e) {
            logger.error("Failed to send internship email", e);
        }
    }

    @Async
    public void sendContactEmail(Contact savedContact) {
        logger.info("Sending contact notification email for {}", savedContact.getEmail());
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);

            helper.setFrom(mailFrom);
            helper.setTo(adminEmail);
            helper.setSubject("New Contact Form Submission");

            helper.setText(
                    "A new contact form has been submitted.\n\n"
                            + "Name: " + savedContact.getName() + "\n"
                            + "Email: " + savedContact.getEmail() + "\n"
                            + "Number: " + savedContact.getNumber() + "\n"
                            + "Course: " + savedContact.getCourse() + "\n"
                            + "Education: " + savedContact.getEducation_background()
            );

            mailSender.send(message);
            logger.info("Contact email sent successfully to {}", adminEmail);

        } catch (Exception e) {
            logger.error("Failed to send contact email", e);
        }
    }
}
