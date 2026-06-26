package com.example.sw_backend.controller;


import com.example.sw_backend.entity.Contact;
import com.example.sw_backend.entity.Internship;
import com.example.sw_backend.service.ContactService;
import com.example.sw_backend.service.IntershipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.annotation.JsonAppend;

@RestController
@RequestMapping("/sw")
@CrossOrigin(
        origins = {
                "https://sw-portfolio-five.vercel.app",
                "http://localhost:5173",
                "http://127.0.0.1:5173"
        },
        allowedHeaders = "*",
        allowCredentials = "true"
)
public class SwController {

    @Autowired
    private IntershipService intershipService;

    @Autowired
    private ContactService contactService;

    @PostMapping("/internship")
    public Internship save(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("number") String number,
            @RequestParam("course") String course,
            @RequestParam("education_background") String education_background,
            @RequestParam(value = "file", required = false ) MultipartFile file
    )
    {
        Internship internship = new Internship();
        internship.setName(name);
        internship.setEmail(email);
        internship.setNumber(number);
        internship.setCourse(course);
        internship.setEducation_background(education_background);

        return intershipService.saveIntershipStudent(internship,file);

    }

    @PostMapping("/contact")
    public Contact saveContact(@RequestBody Contact contact)
    {
       return contactService.saveContact(contact);
    }





}
