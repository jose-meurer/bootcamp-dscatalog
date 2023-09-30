package com.devsuperior.uri2621.repositories;

import com.devsuperior.uri2621.dto.ProductOnlyNameDTO;
import com.devsuperior.uri2621.entities.Product;
import com.devsuperior.uri2621.projections.ProductOnlyNameProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(nativeQuery = true, value = "SELECT p.name " +
            "FROM products p " +
            "INNER JOIN providers prov ON p.id_providers = prov.id " +
            "WHERE p.amount BETWEEN :min AND :max " +
            "AND UPPER(prov.name) LIKE UPPER(CONCAT(:fistLetter, '%'))")
    List<ProductOnlyNameProjection> searchSQL(Integer min, Integer max, char fistLetter);


    @Query("SELECT new com.devsuperior.uri2621.dto.ProductOnlyNameDTO(p.name) " +
            "FROM Product p " +
            "WHERE p.amount BETWEEN :min AND :max " +
            "AND UPPER(p.provider.name) LIKE UPPER(CONCAT(:fistLetter, '%'))")
    List<ProductOnlyNameDTO> searchJPQL(Integer min, Integer max, char fistLetter);
}
