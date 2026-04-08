package com.naverrain.grocery.core.mapper.product;

import com.naverrain.grocery.core.dto.product.CategoryDto;
import com.naverrain.grocery.persistence.entity.product.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = SubcategoryMapper.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {

    CategoryDto toDto(Category entity);

    @Mapping(target = "subcategories", ignore = true)
    @Mapping(target = "products", ignore = true)
    @Mapping(target = "version", ignore = true)
    Category toEntity(CategoryDto dto);
}
