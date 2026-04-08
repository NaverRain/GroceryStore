package com.naverrain.grocery.persistence.repository.product;

import com.naverrain.grocery.persistence.entity.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    @EntityGraph(attributePaths = {"category", "brand", "subcategory"})
    @Override
    Page<Product> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"category", "brand", "subcategory"})
    @Override
    Optional<Product> findById(Long id);

    @EntityGraph(attributePaths = {"category", "brand", "subcategory"})
    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);

    @EntityGraph(attributePaths = {"category", "brand", "subcategory"})
    Page<Product> findByCategoryId(Long categoryId, Pageable pageable);

    @EntityGraph(attributePaths = {"category", "brand", "subcategory"})
    Page<Product> findBySubcategoryId(Long subcategoryId, Pageable pageable);

    @EntityGraph(attributePaths = {"category", "brand", "subcategory"})
    Page<Product> findByBrandId(Long brandId, Pageable pageable);

    @EntityGraph(attributePaths = {"category", "brand", "subcategory"})
    Page<Product> findByCategoryIdAndSubcategoryId(Long categoryId, Long subcategoryId, Pageable pageable);

    @EntityGraph(attributePaths = {"category", "brand", "subcategory"})
    Page<Product> findByCategoryIdAndBrandId(Long categoryId, Long brandId, Pageable pageable);

    @EntityGraph(attributePaths = {"category", "brand", "subcategory"})
    Optional<Product> findByGuid(UUID guid);

    @EntityGraph(attributePaths = {"category", "brand", "subcategory"})
    Page<Product> findAllByOrderByIdDesc(Pageable pageable);

    boolean existsByNameIgnoreCase(String name);

    boolean existsByGuid(UUID guid);
}
