package com.devsuperior.bds01.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.bds01.entities.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long>{

}
