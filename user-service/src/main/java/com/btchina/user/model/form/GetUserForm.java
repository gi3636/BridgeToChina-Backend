package com.btchina.user.model.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class GetUserForm {

    @ApiModelProperty(value = "用户id",required = true)
    @NotNull(message = "id不能为空")
    private Long id;
}
