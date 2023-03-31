package com.btchina.content.model.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class QueryQuestionTagForm {
    @ApiModelProperty("问题id或者用户id")
    private List<Long> ids;
}
