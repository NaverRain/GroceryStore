package com.naverrain.grocery.web.controller;

import com.naverrain.grocery.core.dto.product.CategoryDto;
import com.naverrain.grocery.core.dto.product.ProductDto;
import com.naverrain.grocery.core.service.product.CategoryService;
import com.naverrain.grocery.core.service.product.ProductService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    private CategoryService categoryService;
    private ProductService productService;

    public HomeController(CategoryService categoryService, ProductService productService){
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @GetMapping("/")
    public String getHome(Authentication authentication, Model model){
        if (authentication != null && authentication.isAuthenticated()) {
            model.addAttribute("currentUser", authentication.getName());
        }
        List<CategoryDto> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);

        List<ProductDto> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "homepage";
    }
}
