package com.naverrain.grocery.persistence.repository.product;

import com.naverrain.grocery.persistence.entity.product.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {

    List<Brand> findByNameContainingIgnoreCase(String name);
}
