package com.josemeurer.dscatalog.resources;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import com.josemeurer.dscatalog.repositories.ProductRepository;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ProductResourceIT {
	
	@Autowired
	private ProductRepository repository;
	
	@Autowired
	private MockMvc mockMvc;
	
	private long existingId;
	private long nonExistingId;
	private long countTotalProducts;
	
	@BeforeEach
	void setUp() {
		
		existingId = 1L;
		nonExistingId = 2L;
		countTotalProducts = repository.count();
		
	}
	
	@Test
	public void findAllShouldReturnSortedPageWhenSortByName() throws Exception{
		
		ResultActions result = 
				mockMvc.perform(MockMvcRequestBuilders.get("/products?page=0&size=10&sort=name,asc")
						.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(MockMvcResultMatchers.status().isOk()); //200
		result.andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").value(countTotalProducts));
		result.andExpect(MockMvcResultMatchers.jsonPath("$.content").exists());
		result.andExpect(MockMvcResultMatchers.jsonPath("$.content[0].name").value("Macbook Pro"));
		result.andExpect(MockMvcResultMatchers.jsonPath("$.content[1].name").value("PC Gamer"));
		result.andExpect(MockMvcResultMatchers.jsonPath("$.content[2].name").value("PC Gamer Alfa"));
	}
}
