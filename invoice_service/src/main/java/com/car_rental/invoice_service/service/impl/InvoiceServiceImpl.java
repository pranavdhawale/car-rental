package com.car_rental.invoice_service.service.impl;

import com.car_rental.invoice_service.entity.Invoice;
import com.car_rental.invoice_service.repository.InvoiceRepository;
import com.car_rental.invoice_service.service.InvoiceService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    public Invoice createInvoice(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    @Override
    public Invoice getInvoiceById(UUID id) {
        Optional<Invoice> invoice = invoiceRepository.findById(id);
        return invoice.orElse(null);
    }

    @Override
    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    @Override
    public void deleteInvoice(UUID id) {
        invoiceRepository.deleteById(id);
    }
}
