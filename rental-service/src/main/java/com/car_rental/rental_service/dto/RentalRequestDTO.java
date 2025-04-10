package com.car_rental.rental_service.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class RentalRequestDTO {
    private UUID carId;
    private int rentForDays;
    // Add payment details needed for the next step
    private String cardNumber;
    private String cardHolder;
    private int cardExpirationYear;
    private int cardExpirationMonth;
    private String cardCvv;
    // Add user ID if needed
    // private UUID userId;
}