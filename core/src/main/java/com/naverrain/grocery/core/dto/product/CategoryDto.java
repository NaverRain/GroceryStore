package com.naverrain.grocery.core.dto.product;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CategoryDto {

    private Long id;

    @NotBlank(message = "Category name is required")
    private String name;

    private String description;

    private String imageUrl;

    private List<SubcategoryDto> subcategories;

    private Long version;
}

