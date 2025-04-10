package com.car_rental.inventory_service.service;

import com.car_rental.inventory_service.dto.CarAvailabilityDTO;
import com.car_rental.inventory_service.dto.CarDetailsDTO;
import com.car_rental.inventory_service.entity.enums.CarState;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
public interface InventoryService {
    CarAvailabilityDTO checkAvailability(UUID carId);
    boolean updateCarState(UUID carId, CarState newState);
    Optional<CarDetailsDTO> getCarDetails(UUID carId);
}
