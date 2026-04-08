package com.naverrain.grocery.web.controller;

import com.naverrain.grocery.core.dto.product.ProductDto;
import com.naverrain.grocery.core.service.product.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String getAllProducts(@PageableDefault(size = 12) Pageable pageable,
                                 @RequestParam(required = false) BigDecimal minPrice,
                                 @RequestParam(required = false) BigDecimal maxPrice,
                                 @RequestParam Map<String, String> allParams,
                                 Model model) {
        
        Page<ProductDto> productPage = productService.getAllFiltered(minPrice, maxPrice, pageable);

        String queryString = allParams.entrySet().stream()
                .filter(entry -> !entry.getKey().equals("page"))
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining("&"));

        model.addAttribute("basePath", "/products");
        model.addAttribute("queryString", queryString.isEmpty() ? "" : "&" + queryString);
        model.addAttribute("products", productPage);
        model.addAttribute("pages", productPage.getTotalPages());
        model.addAttribute("activePage", pageable.getPageNumber() + 1);
        
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);

        return "product-list-page";
    }

    @GetMapping("/{guid}")
    public String getProductByGuid(@PathVariable UUID guid, Model model) {
        ProductDto product = productService.getByGuid(guid);
        model.addAttribute("product", product);
        return "product-details-page";
    }
}