package com.car_rental.inventory_service.controller;
import com.car_rental.inventory_service.dto.CarAvailabilityDTO;
import com.car_rental.inventory_service.dto.CarDetailsDTO;
import com.car_rental.inventory_service.dto.UpdateCarStateDTO; // Assuming you have this service
import com.car_rental.inventory_service.service.impl.InventoryServiceImpl;
import lombok.RequiredArgsConstructor;
import com.car_rental.inventory_service.service.InventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/inventory/cars")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;
    private static final Logger log = LoggerFactory.getLogger(InventoryController.class);

    @GetMapping("/{carId}/availability")
    public ResponseEntity<CarAvailabilityDTO> checkCarAvailability(@PathVariable UUID carId) {
        CarAvailabilityDTO availability = inventoryService.checkAvailability(carId);
        return ResponseEntity.ok(availability);
    }

    @PutMapping("/{carId}/state")
    public ResponseEntity<Void> updateCarState(@PathVariable UUID carId, @RequestBody UpdateCarStateDTO updateDto) {

        log.info("Received updateCarState request for carId: {}", carId);
        if (updateDto != null) {
            log.info("Received DTO state value: {}", updateDto.getState()); // Check this value!
        } else {
            log.error("Received NULL UpdateCarStateDTO!");
        }

        boolean updated = inventoryService.updateCarState(carId, updateDto.getState());
        if (updated) {
            return ResponseEntity.ok().build();
        } else {
            // Or handle specific errors like CarNotFound
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/{carId}/details")
    public ResponseEntity<CarDetailsDTO> getCarDetails(@PathVariable UUID carId) {
        Optional<CarDetailsDTO> details = inventoryService.getCarDetails(carId);
        return details.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}