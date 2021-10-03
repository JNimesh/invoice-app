package com.vezolve.assignment.repository;

import com.vezolve.assignment.model.Invoice;
import com.vezolve.assignment.model.InvoiceStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface InvoiceRepository extends PagingAndSortingRepository<Invoice, Long> {

    Page<Invoice> findAllByStatus(InvoiceStatus status, Pageable pageable);
}
