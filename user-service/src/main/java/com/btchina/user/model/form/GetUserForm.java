package com.btchina.user.model.form;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotNull;

@Data
public class GetUserForm {

    @Schema(description = "用户id",required = true)
    @NotNull(message = "id不能为空")
    private Long id;
}
