package com.example.sw_backend.service;

import com.example.sw_backend.entity.Internship;
import com.example.sw_backend.repository.InternshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.example.sw_backend.service.CloudinaryService; 

@Service
public class IntershipService {

    @Autowired
    private InternshipRepository internshipRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private CloudinaryService cloudinaryService; // ✅ your custom service, not Cloudinary class

    public Internship saveIntershipStudent(Internship internship, MultipartFile file) {
        Internship savedIntern = internshipRepository.save(internship);

        if (file != null && !file.isEmpty()) {
            try {
                String fileUrl = cloudinaryService.uploadFile(file); // ✅ calls your method
                savedIntern.setResumeFilename(fileUrl);
                savedIntern = internshipRepository.save(savedIntern);
            } catch (Exception e) {
                System.err.println("Failed to upload resume to Cloudinary: " + e.getMessage());
            }
        }

        emailService.sendEmailToAdmin(savedIntern, file);
        return savedIntern;
    }
}