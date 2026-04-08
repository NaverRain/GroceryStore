package com.naverrain.grocery.web.controller;

import com.naverrain.grocery.core.dto.product.BrandDto;
import com.naverrain.grocery.core.dto.product.ProductDto;
import com.naverrain.grocery.core.service.product.BrandService;
import com.naverrain.grocery.core.service.product.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/brands")
public class BrandController {

    private final BrandService brandService;
    private final ProductService productService;

    public BrandController(BrandService brandService, ProductService productService) {
        this.brandService = brandService;
        this.productService = productService;
    }

    @GetMapping
    public String getAllBrands(@PageableDefault(size = 10) Pageable pageable, Model model) {
        Page<BrandDto> brandPage = brandService.getAll(pageable);

        model.addAttribute("brands", brandPage.getContent());
        model.addAttribute("pages", brandPage.getTotalPages());
        model.addAttribute("activePage", pageable.getPageNumber() + 1);

        return "brand-list-page";
    }

    @GetMapping("/{id}/products")
    public String getAllProductsByBrand(@PathVariable Long id, @PageableDefault(size = 10) Pageable pageable, Model model){
        Page<ProductDto> productPage = productService.getByBrand(id, pageable);

        model.addAttribute("basePath", "/brands/" + id + "/products");
        model.addAttribute("products", productPage.getContent());
        model.addAttribute("pages", productPage.getTotalPages());
        model.addAttribute("activePage", pageable.getPageNumber() + 1);
        return "product-list-page";
    }
}
