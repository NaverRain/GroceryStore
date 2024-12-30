package com.naverrain.grocery.core.service.product;

import com.naverrain.grocery.core.dto.product.SubcategoryDto;

import java.util.List;

public interface SubcategoryService {

    SubcategoryDto createSubcategory(SubcategoryDto subcategoryDto);

    SubcategoryDto getSubcategoryById(Long id);

    List<SubcategoryDto> getAllSubcategories();

    List<SubcategoryDto> getSubcategoriesByCategory(Long categoryId);

    SubcategoryDto updateSubcategory(Long id, SubcategoryDto subcategoryDto);

    void deleteSubcategory(Long id);

    boolean subcategoryExistsByName(String name);
}
