package com.naverrain.grocery.core.service.product.impl;

import com.naverrain.grocery.core.dto.product.SubcategoryDto;
import com.naverrain.grocery.core.mapper.product.SubcategoryMapper;
import com.naverrain.grocery.core.service.product.SubcategoryService;
import com.naverrain.grocery.core.utils.ResourceNotFoundException;
import com.naverrain.grocery.persistence.entity.product.Subcategory;
import com.naverrain.grocery.persistence.repository.product.SubcategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SubcategoryServiceImpl implements SubcategoryService {

    private final SubcategoryRepository subcategoryRepository;
    private final SubcategoryMapper subcategoryMapper;

    public SubcategoryServiceImpl(SubcategoryRepository subcategoryRepository, SubcategoryMapper subcategoryMapper) {
        this.subcategoryRepository = subcategoryRepository;
        this.subcategoryMapper = subcategoryMapper;
    }

    @Override
    public SubcategoryDto createSubcategory(SubcategoryDto subcategoryDto) {
        if (subcategoryExistsByName(subcategoryDto.getName())) {
            throw new IllegalArgumentException("Subcategory with the same name already exists");
        }
        Subcategory subcategory = subcategoryMapper.toEntity(subcategoryDto);
        subcategory = subcategoryRepository.save(subcategory);
        return subcategoryMapper.toDto(subcategory);
    }

    @Override
    public SubcategoryDto getSubcategoryById(Long id) {
        Subcategory subcategory = subcategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subcategory not found"));
        return subcategoryMapper.toDto(subcategory);
    }

    @Override
    public List<SubcategoryDto> getAllSubcategories() {
        List<Subcategory> subcategories = subcategoryRepository.findAll();
        return subcategories.stream()
                .map(subcategoryMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<SubcategoryDto> getSubcategoriesByCategory(Long categoryId) {
        List<Subcategory> subcategories = subcategoryRepository.findByCategoryId(categoryId);
        return subcategories.stream()
                .map(subcategoryMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public SubcategoryDto updateSubcategory(Long id, SubcategoryDto subcategoryDto) {
        Subcategory existingSubcategory = subcategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subcategory not found"));

        if (!existingSubcategory.getName().equals(subcategoryDto.getName()) && subcategoryExistsByName(subcategoryDto.getName())) {
            throw new IllegalArgumentException("Subcategory with the same name already exists");
        }

        existingSubcategory.setName(subcategoryDto.getName());
        existingSubcategory.setDescription(subcategoryDto.getDescription());
        existingSubcategory = subcategoryRepository.save(existingSubcategory);
        return subcategoryMapper.toDto(existingSubcategory);
    }

    @Override
    public void deleteSubcategory(Long id) {
        subcategoryRepository.deleteById(id);
    }

    @Override
    public boolean subcategoryExistsByName(String name) {
        return subcategoryRepository.existsByName(name);
    }
}
