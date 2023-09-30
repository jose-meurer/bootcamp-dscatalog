package com.devsuperior.bds01.dto;

import java.util.ArrayList;
import java.util.List;

import com.devsuperior.bds01.entities.Department;
import com.devsuperior.bds01.entities.Employee;

public class DepartmentDTO {

	public Long id;
	public String name;

	public List<Employee> employees = new ArrayList<>();

	public DepartmentDTO() {
	}

	public DepartmentDTO(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public DepartmentDTO(Department entity) {
		this.id = entity.getId();
		this.name = entity.getName();
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

	public List<Employee> getEmployees() {
		return employees;
	}
}
