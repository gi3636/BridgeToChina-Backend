package com.btchina.content.question.feign.qo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class QuestionEditTagQO {
    @ApiModelProperty("问题id或者用户id")
    private Long id;

    @ApiModelProperty("标签内容")
    private List<String> tags;

}
