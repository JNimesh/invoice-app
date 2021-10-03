package com.vezolve.assignment.controller;

import com.vezolve.assignment.criteria.InvoiceSearchCriteria;
import com.vezolve.assignment.model.Invoice;
import com.vezolve.assignment.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {


    @Autowired
    private InvoiceService invoiceService;

    @GetMapping
    @CrossOrigin
    public Page<Invoice> getInvoices(InvoiceSearchCriteria searchCriteria) {
        return invoiceService.getInvoices(searchCriteria == null ?
                InvoiceSearchCriteria.defaultCriteria() : searchCriteria);
    }

    @PutMapping("/{id}")
    @CrossOrigin
    public ResponseEntity<Invoice> updateInvoice(@PathVariable Long id, @RequestBody Invoice invoice) {
        try {
            return new ResponseEntity<>(invoiceService.updateInvoice(id, invoice), HttpStatus.OK);
        } catch (EntityNotFoundException ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
