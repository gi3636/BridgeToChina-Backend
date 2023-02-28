package com.btchina.question.model.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(description = "问题设置最佳答案")
public class QuestionSetAnswerForm {

    @NotNull(message = "问题id不能为空")
    @ApiModelProperty("问题id")
    private Long questionId;
    @NotNull(message = "回答id不能为空")
    @ApiModelProperty("回答id")
    private Long answerId;
}
