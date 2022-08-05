package com.josemeurer.dscatalog.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.josemeurer.dscatalog.entities.Category;
import com.josemeurer.dscatalog.repositories.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository repository;
	
	public List<Category> findall() {
		return repository.findAll();
	}
	
}
