package com.josemeurer.dscatalog.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.josemeurer.dscatalog.dto.ProductDTO;
import com.josemeurer.dscatalog.services.ProductService;
import com.josemeurer.dscatalog.services.exceptions.ResourceNotFoundException;
import com.josemeurer.dscatalog.tests.Factory;
import com.josemeurer.dscatalog.tests.TokenUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductResourceTests {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ProductService service;
	
	@Autowired
	private ObjectMapper objMapper; //Como é uma instancia auxiliar, nao tem problema utilizar no test;

	@Autowired
	private TokenUtil tokenUtil;

	private long existingId;
	private long nonExistingId;
	private long dependentId;
	
	private ProductDTO productDTO;
	private PageImpl<ProductDTO> page;

	private String username;
	private String password;
	
	
	@BeforeEach
	void setUp() throws Exception {

		username = "maria@gmail.com";
		password = "123456";
		
		existingId = 1L;
		nonExistingId = 2L;
		dependentId = 3L;
		
		productDTO = Factory.createProductDTO();
		page = new PageImpl<>(List.of(productDTO));
		
		//findAll
		Mockito.when(service.findAllPaged(any(), any(), any())).thenReturn(page);
		
		//findById
		Mockito.when(service.findById(existingId)).thenReturn(productDTO);
		Mockito.when(service.findById(nonExistingId)).thenThrow(ResourceNotFoundException.class);
		
		//update
		Mockito.when(service.update(ArgumentMatchers.eq(existingId), any())).thenReturn(productDTO);
		Mockito.when(service.update(ArgumentMatchers.eq(nonExistingId), any())).thenThrow(ResourceNotFoundException.class);
		
		//insert
		Mockito.when(service.insert(any())).thenReturn(productDTO);
		
		//delete
		Mockito.doNothing().when(service).delete(existingId); //Metodo void altera a ordem;
		Mockito.doThrow(ResourceNotFoundException.class).when(service).delete(nonExistingId);
		Mockito.doThrow(DataIntegrityViolationException.class).when(service).delete(dependentId);
	}
	
	@Test
	public void deleteShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {

		String accessToken = tokenUtil.obtainAccessToken(mockMvc, username, password);
		
		ResultActions result = 
				mockMvc.perform(MockMvcRequestBuilders.delete("/products/{id}", nonExistingId)
						.header("Authorization", "Bearer " + accessToken));
		
		result.andExpect(MockMvcResultMatchers.status().isNotFound()); //404
	}
	
	@Test
	public void deleteShouldNoContentWhenIdExists() throws Exception {

		String accessToken = tokenUtil.obtainAccessToken(mockMvc, username, password);
		
		ResultActions result = 
				mockMvc.perform(MockMvcRequestBuilders.delete("/products/{id}", existingId)
						.header("Authorization", "Bearer " + accessToken));
		
		result.andExpect(MockMvcResultMatchers.status().isNoContent()); //204
	}
	
	@Test
	public void insertShouldReturnProductDTOCreated() throws Exception {

		String accessToken = tokenUtil.obtainAccessToken(mockMvc, username, password);
		
		String jsonBody = objMapper.writeValueAsString(productDTO);
		
		ResultActions result = 
				mockMvc.perform(MockMvcRequestBuilders.post("/products")
						.header("Authorization", "Bearer " + accessToken)
						.content(jsonBody) 
						.contentType(MediaType.APPLICATION_JSON) 
						.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(MockMvcResultMatchers.status().isCreated()); //201
		result.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists()); 
		result.andExpect(MockMvcResultMatchers.jsonPath("$.name").exists());
		result.andExpect(MockMvcResultMatchers.jsonPath("$.description").exists());
		result.andExpect(MockMvcResultMatchers.jsonPath("$.price").exists());
	}
	
	@Test
	public void updateShouldReturnProductDTOWhenIdExists() throws Exception{

		String accessToken = tokenUtil.obtainAccessToken(mockMvc, username, password);
		
		String jsonBody = objMapper.writeValueAsString(productDTO);
		
		ResultActions result = 
				mockMvc.perform(MockMvcRequestBuilders.put("/products/{id}", existingId)
						.header("Authorization", "Bearer " + accessToken)
						.content(jsonBody) //Passa o corpo json;
						.contentType(MediaType.APPLICATION_JSON) //Tipo de dado da requisicao;
						.accept(MediaType.APPLICATION_JSON)); //Tipo de dado da reposta;
		
		result.andExpect(MockMvcResultMatchers.status().isOk());
		result.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists()); 
		result.andExpect(MockMvcResultMatchers.jsonPath("$.name").exists());
		result.andExpect(MockMvcResultMatchers.jsonPath("$.description").exists());
		result.andExpect(MockMvcResultMatchers.jsonPath("$.price").exists());
	}
	
	@Test
	public void updateShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {

		String accessToken = tokenUtil.obtainAccessToken(mockMvc, username, password);
		
		String jsonBody = objMapper.writeValueAsString(productDTO);
		
		ResultActions result = 
				mockMvc.perform(MockMvcRequestBuilders.put("/products/{id}", nonExistingId)
						.header("Authorization", "Bearer " + accessToken)
						.content(jsonBody)
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON));
		result.andExpect(MockMvcResultMatchers.status().isNotFound()); //404
	}
	
	@Test
	public void findByIdShouldReturnProductDTOWhenIdExists() throws Exception{
		 
		ResultActions result = 
				mockMvc.perform(MockMvcRequestBuilders.get("/products/{id}", existingId)
						.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(MockMvcResultMatchers.status().isOk()); //200;
		result.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists()); //$ acessa o obj;
		result.andExpect(MockMvcResultMatchers.jsonPath("$.name").exists());
		result.andExpect(MockMvcResultMatchers.jsonPath("$.description").exists());
		result.andExpect(MockMvcResultMatchers.jsonPath("$.price").exists());
	}
	
	@Test
	public void findByIdShouldReturnNotFoundWhenIdDoesNotExists() throws Exception {
		
		ResultActions result =
				mockMvc.perform(MockMvcRequestBuilders.get("/products/{id}", nonExistingId)
						.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(MockMvcResultMatchers.status().isNotFound()); //404;
	}
	
	@Test
	public void findAllShouldReturnPage() throws Exception{
		
		ResultActions result = 
				mockMvc.perform(MockMvcRequestBuilders.get("/products")
						.accept(MediaType.APPLICATION_JSON));
		
				result.andExpect(MockMvcResultMatchers.status().isOk()); //Esperado 200;
	}
}
