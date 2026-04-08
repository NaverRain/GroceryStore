package com.naverrain.grocery.core.service.product;

import com.naverrain.grocery.core.dto.product.BrandDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BrandService {
    BrandDto create(BrandDto dto);
    BrandDto getById(Long id);
    Page<BrandDto> getAll(Pageable pageable);
    Page<BrandDto> searchByName(String name, Pageable pageable);
    BrandDto update(Long id, BrandDto dto);
    void delete(Long id);
    boolean existsByNameIgnoreCase(String name);
}