package com.btchina.core.api;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Data;

import jakarta.validation.constraints.NotNull;

@Data
public class DeleteForm {

    @NotNull(message = "id不能为空")
    @Parameter(name = "id", required = true)
    private Long id;

}
