package com.car_rental.rental_service.client;

import com.car_rental.rental_service.dto.payment.PaymentRequestDTO;
import com.car_rental.rental_service.dto.payment.PaymentResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "payment-service", url = "http://localhost:8081/api/payments")
public interface PaymentClient {

    @PostMapping("/process")
    ResponseEntity<PaymentResponseDTO> processPayment(@RequestBody PaymentRequestDTO paymentRequestDTO);
}
