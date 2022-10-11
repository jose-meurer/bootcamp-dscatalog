package com.josemeurer.dscatalog.services;

import com.josemeurer.dscatalog.dto.ProductDTO;
import com.josemeurer.dscatalog.entities.Category;
import com.josemeurer.dscatalog.entities.Product;
import com.josemeurer.dscatalog.repositories.CategoryRepository;
import com.josemeurer.dscatalog.repositories.ProductRepository;
import com.josemeurer.dscatalog.services.exceptions.DatabaseException;
import com.josemeurer.dscatalog.services.exceptions.ResourceNotFoundException;
import com.josemeurer.dscatalog.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {

	@InjectMocks
	private ProductService service;
	
	@Mock
	private ProductRepository repository;
	
	@Mock
	private CategoryRepository catRepository;
	
	private long existingId;
	private long nonExistingId;
	private long dependentId;
	
	private PageImpl<Product> page;
	
	private Product product;
	private ProductDTO productDto;
	
	private Category category;
	
	@BeforeEach
	void setUp() throws Exception {
		existingId = 1L; 
		nonExistingId = 2L;
		dependentId = 3L;
		
		product = Factory.createProduct();
		productDto = Factory.createProductDTO();
		
		category = Factory.createCategory();
		
		page = new PageImpl<>(List.of(product));
		
		//delete, quando tudo ocorre bem;
		Mockito.doNothing().when(repository).deleteById(existingId);
		//delete, o id nao existe ou tem dependencia com outro obj, lancando excessao;
		Mockito.doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(nonExistingId);
		Mockito.doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentId); //teste para o futuro da aplicacao, quando tiver obj que depende do Product;
		
		//save, retornando um produto "que foi salvo";
		Mockito.when(repository.save(any())).thenReturn(product);
		
		//findAll pageable, retornando uma page com um produto dentro;
		Mockito.when(repository.findAll((Pageable) any())).thenReturn(page);
		/* Quando o metodo aceita sobrecarga, precisa fazer um casting para especificar 
		 * para o compilador qual sobrecarga utilizar */

		Mockito.when(repository.find(any(),any(),any())).thenReturn(page);
		
		//findById, quando o id existe, retornando um optional com product dentro;
		Mockito.when(repository.findById(existingId)).thenReturn(Optional.of(product));
		//findById, quando o id nao existe, retornando um optional vazio;
		Mockito.when(repository.findById(nonExistingId)).thenReturn(Optional.empty());
		
		//getOne
		Mockito.when(repository.getOne(existingId)).thenReturn(product);
		Mockito.when(repository.getOne(nonExistingId)).thenThrow(EntityNotFoundException.class);
		
		Mockito.when(catRepository.getOne(existingId)).thenReturn(category);
		Mockito.when(catRepository.getOne(nonExistingId)).thenThrow(EntityNotFoundException.class);
		}
	
	@Test
	public void deleteShouldDoNothingWhenIdExists() {

		Assertions.assertDoesNotThrow(() -> {
			service.delete(existingId);
		});
		
		Mockito.verify(repository, Mockito.times(1)).deleteById(existingId);
	}
	
	@Test
	public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
		
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.delete(nonExistingId);
		});
		
		Mockito.verify(repository, Mockito.times(1)).deleteById(nonExistingId);
	}
	
	@Test
	public void deleteShouldThrowDatabaseExceptionWhenDependentId() {
		
		Assertions.assertThrows(DatabaseException.class, () -> {
			service.delete(dependentId);
		});
		
		Mockito.verify(repository, Mockito.times(1)).deleteById(dependentId);
	}
	
	@Test
	public void findAllPagedShouldReturnPage() {
		
		Pageable pageable = PageRequest.of(0, 10); //Pagina 0, tamanho 10;
		
		Page<ProductDTO> result = service.findAllPaged(0L, "", pageable);
		
		Assertions.assertNotNull(result);
		Mockito.verify(repository, Mockito.times(1)).find(null, "",pageable);
	}
	
	@Test
	public void findByIdShouldReturnProductDTOWhenIdExists() {
		
		ProductDTO result = service.findById(existingId);
		
		Assertions.assertNotNull(result);
		
		Mockito.verify(repository, Mockito.times(1)).findById(existingId);
	}
	
	@Test
	public void findByIdShouldResourceNotFoundExceptionWhenIdDoesNotExist() {
		
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.findById(nonExistingId);
		});
		
		Mockito.verify(repository, Mockito.times(1)).findById(nonExistingId);
	}
	
	@Test
	public void updateShouldReturnProductDTOWhenIdExists() {

		ProductDTO result = service.update(existingId, productDto);
		
		Assertions.assertNotNull(result);
		
		Mockito.verify(repository, Mockito.times(1)).getOne(existingId);
		Mockito.verify(repository, Mockito.times(1)).save(product);
	}
	
	@Test
	public void updateShouldResourceNotFoundExceptionWhenIdDoesNotExist() {
		
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.update(nonExistingId, productDto);
		});

		Mockito.verify(repository, Mockito.times(1)).getOne(nonExistingId);
		Mockito.verify(repository, Mockito.times(0)).save(product);
	}
}