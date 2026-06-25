package com.example.sw_backend.service;

import com.example.sw_backend.entity.Contact;
import com.example.sw_backend.entity.Internship;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
@Component
@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${app.admin.email}")
    private String adminEmail;

    public void sendEmailToAdmin(Internship saveIntern, MultipartFile file) {
        try{
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,true);

            helper.setTo(adminEmail);
            helper.setSubject("New Intern Registration");
            helper.setText(
                    "A new Intern has registered.\n\n"
                    + "Name : " + saveIntern.getName() + "\n"
                    + "Email : " + saveIntern.getEmail() + "\n"
                    + "Number : " + saveIntern.getNumber() + "\n"
                    + "Cource : " + saveIntern.getCourse() + "\n"
                    + "education_detais : " + saveIntern.getEducation_background());

            // ************ CHANGED ************
            // Attach uploaded file if available.

            if(file!=null && !file.isEmpty()){
                helper.addAttachment(
                        file.getOriginalFilename(),
                        new ByteArrayResource(file.getBytes())
                );
            }
            mailSender.send(message);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void sendContactEmail(Contact savedContact) {

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);

            helper.setTo(adminEmail);
            helper.setSubject("New Contact Form Submission");

            helper.setText(
                    "A new contact form has been submitted.\n\n" +
                            "Name: " + savedContact.getName() + "\n" +
                            "Email: " + savedContact.getEmail() + "\n" +
                            "Number: " + savedContact.getNumber() + "\n" +
                            "Course: " + savedContact.getCourse() + "\n" +
                            "Education: " + savedContact.getEducation_background()
            );

            mailSender.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
