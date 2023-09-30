package com.josemeurer.sendingemails.config;

import com.josemeurer.sendingemails.services.SendGridEmailService;
import com.josemeurer.sendingemails.services.interfaces.EmailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class DevConfig {

    @Bean
    public EmailService emailService() {
        return new SendGridEmailService();
    }
}
