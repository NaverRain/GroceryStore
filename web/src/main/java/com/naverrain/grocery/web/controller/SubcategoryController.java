package com.naverrain.grocery.web.controller;

import com.naverrain.grocery.core.dto.product.ProductDto;
import com.naverrain.grocery.core.dto.product.SubcategoryDto;
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
@RequestMapping("/subcategories")
public class SubcategoryController {

    private final SubcategoryService subcategoryService;
    private final ProductService productService;

    public SubcategoryController(SubcategoryService subcategoryService, ProductService productService) {
        this.subcategoryService = subcategoryService;
        this.productService = productService;
    }

    @GetMapping
    public String getAllSubcategories(@PageableDefault(size = 10) Pageable pageable, Model model) {
        Page<SubcategoryDto> subcategoryPage = subcategoryService.getAll(pageable);

        model.addAttribute("subcategories", subcategoryPage.getContent());
        model.addAttribute("pages", subcategoryPage.getTotalPages());
        model.addAttribute("activePage", pageable.getPageNumber() + 1);

        return "subcategory-list-page";
    }

    @GetMapping("/{id}/products")
    public String getAllProductsByBrand(@PathVariable Long id, @PageableDefault(size = 10) Pageable pageable, Model model){
        Page<ProductDto> productPage = productService.getBySubcategory(id, pageable);

        model.addAttribute("basePath", "/subcategories/" + id + "/products");

        model.addAttribute("products", productPage.getContent());
        model.addAttribute("pages", productPage.getTotalPages());
        model.addAttribute("activePage", pageable.getPageNumber() + 1);
        return "product-list-page";
    }
}