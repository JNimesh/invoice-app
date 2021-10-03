package com.vezolve.assignment.service;


import com.vezolve.assignment.criteria.InvoiceSearchCriteria;
import com.vezolve.assignment.model.Invoice;
import com.vezolve.assignment.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class InvoiceService {

    @Autowired
    InvoiceRepository invoiceRepository;

    public Page<Invoice> getInvoices(InvoiceSearchCriteria searchCriteria) {
        Pageable paging = searchCriteria.toPageable();

        if (searchCriteria.getStatus() != null) {
            return invoiceRepository.findAllByStatus(searchCriteria.getStatus(), paging);
        }
        return invoiceRepository.findAll(paging);
    }


    public Invoice updateInvoice(Long id, Invoice invoice) {
        Optional<Invoice> optional = invoiceRepository.findById(id);

        if (!optional.isPresent()) {
            throw new EntityNotFoundException();
        }

        Invoice invoiceToUpdate = optional.get();
        invoiceToUpdate.setDiscount(invoice.getDiscount());
        invoiceToUpdate.setStatus(invoice.getStatus());
        invoiceToUpdate.setNetAmount(invoice.getNetAmount());
        invoiceToUpdate.setGrossAmount(invoice.getGrossAmount());
        invoiceToUpdate.setCustomerName(invoice.getCustomerName());

        return invoiceRepository.save(invoiceToUpdate);
    }
}
