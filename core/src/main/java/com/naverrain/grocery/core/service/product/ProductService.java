package com.naverrain.grocery.core.service.product;

import com.naverrain.grocery.core.dto.product.ProductDto;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    List<ProductDto> getAllProducts();

    List<ProductDto> getProductsLikeName(String searchQuery);

    List<ProductDto> getProductsByCategoryId(Long categoryId);

    List<ProductDto> getProductsByCategoryIdForPageWithLimit(Long categoryId, int page, int pageSize);

    int getNumberOfPagesForCategory(Long categoryId, int pageSize);

    int getNumberOfPagesForSearch(String searchQuery, int pageSize);

    List<ProductDto> getProductsLikeNameForPageWithLimit(String searchQuery, int page, int pageSize);

    ProductDto getProductById(Long id);

    ProductDto getProductByGuid(UUID guid);

    List<ProductDto> findBySubcategoryId(Long subcategoryId);

    List<ProductDto> findByBrandId(Long brandId);
}
