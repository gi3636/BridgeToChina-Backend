package com.btchina.content.question.model.qo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class AnswerAddQO {

    @NotNull(message = "问题id不能为空")
    @ApiModelProperty("问题id")
    private Long questionId;

    @NotBlank(message = "回答内容不能为空")
    @ApiModelProperty("回答内容")
    private String content;
}
