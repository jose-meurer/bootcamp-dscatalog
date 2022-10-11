package com.josemeurer.dscatalog.tests;

import com.josemeurer.dscatalog.dto.CategoryDTO;
import com.josemeurer.dscatalog.dto.ProductDTO;
import com.josemeurer.dscatalog.entities.Category;
import com.josemeurer.dscatalog.entities.Product;

import java.time.Instant;

public class Factory {

	
	public static Product createProduct() {
		Product p = new Product(1L, "Phone", "Good Phone", 800.0, "https://img.com/img.png", 
				Instant.parse("2021-10-20T03:00:00Z"));
		p.getCategories().add(createCategory());
		return p;
	}
	
	public static ProductDTO createProductDTO() {
		Product p = createProduct();
		return new ProductDTO(p, p.getCategories());
	}
	
	public static Category createCategory() {
		return new Category(1L, "Electronics");
	}
	
	public static CategoryDTO createCategoryDTO() {
		return new CategoryDTO(createCategory());
	}
}
