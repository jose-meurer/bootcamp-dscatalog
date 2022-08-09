package com.josemeurer.dscatalog.dto;

import java.io.Serializable;
import java.time.Instant;

import com.josemeurer.dscatalog.entities.Category;

public class CategoryDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private Instant lastUpdate;
	
	public CategoryDTO() {
	}
	
	public CategoryDTO(Long id, String name, Instant create, Instant update) {
		this.id = id;
		this.name = name;
		
		this.lastUpdate = verifyUpdate(create, update);
	}
	
	public CategoryDTO(Category entity) {
		this.id = entity.getId();
		this.name = entity.getName();
		
		//adicionado a funcao de retornar o ultimo update;
		this.lastUpdate = verifyUpdate(entity.getCreatedAt(), entity.getUpdateAt()); 
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Instant getLastUpdate() {
		return lastUpdate;
	}
	
	private Instant verifyUpdate(Instant create, Instant update) {
		if(update != null) {
			return update;
		}
		return create;
	}
}
