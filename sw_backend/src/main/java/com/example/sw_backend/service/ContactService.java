package com.example.sw_backend.service;

import com.example.sw_backend.entity.Contact;
import com.example.sw_backend.repository.ContactRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ContactService {

    private static final Logger logger = LoggerFactory.getLogger(ContactService.class);

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private EmailService emailService;

    @Transactional
    public Contact saveContact(Contact contact) {
        logger.info("Saving contact submission: name={}, email={}, phone={}", contact.getName(), contact.getEmail(), contact.getNumber());
        Contact savedContact = contactRepository.save(contact);
        try {
            emailService.sendContactEmail(savedContact);
        } catch (Exception e) {
            logger.error("Contact saved but email notification failed", e);
        }
        return savedContact;
    }
}
