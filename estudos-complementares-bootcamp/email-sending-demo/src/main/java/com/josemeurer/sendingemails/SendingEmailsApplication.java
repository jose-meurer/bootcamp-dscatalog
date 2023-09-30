package com.josemeurer.sendingemails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class SendingEmailsApplication {

	@Autowired
	private Environment env;

	public static void main(String[] args) {
		SpringApplication.run(SendingEmailsApplication.class, args);
	}
}
