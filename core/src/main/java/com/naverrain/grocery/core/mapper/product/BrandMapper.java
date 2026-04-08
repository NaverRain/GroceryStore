package com.naverrain.grocery.core.mapper.product;

import com.naverrain.grocery.core.dto.product.BrandDto;
import com.naverrain.grocery.persistence.entity.product.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BrandMapper {

    BrandDto toDto(Brand entity);

    @Mapping(target = "version", ignore = true)
    Brand toEntity(BrandDto dto);
}
