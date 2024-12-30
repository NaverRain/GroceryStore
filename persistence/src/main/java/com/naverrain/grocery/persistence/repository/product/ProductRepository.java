package com.naverrain.grocery.persistence.repository.product;

import com.naverrain.grocery.persistence.entity.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :searchQuery, '%'))")
    List<Product> findByNameContainingIgnoreCase(@Param("searchQuery") String searchQuery);

    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :searchQuery, '%'))")
    Page<Product> findByNameContainingIgnoreCase(@Param("searchQuery") String searchQuery, Pageable pageable);

    List<Product> findByCategoryId(Long categoryId);

    Page<Product> findByCategoryId(Long categoryId, Pageable pageable);

    long countByCategoryId(Long categoryId);

    @Query("SELECT COUNT(p) FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :searchQuery, '%'))")
    long countByNameContainingIgnoreCase(@Param("searchQuery") String searchQuery);

    Optional<Product> findByGuid(UUID guid);

    List<Product> findByBrandId(Long brandId);
    List<Product> findBySubcategoryId(Long subcategoryId);

}
