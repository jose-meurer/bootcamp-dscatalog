package com.devsuperior.uri2611;

import com.devsuperior.uri2611.dto.MovieMinDTO;
import com.devsuperior.uri2611.projections.MovieMinProjection;
import com.devsuperior.uri2611.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class Uri2611Application implements CommandLineRunner {

	@Autowired
	MovieRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(Uri2611Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		List<MovieMinProjection> listSQL = repository.searchSQL("action");
		System.out.println("--SQL--");
		for (MovieMinProjection obj : listSQL)
			System.out.printf("Id: %d, Name: %s\n", obj.getId(), obj.getName());


		List<MovieMinDTO> listJPQL = repository.searchJPQL("action");
		System.out.println("--JPQL--");
		for (MovieMinProjection obj : listSQL)
			System.out.printf("Id: %d, Name: %s\n", obj.getId(), obj.getName());
	}
}
