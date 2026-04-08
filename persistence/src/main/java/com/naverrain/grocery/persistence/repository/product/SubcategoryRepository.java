package com.naverrain.grocery.persistence.repository.product;

import com.naverrain.grocery.persistence.entity.product.Subcategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubcategoryRepository extends JpaRepository<Subcategory, Long> {

    @EntityGraph(attributePaths = {"category"})
    @Override
    Page<Subcategory> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"category"})
    @Override
    Optional<Subcategory> findById(Long id);

    @EntityGraph(attributePaths = {"category"})
    List<Subcategory> findByCategoryId(Long categoryId);

    @EntityGraph(attributePaths = {"category"})
    Page<Subcategory> findByCategoryId(Long categoryId, Pageable pageable);

    @EntityGraph(attributePaths = {"category"})
    Page<Subcategory> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Optional<Subcategory> findByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCase(String name);
}
