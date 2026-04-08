package com.naverrain.grocery.core.service.product;

import com.naverrain.grocery.core.dto.product.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface ProductService {
    ProductDto create(ProductDto dto);
    ProductDto getById(Long id);
    ProductDto getByGuid(UUID guid);

    List<ProductDto> listForHomepage(int maxResults);

    Page<ProductDto> getAll(Pageable pageable);
    
    Page<ProductDto> getAllFiltered(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);
    
    Page<ProductDto> searchByName(String searchQuery, Pageable pageable);

    Page<ProductDto> getByCategory(Long categoryId, Pageable pageable);
    Page<ProductDto> getBySubcategory(Long subcategoryId, Pageable pageable);
    Page<ProductDto> getByBrand(Long brandId, Pageable pageable);

    ProductDto update(Long id, ProductDto dto);
    void delete(Long id);

    boolean existsByName(String name);
    boolean existsByGuid(UUID guid);
}