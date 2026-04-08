package com.naverrain.grocery.web.controller;

import com.naverrain.grocery.core.dto.product.CategoryDto;
import com.naverrain.grocery.core.dto.product.ProductDto;
import com.naverrain.grocery.core.service.product.CategoryService;
import com.naverrain.grocery.core.service.product.ProductService;
import com.naverrain.grocery.web.config.HomepageProperties;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    private final CategoryService categoryService;
    private final ProductService productService;
    private final HomepageProperties homepageProperties;

    public HomeController(CategoryService categoryService,
                          ProductService productService,
                          HomepageProperties homepageProperties) {
        this.categoryService = categoryService;
        this.productService = productService;
        this.homepageProperties = homepageProperties;
    }

    @GetMapping("/")
    public String getHome(Authentication authentication, Model model) {
        if (authentication != null && authentication.isAuthenticated()) {
            model.addAttribute("currentUser", authentication.getName());
        }
        List<CategoryDto> categories = categoryService.listForHomepage(homepageProperties.getCategoriesLimit());
        model.addAttribute("categories", categories);

        List<ProductDto> products = productService.listForHomepage(homepageProperties.getProductsLimit());
        model.addAttribute("products", products);
        return "homepage";
    }
}
