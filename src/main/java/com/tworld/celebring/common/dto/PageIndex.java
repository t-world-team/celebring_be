package com.tworld.celebring.common.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class PageIndex {
    private Long totalElements;
    private int pageNumber;
    private int pageSize;
    private List<?> contents;

    @Builder
    public PageIndex(Long totalElements, int pageNumber, int pageSize, List<?> contents) {
        this.totalElements = totalElements;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.contents = contents;

    }
}
