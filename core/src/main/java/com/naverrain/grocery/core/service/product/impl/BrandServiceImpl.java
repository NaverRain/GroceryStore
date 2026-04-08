package com.naverrain.grocery.core.service.product.impl;

import com.naverrain.grocery.core.dto.product.BrandDto;
import com.naverrain.grocery.core.mapper.product.BrandMapper;
import com.naverrain.grocery.core.service.product.BrandService;
import com.naverrain.grocery.core.utils.ResourceNotFoundException;
import com.naverrain.grocery.persistence.entity.product.Brand;
import com.naverrain.grocery.persistence.repository.product.BrandRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Transactional
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;

    public BrandServiceImpl(BrandRepository brandRepository, BrandMapper brandMapper) {
        this.brandRepository = brandRepository;
        this.brandMapper = brandMapper;
    }

    @Override
    public BrandDto create(BrandDto dto) {
        if (brandRepository.existsByNameIgnoreCase(dto.getName())){
            throw new IllegalArgumentException("Brand already exists");
        }

        Brand brand = brandMapper.toEntity(dto);
        brand.setId(null);
        brand.setVersion(null);

        Brand savedBrand = brandRepository.save(brand);
        return brandMapper.toDto(savedBrand);
    }

    @Override
    @Transactional(readOnly = true)
    public BrandDto getById(Long id) {
        return brandRepository.findById(id)
                .map(brandMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found with ID: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BrandDto> getAll(Pageable pageable) {
        return brandRepository.findAll(pageable)
                .map(brandMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BrandDto> searchByName(String name, Pageable pageable) {
        return brandRepository.findByNameContainingIgnoreCase(name, pageable)
                .map(brandMapper::toDto);
    }

    @Override
    public BrandDto update(Long id, BrandDto dto) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found with ID: " + id));

        if (dto.getVersion() != null && !Objects.equals(dto.getVersion(), brand.getVersion())) {
            throw new IllegalStateException("Brand was modified by another operation. Please refresh and try again.");
        }

        if (!brand.getName().equalsIgnoreCase(dto.getName())
                && brandRepository.existsByNameIgnoreCase(dto.getName())){
            throw new IllegalArgumentException("Brand already exists");
        }

        brand.setName(dto.getName());
        brand.setDescription(dto.getDescription());
        brand.setImageUrl(dto.getImageUrl());

        Brand saved = brandRepository.save(brand);
        return brandMapper.toDto(saved);
    }

    @Override
    public void delete(Long id) {
        if (!brandRepository.existsById(id)) {
            throw new ResourceNotFoundException("Brand not found with ID: " + id);
        }
        brandRepository.deleteById(id);
    }

    @Override
    public boolean existsByNameIgnoreCase(String name) {
        return brandRepository.existsByNameIgnoreCase(name);
    }
}
