package com.josemeurer.sendingemails.config;

import com.josemeurer.sendingemails.services.MockEmailService;
import com.josemeurer.sendingemails.services.interfaces.EmailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class TestConfig {

    @Bean
    public EmailService emailService() {
        return new MockEmailService();
    }
}
