package com.car_rental.inventory_service.repository;

import com.car_rental.inventory_service.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface BrandRepository extends JpaRepository<Brand, UUID> {
}
