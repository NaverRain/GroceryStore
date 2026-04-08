package com.naverrain.grocery.persistence.repository.product;

import com.naverrain.grocery.persistence.entity.product.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @EntityGraph(attributePaths = {"subcategories"})
    @Override
    Page<Category> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"subcategories"})
    @Override
    Optional<Category> findById(Long id);

    @EntityGraph(attributePaths = {"subcategories"})
    Page<Category> findByNameContainingIgnoreCase(String name, Pageable pageable);

    @EntityGraph(attributePaths = {"subcategories"})
    Page<Category> findAllByOrderByNameAsc(Pageable pageable);

    Optional<Category> findByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCase(String name);
}
