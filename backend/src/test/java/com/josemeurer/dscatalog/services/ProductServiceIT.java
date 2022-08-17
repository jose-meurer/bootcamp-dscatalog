package com.josemeurer.dscatalog.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import com.josemeurer.dscatalog.dto.ProductDTO;
import com.josemeurer.dscatalog.repositories.ProductRepository;
import com.josemeurer.dscatalog.services.exceptions.ResourceNotFoundException;

@SpringBootTest
@Transactional //Faz rollback no banco a cada teste;
public class ProductServiceIT {

	@Autowired
	private ProductService service;
	
	@Autowired
	private ProductRepository repository;
	
	private long existingId;
	private long nonExistingId;
	private long countTotalProducts;
	
	
	@BeforeEach
	void setUp() {
		
		existingId = 1L;
		nonExistingId = 1000L;
		countTotalProducts = repository.count();
		
	}
	
	@Test
	public void findAllPagedShouldReturnSortedPageWhenSortByName() {
		
		PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("price"));
		
		Page<ProductDTO> result = service.findAllPaged(pageRequest);
		
		Assertions.assertFalse(result.isEmpty());
		Assertions.assertTrue(result.getContent().get(0).getPrice() < result.getContent().get(1).getPrice());
		Assertions.assertTrue(result.getContent().get(2).getPrice() < result.getContent().get(3).getPrice());
		Assertions.assertTrue(result.getContent().get(4).getPrice() < result.getContent().get(5).getPrice());
	}
	
	@Test
	public void findAllPagedShouldReturnEmptyPageWhenPageDoesNotExist() {
		
		PageRequest pageRequest = PageRequest.of(50, 10);
		
		Page<ProductDTO> result = service.findAllPaged(pageRequest);
		
		Assertions.assertTrue(result.isEmpty());
	}
	
	@Test
	public void findAllPagedShouldReturnPageWhenPageArguments() { //Page 0, Size 10;
		
		PageRequest pageRequest = PageRequest.of(0, 10);
		
		Page<ProductDTO> result = service.findAllPaged(pageRequest);
		
		Assertions.assertFalse(result.isEmpty());
		Assertions.assertEquals(0, result.getNumber());
		Assertions.assertTrue(result.getSize() == 10);
		Assertions.assertEquals(countTotalProducts, result.getTotalElements());
	}
	
	@Test
	public void deleteShouldDeleteResourceWhenIdExists() {
		
		service.delete(existingId);
		
		Assertions.assertEquals((countTotalProducts - 1), repository.count());
	}
	
	@Test
	public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {
	
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.delete(nonExistingId);
		});
	}
}
