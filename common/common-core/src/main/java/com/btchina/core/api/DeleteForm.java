package com.btchina.core.api;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DeleteForm {

    @NotNull(message = "id不能为空")
    @ApiModelProperty(value = "id", required = true)
    private Long id;

}
