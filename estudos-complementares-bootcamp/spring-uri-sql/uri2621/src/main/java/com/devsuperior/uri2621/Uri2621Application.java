package com.devsuperior.uri2621;

import com.devsuperior.uri2621.dto.ProductOnlyNameDTO;
import com.devsuperior.uri2621.projections.ProductOnlyNameProjection;
import com.devsuperior.uri2621.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class Uri2621Application implements CommandLineRunner {

	@Autowired
	private ProductRepository repository;
	
	public static void main(String[] args) {
		SpringApplication.run(Uri2621Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		List<ProductOnlyNameProjection> listSQL = repository.searchSQL(10, 20, 'p');
		System.out.println("--SQL--");
		for (ProductOnlyNameProjection p : listSQL) {
			System.out.println("Name: " + p.getName());
		}

		List<ProductOnlyNameDTO> listJPQL = repository.searchJPQL(10, 20, 'p');
		System.out.println("--JPQL--");
		for (ProductOnlyNameDTO p : listJPQL) {
			System.out.println("Name: " + p.getName());
		}
	}
}
