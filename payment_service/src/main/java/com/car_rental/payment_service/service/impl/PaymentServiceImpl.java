package com.car_rental.payment_service.service.impl;

import com.car_rental.payment_service.dto.PaymentRequestDTO;
import com.car_rental.payment_service.dto.PaymentResponseDTO;
import com.car_rental.payment_service.entity.Payment;
import com.car_rental.payment_service.exception.PaymentNotFoundException;
import com.car_rental.payment_service.repository.PaymentRepository;
import com.car_rental.payment_service.service.PaymentService;
import org.modelmapper.ModelMapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ModelMapper modelMapper;
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(PaymentServiceImpl.class);

    @Override
    public PaymentResponseDTO createPayment(PaymentRequestDTO requestDto) {
        Payment payment = modelMapper.map(requestDto, Payment.class);
        payment = paymentRepository.save(payment);
        return modelMapper.map(payment, PaymentResponseDTO.class);
    }
//    public PaymentResponseDTO processPayment(PaymentRequestDTO request) {
//        // Store payment details in the database
//        Payment payment = new Payment();
//        payment.setCardNumber(request.getCardNumber());
//        payment.setCardHolder(request.getCardHolder());
//        payment.setCardExpirationYear(request.getCardExpirationYear());
//        payment.setCardExpirationMonth(request.getCardExpirationMonth());
//        payment.setCardCvv(request.getCardCvv());
//        payment.setBalance(request.getBalance());
//
//        paymentRepository.save(payment);
//
//        // Return response
//        return new PaymentResponseDTO(payment.getId(), payment.getCardHolder(), payment.getBalance());
//    }


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


    private void callInvoiceService(Payment savedPayment, PaymentRequestDTO originalRequest) {
        log.warn("Invoice service call not implemented yet.");
        // Implementation in Step 3
    }


    @Transactional // For saving Payment entity
    public PaymentResponseDTO processPayment(PaymentRequestDTO request) {
        log.info("Processing payment for amount: {}", request.getAmount());

        // ** SIMULATE PAYMENT PROCESSING **
        // In a real app:
        // 1. Validate card details (Luhn check, expiry date)
        // 2. Communicate with a real Payment Gateway (Stripe, PayPal, etc.)
        // 3. Handle success/failure responses from the gateway.
        boolean paymentGatewaySuccess = simulatePaymentGateway(request); // Replace with real logic

        if (paymentGatewaySuccess) {
            log.info("Payment gateway approved payment for card ending in {}", request.getCardNumber().substring(request.getCardNumber().length() - 4));
            // Payment successful, save payment record
            Payment payment = new Payment();
            payment.setCardNumber("**** **** **** " + request.getCardNumber().substring(request.getCardNumber().length() - 4)); // Mask card number
            payment.setCardHolder(request.getCardHolder());
            payment.setCardExpirationMonth(request.getCardExpirationMonth());
            payment.setCardExpirationYear(request.getCardExpirationYear());
            payment.setCardCvv("***"); // Don't store CVV
            payment.setBalance(request.getAmount()); // Assuming 'balance' means the transaction amount here

            Payment savedPayment = paymentRepository.save(payment);
            log.info("Payment record saved with ID: {}", savedPayment.getId());

            // Call Invoice Service (Implementation in Step 3)
            // callInvoiceService(savedPayment, request);

            return new PaymentResponseDTO(true, "Payment successful", savedPayment.getId());
        } else {
            log.warn("Payment gateway declined payment for card ending in {}", request.getCardNumber().substring(request.getCardNumber().length() - 4));
            return new PaymentResponseDTO(false, "Payment declined by gateway", null);
        }
    }

//     Dummy simulation - replace with actual gateway interaction
    private boolean simulatePaymentGateway(PaymentRequestDTO request) {
        // Basic validation example
        if (request.getCardNumber() == null || request.getCardNumber().length() < 13 || request.getAmount() <= 0) {
            return false;
        }
        // Simulate success unless card number ends with "0000"
        return !request.getCardNumber().endsWith("0000");
    }
//
//    // Helper method to simulate gateway interaction (keep as is)
//    private boolean simulatePaymentGateway(PaymentRequestDTO request) {
//        // Basic validation example
//        if (request.getCardNumber() == null || request.getCardNumber().length() < 13 || request.getAmount() <= 0) {
//            log.warn("Payment simulation failed due to invalid input data.");
//            return false;
//        }
//        // Simulate success unless card number ends with "0000"
//        boolean success = !request.getCardNumber().endsWith("0000");
//        log.info("Payment gateway simulation result: {}", success ? "Success" : "Declined");
//        return success;
//    }
//
//    // Helper method to safely get last 4 digits (useful for logging/DTO)
//    private String getCardLast4(String cardNumber) {
//        if (cardNumber != null && cardNumber.length() > 4) {
//            return cardNumber.substring(cardNumber.length() - 4);
//        }
//        return "****";
//    }
//
//    @Override // Add if implementing an interface
//    @Transactional // Keep for saving Payment entity
//    public PaymentResponseDTO processPayment(PaymentRequestDTO request) {
//        log.info("Processing payment for amount: {}", request.getAmount());
//
//        // ** SIMULATE PAYMENT PROCESSING **
//        boolean paymentGatewaySuccess = simulatePaymentGateway(request);
//
//        if (paymentGatewaySuccess) {
//            log.info("Payment gateway approved payment for card ending in {}", getCardLast4(request.getCardNumber()));
//
//            // Payment successful, save payment record
//            Payment payment = new Payment();
//            payment.setCardNumber("**** **** **** " + getCardLast4(request.getCardNumber())); // Mask card number
//            payment.setCardHolder(request.getCardHolder());
//            payment.setCardExpirationMonth(request.getCardExpirationMonth());
//            payment.setCardExpirationYear(request.getCardExpirationYear());
//            payment.setCardCvv("***"); // Don't store CVV
//            payment.setBalance(request.getAmount());
//
//            Payment savedPayment;
//            try {
//                savedPayment = paymentRepository.save(payment);
//                log.info("Payment record saved with ID: {}", savedPayment.getId());
//            } catch (Exception e) {
//                log.error("Failed to save payment record after successful authorization: {}", e.getMessage(), e);
//                // Even if gateway succeeded, if DB save fails, we must report failure.
//                return new PaymentResponseDTO(false, "Failed to save payment record.", null);
//            }
//
//
//            // --- Start: Integrated Invoice Service Call Logic ---
//            try {
//                // You don't necessarily need 'last4' variable here if not logging it,
//                // but buildInvoiceRequest helper would need it, or build inline.
//
//                // Parse the rentedAt string into LocalDateTime
//                LocalDateTime rentedAtDateTime = null;
//                try {
//                    if (request.getRentedAt() != null) { // Check for null before parsing
//                        rentedAtDateTime = LocalDateTime.parse(request.getRentedAt(), DateTimeFormatter.ISO_DATE_TIME);
//                    } else {
//                        log.warn("rentedAt timestamp was null in PaymentRequestDTO.");
//                    }
//                } catch (DateTimeParseException e) { // Catch only relevant exception
//                    log.error("Failed to parse rentedAt timestamp string: {}", request.getRentedAt(), e);
//                    // Decide how to handle - maybe default to now(), or proceed with null?
//                    // Proceeding with null. Invoice service might need to handle it.
//                }
//
//                // Build the DTO for the invoice service call
//                InvoiceRequestDTO invoiceDto = InvoiceRequestDTO.builder()
//                        .paymentId(savedPayment.getId())
//                        .cardNumberLast4(getCardLast4(request.getCardNumber())) // Use helper
//                        .cardHolder(request.getCardHolder())
//                        .amountPaid(request.getAmount())
//                        .rentalId(request.getRentalId()) // Make sure this is passed in PaymentRequestDTO
//                        .rentedForDays(request.getRentedForDays())
//                        .rentedAt(rentedAtDateTime) // Pass LocalDateTime object
//                        .carId(request.getCarId())
//                        .modelName(request.getCarModelName())
//                        .brandName(request.getCarBrandName())
//                        .plate(request.getCarPlate())
//                        .modelYear(request.getCarModelYear())
//                        .dailyPrice(request.getDailyPrice())
//                        .totalPrice(request.getAmount())
//                        .build();
//
//                log.info("Calling Invoice Service for payment ID: {}", savedPayment.getId());
//                // Make the actual call using the injected Feign client
//                ResponseEntity<Void> invoiceResponse = invoiceServiceClient.generateInvoice(invoiceDto);
//
//                // Check if invoice generation was successful (e.g., 201 Created or other 2xx)
//                // Using is2xxSuccessful() is more flexible than just checking for CREATED
//                if (invoiceResponse.getStatusCode().is2xxSuccessful()) {
//                    log.info("Invoice Service call successful for payment ID: {}", savedPayment.getId());
//                } else {
//                    // Invoice generation failed AFTER payment succeeded. Log appropriately.
//                    log.warn("Invoice Service call failed for payment ID: {}. Status: {}", savedPayment.getId(), invoiceResponse.getStatusCode());
//                    // Do NOT fail the entire payment process here.
//                }
//
//            } catch (FeignException e) { // Catch Feign specific errors
//                // Log error, but don't fail the payment response because payment succeeded.
//                log.error("FeignException calling Invoice Service for payment ID: {}. Status: {}, Content: {}",
//                        savedPayment.getId(), e.status(), e.contentUTF8(), e);
//                // Add monitoring/alerting for this situation.
//            } catch (Exception e) { // Catch other potential errors during invoice DTO build or call
//                log.error("Unexpected error calling Invoice Service for payment ID: {}", savedPayment.getId(), e);
//                // Add monitoring/alerting for this situation.
//            }
//            // --- End: Integrated Invoice Service Call Logic ---
//
//
//            // Return success because the payment itself was saved
//            return new PaymentResponseDTO(true, "Payment successful", savedPayment.getId());
//
//        } else {
//            // Payment gateway failed
//            log.warn("Payment gateway declined payment for card ending in {}", getCardLast4(request.getCardNumber()));
//            return new PaymentResponseDTO(false, "Payment declined by gateway", null);
//        }
//    }
}
