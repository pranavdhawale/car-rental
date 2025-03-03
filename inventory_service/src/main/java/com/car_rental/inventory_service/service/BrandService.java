package com.car_rental.inventory_service.service;

import com.car_rental.inventory_service.entity.Brand;
import java.util.List;
import java.util.UUID;

public interface BrandService {
    List<Brand> getAllBrands();
    Brand getBrandById(UUID id);
    Brand saveBrand(Brand brand);
    void deleteBrand(UUID id);
}
