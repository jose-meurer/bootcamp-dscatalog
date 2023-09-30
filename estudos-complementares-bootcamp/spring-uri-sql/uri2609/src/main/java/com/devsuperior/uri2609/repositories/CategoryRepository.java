package com.devsuperior.uri2609.repositories;

import com.devsuperior.uri2609.dto.CategorySumDTO;
import com.devsuperior.uri2609.entities.Category;
import com.devsuperior.uri2609.projections.CategorySumProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query(nativeQuery = true, value =
            "SELECT c.name, SUM(p.amount) " +
                    "FROM products p " +
                    "INNER JOIN categories c " +
                    "ON p.id_categories = c.id " +
                    "GROUP BY c.name " +
                    "ORDER BY c.name asc"
    )
    List<CategorySumProjection> searchSQL();

    @Query("SELECT new com.devsuperior.uri2609.dto.CategorySumDTO(p.category.name, SUM(p.amount)) " +
            "FROM Product p " +
            "GROUP BY p.category.name " +
            "ORDER BY p.category.name asc"
    )
    List<CategorySumDTO> searchJPQL();
}
