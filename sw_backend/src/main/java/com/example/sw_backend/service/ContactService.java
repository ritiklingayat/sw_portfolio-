package com.example.sw_backend.service;

import com.example.sw_backend.entity.Contact;
import com.example.sw_backend.repository.ContactRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ContactService {

    private static final Logger logger = LoggerFactory.getLogger(ContactService.class);

    @Autowired
    private ContactRepository contactRepository;

    @Transactional
    public Contact saveContact(Contact contact) {
        validateContactDetails(contact.getEmail(), contact.getNumber());
        logger.info("Saving contact submission: name={}, email={}, phone={}", contact.getName(), contact.getEmail(), contact.getNumber());
        Contact savedContact = contactRepository.save(contact);
        return savedContact;
    }

    private void validateContactDetails(String email, String phone) {
        if (email == null || !email.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please enter a valid email address.");
        }

        if (phone == null || !phone.matches("^\\d{10}$")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please enter a valid 10-digit phone number.");
        }
    }
}
