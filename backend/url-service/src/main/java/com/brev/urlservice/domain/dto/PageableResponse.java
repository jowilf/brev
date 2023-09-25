package com.brev.urlservice.domain.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@Builder
public class PageableResponse<T> {
    private List<T> data;
    private int currentPage;
    private long totalItems;
    private int totalPages;

    public static <T> PageableResponse<T> fromPage(Page<T> pageableData) {
        return new PageableResponseBuilder<T>().data(pageableData.getContent()).currentPage(pageableData.getNumber())
                .totalItems(pageableData.getTotalElements()).totalPages(pageableData.getTotalPages()).build();
    }
}
