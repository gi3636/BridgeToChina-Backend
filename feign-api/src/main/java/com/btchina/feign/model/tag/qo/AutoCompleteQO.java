package com.btchina.feign.model.tag.qo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AutoCompleteQO {
    @ApiModelProperty(required = true, value = "关键字")
    private String keyword;
}
