package com.car_rental.rental_service.client;

import com.car_rental.rental_service.dto.PaymentRequestDTO;
import com.car_rental.rental_service.dto.PaymentResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "payment-service", url = "http://localhost:8082") // Payment Service URL
public interface PaymentClient {

    @PostMapping("/api/payments/process")
    PaymentResponseDTO processPayment(@RequestBody PaymentRequestDTO request);
}
