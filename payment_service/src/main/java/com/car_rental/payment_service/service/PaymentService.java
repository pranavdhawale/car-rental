package com.car_rental.payment_service.service;

import com.car_rental.payment_service.dto.PaymentRequestDTO;
import com.car_rental.payment_service.dto.PaymentResponseDTO;

import java.util.List;
import java.util.UUID;

public interface PaymentService {
    PaymentResponseDTO createPayment(PaymentRequestDTO requestDto);
    PaymentResponseDTO getPaymentById(UUID id);
    List<PaymentResponseDTO> getAllPayments();
    void deletePayment(UUID id);

    PaymentResponseDTO processPayment(PaymentRequestDTO request);
}
