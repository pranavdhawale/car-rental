package com.car_rental.rental_service.controller;

import com.car_rental.rental_service.client.PaymentClient;
import com.car_rental.rental_service.dto.payment.PaymentRequestDTO;
import com.car_rental.rental_service.dto.RentalDTO;
import com.car_rental.rental_service.dto.RentalRequestDTO;
import com.car_rental.rental_service.entity.Rental;
import com.car_rental.rental_service.service.RentalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {

    private final RentalService rentalService;
    private static final Logger log = LoggerFactory.getLogger(RentalController.class);

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

    @PostMapping("/rent")
    public ResponseEntity<Rental> rentCar(@RequestBody RentalRequestDTO rentalRequest) {
        log.info("Received rental request for carId: {}", rentalRequest.getCarId());

        // Delegate the complex logic (inventory check, payment, inventory update, save rental)
        // to the RentalService. The service method will throw exceptions on failure.
        Rental createdRental = rentalService.rentCar(rentalRequest);

        log.info("Successfully processed rental request for carId: {}. Rental ID: {}",
                rentalRequest.getCarId(), createdRental.getId());

        // Return 201 Created status with the newly created Rental object in the body
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRental);
    }
}
