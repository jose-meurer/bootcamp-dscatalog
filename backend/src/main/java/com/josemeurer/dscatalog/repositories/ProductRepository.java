package com.josemeurer.dscatalog.repositories;

import com.josemeurer.dscatalog.entities.Category;
import com.josemeurer.dscatalog.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

    @Query("SELECT DISTINCT p FROM Product p INNER JOIN p.categories c " +
            "WHERE (:category IS NULL OR c IN :category) AND " +
            "(UPPER(p.name) LIKE UPPER(CONCAT('%',:name,'%')) ) ")
    Page<Product> find(Category category, String name, Pageable pageable);


    //Feito na aula
//    @Query("SELECT DISTINCT p FROM Product p INNER JOIN p.categories c " +
//            "WHERE (:category IS NULL OR c IN :category) AND " +
//            "(UPPER(p.name) LIKE UPPER(CONCAT('%',:name,'%')) ) ")
//    Page<Product> find(List<Category> categories, String name, Pageable pageable);
}
