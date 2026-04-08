package com.naverrain.grocery.core.dto.product;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BrandDto {

    private Long id;

    @NotBlank(message = "Brand name is required")
    private String name;

    private String description;

    private String imageUrl;

    private Long version;
}

