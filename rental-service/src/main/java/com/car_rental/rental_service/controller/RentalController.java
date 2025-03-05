package com.car_rental.rental_service.controller;

import com.car_rental.rental_service.client.PaymentClient;
import com.car_rental.rental_service.dto.PaymentRequestDTO;
import com.car_rental.rental_service.dto.PaymentResponseDTO;
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

    public RentalController(RentalService rentalService, PaymentClient paymentClient) {
        this.rentalService = rentalService;
        this.paymentClient = paymentClient;
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

    private final PaymentClient paymentClient;

    @PostMapping("/pay")
    public ResponseEntity<String> processPayment(@RequestBody PaymentRequestDTO paymentRequest) {
        // Handle the request
        return ResponseEntity.ok("Payment processed successfully");
    }

}
