package com.naverrain.grocery.core.service.product.impl;

import com.naverrain.grocery.core.dto.product.BrandDto;
import com.naverrain.grocery.core.mapper.product.BrandMapper;
import com.naverrain.grocery.core.service.product.BrandService;
import com.naverrain.grocery.core.utils.ResourceNotFoundException;
import com.naverrain.grocery.persistence.entity.product.Brand;
import com.naverrain.grocery.persistence.repository.product.BrandRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
    public BrandDto createBrand(BrandDto brandDto) {
        Brand brand = brandMapper.toEntity(brandDto);
        Brand savedBrand = brandRepository.save(brand);
        return brandMapper.toDto(savedBrand);
    }

    @Override
    public BrandDto getBrandById(Long id) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found with ID: " + id));
        return brandMapper.toDto(brand);
    }

    @Override
    public List<BrandDto> getAllBrands() {
        return brandRepository.findAll()
                .stream()
                .map(brandMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public BrandDto updateBrand(Long id, BrandDto brandDto) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found with ID: " + id));
        brand.setName(brandDto.getName());
        brand.setDescription(brandDto.getDescription());
        Brand updatedBrand = brandRepository.save(brand);
        return brandMapper.toDto(updatedBrand);
    }

    @Override
    public void deleteBrand(Long id) {
        if (!brandRepository.existsById(id)) {
            throw new ResourceNotFoundException("Brand not found with ID: " + id);
        }
        brandRepository.deleteById(id);
    }
}
