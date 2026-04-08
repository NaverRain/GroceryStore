package com.naverrain.grocery.core.dto.product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class ProductDto {

    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotNull
    @Positive
    private BigDecimal price;

    @NotNull
    @Min(0)
    private Integer quantity;

    private String imageUrl;

    private UUID guid;

    private Long version;

    @NotNull
    private Long categoryId;

    @NotNull
    private Long brandId;

    private Long subcategoryId;

    private String categoryName;
    private String brandName;
    private String subcategoryName;
}
