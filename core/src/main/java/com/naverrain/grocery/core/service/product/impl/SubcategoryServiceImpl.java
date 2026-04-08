package com.naverrain.grocery.core.service.product.impl;

import com.naverrain.grocery.core.dto.product.SubcategoryDto;
import com.naverrain.grocery.core.mapper.product.SubcategoryMapper;
import com.naverrain.grocery.core.service.product.SubcategoryService;
import com.naverrain.grocery.core.utils.ResourceNotFoundException;
import com.naverrain.grocery.persistence.entity.product.Category;
import com.naverrain.grocery.persistence.entity.product.Subcategory;
import com.naverrain.grocery.persistence.repository.product.CategoryRepository;
import com.naverrain.grocery.persistence.repository.product.SubcategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Transactional
public class SubcategoryServiceImpl implements SubcategoryService {

    private final SubcategoryRepository subcategoryRepository;
    private final SubcategoryMapper subcategoryMapper;
    private final CategoryRepository categoryRepository;

    public SubcategoryServiceImpl(SubcategoryRepository subcategoryRepository,
                                  SubcategoryMapper subcategoryMapper,
                                  CategoryRepository categoryRepository) {
        this.subcategoryRepository = subcategoryRepository;
        this.subcategoryMapper = subcategoryMapper;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public SubcategoryDto create(SubcategoryDto dto) {
        if (subcategoryRepository.existsByNameIgnoreCase(dto.getName())) {
            throw new IllegalArgumentException("Subcategory with the same name already exists");
        }

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + dto.getCategoryId()));

        Subcategory subcategory = subcategoryMapper.toEntity(dto, category);
        subcategory.setId(null);
        subcategory.setVersion(null);

        return subcategoryMapper.toDto(subcategoryRepository.save(subcategory));
    }

    @Override
    @Transactional(readOnly = true)
    public SubcategoryDto getById(Long id) {
        return subcategoryRepository.findById(id)
                .map(subcategoryMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Subcategory not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SubcategoryDto> getAll(Pageable pageable) {
        return subcategoryRepository.findAll(pageable)
                .map(subcategoryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SubcategoryDto> getByCategory(Long categoryId, Pageable pageable) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new ResourceNotFoundException("Category not found with id: " + categoryId);
        }
        return subcategoryRepository.findByCategoryId(categoryId, pageable)
                .map(subcategoryMapper::toDto);
    }

    @Override
    public SubcategoryDto update(Long id, SubcategoryDto dto) {
        Subcategory existing = subcategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subcategory not found with id: " + id));

        if (dto.getVersion() != null && !Objects.equals(dto.getVersion(), existing.getVersion())) {
            throw new IllegalStateException("Subcategory was modified by another operation. Please refresh and try again.");
        }

        if (!Objects.equals(existing.getName(), dto.getName())
                && subcategoryRepository.existsByNameIgnoreCase(dto.getName())) {
            throw new IllegalArgumentException("Subcategory with the same name already exists");
        }

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + dto.getCategoryId()));

        Subcategory updated = subcategoryMapper.toEntity(dto, category);
        updated.setId(existing.getId());
        updated.setVersion(existing.getVersion());

        return subcategoryMapper.toDto(subcategoryRepository.save(updated));
    }

    @Override
    public void delete(Long id) {
        if (!subcategoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Subcategory not found with id: " + id);
        }
        subcategoryRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByName(String name) {
        return subcategoryRepository.existsByNameIgnoreCase(name);
    }
}
