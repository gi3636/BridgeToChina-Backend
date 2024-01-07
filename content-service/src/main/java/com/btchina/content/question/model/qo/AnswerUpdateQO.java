package com.btchina.content.question.model.qo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AnswerUpdateQO {

    @ApiModelProperty("回答id")
    private Long id;

    @ApiModelProperty("回答内容")
    private String content;

}
