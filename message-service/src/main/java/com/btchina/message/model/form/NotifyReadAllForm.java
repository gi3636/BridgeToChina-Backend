package com.btchina.message.model.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class NotifyReadAllForm {
    @ApiModelProperty(value = "多个通知的id",required = true)
    private Long[] ids;
}
