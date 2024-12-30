package com.naverrain.grocery.core.service.product;

import com.naverrain.grocery.core.dto.product.BrandDto;

import java.util.List;

public interface BrandService {
    BrandDto createBrand(BrandDto brandDto);
    BrandDto getBrandById(Long id);
    List<BrandDto> getAllBrands();
    BrandDto updateBrand(Long id, BrandDto brandDto);
    void deleteBrand(Long id);
}
