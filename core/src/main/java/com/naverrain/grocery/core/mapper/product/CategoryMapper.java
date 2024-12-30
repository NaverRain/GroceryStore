package com.naverrain.grocery.core.mapper.product;

import com.naverrain.grocery.core.dto.product.CategoryDto;
import com.naverrain.grocery.core.dto.product.SubcategoryDto;
import com.naverrain.grocery.persistence.entity.product.Category;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class CategoryMapper {

    private final SubcategoryMapper subcategoryMapper;

    public CategoryMapper(SubcategoryMapper subcategoryMapper){
        this.subcategoryMapper = subcategoryMapper;
    }


    public CategoryDto toDto(Category category) {
        if (category == null) {
            return null;
        }
        CategoryDto dto = new CategoryDto();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());
        dto.setImageUrl(category.getImageUrl());

        List<SubcategoryDto> subcategoryDtos = category.getSubcategories().stream()
                .map(subcategoryMapper::toDto)
                .collect(Collectors.toList());
        dto.setSubcategories(subcategoryDtos);
        return dto;
    }

    public Category toEntity(CategoryDto dto) {
        if (dto == null) {
            return null;
        }
        Category category = new Category();
        category.setId(dto.getId());
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        category.setImageUrl(dto.getImageUrl());
        return category;
    }
}
