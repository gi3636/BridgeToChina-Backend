package com.btchina.core.api;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;


@Data
@ToString
@Tag(name = "分页查询参数对象")
public class PageQueryParam {

    @NotNull(message = "每页数量不能为空")
    @Parameter(name = "每页数量", required = true, example = "10")
    private Integer pageSize = 10;

    @Min(value = 1, message = "页码最小为1")
    @NotNull(message = "页码不能为空")
    @Parameter(name = "当前页码", required = true, example = "1")
    private Integer currentPage = 1;

}
