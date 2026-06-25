package com.example.sw_backend.service;

import com.example.sw_backend.entity.Contact;
import com.example.sw_backend.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private EmailService emailService;

    public Contact saveContact(Contact contact) {
        Contact savedContact =  contactRepository.save(contact);
        emailService.sendContactEmail(savedContact);
        return savedContact;
    }
}
