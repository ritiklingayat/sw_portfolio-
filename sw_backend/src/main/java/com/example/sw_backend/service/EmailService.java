package com.example.sw_backend.service;

import com.example.sw_backend.entity.Contact;
import com.example.sw_backend.entity.Internship;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Value("${brevo.api.key}")
    private String brevoApiKey;

    @Value("${brevo.sender.email}")
    private String brevoSenderEmail;

    @Value("${brevo.sender.name}")
    private String brevoSenderName;

    @Value("${app.admin.email}")
    private String adminEmail;

    private void sendEmail(String subject, String textContent) {
        try {
            String json = "{"
                    + "\"sender\":{\"name\":\"" + brevoSenderName + "\",\"email\":\"" + brevoSenderEmail + "\"},"
                    + "\"to\":[{\"email\":\"" + adminEmail + "\"}],"
                    + "\"subject\":\"" + subject + "\","
                    + "\"textContent\":\"" + textContent.replace("\n", "\\n").replace("\"", "\\\"") + "\""
                    + "}";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.brevo.com/v3/smtp/email"))
                    .header("accept", "application/json")
                    .header("api-key", brevoApiKey)
                    .header("content-type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());

            logger.info("Brevo API response: {} {}", response.statusCode(), response.body());

        } catch (Exception e) {
            logger.error("Failed to send email via Brevo API", e);
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
        sendEmail("New Intern Registration", content);
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
        sendEmail("New Contact Form Submission", content);
    }
}