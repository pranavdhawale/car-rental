package com.car_rental.rental_service.service.impl;

import ch.qos.logback.classic.Logger;
import com.car_rental.rental_service.client.PaymentClient;
import com.car_rental.rental_service.controller.RentalController;
import com.car_rental.rental_service.dto.CarState;
import com.car_rental.rental_service.dto.RentalDTO;
import com.car_rental.rental_service.dto.inventory.CarDetailsDTO;
import com.car_rental.rental_service.dto.payment.PaymentRequestDTO;
import com.car_rental.rental_service.dto.payment.PaymentResponseDTO;
import com.car_rental.rental_service.entity.Rental;
import com.car_rental.rental_service.exception.ResourceNotFoundException;
import com.car_rental.rental_service.repository.RentalRepository;
import com.car_rental.rental_service.service.RentalService;
import feign.FeignException;
import org.modelmapper.ModelMapper;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.car_rental.rental_service.client.InventoryServiceClient;
import com.car_rental.rental_service.dto.RentalRequestDTO;
import com.car_rental.rental_service.dto.inventory.CarAvailabilityDTO;
import com.car_rental.rental_service.dto.inventory.UpdateCarStateDTO;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RentalServiceImpl implements RentalService {

    private final RentalRepository rentalRepository;
    private final ModelMapper modelMapper;
    private final PaymentClient paymentClient;
    private final InventoryServiceClient inventoryClient;
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(RentalServiceImpl.class);

    public RentalServiceImpl(RentalRepository rentalRepository, ModelMapper modelMapper, PaymentClient paymentClient, InventoryServiceClient inventoryClient) {
        this.rentalRepository = rentalRepository;
        this.modelMapper = modelMapper;
        this.paymentClient = paymentClient;
        this.inventoryClient = inventoryClient;
    }

    @Override
    public RentalDTO createRental(RentalDTO rentalDTO) {
        Rental rental = modelMapper.map(rentalDTO, Rental.class);
        rental = rentalRepository.save(rental);
        return modelMapper.map(rental, RentalDTO.class);
    }

    @Override
    public RentalDTO getRentalById(UUID id) {
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rental not found with id: " + id));
        return modelMapper.map(rental, RentalDTO.class);
    }

    @Override
    public List<RentalDTO> getAllRentals() {
        return rentalRepository.findAll()
                .stream()
                .map(rental -> modelMapper.map(rental, RentalDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public RentalDTO updateRental(UUID id, RentalDTO rentalDTO) {
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rental not found with id: " + id));

        rental.setDailyPrice(rentalDTO.getDailyPrice());
        rental.setTotalPrice(rentalDTO.getTotalPrice());
        rental.setRentedForDays(rentalDTO.getRentedForDays());
        rental.setRentedAt(rentalDTO.getRentedAt());

        rental = rentalRepository.save(rental);
        return modelMapper.map(rental, RentalDTO.class);
    }

    @Override
    public void deleteRental(UUID id) {
        if (!rentalRepository.existsById(id)) {
            throw new ResourceNotFoundException("Rental not found with id: " + id);
        }
        rentalRepository.deleteById(id);
    }

//    public String rentCar(Rental rental, PaymentRequestDTO paymentRequestDTO) {
//        // Step 1: Process Payment
//        ResponseEntity<String> response = paymentClient.processPayment(paymentRequestDTO);
//
//        if (response.getStatusCode().is2xxSuccessful()) {
//            // Step 2: Save rental details if payment is successful
//            rentalRepository.save(rental);
//            return "Car rented successfully!";
//        } else {
//            return "Payment failed!";
//        }
//    }

//    public String rentCar(Rental rental, PaymentRequestDTO paymentRequestDTO) {
//        // Step 0: Check car exists & is available
//        CarResponseDTO car = inventoryClient.getCarById(rental.getCarId());
//        if (car.getState() != CarState.AVAILABLE) {
//            return "Car is not available for rent.";
//        }
//
//        // Step 1: Payment
//        ResponseEntity<String> response = paymentClient.processPayment(paymentRequestDTO);
//        if (!response.getStatusCode().is2xxSuccessful()) {
//            return "Payment failed!";
//        }
//
//        // Step 2: Save rental & update car state
//        rentalRepository.save(rental);
//        inventoryClient.updateCarState(rental.getCarId(), CarState.RENTED);
//        return "Car rented successfully!";
//    }

//    @Transactional // Manages transaction for saving Rental entity locally
//    public Rental rentCar(RentalRequestDTO request) {
//        log.info("Attempting to rent car ID: {}", request.getCarId());
//        UUID carId = request.getCarId();
//
//        // 1. Check Car Availability via Inventory Service
//        CarAvailabilityDTO availability;
//        try {
//            ResponseEntity<CarAvailabilityDTO> response = inventoryClient.checkCarAvailability(request.getCarId());
//
//            if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
//                log.error("Failed to get availability info from Inventory Service. Status: {}", response.getStatusCode());
//                throw new RuntimeException("Could not check car availability.");
//            }
//            availability = response.getBody();
//
//            if (!availability.isAvailable()) {
//                log.warn("Car {} is not available.", request.getCarId());
//                throw new RuntimeException("Car is not available for rent."); // Or a custom exception
//            }
//            log.info("Car {} is available. Daily price: {}", request.getCarId(), availability.getDailyPrice());
//
//        } catch (Exception e) { // Catch FeignException or other network issues
//            log.error("Error calling Inventory Service for availability check", e);
//            throw new RuntimeException("Error communicating with Inventory Service: " + e.getMessage());
//        }
//
//        // 2. Calculate Total Price
//        double dailyPrice = availability.getDailyPrice();
//        double totalPrice = dailyPrice * request.getRentForDays();
//
//        // 3. Call Payment Service (Implementation in Step 2)
//        // boolean paymentSuccessful = callPaymentService(request, totalPrice);
//        // For now, assume payment is successful AFTER checking availability
//        boolean paymentSuccessful = true; // *** TEMPORARY *** Replace in Step 2
//
//        if (!paymentSuccessful) {
//            log.error("Payment failed for rental request of car {}", request.getCarId());
//            throw new RuntimeException("Payment failed."); // Or custom exception
//        }
//        log.info("Payment successful (simulated) for car {}", request.getCarId());
//
//
//        // 4. Update Car State in Inventory Service (ONLY IF PAYMENT WAS SUCCESSFUL)
//        try {
//            UpdateCarStateDTO updateDto = new UpdateCarStateDTO(CarState.RENTED);
//            ResponseEntity<Void> updateResponse = inventoryClient.updateCarState(request.getCarId(), updateDto);
//
//            if (updateResponse.getStatusCode() != HttpStatus.OK) {
//                // This is problematic: Payment succeeded, but inventory update failed.
//                // Requires compensation logic (e.g., Saga pattern) in a real system.
//                // For now, log a critical error.
//                log.error("CRITICAL: Payment succeeded but failed to update car {} state to RENTED. Status: {}", request.getCarId(), updateResponse.getStatusCode());
//                // Optionally throw an exception, but the rental record might still be saved below
//                // depending on transaction boundaries. Consider manual intervention/alerting.
//                throw new RuntimeException("Failed to update car state after successful payment.");
//            }
//            log.info("Successfully updated car {} state to RENTED", request.getCarId());
//        } catch (Exception e) {
//            log.error("CRITICAL: Error calling Inventory Service to update car state after payment", e);
//            // Again, requires compensation.
//            throw new RuntimeException("Error communicating with Inventory Service for state update: " + e.getMessage());
//        }
//
//
//        // 5. Create and Save Rental Record Locally
//        Rental rental = new Rental();
//        rental.setCarId(request.getCarId());
//        rental.setDailyPrice(dailyPrice);
//        rental.setTotalPrice(totalPrice);
//        rental.setRentedForDays(request.getRentForDays());
//        rental.setRentedAt(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
//
//        Rental savedRental = rentalRepository.save(rental);
//        log.info("Rental record created successfully with ID: {}", savedRental.getId());
//
//        return savedRental;
//    }


    @Override // Add if implementing an interface
    @Transactional // Manages transaction for saving Rental entity locally and rolls back on RuntimeExceptions
    public Rental rentCar(RentalRequestDTO request) {
        log.info("Attempting to rent car ID: {}", request.getCarId());
        UUID carId = request.getCarId();

        // --------------------------------------------------------------------
        // Step 1a: Check Car Availability via Inventory Service
        // --------------------------------------------------------------------
        CarAvailabilityDTO availability;
        try {
            log.debug("Calling Inventory Service to check availability for carId: {}", carId);
            ResponseEntity<CarAvailabilityDTO> response = inventoryClient.checkCarAvailability(carId);

            if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
                log.error("Failed to get availability info from Inventory Service. Status: {}", response.getStatusCode());
                // Handle non-OK status specifically if needed (e.g., 404 might mean car doesn't exist)
                throw new RuntimeException("Could not check car availability (Inventory Service returned non-OK status).");
            }
            availability = response.getBody();

            if (!availability.isAvailable()) {
                log.warn("Car {} is not available for rent.", carId);
                throw new RuntimeException("Car is not available for rent."); // Or a custom exception
            }
            log.info("Car {} is available. Daily price confirmed: {}", carId, availability.getDailyPrice());

        } catch (FeignException e) { // Catch Feign specific exceptions for network/client errors
            log.error("FeignException calling Inventory Service for availability check. Status: {}, Content: {}", e.status(), e.contentUTF8(), e);
            throw new RuntimeException("Error communicating with Inventory Service (availability check): " + e.getMessage());
        } catch (Exception e) { // Catch other unexpected exceptions
            log.error("Unexpected error calling Inventory Service for availability check", e);
            throw new RuntimeException("Unexpected error checking car availability: " + e.getMessage());
        }


        // --------------------------------------------------------------------
        // Step 1b: Fetch Full Car Details for Payment/Invoice
        // --------------------------------------------------------------------
        CarDetailsDTO carDetails;
        try {
            log.debug("Calling Inventory Service to get details for carId: {}", carId);
            ResponseEntity<CarDetailsDTO> detailsResponse = inventoryClient.getCarDetails(carId);

            if (detailsResponse.getStatusCode() != HttpStatus.OK || detailsResponse.getBody() == null) {
                log.error("Failed to get car details from Inventory Service for carId: {}. Status: {}", carId, detailsResponse.getStatusCode());
                throw new RuntimeException("Could not fetch car details (Inventory Service returned non-OK status).");
            }
            carDetails = detailsResponse.getBody();
            log.info("Successfully fetched details for car {}: Model={}, Brand={}, Plate={}",
                    carId, carDetails.getModelName(), carDetails.getBrandName(), carDetails.getPlate());

        } catch (FeignException e) {
            log.error("FeignException calling Inventory Service for car details. Status: {}, Content: {}", e.status(), e.contentUTF8(), e);
            throw new RuntimeException("Error communicating with Inventory Service (fetch details): " + e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error calling Inventory Service for car details", e);
            throw new RuntimeException("Unexpected error fetching car details: " + e.getMessage());
        }


        // --------------------------------------------------------------------
        // Step 2: Prepare for Payment
        // --------------------------------------------------------------------
        double dailyPrice = carDetails.getDailyPrice(); // Use price from fetched details
        double totalPrice = dailyPrice * request.getRentForDays();
        String rentalTimestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME); // Consistent timestamp
        UUID rentalAttemptId = UUID.randomUUID(); // Generate Rental ID before calling payment
        log.info("Calculated total price: {} for {} days. Generated rentalAttemptId: {}", totalPrice, request.getRentForDays(), rentalAttemptId);


        // --------------------------------------------------------------------
        // Step 3: Call Payment Service
        // --------------------------------------------------------------------
        // boolean paymentSuccessful = false; // Not needed if we throw exceptions on failure
        UUID paymentId = null;
        try {
            // Build the request DTO for payment service
            PaymentRequestDTO paymentDto = PaymentRequestDTO.builder()
                    .cardNumber(request.getCardNumber())
                    .cardHolder(request.getCardHolder())
                    .cardExpirationMonth(request.getCardExpirationMonth())
                    .cardExpirationYear(request.getCardExpirationYear())
                    .cardCvv(request.getCardCvv()) // Payment service should handle this securely
                    .amount(totalPrice)
                    .rentalId(rentalAttemptId) // Pass the generated ID
                    .carId(carId)
                    .dailyPrice(dailyPrice)
                    .rentedForDays(request.getRentForDays())
                    .rentedAt(rentalTimestamp) // Pass consistent timestamp as String
                    .carModelName(carDetails.getModelName())
                    .carBrandName(carDetails.getBrandName())
                    .carPlate(carDetails.getPlate())
                    .carModelYear(carDetails.getModelYear())
                    .build();

            log.info("Calling Payment Service for rentalId: {}", rentalAttemptId);
            ResponseEntity<PaymentResponseDTO> paymentResponse = paymentClient.processPayment(paymentDto);

            // Check response from Payment Service
            if (paymentResponse.getStatusCode() == HttpStatus.OK && paymentResponse.getBody() != null && paymentResponse.getBody().isSuccess()) {
                // Payment successful = true implicitly by not throwing exception
                paymentId = paymentResponse.getBody().getPaymentId(); // Get the payment ID
                log.info("Payment successful for rentalId: {}. Payment ID received: {}", rentalAttemptId, paymentId);
            } else {
                // Payment failed or communication issue resulted in non-success response
                String failureMessage = "Payment failed";
                if(paymentResponse.getBody() != null && paymentResponse.getBody().getMessage() != null) {
                    failureMessage = paymentResponse.getBody().getMessage();
                }
                log.error("Payment failed according to Payment Service response. Status: {}, Reason: {}", paymentResponse.getStatusCode(), failureMessage);
                throw new RuntimeException("Payment failed: " + failureMessage); // Throw exception to stop process and rollback transaction
            }

        } catch (FeignException e) { // Catch Feign specific errors
            log.error("FeignException calling Payment Service. Status: {}, Content: {}", e.status(), e.contentUTF8(), e);
            throw new RuntimeException("Error communicating with Payment Service: " + e.getMessage());
        } catch (Exception e) { // Catch other unexpected errors like RuntimeException from above block
            log.error("Error during payment processing call", e);
            // Check if it's the specific exception we threw for payment failure
            if (e.getMessage() != null && e.getMessage().startsWith("Payment failed:")) {
                throw e; // Re-throw the specific payment failure exception
            }
            throw new RuntimeException("Unexpected error during payment call: " + e.getMessage());
        }

        // --------------------------------------------------------------------
        // Step 4: Update Car State in Inventory Service (Only reached if payment succeeded)
        // --------------------------------------------------------------------
        try {
            UpdateCarStateDTO updateDto = new UpdateCarStateDTO(CarState.RENTED);
            log.info("Calling Inventory Service to update car {} state to {}", carId, CarState.RENTED);
            ResponseEntity<Void> updateResponse = inventoryClient.updateCarState(carId, updateDto);

            if (!updateResponse.getStatusCode().is2xxSuccessful()) { // Check for any 2xx success status
                // CRITICAL: Payment succeeded, but inventory update failed.
                // Requires compensation logic (e.g., Saga pattern) or manual intervention.
                log.error("CRITICAL: Payment succeeded but failed to update car {} state to RENTED. Inventory Service Status: {}", carId, updateResponse.getStatusCode());
                // Throwing an exception here will roll back the saving of the *Rental* record locally,
                // which might be desired, but the payment is already done and inventory is wrong.
                throw new RuntimeException("CRITICAL: Failed to update car state after successful payment.");
            }
            log.info("Successfully updated car {} state to RENTED via Inventory Service", carId);

        } catch (FeignException e) {
            // CRITICAL: Payment succeeded, but communication error updating inventory.
            log.error("CRITICAL: FeignException calling Inventory Service to update car state after payment. Status: {}, Content: {}", e.status(), e.contentUTF8(), e);
            throw new RuntimeException("CRITICAL: Error communicating with Inventory Service for state update: " + e.getMessage());
        } catch (Exception e) {
            // Check if it's the specific exception we threw for critical failure
            if (e.getMessage() != null && e.getMessage().startsWith("CRITICAL:")) {
                throw e; // Re-throw the specific critical failure exception
            }
            log.error("CRITICAL: Unexpected error calling Inventory Service to update car state after payment", e);
            throw new RuntimeException("CRITICAL: Unexpected error updating car state: " + e.getMessage());
        }



        // --------------------------------------------------------------------
        // Step 5: Create and Save Rental Record Locally (Only reached if all above steps succeeded)
        // --------------------------------------------------------------------
        Rental rental = new Rental();
        // rental.setId(rentalAttemptId); // <-- *** ENSURE THIS LINE IS REMOVED OR COMMENTED OUT ***
        rental.setCarId(carId);
        rental.setDailyPrice(dailyPrice);
        rental.setTotalPrice(totalPrice);
        rental.setRentedForDays(request.getRentForDays());
        rental.setRentedAt(rentalTimestamp);

        // Optional: Set paymentId if you added the field
        // if (paymentId != null) {
        //    rental.setPaymentId(paymentId);
        // }

        Rental savedRental;
        try {
            // Save the new Rental entity; ID will be generated by JPA/Hibernate
            savedRental = rentalRepository.save(rental);
            // Log the actual generated ID AFTER saving
            log.info("Rental record saved successfully to local DB with generated ID: {}", savedRental.getId());
        } catch(Exception e) {
            log.error("CRITICAL: Failed to save final Rental record locally! Error: {}", e.getMessage(), e);
            throw new RuntimeException("CRITICAL: Failed to save rental record locally. Please check logs. " + e.getMessage());
        }

        return savedRental;
    }

}

