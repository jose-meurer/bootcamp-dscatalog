package com.devsuperior.bds01.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devsuperior.bds01.entities.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long>{

}
