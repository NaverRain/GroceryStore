package com.naverrain.grocery.core.mapper.product;

import com.naverrain.grocery.core.dto.product.SubcategoryDto;
import com.naverrain.grocery.persistence.entity.product.Category;
import com.naverrain.grocery.persistence.entity.product.Subcategory;
import org.springframework.stereotype.Component;

@Component
public class SubcategoryMapper {

    public SubcategoryDto toDto(Subcategory subcategory) {
        if (subcategory == null) {
            return null;
        }
        SubcategoryDto dto = new SubcategoryDto();
        dto.setId(subcategory.getId());
        dto.setName(subcategory.getName());
        dto.setDescription(subcategory.getDescription());
        dto.setCategoryId(subcategory.getCategory().getId());
        return dto;
    }

    public Subcategory toEntity(SubcategoryDto dto) {
        if (dto == null) {
            return null;
        }
        Subcategory subcategory = new Subcategory();
        subcategory.setId(dto.getId());
        subcategory.setName(dto.getName());
        subcategory.setDescription(dto.getDescription());

        Category category = new Category();
        category.setId(dto.getCategoryId());
        subcategory.setCategory(category);

        return subcategory;
    }
}
