package com.josemeurer.dscatalog.repositories;

import com.josemeurer.dscatalog.entities.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.josemeurer.dscatalog.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

    @Query("SELECT p FROM Product p INNER JOIN p.categories c " +
            "WHERE c = :category")
    Page<Product> find(Category category, Pageable pageable);
}
