package com.btchina.model.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AutoCompleteForm {
    @ApiModelProperty(required = true, value = "关键字")
    private String keyword;
}
