package com.naverrain.grocery.web.controller;

import com.naverrain.grocery.core.dto.product.ProductDto;
import com.naverrain.grocery.core.service.product.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
public class ProductController {

    final private ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping("/product")
    public String doGet(@RequestParam UUID guid, Model model){
        ProductDto product = productService.getProductByGuid(guid);
        model.addAttribute("product", product);
        return "product-details-page";
    }

}
