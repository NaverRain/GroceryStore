package com.naverrain.grocery.core.mapper.product;

import com.naverrain.grocery.core.dto.product.ProductDto;
import com.naverrain.grocery.persistence.entity.product.Product;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductMapper {

    private final CategoryMapper categoryMapper;
    private final BrandMapper brandMapper;
    private final SubcategoryMapper subcategoryMapper;

    public ProductMapper(CategoryMapper categoryMapper, BrandMapper brandMapper, SubcategoryMapper subcategoryMapper) {
        this.categoryMapper = categoryMapper;
        this.brandMapper = brandMapper;
        this.subcategoryMapper = subcategoryMapper;
    }

    public ProductDto toDto(Product product) {
        if (product == null) {
            return null;
        }
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setQuantity(product.getQuantity());
        dto.setImageUrl(product.getImageUrl());
        dto.setGuid(product.getGuid());
        dto.setCategory(categoryMapper.toDto(product.getCategory()));
        dto.setBrand(brandMapper.toDto(product.getBrand()));
        dto.setSubcategory(subcategoryMapper.toDto(product.getSubcategory()));
        return dto;
    }

    public Product toEntity(ProductDto dto) {
        if (dto == null) {
            return null;
        }
        Product product = new Product();
        product.setId(dto.getId());
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setQuantity(dto.getQuantity());
        product.setImageUrl(dto.getImageUrl());
        product.setGuid(dto.getGuid());
        product.setCategory(categoryMapper.toEntity(dto.getCategory()));
        product.setBrand(brandMapper.toEntity(dto.getBrand()));
        product.setSubcategory(subcategoryMapper.toEntity(dto.getSubcategory()));
        return product;
    }

    public List<ProductDto> toDtoList(List<Product> products) {
        if (products == null || products.isEmpty()) {
            return List.of();
        }
        return products.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}