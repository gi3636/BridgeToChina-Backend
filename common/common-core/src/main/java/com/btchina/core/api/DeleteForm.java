package com.btchina.core.api;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DeleteForm {

    @NotNull(message = "id不能为空")
    private Long id;

}
