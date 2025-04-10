package com.car_rental.rental_service.client;

import com.car_rental.rental_service.dto.inventory.CarAvailabilityDTO;
import com.car_rental.rental_service.dto.inventory.CarDetailsDTO;
import com.car_rental.rental_service.dto.inventory.UpdateCarStateDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@FeignClient(name = "inventory-service", url = "http://localhost:8084/api/v1/inventory/cars") // change port as needed

//@FeignClient(name = "inventoryClient", url = "${inventory_service.url}", path = "/api/v1/inventory/cars")
public interface InventoryServiceClient {

    @GetMapping("/{carId}/availability")
    ResponseEntity<CarAvailabilityDTO> checkCarAvailability(@PathVariable("carId") UUID carId);

    @PutMapping("/{carId}/state")
    ResponseEntity<Void> updateCarState(@PathVariable("carId") UUID carId, @RequestBody UpdateCarStateDTO updateDto);

    @GetMapping("/{carId}/details")
    ResponseEntity<CarDetailsDTO> getCarDetails(@PathVariable("carId") UUID carId);
}