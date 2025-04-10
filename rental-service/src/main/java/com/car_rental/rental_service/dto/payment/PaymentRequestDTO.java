package com.car_rental.rental_service.dto.payment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentRequestDTO {
    // Info needed to process payment
    private String cardNumber;
    private String cardHolder;
    private int cardExpirationYear;
    private int cardExpirationMonth;
    private String cardCvv;
    private double amount;

    // Info needed potentially for Invoice generation later
    private UUID rentalId; // ID from the Rental record (generate in RentalService before calling Payment)
    private UUID carId;
    private double dailyPrice;
    private int rentedForDays;
    private String rentedAt; // Pass timestamp from RentalService

    // You might need Car details (model, brand, plate) for the invoice.
    // Option 1: Add them here (RentalService fetches from Inventory and passes through)
    // Option 2: Invoice service fetches them later using carId (adds complexity)
    // Let's go with Option 1 for simplicity here:
    private String carModelName;
    private String carBrandName;
    private String carPlate;
    private int carModelYear;


}
