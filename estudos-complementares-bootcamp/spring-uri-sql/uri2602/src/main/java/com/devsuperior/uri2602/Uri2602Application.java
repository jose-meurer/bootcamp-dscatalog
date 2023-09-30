package com.devsuperior.uri2602;

import com.devsuperior.uri2602.dto.CustomerOnlyNameDTO;
import com.devsuperior.uri2602.entities.Customer;
import com.devsuperior.uri2602.projections.CustomerProjection;
import com.devsuperior.uri2602.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class Uri2602Application implements CommandLineRunner {

	@Autowired
	private CustomerRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(Uri2602Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("PADRAO");
		List<Customer> list = repository.findAll();
		list.forEach(System.out::println);

		System.out.println("\n\nSQL");
		List<CustomerProjection> listProj = repository.projections("rs");
		for (CustomerProjection obj : listProj) {
			System.out.println(obj.getName());
		}


		System.out.println("\n\nJPQL");
		List<CustomerOnlyNameDTO> listJPQL = repository.searchJPQL("rs");
		for (CustomerOnlyNameDTO obj : listJPQL) {
			System.out.println(obj.getName());
		}
	}
}
