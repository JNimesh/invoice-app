package com.vezolve.assignment.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private Date created;
    @NonNull
    private BigDecimal grossAmount;
    @NonNull
    private BigDecimal discount;
    @NonNull
    private BigDecimal netAmount;
    @NonNull
    private String customerName;
    @NonNull
    private InvoiceStatus status;
}
