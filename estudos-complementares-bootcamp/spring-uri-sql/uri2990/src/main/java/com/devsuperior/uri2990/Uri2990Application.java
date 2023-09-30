package com.devsuperior.uri2990;

import com.devsuperior.uri2990.dto.EmpregadoDeptDTO;
import com.devsuperior.uri2990.projections.EmpregadoDeptProjection;
import com.devsuperior.uri2990.repositories.EmpregadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class Uri2990Application implements CommandLineRunner {

	@Autowired
	private EmpregadoRepository repository;
	
	public static void main(String[] args) {
		SpringApplication.run(Uri2990Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {


		List<EmpregadoDeptProjection> listSQL = repository.searchSQL();
		List<EmpregadoDeptDTO> listDTO = listSQL.stream().map(EmpregadoDeptDTO::new).collect(Collectors.toList());
		System.out.println("\n--SQL--");
		listDTO.forEach(System.out::println);

		List<EmpregadoDeptDTO> listJPQL = repository.searchJPQL();
		System.out.println("\n--JPQL--");
		listJPQL.forEach(System.out::println);
	}
}
