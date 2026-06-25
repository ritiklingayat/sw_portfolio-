package com.example.sw_backend.service;

import com.example.sw_backend.entity.Contact;
import com.example.sw_backend.entity.Internship;
import com.example.sw_backend.repository.ContactRepository;
import com.example.sw_backend.repository.InternshipRepository;
import jakarta.mail.Multipart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.multipart.MultipartHttpMessageReader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class IntershipService {


    @Autowired
    private InternshipRepository internshipRepository;

    @Autowired
    private EmailService emailService;



    public Internship saveIntershipStudent(Internship internship, MultipartFile file) {
        Internship saveIntern = internshipRepository.save(internship);

        emailService.sendEmailToAdmin(saveIntern,file);
        return saveIntern;
    }
}
