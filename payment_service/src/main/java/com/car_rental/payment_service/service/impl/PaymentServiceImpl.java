package com.car_rental.payment_service.service.impl;

import com.car_rental.payment_service.dto.PaymentRequestDTO;
import com.car_rental.payment_service.dto.PaymentResponseDTO;
import com.car_rental.payment_service.entity.Payment;
import com.car_rental.payment_service.exception.PaymentNotFoundException;
import com.car_rental.payment_service.repository.PaymentRepository;
import com.car_rental.payment_service.service.PaymentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PaymentResponseDTO createPayment(PaymentRequestDTO requestDto) {
        Payment payment = modelMapper.map(requestDto, Payment.class);
        payment = paymentRepository.save(payment);
        return modelMapper.map(payment, PaymentResponseDTO.class);
    }
    public PaymentResponseDTO processPayment(PaymentRequestDTO request) {
        // Store payment details in the database
        Payment payment = new Payment();
        payment.setCardNumber(request.getCardNumber());
        payment.setCardHolder(request.getCardHolder());
        payment.setCardExpirationYear(request.getCardExpirationYear());
        payment.setCardExpirationMonth(request.getCardExpirationMonth());
        payment.setCardCvv(request.getCardCvv());
        payment.setBalance(request.getBalance());

        paymentRepository.save(payment);

        // Return response
        return new PaymentResponseDTO(payment.getId(), payment.getCardHolder(), payment.getBalance());
    }
    @Override
    public PaymentResponseDTO getPaymentById(UUID id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found with id: " + id));
        return modelMapper.map(payment, PaymentResponseDTO.class);
    }

    @Override
    public List<PaymentResponseDTO> getAllPayments() {
        return paymentRepository.findAll()
                .stream()
                .map(payment -> modelMapper.map(payment, PaymentResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deletePayment(UUID id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found with id: " + id));
        paymentRepository.delete(payment);
    }
}
