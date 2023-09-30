package com.josemeurer.devcourses;

import com.josemeurer.devcourses.Repositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DevcoursesApplication {

	@Autowired
	private NotificationRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(DevcoursesApplication.class, args);
	}

}
