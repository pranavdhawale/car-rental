package com.car_rental.inventory_service.service.impl;

import com.car_rental.inventory_service.dto.CarDetailsDTO;
import com.car_rental.inventory_service.entity.Car;
import java.util.Optional;
import com.car_rental.inventory_service.dto.CarAvailabilityDTO;
import com.car_rental.inventory_service.entity.enums.CarState;
import com.car_rental.inventory_service.repository.CarRepository;
import com.car_rental.inventory_service.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final CarRepository carRepository;
    private static final Logger log = LoggerFactory.getLogger(InventoryServiceImpl.class);
    public CarAvailabilityDTO checkAvailability(UUID carId) {
        Optional<Car> carOpt = carRepository.findById(carId);
        if (carOpt.isPresent()) {
            Car car = carOpt.get();
            boolean isAvailable = car.getState() == CarState.AVAILABLE;
            return new CarAvailabilityDTO(isAvailable, car.getDailyPrice());
        } else {
            // Or throw a specific CarNotFoundException
            return new CarAvailabilityDTO(false, null);
        }
    }

    @Transactional // Make state update transactional
    public boolean updateCarState(UUID carId, CarState newState) {
        Optional<Car> carOpt = carRepository.findById(carId);
        if (carOpt.isPresent()) {
            Car car = carOpt.get();
            // Optional: Add logic to prevent invalid state transitions
            // e.g., if (car.getState() == CarState.AVAILABLE && newState == CarState.RENTED)
            car.setState(newState);
            log.info("--- Inspecting Car before save ---");
            log.info("Car ID: {}", car.getId());
            log.info("Car Plate: {}", car.getPlate());
            log.info("Car Model Year: {}", car.getModelYear());
            log.info("Car State (being set): {}", car.getState());
            log.info("Car Daily Price: {}", car.getDailyPrice());
            // Check the Model carefully
            if (car.getModel() != null) {
                log.info("Car Model ID: {}", car.getModel().getId());
                log.info("Car Model Name: {}", car.getModel().getName());
                if (car.getModel().getBrand() != null) {
                    log.info("Car Brand ID: {}", car.getModel().getBrand().getId());
                    log.info("Car Brand Name: {}", car.getModel().getBrand().getName());
                } else {
                    log.warn("Car Model's Brand is NULL!");
                }
            } else {
                log.error("CRITICAL: Car Model is NULL before save!"); // <-- This is likely the culprit
            }
            log.info("--- End Inspecting Car ---");
            // --- END DEBUG LOGGING ---
            carRepository.save(car);
            return true;
        } else {
            return false; // Car not found
        }
    }
    public Optional<CarDetailsDTO> getCarDetails(UUID carId) {
        return carRepository.findById(carId)
                .map(car -> CarDetailsDTO.builder()
                        .carId(car.getId())
                        .modelName(car.getModel().getName())
                        .brandName(car.getModel().getBrand().getName())
                        .plate(car.getPlate())
                        .modelYear(car.getModelYear())
                        .dailyPrice(car.getDailyPrice())
                        .build());
    }
}

