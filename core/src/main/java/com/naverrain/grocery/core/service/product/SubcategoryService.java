package com.naverrain.grocery.core.service.product;

import com.naverrain.grocery.core.dto.product.SubcategoryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SubcategoryService {
    SubcategoryDto create(SubcategoryDto dto);
    SubcategoryDto getById(Long id);

    Page<SubcategoryDto> getAll(Pageable pageable);
    Page<SubcategoryDto> getByCategory(Long categoryId, Pageable pageable);

    SubcategoryDto update(Long id, SubcategoryDto dto);
    void delete(Long id);

    boolean existsByName(String name);
}