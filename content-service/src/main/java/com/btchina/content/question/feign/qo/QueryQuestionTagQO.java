package com.btchina.content.question.feign.qo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class QueryQuestionTagQO {
    @ApiModelProperty("问题id或者用户id")
    private List<Long> ids;
}
