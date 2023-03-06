package com.btchina.core.api;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


@Data
@ToString
@ApiModel(description = "分页查询参数对象")
public class PageQueryParam {

    @NotNull(message = "每页数量不能为空")
    @ApiModelProperty(value = "每页数量", required = true, example = "10")
    private Integer pageSize = 10;

    @Min(value = 1, message = "页码最小为1")
    @NotNull(message = "页码不能为空")
    @ApiModelProperty(value = "当前页码", required = true, example = "1")
    private Integer currentPage = 1;

}
