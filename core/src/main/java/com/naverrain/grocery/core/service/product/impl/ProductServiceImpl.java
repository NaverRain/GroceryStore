package com.naverrain.grocery.core.service.product.impl;

import com.naverrain.grocery.core.dto.product.ProductDto;
import com.naverrain.grocery.core.mapper.product.ProductMapper;
import com.naverrain.grocery.core.service.product.ProductService;
import com.naverrain.grocery.core.utils.ResourceNotFoundException;
import com.naverrain.grocery.persistence.entity.product.Product;
import com.naverrain.grocery.persistence.repository.product.BrandRepository;
import com.naverrain.grocery.persistence.repository.product.CategoryRepository;
import com.naverrain.grocery.persistence.repository.product.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper,
                              CategoryRepository categoryRepository, BrandRepository brandRepository) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.categoryRepository = categoryRepository;
        this.brandRepository = brandRepository;
    }


    private Pageable createPageRequest(int page, int pageSize) {
        return PageRequest.of(page - 1, pageSize);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return productMapper.toDtoList(products);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDto> getProductsLikeName(String searchQuery) {
        List<Product> products = productRepository.findByNameContainingIgnoreCase(searchQuery);
        return productMapper.toDtoList(products);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDto> getProductsByCategoryId(Long categoryId) {
        validateCategoryExists(categoryId);
        List<Product> products = productRepository.findByCategoryId(categoryId);
        return productMapper.toDtoList(products);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDto> getProductsByCategoryIdForPageWithLimit(Long categoryId, int page, int pageSize) {
        validateCategoryExists(categoryId);
        Pageable pageable = createPageRequest(page, pageSize);
        Page<Product> productPage = productRepository.findByCategoryId(categoryId, pageable);
        return productMapper.toDtoList(productPage.getContent());
    }

    @Override
    @Transactional(readOnly = true)
    public int getNumberOfPagesForCategory(Long categoryId, int pageSize) {
        validateCategoryExists(categoryId);
        long totalProducts = productRepository.countByCategoryId(categoryId);
        return calculateNumberOfPages(totalProducts, pageSize);
    }

    @Override
    @Transactional(readOnly = true)
    public int getNumberOfPagesForSearch(String searchQuery, int pageSize) {
        long totalProducts = productRepository.countByNameContainingIgnoreCase(searchQuery);
        return calculateNumberOfPages(totalProducts, pageSize);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDto> getProductsLikeNameForPageWithLimit(String searchQuery, int page, int pageSize) {
        Pageable pageable = createPageRequest(page, pageSize);
        Page<Product> productPage = productRepository.findByNameContainingIgnoreCase(searchQuery, pageable);
        return productMapper.toDtoList(productPage.getContent());
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        return productMapper.toDto(product);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDto getProductByGuid(UUID guid) {
        Product product = productRepository.findByGuid(guid)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with GUID: " + guid));
        return productMapper.toDto(product);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDto> findBySubcategoryId(Long subcategoryId) {
        List<Product> products = productRepository.findBySubcategoryId(subcategoryId);
        return productMapper.toDtoList(products);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDto> findByBrandId(Long brandId) {
        validateBrandExists(brandId);
        List<Product> products = productRepository.findByBrandId(brandId);
        return productMapper.toDtoList(products);
    }

    // Helper Methods

    private void validateCategoryExists(Long categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new ResourceNotFoundException("Category not found with id: " + categoryId);
        }
    }

    private void validateBrandExists(Long brandId) {
        if (!brandRepository.existsById(brandId)) {
            throw new ResourceNotFoundException("Brand not found with id: " + brandId);
        }
    }

    private int calculateNumberOfPages(long totalItems, int pageSize) {
        return (int) Math.ceil((double) totalItems / pageSize);
    }
}

