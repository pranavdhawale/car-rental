package com.car_rental.inventory_service.repository;

import com.car_rental.inventory_service.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface CarRepository extends JpaRepository<Car, UUID> {
}
