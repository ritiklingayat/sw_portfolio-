package com.example.sw_backend.service;

import com.example.sw_backend.entity.Contact;
import com.example.sw_backend.entity.Internship;
import com.example.sw_backend.repository.ContactRepository;
import com.example.sw_backend.repository.InternshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class IntershipService {


    @Autowired
    private InternshipRepository internshipRepository;

    @Autowired
    private EmailService emailService;



    public Internship saveIntershipStudent(Internship internship, MultipartFile file) {
        // Save internship first
        Internship savedIntern = internshipRepository.save(internship);

        // If a resume file was uploaded, store it on the server and update the record
        if (file != null && !file.isEmpty()) {
            try {
                Path uploadDir = Paths.get("uploads/resumes");
                Files.createDirectories(uploadDir);
                String original = file.getOriginalFilename() != null ? file.getOriginalFilename().replaceAll("\\s+", "_") : "resume";
                String filename = System.currentTimeMillis() + "_" + original;
                Path destination = uploadDir.resolve(filename);
                Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
                savedIntern.setResumeFilename(filename);
                savedIntern = internshipRepository.save(savedIntern);
            } catch (IOException e) {
                // log but do not fail the request; resume will not be attached
                System.err.println("Failed to store uploaded resume: " + e.getMessage());
            }
        }

        // Send notification email (attachment optional)
        emailService.sendEmailToAdmin(savedIntern, file);
        return savedIntern;
    }
}
