package com.vezolve.assignment;

import com.vezolve.assignment.model.Invoice;
import com.vezolve.assignment.model.InvoiceStatus;
import com.vezolve.assignment.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.stream.IntStream;

@Component
public class InvoiceDataCommandLineRunner implements CommandLineRunner {

    @Autowired
    InvoiceRepository invoiceRepository;

    private static final String[] CUSTOMER_NAMES = {"John Smith", "David Marlon", "George Clarke", "Melissa Jones"};
    private static final BigDecimal[] GROSS_AMOUNTS = {new BigDecimal("100.12"), new BigDecimal("500"),
            new BigDecimal("12.23"), new BigDecimal("23.45"), new BigDecimal("211.12")};
    private static final BigDecimal[] DISCOUNTS = {new BigDecimal("5.21"), new BigDecimal("45"),
            new BigDecimal("1.22"), new BigDecimal("3.41"), new BigDecimal("0")};


    @Override
    public void run(String... args) {
        long currentInMillis = System.currentTimeMillis();
        IntStream.range(1, 105).forEach((index) -> {
            int nameIndex = (int) (Math.ceil(Math.random() * 100) % CUSTOMER_NAMES.length);
            int amountIndex = (int) (Math.ceil(Math.random() * 100) % GROSS_AMOUNTS.length);
            invoiceRepository.save(
                    new Invoice(
                            new Date((long) (currentInMillis - (1000 * 3600 * 24) * (Math.random() * 100))),
                            GROSS_AMOUNTS[amountIndex],
                            DISCOUNTS[amountIndex],
                            GROSS_AMOUNTS[amountIndex].subtract(DISCOUNTS[amountIndex]),
                            CUSTOMER_NAMES[nameIndex],
                            index % 2 == 1 ? InvoiceStatus.PENDING : InvoiceStatus.APPROVED));
        });
    }
}
