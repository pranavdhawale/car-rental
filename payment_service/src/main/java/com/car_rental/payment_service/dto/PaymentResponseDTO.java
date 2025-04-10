package com.car_rental.payment_service.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponseDTO {
    private boolean success;
    private String message;
    private UUID paymentId;
}
