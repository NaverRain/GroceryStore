package com.naverrain.grocery.core.mapper.product;

import com.naverrain.grocery.core.dto.product.SubcategoryDto;
import com.naverrain.grocery.persistence.entity.product.Category;
import com.naverrain.grocery.persistence.entity.product.Subcategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SubcategoryMapper {

    @Mapping(source = "category.id", target = "categoryId")
    SubcategoryDto toDto(Subcategory entity);

    @Mapping(target = "category", ignore = true)
    @Mapping(target = "products", ignore = true)
    @Mapping(target = "version", ignore = true)
    Subcategory toEntity(SubcategoryDto dto);

    default Subcategory toEntity(SubcategoryDto dto, Category category) {
        if (dto == null) {
            return null;
        }
        Subcategory entity = toEntity(dto);
        entity.setCategory(category);
        return entity;
    }
}
