package com.vezolve.assignment.criteria;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Getter
@Setter
public class PagingAndSortingCriteria {

    private String sortBy;
    private Sort.Direction sortOrder;
    private int page = 0;
    private int pageSize = 10;

    public Pageable toPageable() {
        return this.sortBy == null || this.sortBy.isEmpty() ? PageRequest.of(this.getPage(), this.getPageSize()) :
                PageRequest.of(this.getPage(),
                        this.getPageSize(),
                        Sort.by(sortOrder == null ? Sort.Direction.ASC : sortOrder, sortBy));
    }
}
