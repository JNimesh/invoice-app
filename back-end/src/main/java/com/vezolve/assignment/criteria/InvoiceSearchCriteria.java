package com.vezolve.assignment.criteria;

import com.vezolve.assignment.model.InvoiceStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvoiceSearchCriteria extends PagingAndSortingCriteria {

    private InvoiceStatus status;

    public static InvoiceSearchCriteria defaultCriteria() {
        return new InvoiceSearchCriteria();
    }
}
