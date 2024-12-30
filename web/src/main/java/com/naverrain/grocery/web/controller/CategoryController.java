package com.naverrain.grocery.web.controller;

import com.naverrain.grocery.core.dto.product.ProductDto;
import com.naverrain.grocery.core.service.product.ProductService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.IntStream;

@Controller
public class CategoryController {


    @Value("${limit}")
    private Integer paginationLimit;

    private final ProductService productService;

    public CategoryController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/category/{id}")
    public String getProductsByCategory(
            @PathVariable("id") Long categoryId,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            Model model) {

        int totalPages = productService.getNumberOfPagesForCategory(categoryId, paginationLimit);
        if (page < 1 || page > totalPages) {
            page = 1;
        }

        List<ProductDto> products = productService.getProductsByCategoryIdForPageWithLimit(categoryId, page, paginationLimit);
        List<Integer> pages = IntStream.range(1, totalPages + 1).boxed().toList();

        model.addAttribute("products", products);
        model.addAttribute("pages", pages);
        model.addAttribute("activePage", page);
        model.addAttribute("categoryId", categoryId);

        return "product-list-page";
    }


}