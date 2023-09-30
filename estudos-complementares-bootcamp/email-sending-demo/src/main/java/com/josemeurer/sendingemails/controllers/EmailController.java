package com.josemeurer.sendingemails.controllers;

import com.josemeurer.sendingemails.dto.EmailDTO;
import com.josemeurer.sendingemails.services.interfaces.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/emails")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping
    public ResponseEntity<Void> send(@RequestBody EmailDTO dto) {
        emailService.sendEmail(dto);
        return ResponseEntity.noContent().build();
    }
}
