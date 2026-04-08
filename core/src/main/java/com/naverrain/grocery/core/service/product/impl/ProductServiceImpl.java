package com.naverrain.grocery.core.service.product.impl;

import com.naverrain.grocery.core.dto.product.ProductDto;
import com.naverrain.grocery.core.mapper.product.ProductMapper;
import com.naverrain.grocery.core.service.product.ProductService;
import com.naverrain.grocery.core.utils.ResourceNotFoundException;
import com.naverrain.grocery.persistence.entity.product.Brand;
import com.naverrain.grocery.persistence.entity.product.Category;
import com.naverrain.grocery.persistence.entity.product.Product;
import com.naverrain.grocery.persistence.entity.product.Subcategory;
import com.naverrain.grocery.persistence.repository.product.BrandRepository;
import com.naverrain.grocery.persistence.repository.product.CategoryRepository;
import com.naverrain.grocery.persistence.repository.product.ProductRepository;
import com.naverrain.grocery.persistence.repository.product.SubcategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {


    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final SubcategoryRepository subcategoryRepository;

    public ProductServiceImpl(ProductRepository productRepository,
                              ProductMapper productMapper,
                              CategoryRepository categoryRepository,
                              BrandRepository brandRepository,
                              SubcategoryRepository subcategoryRepository) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.categoryRepository = categoryRepository;
        this.brandRepository = brandRepository;
        this.subcategoryRepository = subcategoryRepository;
    }

    @Override
    public ProductDto create(ProductDto dto) {
        if (productRepository.existsByNameIgnoreCase(dto.getName())) {
            throw new IllegalArgumentException("Product already exists");
        }

        Product product = productMapper.toEntity(dto);

        product.setCategory(getCategory(dto.getCategoryId()));
        product.setBrand(getBrand(dto.getBrandId()));
        product.setSubcategory(getSubcategory(dto.getSubcategoryId()));

        return productMapper.toDto(productRepository.save(product));
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDto getById(Long id) {
        return productRepository.findById(id)
                .map(productMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDto getByGuid(UUID guid) {
        return productRepository.findByGuid(guid)
                .map(productMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + guid));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDto> listForHomepage(int maxResults) {
        int size = Math.max(1, maxResults);
        return productRepository.findAllByOrderByIdDesc(PageRequest.of(0, size)).getContent().stream()
                .map(productMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductDto> getAll(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(productMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductDto> getAllFiltered(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable) {
        Specification<Product> spec = Specification.where(null);
        
        if (minPrice != null) {
            spec = spec.and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("price"), minPrice));
        }
        
        if (maxPrice != null) {
            spec = spec.and((root, query, cb) -> cb.lessThanOrEqualTo(root.get("price"), maxPrice));
        }
        
        return productRepository.findAll(spec, pageable)
                .map(productMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductDto> searchByName(String searchQuery, Pageable pageable) {
        return productRepository.findByNameContainingIgnoreCase(searchQuery, pageable)
                .map(productMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductDto> getByCategory(Long categoryId, Pageable pageable) {
        return productRepository.findByCategoryId(categoryId, pageable)
                .map(productMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductDto> getBySubcategory(Long subcategoryId, Pageable pageable) {
        return productRepository.findBySubcategoryId(subcategoryId, pageable)
                .map(productMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductDto> getByBrand(Long brandId, Pageable pageable) {
        return productRepository.findByBrandId(brandId, pageable)
                .map(productMapper::toDto);
    }

    @Override
    public ProductDto update(Long id, ProductDto dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + id));

        if (dto.getVersion() != null && !Objects.equals(dto.getVersion(), product.getVersion())) {
            throw new IllegalStateException("Product was modified by another operation. Please refresh and try again.");
        }

        if (!product.getName().equalsIgnoreCase(dto.getName()) &&
                productRepository.existsByNameIgnoreCase(dto.getName())) {
            throw new IllegalArgumentException("Product already exists");
        }

        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setQuantity(dto.getQuantity());
        product.setImageUrl(dto.getImageUrl());

        product.setCategory(getCategory(dto.getCategoryId()));
        product.setBrand(getBrand(dto.getBrandId()));
        product.setSubcategory(getSubcategory(dto.getSubcategoryId()));

        Product saved = productRepository.save(product);
        return productMapper.toDto(saved);
    }

    @Override
    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product not found with ID: " + id);
        }
        productRepository.deleteById(id);
    }

    @Override
    public boolean existsByName(String name) {
        return productRepository.existsByNameIgnoreCase(name);
    }

    @Override
    public boolean existsByGuid(UUID guid) {
        return productRepository.existsByGuid(guid);
    }

    private Category getCategory(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found: " + id));
    }

    private Brand getBrand(Long id) {
        return brandRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found: " + id));
    }

    private Subcategory getSubcategory(Long id) {
        if (id == null) return null;
        return subcategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subcategory not found: " + id));
    }
}