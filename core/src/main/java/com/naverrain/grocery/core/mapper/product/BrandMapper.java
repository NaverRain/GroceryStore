package com.naverrain.grocery.core.mapper.product;

import com.naverrain.grocery.core.dto.product.BrandDto;
import com.naverrain.grocery.persistence.entity.product.Brand;
import org.springframework.stereotype.Component;


@Component
public class BrandMapper {

    public BrandDto toDto(Brand brand) {
        if (brand == null) {
            return null;
        }
        BrandDto dto = new BrandDto();
        dto.setId(brand.getId());
        dto.setName(brand.getName());
        dto.setDescription(brand.getDescription());
        dto.setImageUrl(brand.getImageUrl());
        return dto;
    }

    public Brand toEntity(BrandDto dto) {
        if (dto == null) {
            return null;
        }
        Brand brand = new Brand();
        brand.setId(dto.getId());
        brand.setName(dto.getName());
        brand.setDescription(dto.getDescription());
        brand.setImageUrl(dto.getImageUrl());
        return brand;
    }
}
