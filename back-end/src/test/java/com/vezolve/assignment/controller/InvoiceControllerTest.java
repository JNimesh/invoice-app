package com.vezolve.assignment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vezolve.assignment.model.Invoice;
import com.vezolve.assignment.model.InvoiceStatus;
import com.vezolve.assignment.repository.InvoiceRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class InvoiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;


    private Map<Long, Invoice> testData;


    @Autowired
    private InvoiceRepository invoiceRepository;


    @Before
    public void setUp() {
        invoiceRepository.deleteAll();
        testData = populateTestData();
    }


    @Test
    public void testGetAllInvoices() throws Exception {
        invoiceRepository.saveAll(testData.values());
        mockMvc.perform(get("/invoices"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void testGetAllPendingInvoices() throws Exception {

        invoiceRepository.saveAll(testData.values());
        mockMvc.perform(get("/invoices?status=PENDING"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements", is(1)))
                .andDo(print());
    }


    @Test
    public void testInvoiceUpdate() throws Exception {

        Invoice invoice = testData.get(1L);
        invoice = invoiceRepository.save(invoice);

        invoice.setDiscount(BigDecimal.valueOf(150.50));
        String modifiedInvoiceStr = mapper.writeValueAsString(invoice);

        mockMvc.perform(put("/invoices/" + invoice.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(modifiedInvoiceStr))
                .andExpect(status().isOk());

        Optional<Invoice> optional = invoiceRepository.findById(invoice.getId());

        assertTrue(optional.isPresent());
        assertEquals(optional.get().getDiscount().doubleValue(), 150.50, 0.0001);

    }

    @Test
    public void testUpdateForInvalidInvoice() throws Exception {

        mockMvc.perform(put("/invoices/100")
                .contentType(MediaType.APPLICATION_JSON)
                .content(""))
                .andExpect(status().isBadRequest());
    }

    private Map<Long, Invoice> populateTestData() {
        Map<Long, Invoice> invoiceMap = new HashMap<>();

        Invoice invoice = new Invoice();
        invoice.setCreated(new Date());
        invoice.setId(1L);
        invoice.setCustomerName("C1");
        invoice.setDiscount(BigDecimal.valueOf(50.0));
        invoice.setGrossAmount(BigDecimal.valueOf(500.50));
        invoice.setNetAmount(BigDecimal.valueOf(500.50));
        invoice.setStatus(InvoiceStatus.PENDING);
        invoiceMap.put(invoice.getId(), invoice);


        invoice = new Invoice();
        invoice.setCreated(new Date());
        invoice.setId(2L);
        invoice.setCustomerName("C2");
        invoice.setDiscount(BigDecimal.valueOf(660.0));
        invoice.setGrossAmount(BigDecimal.valueOf(6500.60));
        invoice.setNetAmount(BigDecimal.valueOf(600.60));
        invoice.setStatus(InvoiceStatus.APPROVED);
        invoiceMap.put(invoice.getId(), invoice);


        return invoiceMap;
    }


}
