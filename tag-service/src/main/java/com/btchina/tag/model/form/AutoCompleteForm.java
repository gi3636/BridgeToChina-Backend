package com.btchina.tag.model.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.checkerframework.checker.units.qual.A;

@Data
public class AutoCompleteForm {
    @ApiModelProperty(required = true, value = "关键字")
    private String keyword;
}
