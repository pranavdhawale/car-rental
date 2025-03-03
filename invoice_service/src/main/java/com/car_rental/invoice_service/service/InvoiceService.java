package com.car_rental.invoice_service.service;

import com.car_rental.invoice_service.entity.Invoice;

import java.util.List;
import java.util.UUID;

public interface InvoiceService {
    Invoice createInvoice(Invoice invoice);
    Invoice getInvoiceById(UUID id);
    List<Invoice> getAllInvoices();
    void deleteInvoice(UUID id);
}
