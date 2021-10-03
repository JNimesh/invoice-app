package com.vezolve.assignment.service;

import com.vezolve.assignment.criteria.InvoiceSearchCriteria;
import com.vezolve.assignment.model.Invoice;
import com.vezolve.assignment.model.InvoiceStatus;
import com.vezolve.assignment.repository.InvoiceRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class InvoiceServiceTest {

    @Mock
    private InvoiceRepository mockedInvoiceRepository;

    @Mock
    private Page<Invoice> mockedPagedInvoices;

    @Mock
    private Page<Invoice> mockedPagedInvoicesForFindByStatus;

    private InvoiceService invoiceService;

    @Before
    public void setUp() {
        invoiceService = new InvoiceService(mockedInvoiceRepository);
        when(mockedInvoiceRepository.findAll(PageRequest.of(0, 10))).thenReturn(mockedPagedInvoices);
        when(mockedInvoiceRepository.findAllByStatus(InvoiceStatus.PENDING, PageRequest.of(0, 10))).thenReturn(mockedPagedInvoicesForFindByStatus);
    }

    @Test
    public void testGetInvoices() {
        InvoiceSearchCriteria searchCriteria = new InvoiceSearchCriteria();
        assertEquals(mockedPagedInvoices, invoiceService.getInvoices(searchCriteria));
    }

    @Test
    public void testGetInvoicesWithSortByField() {
        InvoiceSearchCriteria searchCriteria = new InvoiceSearchCriteria();
        searchCriteria.setStatus(InvoiceStatus.PENDING);
        assertEquals(mockedPagedInvoicesForFindByStatus, invoiceService.getInvoices(searchCriteria));
    }

    @Test
    public void testUpdateInvoice() {
        Invoice invoice = new Invoice(
                new Date(),
                new BigDecimal("200.00"),
                new BigDecimal("23.12"),
                new BigDecimal("166.78"),
                "C1",
                InvoiceStatus.PENDING);
        invoice.setId(1L);
        Invoice expectedInvoice = new Invoice(
                new Date(),
                new BigDecimal("200.00"),
                new BigDecimal("23.12"),
                new BigDecimal("166.78"),
                "C1",
                InvoiceStatus.APPROVED);
        expectedInvoice.setId(1L);
        Optional<Invoice> invoiceOptional = Optional.of(invoice);
        when(mockedInvoiceRepository.findById(1L)).thenReturn(invoiceOptional);
        when(mockedInvoiceRepository.save(invoice)).thenReturn(expectedInvoice);
        assertEquals(expectedInvoice, invoiceService.updateInvoice(1L, expectedInvoice));
    }

    @Test(expected = EntityNotFoundException.class)
    public void testUpdateForInvalidInvoice() {
        Optional<Invoice> mockedOptional = Optional.empty();
        when(mockedInvoiceRepository.findById(1L)).thenReturn(mockedOptional);
        invoiceService.updateInvoice(1L, null);
    }
}
