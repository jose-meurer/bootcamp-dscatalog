package com.josemeurer.sendingemails.services;

import com.josemeurer.sendingemails.dto.EmailDTO;
import com.josemeurer.sendingemails.services.interfaces.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MockEmailService implements EmailService {

    private static final Logger LOG = LoggerFactory.getLogger(MockEmailService.class);

    public void sendEmail(EmailDTO dto) {
        LOG.info("Sending email to: " + dto.getTo());
        LOG.info("Email sent!");
    }
}
