package com.naverrain.grocery.web.controller;

import com.naverrain.grocery.core.dto.product.CategoryDto;
import com.naverrain.grocery.core.dto.product.ProductDto;
import com.naverrain.grocery.core.dto.product.SubcategoryDto;
import com.naverrain.grocery.core.service.product.CategoryService;
import com.naverrain.grocery.core.service.product.ProductService;
import com.naverrain.grocery.core.service.product.SubcategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final SubcategoryService subcategoryService;

    public CategoryController(CategoryService categoryService,
                              SubcategoryService subcategoryService) {
        this.categoryService = categoryService;
        this.subcategoryService = subcategoryService;
    }

    @GetMapping
    public String getAllCategories(@PageableDefault(size = 10) Pageable pageable, Model model) {
        Page<CategoryDto> categoryPage = categoryService.getAll(pageable);

        model.addAttribute("categories", categoryPage.getContent());
        model.addAttribute("pages", categoryPage.getTotalPages());
        model.addAttribute("activePage", pageable.getPageNumber() + 1);

        return "category-list-page";
    }

    @GetMapping("/{id}/subcategories")
    public String getSubcategoriesByCategoryId(@PathVariable Long id, @PageableDefault(size = 10) Pageable pageable, Model model){
        Page<SubcategoryDto> subcategoryPage = subcategoryService.getByCategory(id, pageable);

        model.addAttribute("basePath", "/categories/" + id + "/subcategories");

        model.addAttribute("subcategories", subcategoryPage.getContent());
        model.addAttribute("pages", subcategoryPage.getTotalPages());
        model.addAttribute("activePage", pageable.getPageNumber() + 1);

        return "subcategory-list-page";
    }
}