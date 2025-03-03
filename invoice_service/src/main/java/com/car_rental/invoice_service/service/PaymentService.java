package com.car_rental.invoice_service.service;

import com.car_rental.invoice_service.entity.Payment;

import java.util.List;
import java.util.UUID;

public interface PaymentService {
    Payment createPayment(Payment payment);
    Payment getPaymentById(UUID id);
    List<Payment> getAllPayments();
    void deletePayment(UUID id);
}
