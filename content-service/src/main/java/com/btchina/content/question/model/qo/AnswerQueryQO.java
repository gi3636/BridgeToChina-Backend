package com.btchina.content.question.model.qo;

import com.btchina.core.api.PageQueryParam;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotNull;

@Data
public class AnswerQueryQO extends PageQueryParam {
    @NotNull(message = "问题id不能为空")
    @Schema(description ="问题id")
    private Long questionId;

}
