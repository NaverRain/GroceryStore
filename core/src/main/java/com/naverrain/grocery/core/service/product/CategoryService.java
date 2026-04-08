package com.naverrain.grocery.core.service.product;

import com.naverrain.grocery.core.dto.product.CategoryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {
    CategoryDto create(CategoryDto dto);
    CategoryDto getById(Long id);

    List<CategoryDto> listForHomepage(int maxResults);

    Page<CategoryDto> getAll(Pageable pageable);
    Page<CategoryDto> searchByName(String name, Pageable pageable);
    CategoryDto update(Long id, CategoryDto dto);
    void delete(Long id);
    boolean existsByName(String name);
}