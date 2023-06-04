package com.tworld.celebring.common.dto;

import lombok.Data;

import java.util.List;

@Data
public class PageIndex {
    private Long totalElements;
    private int pageNumber;
    private int pageSize;
    private List<?> contents;

    public static List<?> getPage(List<?> list, int pageNumber, int pageSize) {
        int totalElements = list.size();
        int totalPages = (int) Math.ceil((double) totalElements/pageSize);

        int fromIndex = pageNumber * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, totalElements);

        return list.subList(fromIndex, toIndex);
    }
}
