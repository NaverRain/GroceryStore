package com.naverrain.grocery.core.dto.product;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubcategoryDto {

    private Long id;

    @NotBlank(message = "Subcategory name is required")
    private String name;

    private String description;

    private Long categoryId;

    private String imageUrl;

    private Long version;
}
