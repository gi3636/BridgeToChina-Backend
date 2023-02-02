package com.btchina.core.api;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


@Data
@ApiModel(description = "分页查询参数对象")
public class PageQueryParam {

    @NotNull(message = "每页数量不能为空")
    @ApiModelProperty(value = "每页数量")
    private Integer size = 10;

    @Min(value = 1, message = "页码最小为1")
    @NotNull(message = "页码不能为空")
    @ApiModelProperty(value = "当前页码")
    private Integer currentPage = 1;

}
