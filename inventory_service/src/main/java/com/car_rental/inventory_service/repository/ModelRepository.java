package com.car_rental.inventory_service.repository;

import com.car_rental.inventory_service.entity.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ModelRepository extends JpaRepository<Model, UUID> {
}
