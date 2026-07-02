package com.example.sw_backend.service;

import com.example.sw_backend.entity.Internship;
import com.example.sw_backend.repository.InternshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Service
public class IntershipService {

    @Autowired
    private InternshipRepository internshipRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private CloudinaryService cloudinaryService; // ✅ your custom service, not Cloudinary class

    public Internship saveIntershipStudent(Internship internship, MultipartFile file) {
        validateContactDetails(internship.getEmail(), internship.getNumber());
        validateResumeFile(file);

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

    private void validateContactDetails(String email, String phone) {
        if (email == null || !email.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please enter a valid email address.");
        }

        if (phone == null || !phone.matches("^\\d{10}$")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please enter a valid 10-digit phone number.");
        }
    }

    private void validateResumeFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please upload a resume file (PDF or Word)." );
        }

        String fileName = file.getOriginalFilename() == null ? "" : file.getOriginalFilename().toLowerCase();
        String contentType = file.getContentType() == null ? "" : file.getContentType();
        boolean isAllowedFile = contentType.equals("application/pdf")
                || contentType.equals("application/msword")
                || contentType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document")
                || fileName.endsWith(".pdf")
                || fileName.endsWith(".doc")
                || fileName.endsWith(".docx");

        if (!isAllowedFile) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please upload a PDF or Word file only.");
        }
    }
}