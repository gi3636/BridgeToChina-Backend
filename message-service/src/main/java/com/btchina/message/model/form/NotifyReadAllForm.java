package com.btchina.message.model.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class NotifyReadAllForm {
    @ApiModelProperty(value = "用户ID",required = true)
    Long userId;
}
