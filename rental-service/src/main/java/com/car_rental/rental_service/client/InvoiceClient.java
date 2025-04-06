package com.car_rental.rental_service.client;


import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name="invoice-service", path = "/api/invoices")
public interface InvoiceClient {
}
