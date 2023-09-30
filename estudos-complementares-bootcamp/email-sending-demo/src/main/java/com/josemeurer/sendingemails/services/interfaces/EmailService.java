package com.josemeurer.sendingemails.services.interfaces;

import com.josemeurer.sendingemails.dto.EmailDTO;

public interface EmailService {

    void sendEmail(EmailDTO dto);
}
