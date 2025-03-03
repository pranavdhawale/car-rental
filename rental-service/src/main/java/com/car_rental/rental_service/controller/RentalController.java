package com.car_rental.rental_service.controller;

import com.car_rental.rental_service.dto.RentalDTO;
import com.car_rental.rental_service.service.RentalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {

    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @PostMapping("create")
    public ResponseEntity<RentalDTO> createRental(@RequestBody RentalDTO rentalDTO) {
        return ResponseEntity.ok(rentalService.createRental(rentalDTO));
    }

    @GetMapping("get/{id}")
    public ResponseEntity<RentalDTO> getRental(@PathVariable UUID id) {
        return ResponseEntity.ok(rentalService.getRentalById(id));
    }

    @GetMapping("get-all")
    public ResponseEntity<List<RentalDTO>> getAllRentals() {
        return ResponseEntity.ok(rentalService.getAllRentals());
    }

    @PutMapping("update/{id}")
    public ResponseEntity<RentalDTO> updateRental(@PathVariable UUID id, @RequestBody RentalDTO rentalDTO) {
        return ResponseEntity.ok(rentalService.updateRental(id, rentalDTO));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteRental(@PathVariable UUID id) {
        rentalService.deleteRental(id);
        return ResponseEntity.noContent().build();
    }
}
