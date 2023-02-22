package com.btchina.core.api;

import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class PageResult<T> {

    private Integer currentPage;
    private Integer pageSize;
    private Integer totalPage;
    private Long total;
    private List<T> list;

}
