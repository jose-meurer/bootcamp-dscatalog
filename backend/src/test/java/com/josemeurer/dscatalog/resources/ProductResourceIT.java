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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.josemeurer.dscatalog.dto.ProductDTO;
import com.josemeurer.dscatalog.repositories.ProductRepository;
import com.josemeurer.dscatalog.tests.Factory;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ProductResourceIT {
	
	@Autowired
	private ProductRepository repository;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objMapper;
	
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
	public void updateShouldReturnNotFoundWhenIdDoesNotExist() throws Exception{
		
		ProductDTO productDto = Factory.createProductDTO();
		String jsonBody = objMapper.writeValueAsString(productDto);
		
		ResultActions result = 
				mockMvc.perform(MockMvcRequestBuilders.put("/products/{id}", nonExistingId)
						.content(jsonBody)
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
	@Test
	public void updateShouldReturnProductDTOWhenIdExists() throws Exception{
		
		ProductDTO productDto = Factory.createProductDTO();
		String jsonBody = objMapper.writeValueAsString(productDto);
		
		String expectedName = productDto.getName();
		String expectedDescription = productDto.getDescription();
		
		ResultActions result = 
				mockMvc.perform(MockMvcRequestBuilders.put("/products/{id}", existingId)
						.content(jsonBody)
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(MockMvcResultMatchers.status().isOk());
		result.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(existingId));
		result.andExpect(MockMvcResultMatchers.jsonPath("$.name").value(expectedName));
		result.andExpect(MockMvcResultMatchers.jsonPath("$.description").value(expectedDescription));
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
