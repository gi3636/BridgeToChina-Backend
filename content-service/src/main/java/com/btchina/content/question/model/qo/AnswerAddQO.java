package com.btchina.content.question.model.qo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
public class AnswerAddQO {

    @NotNull(message = "问题id不能为空")
    @Schema(description ="问题id")
    private Long questionId;

    @NotBlank(message = "回答内容不能为空")
    @Schema(description ="回答内容")
    private String content;
}
